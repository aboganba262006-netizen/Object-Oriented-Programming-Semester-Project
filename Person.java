import java.util.List;
import java.util.ArrayList;

abstract class Person {
    private String fname;
    private String lname;
    private String email;
    private String password;

    Person(String fname, String lname,String email, String password){
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
    }

    public void login(){
        System.out.println(fname + " " + lname +" has logged in");
    }

    public void logout(){
        System.out.println(fname + " " + lname +" has logged out");
    }

    public abstract void displayMenu();

    public String getName(){
        return fname + " " + lname;
    }

    public String getEmail(){
        return email;
    }
}