package controllers;

import dto.Priority;
import dto.Todo;
import dto.TodoStatus;
import services.TodoService;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class TodoItemController {
    private final TodoService todoService = new TodoService();
    public void addTodo() {
        try {
            Todo todo = this.collectTodoInfo();
            // and then we will send it to some API using http request
            this.todoService.createTodo(todo);
            this.displayMessage("Todo item created successfully");
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }

    private void displayMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private Todo collectTodoInfo() {
        Todo todo = new Todo();
        todo.setDescription(this.getUserInput("What would you like to do?"));
        String selectedStatus = (String) this.getUserInputFromDropdown(
                Arrays.stream(TodoStatus.values()).map(TodoStatus::name).toArray(),
                "Choose Status",
                "What is the current state of this task?"
        );
        todo.setStatus(TodoStatus.valueOf(selectedStatus));
        //String[] priorityListAsString = Arrays.stream(Priority.values()).map(Priority::name).toArray();
        //https://commons.apache.org/proper/commons-lang/javadocs/api-3.9/org/apache/commons/lang3/EnumUtils.html
        //String[] priorityListAsString = EnumUtils.getEnumList(Priority.class).toArray(); // this is the same as above
        String selectedPriority = (String) this.getUserInputFromDropdown( // use single method to ask user dropdown selection in multiple places
                Arrays.stream(Priority.values()).map(Priority::name).toArray(), // convert enum to list then to array of string
                "Choose Priority",
                "How important is this item?"
        );
        todo.setPriority(Priority.valueOf(selectedPriority)); // covert string to enum
        todo.setDueDate(this.getUserInput("When will you complete this item?"));
        return todo;
    }

    private String getUserInput(String message) {
        return JOptionPane.showInputDialog(message);
    }

    private Object getUserInputFromDropdown(Object[] dropDownOptions, String title, String message){
        return JOptionPane.showInputDialog(
                null,
                message,
                title,
                JOptionPane.QUESTION_MESSAGE,
                null,
                dropDownOptions, // the list of items user will choose from
                dropDownOptions[0] // first item in the array can be used as default option
        );
    }

    public void viewAllTodo() {
        try {
            StringBuilder todoItemsAsString = new StringBuilder();

            for (Todo todo: this.todoService.getAllTodoItems()) {
                todoItemsAsString.append(todo.toString());
            }

            this.displayMessage(todoItemsAsString.toString());
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }
    public void viewTodo() {
        try {
            List<Todo> existingTodoItem = this.todoService.getAllTodoItems();
           Todo selectTodo = (Todo) this.getUserInputFromDropdown(
                    existingTodoItem.toArray(),
                    "View item",
                    "Choose the item to view"
            );

           Todo todo = this.todoService.getTodoItem(selectTodo.get_id());

           this.displayMessage(
                   new StringBuilder()
                       .append("Description: \t").append(todo.getDescription()).append("\n")
                       .append("Due Date: \t").append(todo.getDueDate()).append("\n")
                       .append("Status: \t").append(todo.getStatus()).append("\n")
                       .append("Priority: \t").append(todo.getPriority()).append("\n")
                       .append("ID: \t").append(todo.get_id()).append("\n")
                       .toString()
           );
        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }
    public void removeTodo() {
        try {
            List<Todo> existingTodoItem = this.todoService.getAllTodoItems();
            Todo selectTodo = (Todo) this.getUserInputFromDropdown(
                    existingTodoItem.toArray(),
                    "Delete item",
                    "Choose the item to delete"
            );

            this.todoService.deleteTodoItem(selectTodo.get_id());

            this.displayMessage("Todo item was deleted successfully");

        } catch (Exception exception) {
            this.displayMessage(exception.getMessage());
        }
    }
    public void updateTodo() {
      try {
          List<Todo> existingTodoItem = this.todoService.getAllTodoItems();
          Todo selectTodo = (Todo) this.getUserInputFromDropdown(
                  existingTodoItem.toArray(),
                  "Update item",
                  "Choose the item to update"
          );
          Todo todo = this.collectTodoInfo();
          this.todoService.updateTodoItem(todo, selectTodo.get_id());
          this.displayMessage("Todo item updated successfully");
      } catch (Exception e) {
          this.displayMessage(e.getMessage());
      }
    }
}
