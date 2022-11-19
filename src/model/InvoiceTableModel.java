package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvoiceTableModel extends AbstractTableModel {

    private ArrayList<InvoiceDetails> invoices;
    private String[] itemsColumns = {"No.", "Date", "Customer", "Total"};

    public InvoiceTableModel(ArrayList<InvoiceDetails> invoices) {
        this.invoices = invoices;
    }

    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return itemsColumns.length;

    }

    @Override
    public String getColumnName(int columnIndex) {
        return itemsColumns[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceDetails invoiceRow = invoices.get(rowIndex);

        switch (columnIndex) {

            case 0:
                return invoiceRow.getNum();
            case 1:
                return invoiceRow.getDate();
            case 2:
                return invoiceRow.getName();
            case 3:
                return invoiceRow.getTotal();
            default:
                return "---";
                

        }

    }

}
