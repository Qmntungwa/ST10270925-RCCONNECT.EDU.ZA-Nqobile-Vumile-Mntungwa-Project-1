package com.mycompany.st10270925_nqobile_vumile_mntungwa_final_POE_Part3;

public class TaskManager {
    String[] developers;
    String[] taskNames;
    String[] taskDescriptions;
    String[] taskStatuses;
    String[] taskIDs;
    int[] taskDurations;
    int taskCount = 0;

    public TaskManager(int maxTasks) {
        developers = new String[maxTasks];
        taskNames = new String[maxTasks];
        taskDescriptions = new String[maxTasks];
        taskStatuses = new String[maxTasks];
        taskIDs = new String[maxTasks];
        taskDurations = new int[maxTasks];
    }

    public void addTask(String developer, String taskName, String description, int duration, String status) {
        if (!checkTaskDescription(description)) {
            throw new IllegalArgumentException("Please enter a task description of less than 50 characters.");
        }

        developers[taskCount] = developer;
        taskNames[taskCount] = taskName;
        taskDescriptions[taskCount] = description;
        taskDurations[taskCount] = duration;
        taskStatuses[taskCount] = status;
        taskIDs[taskCount] = createTaskID(taskName, taskCount, developer);
        taskCount++;
    }

    public boolean checkTaskDescription(String description) {
        return description.length() <= 50;
    }

    public String createTaskID(String taskName, int taskNumber, String developer) {
        String taskInitials = taskName.length() >= 2 ? taskName.substring(0, 2).toUpperCase() : taskName.toUpperCase();
        String devSuffix = developer.length() >= 3 ? developer.substring(developer.length() - 3).toUpperCase() : developer.toUpperCase();
        return taskInitials + ":" + taskNumber + ":" + devSuffix;
    }

    public String showFullReport() {
        if (taskCount == 0) return "No tasks to display.";

        StringBuilder report = new StringBuilder("Full Task Report:\n");
        for (int i = 0; i < taskCount; i++) {
            report.append("Task ID: ").append(taskIDs[i]).append("\n")
                    .append("Task Name: ").append(taskNames[i]).append("\n")
                    .append("Developer: ").append(developers[i]).append("\n")
                    .append("Description: ").append(taskDescriptions[i]).append("\n")
                    .append("Duration: ").append(taskDurations[i]).append(" hours\n")
                    .append("Status: ").append(taskStatuses[i]).append("\n\n");
        }
        return report.toString();
    }

    public String getLongestTask() {
        if (taskCount == 0) return "No tasks available.";

        int longestIndex = 0;
        for (int i = 1; i < taskCount; i++) {
            if (taskDurations[i] > taskDurations[longestIndex]) {
                longestIndex = i;
            }
        }

        return "Developer: " + developers[longestIndex] + "\n" +
                "Task Name: " + taskNames[longestIndex] + "\n" +
                "Duration: " + taskDurations[longestIndex] + " hours";
    }

    public String searchTaskByName(String name) {
        for (int i = 0; i < taskCount; i++) {
            if (taskNames[i].equalsIgnoreCase(name)) {
                return "Task Name: " + taskNames[i] + "\n" +
                        "Developer: " + developers[i] + "\n" +
                        "Status: " + taskStatuses[i];
            }
        }
        return "Task not found.";
    }

    public String searchTasksByDeveloper(String developerName) {
        StringBuilder tasks = new StringBuilder("Tasks assigned to " + developerName + ":\n");
        for (int i = 0; i < taskCount; i++) {
            if (developers[i].equalsIgnoreCase(developerName)) {
                tasks.append("Task Name: ").append(taskNames[i]).append("\n")
                        .append("Status: ").append(taskStatuses[i]).append("\n\n");
            }
        }
        return tasks.length() > 0 ? tasks.toString() : "No tasks assigned to " + developerName + ".";
    }

    public void deleteTask(String taskName) {
        for (int i = 0; i < taskCount; i++) {
            if (taskNames[i].equalsIgnoreCase(taskName)) {
                for (int j = i; j < taskCount - 1; j++) {
                    developers[j] = developers[j + 1];
                    taskNames[j] = taskNames[j + 1];
                    taskDescriptions[j] = taskDescriptions[j + 1];
                    taskStatuses[j] = taskStatuses[j + 1];
                    taskIDs[j] = taskIDs[j + 1];
                    taskDurations[j] = taskDurations[j + 1];
                }
                taskCount--;
                return;
            }
        }
    }

    public int returnTotalHours() {
        int totalHours = 0;
        for (int i = 0; i < taskCount; i++) {
            totalHours += taskDurations[i];
        }
        return totalHours;
    }

    public String printTaskDetails(int taskIndex) {
        return """
                Task Status: %s
                Developer Details: %s
                Task Number: %d
                Task Name: %s
                Task Description: %s
                Task ID: %s
                Duration: %d hours
                """.formatted(taskStatuses[taskIndex], developers[taskIndex], taskIndex, taskNames[taskIndex], taskDescriptions[taskIndex], taskIDs[taskIndex], taskDurations[taskIndex]);
    }

    public int getTaskCount() {
        return taskCount;
    }

    public String getTasksWithStatusDone() {
        StringBuilder doneTasks = new StringBuilder("Tasks with Status 'Done':\n");
        for (int i = 0; i < taskCount; i++) {
            if ("Done".equalsIgnoreCase(taskStatuses[i])) {
                doneTasks.append("Task Name: ").append(taskNames[i]).append("\n")
                        .append("Developer: ").append(developers[i]).append("\n")
                        .append("Duration: ").append(taskDurations[i]).append(" hours\n\n");
            }
        }
        return doneTasks.length() > 25 ? doneTasks.toString() : "No tasks with status 'Done'.";
    }
}