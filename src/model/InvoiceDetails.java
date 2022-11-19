package model;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceDetails {

    private int num;
    private String name;
    private String date;
      private ArrayList<InvoiceItems> items;
        


    public InvoiceDetails(int num, String date,String name ) {
        this.num = num;
        this.name = name;
        this.date = date;
    }

    

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<InvoiceItems> getItems() {
        if (items == null) {

            items = new ArrayList<>();

        }
        return items;
    }

    @Override
    public String toString() {
        return "InvoiceDetails{" + "num=" + num +", date=" + date +  ", name=" + name + ", items=" + items + '}';
    }
    
    

    public double getTotal() {
        double total = 0.0;

        for (InvoiceItems item : getItems()) {

            total += item.getTotal();
        }

        return total;

    }
    
    public String getCsvFile()
            
    {
    
    return num +"," + date +","+name;
    
    }
}
