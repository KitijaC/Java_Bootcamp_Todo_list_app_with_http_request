package controllers;

import javax.swing.*;

public class TodoMenuController {

    private final TodoItemController todoItemController = new TodoItemController();
    public void start(){
        String userChoice = JOptionPane.showInputDialog(this.getMenuItems());
        this.handleUserChoice(userChoice);
        this.start();
    }

    private void handleUserChoice(String userChoice) {
        switch (userChoice) {
            case "1":
                this.todoItemController.addTodo();
                break;
            case "2":
                this.todoItemController.viewAllTodo();
                break;
            case "3":
                this.todoItemController.viewTodo();
                break;
            case "4":
                this.todoItemController.removeTodo();
                break;
            case "5":
                this.todoItemController.updateTodo();
                break;
            case "6":
                System.exit(0);
                break;
            default:
                JOptionPane.showInputDialog(null, "Please choose an option from te list");
        }
    }

    private String getMenuItems() {
        return """
                Welcome to Todo application
                1. Add todo item
                2. Display todo list
                3. View Single item
                4. Remove todo item
                5. Update todo item
                6. Exit
                """;
    }
}
