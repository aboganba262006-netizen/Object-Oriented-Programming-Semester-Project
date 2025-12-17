import java.util.List;
import java.util.ArrayList;

abstract class Person {
    private String name;
    private String email;
    private String password;

    Person(String name, String email, String password){
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void login(){
        System.out.println(name + " has logged in");
    }

    public void logout(){
        System.out.println(name + " has logged out");
    }

    public abstract void displayMenu();

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }
}