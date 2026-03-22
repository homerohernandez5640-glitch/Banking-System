import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
public class Bank {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
      
Boolean validInput = true;
        
        Statement sqlSt;//Runs SQL
       // String useSQL = new String("use usersdatabase");//Tells SQL which database to use
        String output;
        ResultSet result; //Holds output of SQL
        String SQL = null;
        String SQLcustomers = "select * from customers";
        String SQLC_Email = "select * from customers where email = ";
        String SQLC_PIN = "select * from customers where pin = ";
        String SQLBalance = "select balance from customers where pin = ";

        String SQLemployees = "select * from employees";
       

System.out.println("***************************************");
System.out.println("Welcome to The Bank of Java ATM");
System.out.println("Are you a customer or employee? ('customers' or 'employees')");
System.out.println("***************************************");
String userStatus = scanner.nextLine();
if(userStatus.equalsIgnoreCase("employee")){
SQL = SQLemployees;
}
else if(userStatus.equalsIgnoreCase("customer")){
SQL = SQLcustomers;
}
else{
    System.out.println("Invalid input. Please restart the program and enter 'customers' or 'employees'.");
        validInput = false;
}

        try{
Class.forName("com.mysql.cj.jdbc.Driver");
String dbURL = "jdbc:mysql://localhost:3306/usersdatabase";//Database URL
Connection dbConnect = DriverManager.getConnection(dbURL, "root", "Hh280001640*");//
sqlSt = dbConnect.createStatement();// Allows us to run SQL. It's like a bridge between Java and SQL
sqlSt.execute(SQL);//Runs the use SQL
result = sqlSt.executeQuery(SQL);//Runs the SQL and stores the output in result
//Result holds output from SQL

while(result.next() != false){




while(validInput){
if(userStatus.equalsIgnoreCase("customer")){


System.out.println("Do you have an account with us? ('yes' or 'no')");
String hasAccount = scanner.nextLine();
if(hasAccount.equalsIgnoreCase("yes")){

    System.out.println("Please enter your email:");
    String User_email = scanner.nextLine();
String Full_Email = SQLC_Email + "'" + User_email + "'";
if(Full_Email.isEmpty() || !Full_Email.contains("@") || !Full_Email.contains(".")){
    System.out.println("Email cannot be empty. Please restart the program and enter a valid email.");
    validInput = false;
    }
System.out.println("What is your PIN?");
String User_PIN = scanner.nextLine();
String Full_PIN = SQLC_PIN + "'" + User_PIN + "'";
if(Full_PIN.isEmpty() || User_PIN.length() != 4 || !User_PIN.matches("[0-9]+")){
    System.out.println("PIN must be 4 digits. Please restart the program and enter a valid PIN.");
    validInput = false;
    }
    

}
else if(hasAccount.equalsIgnoreCase("no")){
    System.out.println("Please visit our nearest branch to open an account.");
        validInput = false;
}
}


    else if(userStatus.equalsIgnoreCase("employee")){


    }





}
    
output = result.getString("first_name") + " " + result.getString("last_name") + " " + result.getString("email");



}

sqlSt.close();
dbConnect.close();
        }
        
        catch(ClassNotFoundException ex){
Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
System.out.print("Class not found(Check JDBC driver)");

        } 
        
        catch(SQLException ex){
Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
System.out.print("SQL Error(Check your SQL)");
        }

scanner.close();
        }

    }
