import javax.swing.*;
import java.awt.*;

public class Main {
    private final JFrame frame;
    private final JList<String> taskList;
    private final DefaultListModel<String> listModel;
    private final JTextField taskField;
    private final JButton addButton;

    public Main(){
        Font FONT_LARGE = new Font("Arial", Font.PLAIN, 18);
        Font FONT_MEDIUM = new Font("Arial", Font.PLAIN, 16);

        // 1. The List: Scrollable
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);
        taskList.setFont(FONT_LARGE);
        JScrollPane scrollPane = new JScrollPane(taskList);

        // 2. The Input Panel: Text field and button
        JPanel inputPanel = new JPanel(new BorderLayout(5,5));
        taskField = new JTextField();
        taskField.setFont(FONT_MEDIUM);

        addButton = new JButton("Add Task");

        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);

        // 3. The Main Window: Put it together
        frame = new JFrame("To-Do List");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,500);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->{
            Main app = new Main();
            app.frame.setVisible(true);
        });
    }
}
