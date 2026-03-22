import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;
public class Bank {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
      
Boolean validInput = true;
        String User_email = null;
        String userStatus = null;
        Statement sqlSt;//Runs SQL
       // String useSQL = new String("use usersdatabase");//Tells SQL which database to use
        String output;
        ResultSet result; //Holds output of SQL
        String SQL = null;
        String SQLcustomers = "select * from customers";
        String SQLC_Email = "select * from customers where email = ";
        String SQLC_PIN = "select * from customers where pin = ";
        String SQLCBalance = "select balance, first_name, last_name, email from customers where pin = ";

        String SQLemployees = "select * from employees";
         String SQLE_Email = "select * from employees where email_address = ";
        String SQLE_ID = "select * from employees where employee_ID = ";
        String SQLEinfo = "select hire_date, first_name, last_name, email_address from employees where employee_ID = ";
       

        while(validInput){
System.out.println("***************************************");
System.out.println("Welcome to The Bank of Java ATM");
System.out.println("Are you a customer or employee? ('customer' or 'employee')");
System.out.println("***************************************");
userStatus = scanner.nextLine();
if(userStatus.equalsIgnoreCase("employee")){
SQL = SQLemployees;
}
else if(userStatus.equalsIgnoreCase("customer")){
SQL = SQLcustomers;
}
else{
    System.out.println("***************************************");

    System.out.println("Invalid input. Please restart the program and enter 'customers' or 'employees'.");
    System.out.println("***************************************");

        validInput = false;
        
}


if(userStatus.equalsIgnoreCase("customer")){

System.out.println("***************************************");

System.out.println("Do you have an account with us? ('yes' or 'no')");
String hasAccount = scanner.nextLine();
if(hasAccount.equalsIgnoreCase("yes")){
System.out.println("***************************************");

    System.out.println("Please enter your email:");
   User_email = scanner.nextLine();
String Full_Email = SQLC_Email + "'" + User_email + "'";
if(Full_Email.isEmpty() || !Full_Email.contains("@") || !Full_Email.contains(".")){
    System.out.println("***************************************");

    System.out.println("Email cannot be empty. Please restart the program and enter a valid email.");
    validInput = false;
    }
    System.out.println("***************************************");

System.out.println("What is your PIN?");
String User_PIN = scanner.nextLine();
String Full_PIN = SQLC_PIN + "'" + User_PIN + "'";
if(Full_PIN.isEmpty() || User_PIN.length() != 4 || !User_PIN.matches("[0-9]+")){
    System.out.println("***************************************");

    System.out.println("PIN must be 4 digits. Please restart the program and enter a valid PIN.");
    validInput = false;
    System.out.println("***************************************");

    }
    SQLCBalance = SQLCBalance + "'" + User_PIN + "'";
SQL = SQLCBalance;
break;
}
else if(hasAccount.equalsIgnoreCase("no")){
    System.out.println("***************************************");

    System.out.println("Please visit our nearest branch to open an account.");
        validInput = false;
}
}


    else if(userStatus.equalsIgnoreCase("employee")){
        System.out.println("***************************************");

System.out.println("Please enter your email:");
User_email = scanner.nextLine();
String Full_Email = SQLE_Email + "'" + User_email + "'";
if(Full_Email.isEmpty() || !Full_Email.contains("@") || !Full_Email.contains(".")){
    System.out.println("***************************************");

    System.out.println("Email cannot be empty. Please restart the program and enter a valid email.");
    System.out.println("***************************************");

    validInput = false;
    }
    System.out.println("***************************************");

System.out.println("What is your ID?");
String User_ID = scanner.nextLine();
String Full_ID = SQLE_ID + "'" + User_ID + "'";
if(Full_ID.isEmpty() || User_ID.length() != 4 || !User_ID.matches("[0-9]+")){
    System.out.println("***************************************");

    System.out.println("ID must be 4 digits. Please restart the program and enter a valid ID.");
    System.out.println("***************************************");

    validInput = false;
    }
  SQLEinfo = SQLEinfo + "'" + User_ID + "'";
SQL = SQLEinfo;
break;


    }



}

String First_name = null;
String last_name = null;
String Email = null;
double Balance = 0;

        try{
Class.forName("com.mysql.cj.jdbc.Driver");
String dbURL = "jdbc:mysql://localhost:3306/usersdatabase";//Database URL
Connection dbConnect = DriverManager.getConnection(dbURL, "root", "Hh280001640*");//
sqlSt = dbConnect.createStatement();// Allows us to run SQL. It's like a bridge between Java and SQL
sqlSt.execute(SQL);//Runs the use SQL
result = sqlSt.executeQuery(SQL);//Runs the SQL and stores the output in result
//Result holds output from SQL

while(result.next() != false){
if(userStatus.equalsIgnoreCase("customer")){

First_name = result.getString("first_name");
last_name = result.getString("last_name");
Email = result.getString("email");
Balance = result.getDouble("balance");
System.out.println("***************************************");

System.out.println("First Name: " + First_name + "\nLast Name: " + last_name + "\nEmail: " + Email + "\nBalance: " + Balance + "");
System.out.println("***************************************");

System.out.println("Would you like to deposit or withdraw? ('deposit' or 'withdraw')");
String transactionType = scanner.nextLine();
if(Email.equalsIgnoreCase(User_email)){
if(transactionType.equalsIgnoreCase("deposit")){
    System.out.println("***************************************");

    System.out.println("How much would you like to deposit?");
    double depositAmount = scanner.nextDouble();
    if(depositAmount <= 0){
        System.out.println("***************************************");

                System.out.println("Deposit amount must be greater than 0. Please restart the program and enter a valid amount.");
                System.out.println("***************************************");

        validInput = false;
    } else {
        Balance = Balance + depositAmount;
        String SQLUpdateBalance = "UPDATE customers SET balance = " + Balance + " WHERE email = '" + Email + "'";
        try(Statement updateSt = dbConnect.createStatement()){
            updateSt.executeUpdate(SQLUpdateBalance);
        }
        System.out.println("***************************************");

        System.out.println("Your new balance is: " + Balance);
    }
}
else if(transactionType.equalsIgnoreCase("withdraw")){
    System.out.println("***************************************");

    System.out.println("How much would you like to withdraw?");
    double withdrawAmount = scanner.nextDouble();
    if(withdrawAmount <= 0){
        System.out.println("***************************************");

        System.out.println("Withdrawal amount must be greater than 0. Please restart the program and enter a valid amount.");
        System.out.println("***************************************");

        validInput = false;
    }
    else if(withdrawAmount > Balance){
        System.out.println("***************************************");

        System.out.println("Insufficient funds. Please restart the program and enter a valid amount.");
        System.out.println("***************************************");

        validInput = false;
    }
    else{
        Balance = Balance - withdrawAmount;
        String SQLUpdateBalance = "update customers set balance = " + Balance + " where email = '" + Email + "'";
        try(Statement updateSt = dbConnect.createStatement()){
            updateSt.executeUpdate(SQLUpdateBalance);
        }
        System.out.println("***************************************");

        System.out.println("Your new balance is: " + Balance);
    }


}
}
}


if(userStatus.equalsIgnoreCase("employee")){
First_name = result.getString("first_name");
last_name = result.getString("last_name");
Email = result.getString("email_address");
Date hire_date = result.getDate("hire_date");
System.out.println("***************************************");
System.out.println("First Name: " + First_name + "\nLast Name: " + last_name + "\nEmail: " + Email + "\nHire Date: " + hire_date + "");
System.out.println("***************************************");
System.out.println("Would you like to view customer information? ('yes' or 'no')");
String viewCustomerInfo = scanner.nextLine();
if(viewCustomerInfo.equalsIgnoreCase("yes")){
System.out.println("***************************************");

    System.out.println("Here is the customer information:");

    try(Statement sqlSt2 = dbConnect.createStatement()){
    ResultSet customerResult = sqlSt2.executeQuery(SQLcustomers);
    
    while(customerResult.next()){
        String cFirst_name = customerResult.getString("first_name");
        String cLast_name = customerResult.getString("last_name");
        String cEmail = customerResult.getString("Email");
        double cBalance = customerResult.getDouble("balance");
        int cCustomerPin = customerResult.getInt("pin");
        System.out.println("First Name: " + cFirst_name + "\nLast Name: " + cLast_name + "\nEmail: " + cEmail + "\nBalance: " + cBalance + "\nPIN: " + cCustomerPin + "");
System.out.println("***************************************");
    }
    } 
System.out.println("Thank you for using The Bank of Java ATM. Have a great day!");
System.out.println("***************************************");
}

else if(viewCustomerInfo.equalsIgnoreCase("no")){
    System.out.println("***************************************");

    System.out.println("Thank you for using The Bank of Java ATM. Have a great day!");
    System.out.println("***************************************");

    validInput = false;
}
}



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
