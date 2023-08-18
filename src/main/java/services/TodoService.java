package services;

import com.google.gson.Gson;
import dto.Todo;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TodoService {

    private String BASE_URL;
    private final String TODO_ENDPOINT = "/todos";
    private Gson gson = new Gson();
    private HttpClient httpClient = HttpClient.newHttpClient();

    public TodoService(){
        this.loadAPIProperties();
    }
    public void createTodo(Todo todo) throws Exception {

        // convert Todo object to JSON data type
        String requestBody = gson.toJson(todo);

        // create http request using HttpRequest class
        HttpRequest request = HttpRequest.newBuilder()
                // configure the request (type, payload, headers, etc.)
                .uri(new URI(this.BASE_URL + this.TODO_ENDPOINT)) // specify the address to send the request
                .timeout(Duration.ofSeconds(30)) // how long it should wait for response from the API before the request fails
                .header("Content-Type", "application/json") // the type of data we are sending in the request additional configurations could also be added here like cookies
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // the type of request (get, post, put, etc) we are making and the data to send if data is required
                .build(); // the final copy of the request is compiled based on configuration above and ready for sending

        // send the request
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString()); // the request is sent and response body converted into string

        // check the response and response code for success or failure
        if (response.statusCode() != 201) { // we can check the status code to see if it was successful
            throw new Exception("Request failed with status code of: " + response.statusCode());
        }

        // extract the todo item from the response
        Todo createdTodoItem = gson.fromJson(response.body(), Todo.class); // we try to extract the response json / body from the response object

        // check if the object was properly created
        if (createdTodoItem == null || createdTodoItem.get_id() == null) {
            throw new Exception("Failed to create todo item with code: " + response.statusCode());
        }

        // throw exception if some issues with creating the object - above (throw Exception)
        // success message if everything is ok - in todoController
    }

    private void loadAPIProperties() {
        try {
            PropertiesConfiguration propertiesConfiguration = new PropertiesConfiguration();
            propertiesConfiguration.load("application.properties");
            this.BASE_URL = propertiesConfiguration.getString("api.url");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<Todo> getAllTodoItems() {
        return new ArrayList<>();
    }
}
