import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private final JFrame frame;
    private final JList<String> taskList;
    private final DefaultListModel<String> listModel;
    private final JTextField taskField;
    private final JButton addButton, removeButton;
    private static final String TASK_FILE_NAME = System.getProperty("user.home") + "/todo_app_tasks.txt";

    public Main(){
        Font FONT_LARGE = new Font("Arial", Font.PLAIN, 18);
        Font FONT_MEDIUM = new Font("Arial", Font.PLAIN, 16);

        // The List: Scrollable
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(FONT_LARGE);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // The Input Panel: Text field and button
        JPanel inputPanel = new JPanel(new BorderLayout(5,5));
        taskField = new JTextField();
        taskField.setFont(FONT_MEDIUM);
        addButton = new JButton("Add Task");
        removeButton = new JButton("Remove Task");

        addButton.addActionListener(event -> addTask());
        taskField.addActionListener(event -> addTask());
        removeButton.addActionListener(event -> removeTask());

        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        inputPanel.add(removeButton,BorderLayout.WEST);

        // The Main Window: Put it together
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                saveTasks();
            }
        });
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
        int idx =  taskList.getSelectedIndex();
        if (idx < 0) return;

        listModel.removeElementAt(idx);
        if (idx < listModel.getSize()) taskList.setSelectedIndex(idx); // Select the next item
        else if (!listModel.isEmpty()) taskList.setSelectedIndex(idx-1); // Select the new last item
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
