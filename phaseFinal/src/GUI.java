/**
 * @author Group 48 - Karthik, Waqas, Manav, Rania
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI extends Application {

    TableView<Product> bookTable;
    TextField addTitle, addPrice, addQuantity, addUsername, addPassword, addPoints;
    public ObservableList<Product> productSelected;
    public ObservableList<Customer> custSelected;

    @Override
    public void start(Stage primaryStage){ 

        primaryStage.setTitle("Bookstore App");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        Text scenetitle = new Text("Welcome to the Bookstore App");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        Label UserName = new Label("User Name:");
        grid.add(UserName, 0, 1);
        TextField userbox = new TextField();
        grid.add(userbox, 1, 1);

        Label Password = new Label("Password:");
        grid.add(Password, 0, 2);
        PasswordField pwbox = new PasswordField();
        grid.add(pwbox, 1, 2);

        Label invalid = new Label("Invalid Login");

        Button btn = new Button("Sign in");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        
        Handler handler = new Handler();
        
        btn.setOnAction(new EventHandler<ActionEvent>(){
        
            @Override
            public void handle(ActionEvent event){
                
                String username = userbox.getText();
                String password = pwbox.getText();
                
                if (username.equals("admin") && password.equals("admin")){
                    managerWindow(primaryStage, handler);
                    System.out.println("Admin successfully logged in.");
                }
                
                else if (handler.verify(username, password)){
                    customerWindow(primaryStage, handler);
                    System.out.println("Customer successfully logged in.");
                }
                else{
                    System.out.println("Invalid Login.");
                    System.out.println(username);
                    System.out.println(password);
                    grid.add(invalid, 1, 3);
                }
            }
        });
        
        Scene scene = new Scene(grid, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void managerWindow(Stage primaryStage, Handler a){

        Handler handler = new Handler();

        Text scenetitle = new Text("Welcome, Administrator");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Button books = new Button("Books");
        Button customers = new Button("Customers");
        Button logout = new Button("Logout");

        GridPane managerPane = new GridPane();
        
        managerPane.setAlignment(Pos.CENTER);
        managerPane.setHgap(10);
        managerPane.setVgap(10);
        managerPane.setPadding(new Insets(25, 25, 25, 25));
        
        managerPane.add(books, 0, 2);
        managerPane.add(customers, 0, 3);
        managerPane.add(logout, 0, 4);
        
        managerPane.add(scenetitle, 0, 0, 2, 1);
        
        books.setOnAction((ActionEvent e)->{
            managerBooks(primaryStage, handler);
        });
        
        customers.setOnAction((ActionEvent e)->{
            managerCustomers(primaryStage, handler);
        });
       
        logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });
        
        Scene scene = new Scene(managerPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    
    //Rania & Manav
    public void customerWindow(Stage primaryStage, Handler a){
        
        Handler handler = new Handler();
        String status;
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        
        TableView<Product> bookTable;
        
        //Title Column
        TableColumn<Product, String> nameColumn = new TableColumn<>("Title");
        nameColumn.setMinWidth(200);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //Price Column
        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity Column
        TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(75);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

       
        Button buy = new Button("Buy");
        Button rNBuy = new Button("Redeem & Buy");
        Button logout = new Button("Logout");

        GridPane customerPane = new GridPane();
        
        customerPane.setAlignment(Pos.BOTTOM_CENTER);
        customerPane.setHgap(10);
        customerPane.setVgap(10);
        customerPane.setPadding(new Insets(25, 25, 25, 25));

        bookTable = new TableView<>();
        bookTable.setItems(handler.getProduct());
        bookTable.getColumns().addAll(nameColumn, priceColumn, quantityColumn);
        buy.setOnAction((ActionEvent e)->{

            productSelected = bookTable.getSelectionModel().getSelectedItems();

            try {
                customerCostScreen(primaryStage, handler, handler.purchaseBook(productSelected.get(0)));
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });
        
        rNBuy.setOnAction((ActionEvent e)->{
            try {
                customerCostScreen(primaryStage, handler, handler.purchaseBook(productSelected.get(0)) );
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       
        logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(scenetitle, bookTable, buy, rNBuy, logout);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void managerBooks(Stage primaryStage, Handler a){
        
        Handler handler = new Handler();
        
        //Title input
        addTitle = new TextField();
        addTitle.setPromptText("Title");
        addTitle.setMinWidth(100);

        //Price Input
        addPrice = new TextField();
        addPrice.setPromptText("Price");
        addPrice.setMinWidth(80);

        //Quantity Input
        addQuantity = new TextField();
        addQuantity.setPromptText("Quantity");
        addQuantity.setMinWidth(40);
        
        //Title Column
        TableColumn<Product, String> nameColumn = new TableColumn<>("Title");
        nameColumn.setMinWidth(400);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));

        //Price Column
        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Quantity Column
        //TableColumn<Product, String> quantityColumn = new TableColumn<>("Quantity");
        //quantityColumn.setMinWidth(75);
        //quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        bookTable = new TableView<>();
        bookTable.setItems(handler.getProduct());
        bookTable.getColumns().addAll(nameColumn, priceColumn);

        Button delete = new Button("Delete");
        Button back = new Button("Back");
        Button add = new Button("Add");
        add.setOnAction((ActionEvent e) ->{
            handler.addBook(addTitle.getText(),Double.parseDouble(addPrice.getText()), 1);
            addTitle.clear();
            addPrice.clear();
            addQuantity.clear();
        });

        back.setOnAction((ActionEvent e)->{
            managerWindow(primaryStage, handler);
        });

        HBox hBox = new HBox();

        hBox.setSpacing(10);
        hBox.getChildren().addAll(addTitle, addPrice, add);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(bookTable, hBox, delete, back);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        delete.setOnAction((ActionEvent e)->{
            
           productSelected = bookTable.getSelectionModel().getSelectedItems();
           try {
                handler.deleteBook(productSelected.get(0));
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
           productSelected.forEach(handler.product::remove);
            
        });   
    }
    
    public void managerCustomers(Stage primaryStage, Handler a){
        
        TableView<Customer> custTable;
        Handler handler = new Handler();
        
        //Username input
        addUsername = new TextField();
        addUsername.setPromptText("Username");
        addUsername.setMinWidth(100);

        //Password Input
        addPassword = new TextField();
        addPassword.setPromptText("Password");
        addPassword.setMinWidth(80);

        //Points Input
        addPoints = new TextField();
        addPoints.setPromptText("Points");
        addPoints.setMinWidth(40);
        
        //Username Column
        TableColumn<Customer, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setMinWidth(200);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        //Password Column
        TableColumn<Customer, String> passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(200);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));

        //Points Column
        TableColumn<Customer, String> pointsColumn = new TableColumn<>("Points");
        pointsColumn.setMinWidth(100);
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        custTable = new TableView<>();
        custTable.setItems(handler.getCustomers());
        custTable.getColumns().addAll(usernameColumn, passwordColumn, pointsColumn);

        Button delete = new Button("Delete");
        Button back = new Button("Back");
        Button add = new Button("Add");
        add.setOnAction((ActionEvent e) ->{
            handler.addCustomer(addUsername.getText(),addPassword.getText(), 0);
            addUsername.clear();
            addPassword.clear();
            addPoints.clear();
        });
        
        back.setOnAction((ActionEvent e)->{
            managerWindow(primaryStage, handler);
        });
        
        delete.setOnAction((ActionEvent e)->{
            
           custSelected = custTable.getSelectionModel().getSelectedItems();
           try {
                handler.deleteCustomer(custSelected.get(0));
            } catch (IOException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
           custSelected.forEach(handler.customers::remove);
           
        });   

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(addUsername, addPassword, add);
        
        VBox vBox = new VBox();
        vBox.getChildren().addAll(custTable, hBox, delete, back);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);

        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void customerCostScreen(Stage primaryStage, Handler a, double total)
    {

        Text transactionCost = new Text("Transaction cost: " + total);
        transactionCost.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        Text pointStatus = new Text("Points: " + ", Status: " );
        pointStatus.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
       
        Button logout = new Button("Logout");

        GridPane customerPane = new GridPane();
        
        customerPane.setAlignment(Pos.BOTTOM_CENTER);
        customerPane.setHgap(10);
        customerPane.setVgap(10);
        customerPane.setPadding(new Insets(25, 25, 25, 25));
       
        logout.setOnAction((ActionEvent e) -> {
            start(primaryStage);
        });

        VBox vBox = new VBox();
        vBox.getChildren().addAll(transactionCost, pointStatus, logout);
        vBox.setPadding(new Insets(35, 35, 35, 35));
        vBox.setSpacing(10);

        Scene scene1 = new Scene(vBox);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }
    
    public static void main(String[] args){
        launch(args);
    }
    
}
