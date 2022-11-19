/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package  view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import view.UIFrame;

/**
 *
 * @author DELL
 */
public class InvoiceDialog extends JDialog {
    private JTextField customerNameField;
    private JTextField invoiceDateField;
    private JLabel customerNameLabel;
    private JLabel invoiceDateLabel;
    private JButton okButtotn;
    private JButton cancelButton;

    public InvoiceDialog(UIFrame frame     ) {
        customerNameLabel = new JLabel("Customer Name:");
        customerNameField = new JTextField(20);
        invoiceDateLabel = new JLabel("Invoice Date:");
        invoiceDateField = new JTextField(20);
        okButtotn = new JButton("OK");
        cancelButton = new JButton("Cancel");
        
        okButtotn.setActionCommand("createInvoiceOK");
        cancelButton.setActionCommand("createInvoiceCancel");
        
        okButtotn.addActionListener(frame.getController());
        cancelButton.addActionListener(frame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDateLabel);
        add(invoiceDateField);
        add(customerNameLabel);
        add(customerNameField);
        add(okButtotn);
        add(cancelButton);
        
        pack();
        
        
    }

    public JTextField getCustomerNameField() {
        return customerNameField;
    }

    

    public JTextField getInvoiceDateField() {
        return invoiceDateField;
    }
    
}
