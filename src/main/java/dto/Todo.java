package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data // getter, setter, toString
public class Todo {
    private String _id;
    private String description;
    private String dueDate;
    private Priority priority;
    private TodoStatus status;

    @Override
    public String toString() {
        return dueDate + ": " + description + " - " + priority + " - " + status;
    }
}
