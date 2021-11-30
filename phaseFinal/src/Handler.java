import java.io.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;

/**
 * @author kggur
 */

public class Handler {
    private String username;
    private String password;
    private double points;
    private String bookName;
    private double bookPrice;
    private int bookQuantity;
    public User current;
    public Customer c1;
    public ObservableList<Product> product = FXCollections.observableArrayList();
    public ObservableList<Customer> customers = FXCollections.observableArrayList();
    
    public boolean verify(String user, String pw) {
        boolean verification;
        verification = false;
               
        try{
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
            
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                String info[] = line.split(", ");
                username = info[0];
                password = info[1];
                points = Integer.parseInt(info[2]);
                c1 = new Customer(username, password, points);
                if((user.equals(username)) && (pw.equals(password))){
                    verification = true;
                    current = c1;
                    System.out.println(current.getUsername());
                    System.out.println(current.getPassword());
                    System.out.println(current.getPoints());
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e){
            System.out.println("User doesn't exist");
        } return verification;

    } 
    
    public ObservableList<Product> getProduct (){
        
        try{
            BufferedReader reader = new BufferedReader(new FileReader("books.txt"));
            
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                String info[] = line.split(", ");
                bookName = info[0];
                bookPrice = Double.parseDouble(info[1]);
                bookQuantity = Integer.parseInt(info[2]);
                
                product.add(new Product(bookName, bookPrice, bookQuantity));
                
                // read next line 
                line = reader.readLine();
            }
            reader.close();
            
        } catch (Exception e){
            System.out.println("Invalid");
        } 

        return product;
    }
    
    public void addBook(String title, double price, int quantity) {
        product.add(new Product(title, price, quantity));
        try { 
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("books.txt", true)));
            out.println(title + ", " + price + ", " + quantity);
            out.flush();
            out.close();
        }
        catch (IOException e) {  
          System.out.println(e);
        }
    }
    
    public double purchaseBook(Product book) throws IOException
    {
        double total;
        total = book.getPrice();
        deleteBook(book);
        
        return total;
    }
    
   
    public void deleteBook(Product book) throws FileNotFoundException, IOException{
        File inputFile = new File("books.txt");
        File tempFile = new File("myTempFile.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = book.getBookName();
        
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.contains(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
            System.out.println(currentLine);
        }
        writer.close(); 
        reader.close(); 
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
    }   
    
    public void addCustomer(String username, String password, int points) {
        customers.add(new Customer(username, password, points));
        try { 
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("customers.txt", true)));
        out.println(username + ", " + password + ", " + points);
        out.flush();
        out.close();
        }
        catch (IOException e) {  
          System.out.println(e);
        }
    }
    
    public void deleteCustomer(Customer customer) throws FileNotFoundException, IOException{
        File inputFile = new File("customers.txt");
        File tempFile = new File("myTempFile2.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = customer.getUsername();
        
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            // trim newline when comparing with lineToRemove
            String trimmedLine = currentLine.trim();
            if(trimmedLine.contains(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
            System.out.println(currentLine);
        }
        writer.close(); 
        reader.close(); 
        inputFile.delete();
        boolean successful = tempFile.renameTo(inputFile);
    }   
    
    public ObservableList<Customer> getCustomers (){

        try{
            BufferedReader reader = new BufferedReader(new FileReader("customers.txt"));
            
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                String info[] = line.split(", ");
                username = info[0];
                password = info[1];
                points = Double.parseDouble(info[2]);
                
                customers.add(new Customer(username, password, points));
                // read next line
                line = reader.readLine();
            }
            reader.close();
            
        } catch (Exception e){
            System.out.println("Invalid");
        } 

        return customers;
    }
    
}