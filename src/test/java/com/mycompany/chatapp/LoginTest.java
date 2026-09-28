/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.chatapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoginTest {

    // Test 1: Username is correctly formatted
    @Test
    public void testCheckUserNameCorrect() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        assertEquals(true, user.checkUserName());
    }

    // Test 2: Username is incorrectly formatted
    @Test
    public void testCheckUserNameIncorrect() {
        Login user = new Login("Ashley", "Ndawana", "kyle!!!!!!",
                "Ch&&sec@ke99!", "+27838968976");

        assertEquals(false, user.checkUserName());
    }

    // Test 3: Password meets complexity requirements
    @Test
    public void testPasswordComplexityCorrect() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        assertEquals(true, user.checkPasswordComplexity());
    }

    // Test 4: Password does not meet complexity requirements
    @Test
    public void testPasswordComplexityIncorrect() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "password", "+27838968976");

        assertEquals(false, user.checkPasswordComplexity());
    }

    // Test 5: Cellphone number is correctly formatted
    @Test
    public void testCellPhoneNumberCorrect() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        assertEquals(true, user.checkCellPhoneNumber());
    }

    // Test 6: Cellphone number is incorrectly formatted
    @Test
    public void testCellPhoneNumberIncorrect() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "08966553");

        assertEquals(false, user.checkCellPhoneNumber());
    }

    // Test 7: Login is successful
    @Test
    public void testLoginSuccessful() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        user.setLoginUsername("Kyl_1");
        user.setLoginPassword("Ch&&sec@ke99!");

        assertEquals(true, user.loginUser());
    }

    // Test 8: Login fails with incorrect details
    @Test
    public void testLoginFailed() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        user.setLoginUsername("wrong_1");
        user.setLoginPassword("wrongpassword");

        assertEquals(false, user.loginUser());
    }

    // Test 9: Registration is successful
    @Test
    public void testRegisterUserSuccessful() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        assertEquals("User has been successfully registered.",
                user.registerUser());
    }

    // Test 10: Login status message is correct
    @Test
    public void testReturnLoginStatusSuccessful() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        user.setLoginUsername("Kyl_1");
        user.setLoginPassword("Ch&&sec@ke99!");

        assertEquals("Welcome Ashley, Ndawana it is great to see you again.",
                user.returnLoginStatus());
    }

    // Test 11: Failed login status message is correct
    @Test
    public void testReturnLoginStatusFailed() {
        Login user = new Login("Ashley", "Ndawana", "Kyl_1",
                "Ch&&sec@ke99!", "+27838968976");

        user.setLoginUsername("wrong_1");
        user.setLoginPassword("wrongpassword");

        assertEquals("Username or password incorrect, please try again.",
                user.returnLoginStatus());
    }
}