package com.mycompany.st10270925_nqobile_vumile_mntungwa_assignment_one;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

/**
 * KanbanApp is the main class for the EasyKanban application.
 * It handles user interactions, displays a task management menu, and manages task creation.
 */
public class KanbanApp {

    // Variable to keep track of total hours spent on tasks
    private static int totalHours = 0;

    // List to store all tasks created in the application
    private static final List<Task> taskList = new ArrayList<>();

    // Instance of Login class for user management
    private static Login user;

    public static void main(String[] args) {
        // Start the application by displaying the registration menu
        displayRegistrationMenu();
        
        // After successful registration, show the main menu
        displayMainMenu();
    }

    /**
     * Displays the registration menu and processes user registration.
     * This method prompts the user to enter their first name, last name, username,
     * and password. It validates the input and allows the user to register.
     */
    private static void displayRegistrationMenu() {
        boolean registered = false; // Flag to track registration status
        
        // Prompt user for first name
        String firstname = JOptionPane.showInputDialog("Enter First Name:");
        if (firstname == null) return; // Exit if dialog is closed

        // Prompt user for last name
        String lastname = JOptionPane.showInputDialog("Enter Last Name:");
        if (lastname == null) return; // Exit if dialog is closed

        // Loop until the user is successfully registered
        while (!registered) {
            // Prompt user to create a username
            String username = JOptionPane.showInputDialog("Create Username (max 5 characters with '_'):");
            if (username == null) return; // Exit if dialog is closed

            // Prompt user to create a password with specific criteria
            String password = JOptionPane.showInputDialog("Create Password (at least 8 characters with uppercase, number, and special character):");
            if (password == null) return; // Exit if dialog is closed

            // Create a new Login object with provided details
            user = new Login(password, firstname, lastname, username);
            // Attempt to register the user
            String registrationStatus = user.registerUser();

            // Show registration status to the user
            JOptionPane.showMessageDialog(null, registrationStatus);

            // Check if registration was successful
            if (registrationStatus.contains("successfully")) {
                registered = true; // Set flag to true, exit loop
            } else {
                // Prompt user to try again if registration failed
                JOptionPane.showMessageDialog(null, "Please try again to meet the registration requirements.");
            }
        }

        // Prompt for login after successful registration
        loginUser();
    }

    /**
     * Displays the login menu and processes user login.
     * This method allows registered users to log in by entering their username and password.
     */
    private static void loginUser() {
        boolean loggedIn = false; // Flag to track login status
        // Loop until the user successfully logs in
        while (!loggedIn) {
            // Prompt user for username
            String username = JOptionPane.showInputDialog("Enter Username:");
            if (username == null) return; // Exit if dialog is closed

            // Prompt user for password
            String password = JOptionPane.showInputDialog("Enter Password:");
            if (password == null) return; // Exit if dialog is closed

            // Validate user login credentials
            boolean loginStatus = user.LoginUser(username, password);
            // Get login status message
            String loginMessage = user.returnLoginStatus(loginStatus);
            // Show login status to the user
            JOptionPane.showMessageDialog(null, loginMessage);

            // If login is successful, exit loop and continue to main menu
            if (loginStatus) {
                loggedIn = true;
            } else {
                // Prompt user to retry if login failed
                JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
            }
        }
    }

    /**
     * Displays the main menu and processes user choices.
     * This method presents the user with options to input a task, show all tasks, or quit the application.
     */
    private static void displayMainMenu() {
        boolean exit = false; // Flag to track exit status
        // Loop until the user chooses to exit the application
        while (!exit) {
            // Prompt user for their choice
            String choice = JOptionPane.showInputDialog(
                    "Welcome to EasyKanban\nChoose an option:\n1. Input Task\n2. Show All Tasks\n3. Quit");
            if (choice == null) {
                exit = true; // Exit if dialog is closed
                continue;
            }

            int option;
            try {
                // Parse user input to integer
                option = Integer.parseInt(choice);
            } catch (NumberFormatException e) {
                // Show error message if input is not a valid number
                JOptionPane.showMessageDialog(null, "Invalid input. Please select a valid option.");
                continue;
            }

            // Handle user choice using switch statement
            switch (option) {
                case 1 -> addTask(); // Call method to add a new task
                case 2 -> JOptionPane.showMessageDialog(null, showAllTasks()); // Show all tasks
                case 3 -> {
                    JOptionPane.showMessageDialog(null, "Exiting EasyKanban. Goodbye!"); // Exit message
                    exit = true; // Set exit flag to true
                }
                default -> // Show error message for invalid choices
                        JOptionPane.showMessageDialog(null, "Invalid choice. Please select a valid option.");
            }
        }
    }

    /**
     * Prompts the user to input task details and adds the task if valid.
     * This method collects task information such as name, description, developer details,
     * duration, and status, then creates a Task object and adds it to the task list.
     */
    private static void addTask() {
        // Prompt user for task name
        String taskName = JOptionPane.showInputDialog("Enter Task Name:");
        if (taskName == null) return; // Exit if dialog is closed

        // Prompt user for task description with validation for length
        String taskDescription = JOptionPane.showInputDialog("Enter Task Description (max 50 chars):");
        while (taskDescription == null || taskDescription.length() > 50) {
            if (taskDescription == null) return; // Exit if dialog is closed
            // Show error message if description exceeds length limit
            JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
            taskDescription = JOptionPane.showInputDialog("Enter Task Description (max 50 chars):");
        }

        // Prompt user for developer details
        String developerDetails = JOptionPane.showInputDialog("Enter Developer Details (First Last):");
        if (developerDetails == null) return; // Exit if dialog is closed

        int taskDuration; // Variable to store task duration
        // Loop until valid duration is entered
        while (true) {
            // Prompt user for task duration
            String durationInput = JOptionPane.showInputDialog("Enter Task Duration (in hours):");
            if (durationInput == null) return; // Exit if dialog is closed
            try {
                // Parse input to integer
                taskDuration = Integer.parseInt(durationInput);
                break; // Exit loop if parsing is successful
            } catch (NumberFormatException e) {
                // Show error message if input is not a valid number
                JOptionPane.showMessageDialog(null, "Invalid input for task duration. Please enter a number.");
            }
        }

        // Prompt user to select task status from predefined options
        String[] options = {"To Do", "Doing", "Done"};
        String taskStatus = (String) JOptionPane.showInputDialog(null,
                "Select Task Status:", "Task Status", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        // Create a new Task object with the provided details
        Task task = new Task(taskName, taskDescription, developerDetails, taskDuration, taskStatus);
        // Validate task description before adding to the task list
        if (task.checkTaskDescription()) {
            totalHours += task.getTaskDuration(); // Update total hours with new task duration
            taskList.add(task); // Add task to the list
            // Show task details to the user
            JOptionPane.showMessageDialog(null, task.printTaskDetails());
        } else {
            // Show error message if description is invalid
            JOptionPane.showMessageDialog(null, "Task description is too long.");
        }
    }

    /**
     * Shows details of all tasks in the list.
     * @return A formatted string containing details of all tasks.
     */
    private static String showAllTasks() {
        // Check if the task list is empty
        if (taskList.isEmpty()) {
            return "No tasks available."; // Return message if no tasks exist
        }

        // StringBuilder to accumulate task details
        StringBuilder allTasks = new StringBuilder("All Tasks:\n");
        // Loop through each task in the list
        for (Task task : taskList) {
            allTasks.append(task.printTaskDetails()).append("\n\n"); // Append task details
        }
        return allTasks.toString(); // Return formatted string of all tasks
    }
}