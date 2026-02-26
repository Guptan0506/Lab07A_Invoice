import javax.swing.*;
import java.awt.*;

public class InvoiceGeneratorFrame extends JFrame {

    JPanel titlePnl;
    JPanel inputPnl;
    JPanel invoiceDisplayPnl;
    JPanel controlPnl;
    JLabel titleLabel;
    JTextArea customerAddressLabel;
    JTextArea itemsList;


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

        setLocationRelativeTo(null); // Center the frame on the screen
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InvoiceGeneratorFrame();
        });
    }
}