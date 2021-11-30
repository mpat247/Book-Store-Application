/**
 * @author Group 48 - Karthik, Waqas, Manav, Rania
 */

import javafx.scene.control.CheckBox;

public class Product {

    private String bookName;
    private double price;
    private int quantity;
    private CheckBox select = new CheckBox();

    public Product (String bookName, double price, int quantity){
        this.bookName = bookName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBookName(){
        return bookName;
    }
    
    public void setBookName (String bookName){
        this.bookName = bookName;
    }

    public double getPrice(){
        return price;
    }
    
    public void setPrice (double price){
        this.price = price;
    }

    public int getQuantity(){
        return quantity;
    }
    
    public void setQuantity (int quantity){
        this.quantity = quantity;
    }

    public CheckBox getSelect(){
        return select;
    }

    public void setSelect(CheckBox select){
        this.select = select;
    }
    
}