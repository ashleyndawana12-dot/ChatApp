package com.mycompany.chatapp;

import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
 System.out.print("Enter your first name: ");
String firstName = input.nextLine();

System.out.print("Enter your last name: ");
String lastName = input.nextLine();  

System.out.print("Enter your username: ");
String username = input.nextLine();

System.out.print("Enter your password: ");
String password = input.nextLine();

System.out.print("Enter your cellphone number: ");
String cellphoneNumber = input.nextLine();

Login user = new Login(firstName, lastName, username, password, cellphoneNumber);
  
System.out.println(user.registerUser());

if (user.checkCellPhoneNumber()) {
    System.out.println("Cell number successfully captured.");
} else {
    System.out.println("Cell number is incorrectly formatted or does not contain an international code; please correct the number and try again.");
}

System.out.print("Enter your username to login: ");
String loginUsername = input.nextLine();

System.out.print("Enter your password to login: ");
String loginPassword = input.nextLine();

user.setLoginUsername(loginUsername);
user.setLoginPassword(loginPassword);

System.out.println(user.returnLoginStatus());
    }
}
