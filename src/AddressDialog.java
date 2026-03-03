import javax.swing.*;
import java.awt.*;

public class AddressDialog extends JDialog {
    // This class will be responsible for displaying a dialog to the user to input their address information.
    // It will have fields for the street, city, state, zip code, and customer name.
    // It will also have a method to display the dialog and return an Address object based on the user's input.

    private JTextField customerNameTF;
    private JTextField streetTF;
    private JTextField cityTF;
    private JTextField stateTF;
    private JTextField zipCodeTF;
    private JButton okBtn;
    private JButton cancelBtn;
    private Address address;
    private boolean completed;

    public AddressDialog(JFrame parent) {
        super(parent, "Enter Address Information", true);
        completed = false;
        address = null;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        // Create input panel with GridBagLayout for better control
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer Name field
        JLabel customerNameLbl = new JLabel("Customer Name:");
        customerNameTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        inputPanel.add(customerNameLbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(customerNameTF, gbc);

        // Street field
        JLabel streetLbl = new JLabel("Street:");
        streetTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        inputPanel.add(streetLbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(streetTF, gbc);

        // City field
        JLabel cityLbl = new JLabel("City:");
        cityTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        inputPanel.add(cityLbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(cityTF, gbc);

        // State field
        JLabel stateLbl = new JLabel("State:");
        stateTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        inputPanel.add(stateLbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(stateTF, gbc);

        // Zip Code field
        JLabel zipCodeLbl = new JLabel("Zip Code:");
        zipCodeTF = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0.3;
        inputPanel.add(zipCodeLbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        inputPanel.add(zipCodeTF, gbc);

        add(inputPanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        okBtn = new JButton("OK");
        cancelBtn = new JButton("Cancel");

        okBtn.addActionListener(e -> onOK());
        cancelBtn.addActionListener(e -> onCancel());

        buttonPanel.add(okBtn);
        buttonPanel.add(cancelBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onOK() {
        // Validate input
        if (customerNameTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Customer name cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (streetTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Street cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (cityTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "City cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (stateTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "State cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (zipCodeTF.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Zip code cannot be empty",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create Address object with the input values
        address = new Address(
                streetTF.getText().trim(),
                cityTF.getText().trim(),
                stateTF.getText().trim(),
                zipCodeTF.getText().trim()
        );
        address.setCustomerName(customerNameTF.getText().trim());

        completed = true;
        dispose();
    }

    private void onCancel() {
        completed = false;
        address = null;
        dispose();
    }

    public Address getAddress() {
        return address;
    }

    public boolean isCompleted() {
        return completed;
    }
}
