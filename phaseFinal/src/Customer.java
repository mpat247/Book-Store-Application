
import javafx.scene.control.CheckBox;

/**
 * @author Group 48 - Karthik, Waqas, Manav, Rania
 */

public class Customer extends User {

    private String username;
    private String password;
    private double points;
    private CheckBox select = new CheckBox();
    private String status;

    public Customer (String username, String password, double points){
        this.username = username;
        this.password = password;
        this.points = points;
    }
    
    @Override
    public String getUsername(){
        return username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    @Override
    public String getPassword(){
        return password;
    }
    
    @Override
    public double getPoints(){
        return points;
    }           
    
    public CheckBox getSelect(){
        return select;
    }

    public void setSelect(CheckBox select){
        this.select = select;
    }
    
    public String getStatus(){
        if(points < 1000){
            status = "Silver";
        }else{
            status = "Gold";
        }
        return status;
    }
    
    public void setStatus(String status){
        this.status = status;
    }
    
}