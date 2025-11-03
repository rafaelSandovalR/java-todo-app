# Java To-Do List Application

A complete desktop To-Do list application built with Java Swing. This project demonstrates core Java principles, Object-Oriented design, GUI development, and persistent data storage.

## Key Features

* **Full CRUD Functionality:** Users can **C**reate, **R**ead, **U**pdate, and **D**elete tasks.
* **Data Persistence:** The task list is automatically saved to a `.txt` file in the user's home directory on close and reloaded on startup (`FileWriter`/`BufferedReader`).
* **Dynamic UI:** A clean and responsive interface built with Swing components (`JList`, `JScrollPane`, `JOptionPane`).
* **Advanced Event Handling:**
    * Supports multi-selection (`Ctrl`/`Shift` click) for deleting multiple items.
    * The "Edit Task" button is context-aware and only enables when a single task is selected (`ListSelectionListener`).
    * The "Enter" key is bound to the task field for fast input.

## How to Run the Application

The easiest way to run the app is to download the latest compiled version.

1.  Go to the **[Releases Page](https://github.com/YourUsername/java-todo-app/releases)**.
2.  Download the `.zip` file for the latest release (e.g., `v1.0`).
3.  Unzip the folder and follow the instructions in the `README.TXT` file (or just double-click `start.vbs`).

## How to Build from Source

1.  Clone this repository.
2.  Open the project in an IDE like IntelliJ IDEA.
3.  Build the project to resolve dependencies.
4.  Run the `Main.java` class to start the application.