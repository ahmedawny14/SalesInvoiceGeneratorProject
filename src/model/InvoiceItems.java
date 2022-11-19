/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author BMT
 */
public class InvoiceItems {

    public InvoiceItems(String name , double price, int count,InvoiceDetails invoiceDetails) {
        this.invoiceDetails= invoiceDetails;
        this.name = name;
        this.price = price;
        this.count = count;
     }
    private InvoiceDetails invoiceDetails;
    private String name;
    private double price;
    private int count;

     
 
    public InvoiceDetails getInv() {
        return invoiceDetails;
    }

    public void setInv(InvoiceDetails invoiceDetails) {
        this.invoiceDetails= invoiceDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "InvoiceItems{" + "name=" + name +"num"+ invoiceDetails.getNum()+ ", price=" + price + ", count=" + count + '}';
    }

    public double getTotal() {

        return count * price;
    }
    
     public String getCsvFile()
            
    {
    
    return invoiceDetails.getNum() +"," + name +","+price+","+count;
    
    }
}
