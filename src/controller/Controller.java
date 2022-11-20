package controller;

 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.InvoiceDetails;
import model.InvoiceItems;
import model.InvoiceTableModel;
import model.ItemsTableModel;
import view.InvoiceDialog;
import view.ItemsDialog;
import view.UIFrame;

public class Controller implements ActionListener, ListSelectionListener {

    private UIFrame frame;
    private InvoiceDialog invoiceDialog;
    private ItemsDialog itemsDialog;

    public Controller(UIFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("welcome: " + e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Create New Invoice":

                newInvoice();

                break;

            case "Delete Invoice":
                deleteInvoice();

                break;

            case "Create Item":

                newItem();
                break;

            case "Delete Item":
                deleteItem();
                break;

            case "Load":
                loadFiles();
                break;

            case "Save":
                saveFile();
                break;

            case "createInvoiceCancel":
                createInvoiceCancel();
                break;

            case "createInvoiceOK":
                createInvoiceOK();
                break;

            case "createRowOK":
                createRowOK();
                break;

            case "createRowCancel":
                createRowCancel();
                break;

        }
    }

    private void newInvoice() {
        invoiceDialog = new InvoiceDialog(frame);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedItems = frame.getInvoiceTable().getSelectedRow();

        if (selectedItems != -1) {

            frame.getInvoices().remove(selectedItems);
            frame.getInvModel().fireTableDataChanged();
        }
    }

    private void newItem() {

        itemsDialog = new ItemsDialog(frame);
        itemsDialog.setVisible(true);

    }

    private void deleteItem() {
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        int selectedItems = frame.getItemsTable().getSelectedRow();

        if (selectedInvoice != -1 && selectedItems != -1) {
            InvoiceDetails invoiceDetails = frame.getInvoices().get(selectedInvoice);
            invoiceDetails.getItems().remove(selectedItems);
            ItemsTableModel itemsTableModel = new ItemsTableModel(invoiceDetails.getItems());
            frame.getItemsTable().setModel(itemsTableModel);
            itemsTableModel.fireTableDataChanged();
            frame.getInvModel().fireTableDataChanged();
        }

    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {

            System.out.println("You have selected row" + selectedRow);
            InvoiceDetails selectedInvoice = frame.getInvoices().get(selectedRow);
            frame.getInvoiceNumberLabel().setText("" + selectedInvoice.getNum());
            frame.getInvoiceDateLabel().setText(selectedInvoice.getDate());
            frame.getCustomerNameLabel().setText(selectedInvoice.getName());
            frame.getTotalLabel().setText("" + selectedInvoice.getTotal());
            ItemsTableModel itemsModel = new ItemsTableModel(selectedInvoice.getItems());
            frame.getItemsTable().setModel(itemsModel);
            itemsModel.fireTableDataChanged();
        }

    }

    private void loadFiles() {
        JFileChooser fileChooser = new JFileChooser();
        try {
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {

                File headerFile = fileChooser.getSelectedFile();
                Path headerPath = Paths.get(headerFile.getAbsolutePath());
                List< String> headerLines = Files.readAllLines(headerPath);

                // ArrayList<InvoiceDetails> invoicesArray = new ArrayList<>();
                ArrayList<InvoiceDetails> invoicesArray = new ArrayList<>();
                for (String headerLine : headerLines) {
                    try {

                        String[] headerColumns = headerLine.split(",");
                        int invoiceNumber = Integer.parseInt(headerColumns[0]);
                        String invoiceDate = headerColumns[1];
                        String customerName = headerColumns[2];

                        InvoiceDetails invoice = new InvoiceDetails(invoiceNumber, invoiceDate, customerName);
                        invoicesArray.add(invoice);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                     
                    }

                }
                result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {

                    File itemsFile = fileChooser.getSelectedFile();
                    Path ItemPath = Paths.get(itemsFile.getAbsolutePath());
                    List<String> itemLines = Files.readAllLines(ItemPath);
                    for (String itemLine : itemLines) {
                        try {
                            String itemsColumns[] = itemLine.split(",");
                            int invoiceNumber = Integer.parseInt(itemsColumns[0]);
                            String ItemName = itemsColumns[1];
                            double Itemprice = Double.parseDouble(itemsColumns[2]);
                            int count = Integer.parseInt(itemsColumns[3]);
                            InvoiceDetails invoiceDetails = null;
                            for (InvoiceDetails invoice : invoicesArray) {

                                if (invoice.getNum() == invoiceNumber) {
                                    invoiceDetails = invoice;

                                    break;
                                }

                            }
                            InvoiceItems item = new InvoiceItems(ItemName, Itemprice, count, invoiceDetails);
                            invoiceDetails.getItems().add(item);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Error in line format", "Error", JOptionPane.ERROR_MESSAGE);
                        } 

                    }

                }
                frame.setInvoices(invoicesArray);
                InvoiceTableModel invModel = new InvoiceTableModel(invoicesArray);
                frame.setInvModel(invModel);
                frame.getInvoiceTable().setModel(invModel);
                frame.getInvModel().fireTableDataChanged();
            }

        } catch (IOException io) {
            io.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Load a vaild file", "Error", JOptionPane.ERROR_MESSAGE);

        } 
        
    }

    private void saveFile() {
        ArrayList<InvoiceDetails> invoiceDetails = frame.getInvoices();
        String invoiceHeaders = "";
        String InvoiceLines = "";
        for (InvoiceDetails invoice : invoiceDetails) {
            String invHeaderCsvFormat = invoice.getCsvFile();
            invoiceHeaders += invHeaderCsvFormat;
            invoiceHeaders += "\n";

            for (InvoiceItems invoiceItems : invoice.getItems()) {

                String invLinesCsvFormat = invoiceItems.getCsvFile();
                InvoiceLines += invLinesCsvFormat;
                InvoiceLines += "\n";

            }

        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File InvoiceHeader = fileChooser.getSelectedFile();
                FileWriter fileWriter1 = new FileWriter(InvoiceHeader);
                fileWriter1.write(invoiceHeaders);
                fileWriter1.flush();
                fileWriter1.close();

                result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File InvoiceLine = fileChooser.getSelectedFile();
                    FileWriter fileWriter2 = new FileWriter(InvoiceLine);
                    fileWriter2.write(InvoiceLines);
                    fileWriter2.flush();
                    fileWriter2.close();

                }
            }
        } catch (Exception ex) {

        }
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceOK() {

        String date = invoiceDialog.getInvoiceDateField().getText();
        String CustomerName = invoiceDialog.getCustomerNameField().getText();
        int number = frame.getNextInvoiceNumber();
        try {
            String[] dateFields = date.split("-");
            if (dateFields.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateFields[0]);
                int month = Integer.parseInt(dateFields[1]);
                int year = Integer.parseInt(dateFields[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InvoiceDetails invoiceDetails = new InvoiceDetails(number, date, CustomerName);
                    frame.getInvoices().add(invoiceDetails);
                    frame.getInvModel().fireTableDataChanged();
                    invoiceDialog.setVisible(false);
                    invoiceDialog.dispose();
                    invoiceDialog = null;
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createRowCancel() {
        itemsDialog.setVisible(false);
        itemsDialog.dispose();
        itemsDialog = null;
    }

    private void createRowOK() {
        String itemName = itemsDialog.getItemNameField().getText();
        String stringCount = itemsDialog.getItemCountField().getText();
        String stringPrice = itemsDialog.getItemPriceField().getText();
        int count = Integer.parseInt(stringCount);
        double price = Double.parseDouble(stringPrice);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();

        if (selectedInvoice != -1) {
            InvoiceDetails invoiceDetails = frame.getInvoices().get(selectedInvoice);

            InvoiceItems invoiceItems = new InvoiceItems(itemName, price, count, invoiceDetails);
            ItemsTableModel itemsTableModel = (ItemsTableModel) frame.getItemsTable().getModel();
            itemsTableModel.getItems().add(invoiceItems);
            itemsTableModel.fireTableDataChanged();
            frame.getInvModel().fireTableDataChanged();

        }
        itemsDialog.setVisible(false);
        itemsDialog.dispose();
        itemsDialog = null;
    }

}
