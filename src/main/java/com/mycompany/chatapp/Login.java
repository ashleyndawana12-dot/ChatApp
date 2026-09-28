/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

/**
 *
 * @author ashle
 */
public class Login {
   private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellphoneNumber; 
    private String loginUsername;
    private String loginPassword;
    
    public Login(String firstName, String lastName, String username,
             String password, String cellphoneNumber) {
    
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.cellphoneNumber = cellphoneNumber;

}
public boolean checkUserName() {
    return username.contains("_") && username.length() <= 5;
}

public boolean checkPasswordComplexity() {
    
    boolean hasCapital = false;
    boolean hasNumber = false;
    boolean hasSpecial = false;
    
    for (int i = 0; i < password.length(); i++) {
        char character = password.charAt(i);
        
        if (Character.isUpperCase(character)) {
            hasCapital = true;
        }
        
        if (Character.isDigit(character)) {
            hasNumber = true;
        }
        
        if (!Character.isLetterOrDigit(character)) {
            hasSpecial = true;
        }
    }
    
    return password.length() >= 8 && hasCapital && hasNumber && hasSpecial;
} 
// Reference: Oracle Java Pattern documentation, Java SE.
public boolean checkCellPhoneNumber() {
    return cellphoneNumber.matches("^\\+27[0-9]{9}$");
}

public String registerUser() {
    
    if (!checkUserName()) {
        return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
    }
    
    if (!checkPasswordComplexity()) {
        return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
    }
    
 return "User has been successfully registered.";
} 

public void setLoginUsername(String loginUsername) {
    this.loginUsername = loginUsername;
}

public void setLoginPassword(String loginPassword) {
    this.loginPassword = loginPassword;
}
public boolean loginUser() {
 if (loginUsername.equals(username) && loginPassword.equals(password)) {
        return true;
    } else {
        return false;
    }    
}
public String returnLoginStatus() {
    
    if (loginUser()) {
        return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
    } else {
        return "Username or password incorrect, please try again.";
    }
}
} 

