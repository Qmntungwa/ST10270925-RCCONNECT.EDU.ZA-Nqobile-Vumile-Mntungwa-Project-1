/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.st10270925_nqobile_vumile_mntungwa_assignment_one;

import java.util.Scanner;

/**
 *
 * @author Nqobi
 */
public class UserAuthentication {

    public static void main(String[] args) {
       
        Login system = new Login();
        Scanner sc = new Scanner(System.in);

        boolean isAccountCreated = false;

        System.out.println("Hi, please create an account");

        while (!isAccountCreated) {
            System.out.print("Enter first name: ");
            String firstName = sc.nextLine();
            system.setfirstname(firstName);
            
            System.out.print("Enter last name: ");
            String lastName = sc.nextLine();
            system.setlastname(lastName);
            
            System.out.print("Enter username: ");
            String username = sc.nextLine();
            system.setusername(username);
            
            System.out.print("Enter password: ");
            String password = sc.nextLine();
            system.setpassword(password);

            String registrationStatus = system.registerUser();
            System.out.println(registrationStatus);

            if (registrationStatus.contains("successfully")) {
                system.CreatAccount(username, password, firstName, lastName);
                isAccountCreated = true;
            }
        }

        System.out.println("Login:");

        boolean loginStatus = false;

        while (!loginStatus) {
            System.out.print("Enter username: ");
            String loginUsername = sc.nextLine();

            System.out.print("Enter password: ");
            String loginPassword = sc.nextLine();

            loginStatus = system.LoginUser(loginUsername, loginPassword);

            String loginMessage = system.returnLoginStatus(loginStatus);
            System.out.println(loginMessage);
        }
    }
}
