import javax.swing.*;

/**
 * This class allows us to set up the frame in our program
 */
public class MainFrame {
    private JFrame frame;

    public MainFrame() {
        frame = new JFrame("Chocolate Database"); // Set the title of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close operation
        frame.setSize(400, 400); // Set the size of the frame
    }

    public void addButtonList(JPanel buttonListPanel) {
        frame.add(buttonListPanel); // Add the button list panel to the frame
    }

    public void display() {
        frame.setVisible(true); // Make the frame visible
    }
}

