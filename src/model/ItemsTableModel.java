 
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

 
public class ItemsTableModel extends AbstractTableModel {
    
    private ArrayList<InvoiceItems>items;
        private String[] itemsColumns = {"No.", "Item Name", "Item Price", "Count","Item Total"};

    public ItemsTableModel(ArrayList<InvoiceItems> items) {
        this.items = items;
    }
        public ArrayList<InvoiceItems> getItems() {
        return items;
    }


    
    

    @Override
    public int getRowCount() {
        return items.size();
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
        InvoiceItems itemsRow = items.get(rowIndex);
        switch(columnIndex){
            
            case 0: 
                return itemsRow.getInv().getNum();
            case 1:
                return itemsRow.getName();
            case 2:
                return itemsRow.getPrice();
            case 3:
                return itemsRow.getCount();
            case 4:
                return itemsRow.getTotal();
            default:
                return "----";
                
                
            
            
        }
        }
    
    
     }
    

