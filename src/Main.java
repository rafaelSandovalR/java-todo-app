import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class Main {
    private final JFrame frame;
    private final JList<String> taskList;
    private final DefaultListModel<String> listModel;
    private final JTextField taskField;
    private static final String TASK_FILE_NAME = System.getProperty("user.home") + "/todo_app_tasks.txt";

    public Main(){
        Font FONT_LARGE = new Font("Arial", Font.PLAIN, 18);
        Font FONT_MEDIUM = new Font("Arial", Font.PLAIN, 16);

        // The Scrollable List
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(FONT_LARGE);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // Buttons
        JButton addButton = new JButton("Add Task");
        JButton removeButton = new JButton("Remove Task");
        JButton editButton = new JButton("Edit Task");

        addButton.addActionListener(event -> addTask());
        removeButton.addActionListener(event -> removeTask());
        editButton.addActionListener(event -> editTask());

        // Only enable edit button if single task is selected
        editButton.setEnabled(false);
        taskList.addListSelectionListener(event -> editButton.setEnabled(taskList.getSelectedIndices().length == 1));

        // Top Buttons
        JPanel topButtonPanel = new JPanel(new BorderLayout(5,5));
        topButtonPanel.add(removeButton, BorderLayout.EAST);

        // Lower buttons
        JPanel lowerButtonPanel = new JPanel(new GridLayout(2,1,5,5));
        lowerButtonPanel.add(addButton);
        lowerButtonPanel.add(editButton);

        // The Input Panel: Text field and edit/add buttons
        JPanel inputPanel = new JPanel(new BorderLayout(5,5));
        taskField = new JTextField();
        taskField.setFont(FONT_MEDIUM);
        taskField.addActionListener(event -> addTask());

        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(lowerButtonPanel, BorderLayout.EAST);

        // The Main Window: Put it together
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(topButtonPanel, BorderLayout.NORTH);
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
        loadTasks(); // Load any saved tasks before window is made visible
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                saveTasks();
            }
        });
    }
    private void editTask(){
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex == -1) return;

        String currentTask = taskList.getSelectedValue();
        // Create popup to allow user edits
        String newTask = (String) JOptionPane.showInputDialog(
                frame,
                "Edit your task:",
                "Edit task",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                currentTask
        );

        // If user made a non-empty change, save to model
        if (newTask != null && !newTask.trim().isEmpty()){
            listModel.setElementAt(newTask, selectedIndex);
        }
    }
    private void loadTasks(){
        try(BufferedReader reader = new BufferedReader(new FileReader(TASK_FILE_NAME))){
            String line;
            while ((line = reader.readLine()) != null){ // Ignore blank lines
                if (!line.trim().isEmpty()) listModel.addElement(line);
            }
        } catch (FileNotFoundException e){
            // First time the user is running the app
        } catch (IOException e){
            JOptionPane.showMessageDialog(
                    frame,
                    "Error: Could not load tasks from file.\n" + e.getMessage(),
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void saveTasks(){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TASK_FILE_NAME))){
            for (int i = 0; i < listModel.getSize(); i++){
                String task = listModel.getElementAt(i);
                writer.write(task);
                writer.newLine();
            }
        } catch (IOException e){
            JOptionPane.showMessageDialog(
                    frame,
                    "Error: Could not save tasks to file.\n" + e.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    private void removeTask(){
        int[] indices =  taskList.getSelectedIndices();
        if (indices.length == 0) return;

        // Remove items from the highest index to the lowest
        for (int i = indices.length-1; i >= 0 ; i--){
            listModel.removeElementAt(indices[i]);
        }
        // Index for new selection
        int firstRemovedIdx = indices[0];

        if (listModel.isEmpty()) return;

        if (firstRemovedIdx < listModel.getSize()) taskList.setSelectedIndex(firstRemovedIdx); // Select item that is now at original starting position
        else taskList.setSelectedIndex(listModel.size()-1); // If we deleted from the end, select the new last item
    }
    private void addTask(){
        if (!taskField.getText().isEmpty()){
            listModel.addElement(taskField.getText());
            taskField.setText("");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            Main app = new Main();
            app.frame.setVisible(true);
            app.taskField.requestFocusInWindow();
        });
    }
}
