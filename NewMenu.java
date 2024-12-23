import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NewMenu extends JFrame {
    private JPanel panel;
    private JButton displayButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton moreOptionsButton;
    private JButton dropTableButton;
    private JButton searchButton;
    private JTable table;
    private DefaultTableModel tableModel;

    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 550;

    public NewMenu() {
        setTitle("Chocolate Database");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildPanel();
        add(panel);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setVisible(true);
        // Create the table initially
        createTable();
    }

    private void buildPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Header
        JLabel title = new JLabel("Chocolate Database");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        // Image
        ImageIcon chocolateIcon = new ImageIcon("C:\\Users\\dylan\\Downloads\\—Pngtree—unwrapped chocolate bar_5262340.png");
        Image scaledImage = chocolateIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        JLabel chocolateImageLabel = new JLabel(new ImageIcon(scaledImage));
        chocolateImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(chocolateImageLabel);

        // Names
        JLabel smallHeaderLabel = new JLabel("Dylan, Georgia, Carl, Gabe");
        smallHeaderLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        smallHeaderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(smallHeaderLabel);

        // Create a panel for the buttons with BoxLayout centered horizontally
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //spacing on the top
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Buttons
        displayButton = new JButton("Display Original Chocolates");
        displayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        displayButton.addActionListener(new DisplayButtonListener());
        panel.add(displayButton);

        // Add spacing
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        updateButton = new JButton("Update Chocolates");
        updateButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        updateButton.addActionListener(new UpdateButtonListener());
        panel.add(updateButton);

        // Add spacing
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        deleteButton = new JButton("Delete Chocolates");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.addActionListener(new DeleteButtonListener());
        panel.add(deleteButton);

        // Add spacing
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        moreOptionsButton = new JButton("More Options");
        moreOptionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        moreOptionsButton.addActionListener(new MoreOptionsButtonListener());
        panel.add(moreOptionsButton);

        // Add spacing
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        dropTableButton = new JButton("Drop Table");
        dropTableButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        dropTableButton.addActionListener(new DropTableButtonListener());
        panel.add(dropTableButton);

        // Add spacing
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Add search button
        searchButton = new JButton("Search with SQL");
        searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        searchButton.addActionListener(new SearchButtonListener());
        panel.add(searchButton);

        // Add spacing at the bottom
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void createTable() {
        // Create an empty table model
        tableModel = new DefaultTableModel();

        // Create table with the empty model
        table = new JTable(tableModel);

        // Initially, the table is not editable
        table.setEnabled(false);
    }

    public JPanel getPanel() {
        return panel;
    }

    private class DisplayButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                // Create an instance of ShowChocolateData to fetch the data
                ShowChocolateData dataFetcher = new ShowChocolateData();

                // Fetch the data from the database
                tableModel = dataFetcher.fetchChocolates();

                // Set the fetched data to the table model
                table.setModel(tableModel);

                // Display the table in a dialog
                JOptionPane.showMessageDialog(null, new JScrollPane(table));
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class UpdateButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Check if a table exists
            if (table == null) {
                JOptionPane.showMessageDialog(null, "No table available to edit.");
                return;
            }

            // Enable editing for the table
            table.setEnabled(true);

            // Notify the user to edit the table
            int option = JOptionPane.showConfirmDialog(null, new JScrollPane(table), "Edit Table", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                // Apply changes to the table model
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    for (int col = 0; col < tableModel.getColumnCount(); col++) {
                        Object value = table.getValueAt(row, col);
                        tableModel.setValueAt(value, row, col);
                    }
                }

                // Refresh the table to reflect changes
                table.setModel(tableModel);
                JOptionPane.showMessageDialog(null, new JScrollPane(table), "Updated Table", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Table editing cancelled.");
            }

            // After editing is complete, disable editing
            table.setEnabled(false);
        }
    }

    private class DeleteButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Check if a table exists
            if (table == null || table.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(null, "No row selected for deletion.");
                return;
            }

            // Get the index of the selected row
            int selectedRow = table.getSelectedRow();

            // Get the confirmation from the user
            int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                // Remove the selected row from the table model
                tableModel.removeRow(selectedRow);

                JOptionPane.showMessageDialog(null, "Row deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Deletion cancelled.");
            }
        }
    }

    private class MoreOptionsButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Create a dialog for selecting columns
            JDialog optionsDialog = new JDialog();
            optionsDialog.setTitle("Select Columns and Aggregation Function");
            optionsDialog.setLayout(new BorderLayout());

            // Create checkboxes for each column
            JPanel checkBoxPanel = new JPanel(new GridLayout(tableModel.getColumnCount(), 1));
            List<JCheckBox> checkBoxes = new ArrayList<>();
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                JCheckBox checkBox = new JCheckBox(tableModel.getColumnName(i));
                checkBoxes.add(checkBox);
                checkBoxPanel.add(checkBox);
            }

            // Create radio buttons for aggregation functions
            JPanel radioPanel = new JPanel(new GridLayout(5, 1));
            JRadioButton minButton = new JRadioButton("Min");
            JRadioButton maxButton = new JRadioButton("Max");
            JRadioButton countButton = new JRadioButton("Count");
            JRadioButton avgButton = new JRadioButton("Average");
            JRadioButton sumButton = new JRadioButton("Sum");

            // Group the radio buttons so that only one can be selected at a time
            ButtonGroup group = new ButtonGroup();
            group.add(minButton);
            group.add(maxButton);
            group.add(countButton);
            group.add(avgButton);
            group.add(sumButton);

            radioPanel.add(minButton);
            radioPanel.add(maxButton);
            radioPanel.add(countButton);
            radioPanel.add(avgButton);
            radioPanel.add(sumButton);

            optionsDialog.add(checkBoxPanel, BorderLayout.WEST);
            optionsDialog.add(radioPanel, BorderLayout.CENTER);

            // Create OK button
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Get selected columns
                    List<String> selectedColumns = new ArrayList<>();
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            selectedColumns.add(checkBox.getText());
                        }
                    }
                    // Get selected aggregation function
                    String selectedFunction = null;
                    if (minButton.isSelected()) {
                        selectedFunction = "Min";
                    } else if (maxButton.isSelected()) {
                        selectedFunction = "Max";
                    } else if (countButton.isSelected()) {
                        selectedFunction = "Count";
                    } else if (avgButton.isSelected()) {
                        selectedFunction = "Average";
                    } else if (sumButton.isSelected()) {
                        selectedFunction = "Sum";
                    }
                    // Close dialog and perform aggregation
                    optionsDialog.dispose();
                    if (selectedColumns.isEmpty() || selectedFunction == null) {
                        JOptionPane.showMessageDialog(null, "No columns selected or aggregation function chosen.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        new AggregateFunctionListener(selectedFunction, selectedColumns).showResult();
                    }
                }
            });
            optionsDialog.add(okButton, BorderLayout.SOUTH);

            // Set dialog properties
            optionsDialog.setSize(400, 200);
            optionsDialog.setLocationRelativeTo(null);
            optionsDialog.setModal(true);
            optionsDialog.setVisible(true);
        }
    }

    private class DropTableButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Check if a table exists
            if (table == null || table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No table exists.");
                return;
            }

            // Clear the content of the table
            clearTableContent();

            JOptionPane.showMessageDialog(null, "Table content cleared successfully!");
        }

        private void clearTableContent() {
            // Clear all cell values in the table
            for (int row = 0; row < table.getRowCount(); row++) {
                for (int col = 0; col < table.getColumnCount(); col++) {
                    table.setValueAt(null, row, col);
                }
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Prompt the user to select columns and conditions
            JPanel optionsPanel = new JPanel();
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

            // Create a list to store selected column names
            List<String> selectedColumns = new ArrayList<>();

            // Create checkboxes for each column
            String[] columnNames = {"ReferenceNumber", "CountryOfBeanOrigin", "ReviewDate", "CompanyLocation", "PercentCocoa", "Rating"};
            for (String columnName : columnNames) {
                JCheckBox checkBox = new JCheckBox(columnName);
                optionsPanel.add(checkBox);
                checkBox.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        // Add or remove the column name from the selected list
                        if (checkBox.isSelected()) {
                            selectedColumns.add(columnName);
                        } else {
                            selectedColumns.remove(columnName);
                        }
                    }
                });
            }

            // Create a text field for entering search condition
            JTextField conditionField = new JTextField(20);
            optionsPanel.add(conditionField);

            // Create radio buttons for AND and OR
            JRadioButton andButton = new JRadioButton("AND");
            JRadioButton orButton = new JRadioButton("OR");
            ButtonGroup group = new ButtonGroup();
            group.add(andButton);
            group.add(orButton);
            andButton.setSelected(true); // Default to AND
            JPanel radioPanel = new JPanel();
            radioPanel.add(andButton);
            radioPanel.add(orButton);
            optionsPanel.add(radioPanel);

            // Show input dialog
            int result = JOptionPane.showConfirmDialog(null, optionsPanel, "Select Columns and Enter Condition", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                // Construct SQL query
                StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Chocolates WHERE ");
                for (int i = 0; i < selectedColumns.size(); i++) {
                    queryBuilder.append(selectedColumns.get(i)).append(" = ?");
                    if (i < selectedColumns.size() - 1) {
                        queryBuilder.append(" ").append(andButton.isSelected() ? "AND" : "OR").append(" ");
                    }
                }
                queryBuilder.append(" AND ").append(conditionField.getText());

                String query = queryBuilder.toString();
                System.out.println("Query: " + query);

                try {
                    // Load the Derby JDBC driver
                    Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

                    // Establish a connection
                    Connection conn = DriverManager.getConnection("jdbc:derby:ChocolateDB;create=true");
                    PreparedStatement pstmt = conn.prepareStatement(query);

                    // Set parameters
                    for (int i = 0; i < selectedColumns.size(); i++) {
                        // Here you should bind the values according to the data type of each column
                        // For simplicity, let's assume all columns are strings
                        pstmt.setString(i + 1, JOptionPane.showInputDialog("Enter value for " + selectedColumns.get(i)));
                    }

                    // Execute query
                    ResultSet rs = pstmt.executeQuery();

                    // Populate table with query results
                    table.setModel(buildTableModel(rs));

                    // Close connections
                    rs.close();
                    pstmt.close();
                    conn.close();
                } catch (ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Derby JDBC driver not found!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error executing SQL query: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Get column names
        int columnCount = metaData.getColumnCount();
        String[] columnNames = new String[columnCount];
        for (int i = 1; i <= columnCount; i++) {
            columnNames[i - 1] = metaData.getColumnName(i);
        }

        // Get data rows
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        while (rs.next()) {
            Object[] rowData = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                rowData[i - 1] = rs.getObject(i);
            }
            model.addRow(rowData);
        }

        return model;
    }

    private class AggregateFunctionListener implements ActionListener {
        private String functionName;
        private List<String> selectedColumns;

        public AggregateFunctionListener(String functionName, List<String> selectedColumns) {
            this.functionName = functionName;
            this.selectedColumns = selectedColumns;
        }

        public void showResult() {
            if (table == null || table.getRowCount() == 0 || table.getColumnCount() == 0) {
                JOptionPane.showMessageDialog(null, "No data available to perform aggregation.");
                return;
            }

            // Extract data from selected columns
            List<List<Double>> columnData = new ArrayList<>();
            for (String column : selectedColumns) {
                List<Double> data = new ArrayList<>();
                int columnIndex = tableModel.findColumn(column);
                for (int row = 0; row < table.getRowCount(); row++) {
                    try {
                        double value = Double.parseDouble(table.getValueAt(row, columnIndex).toString());
                        data.add(value);
                    } catch (NumberFormatException ex) {
                        // Ignore non-numeric values
                    }
                }
                columnData.add(data);
            }

            // Perform aggregation based on the selected function for each column
            StringBuilder resultMessage = new StringBuilder();
            for (int i = 0; i < selectedColumns.size(); i++) {
                String column = selectedColumns.get(i);
                List<Double> data = columnData.get(i);
                double result = 0;
                switch (functionName) {
                    case "Min":
                        result = calculateMin(data);
                        break;
                    case "Max":
                        result = calculateMax(data);
                        break;
                    case "Count":
                        result = data.size();
                        break;
                    case "Average":
                        result = calculateAverage(data);
                        break;
                    case "Sum":
                        result = calculateSum(data);
                        break;
                }
                resultMessage.append(column).append(" ").append(functionName).append(": ").append(result).append("\n");
            }

            // Display the result
            JOptionPane.showMessageDialog(null, resultMessage.toString().trim());
        }

        private double calculateMin(List<Double> data) {
            if (data.isEmpty()) {
                return 0; // Return default value if data is empty
            }
            double min = data.get(0);
            for (double value : data) {
                if (value < min) {
                    min = value;
                }
            }
            return min;
        }

        private double calculateMax(List<Double> data) {
            if (data.isEmpty()) {
                return 0; // Return default value if data is empty
            }
            double max = data.get(0);
            for (double value : data) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }

        private double calculateAverage(List<Double> data) {
            if (data.isEmpty()) {
                return 0; // Return default value if data is empty
            }
            double sum = calculateSum(data);
            return sum / data.size();
        }

        private double calculateSum(List<Double> data) {
            double sum = 0;
            for (double value : data) {
                sum += value;
            }
            return sum;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            showResult();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewMenu());
    }
}
