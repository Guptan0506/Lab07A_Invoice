import javax.swing.*;
import java.awt.*;

public class InvoiceGeneratorFrame extends JFrame {
    Invoice thisInvoice;
    JPanel titlePnl;
    JPanel inputPnl;
    JTextField customerNameTF;
    JLabel customerNameLbl;
    JTextField streetTF;
    JLabel streetLbl;
    JTextField cityTF;
    JLabel cityLbl;
    JTextField stateTF;
    JLabel stateLbl;
    JTextField zipCodeTF;
    JLabel zipCodeLbl;
    JPanel invoiceDisplayPnl;
    JPanel controlPnl;
    JLabel titleLabel;
    JTextArea customerAddressLabel;
    JTextArea itemsList;
    JButton enterLineItemBtn;
    JButton showInvoiceBtn;


    public InvoiceGeneratorFrame()  {

        setTitle("Invoice Generator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Title Panel
        setLayout(new BorderLayout());
        titlePnl = new JPanel();
        titleLabel = new JLabel("INVOICE");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePnl.add(titleLabel);
        add(titlePnl, BorderLayout.NORTH);

        createInputPanel();

        setLocationRelativeTo(null); // Center the frame on the screen
        pack();
        setVisible(true);
    }

    private void createInputPanel() {
        inputPnl = new JPanel();
        inputPnl.setLayout(new GridLayout(5, 2));

        customerNameLbl = new JLabel("Customer Name:");
        customerNameTF = new JTextField();
        inputPnl.add(customerNameLbl);
        inputPnl.add(customerNameTF);

        streetLbl = new JLabel("Street:");
        streetTF = new JTextField();
        inputPnl.add(streetLbl);
        inputPnl.add(streetTF);

        cityLbl = new JLabel("City:");
        cityTF = new JTextField();
        inputPnl.add(cityLbl);
        inputPnl.add(cityTF);

        stateLbl = new JLabel("State:");
        stateTF = new JTextField();
        inputPnl.add(stateLbl);
        inputPnl.add(stateTF);

        zipCodeLbl = new JLabel("Zip Code:");
        zipCodeTF = new JTextField();
        inputPnl.add(zipCodeLbl);
        inputPnl.add(zipCodeTF);
        enterLineItemBtn = new JButton("Enter Line Item");
        inputPnl.add(enterLineItemBtn);
        showInvoiceBtn = new JButton("Show Invoice");
        inputPnl.add(showInvoiceBtn);

        add(inputPnl, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InvoiceGeneratorFrame();
        });
    }
}