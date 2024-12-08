package com.rudbmsgroupproject.project_2.cli;

import com.rudbmsgroupproject.project_2.model.Application;
import com.rudbmsgroupproject.project_2.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Scanner;

@ShellComponent
public class AppCommands {

    @Autowired
    private ApplicationService applicationService;

    @ShellMethod("Display the main menu")
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1) Search for mortgages");
            System.out.println("2) Calculate the rate");
            System.out.println("3) Package the mortgages");
            System.out.println("0) Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    searchMortgages((short) 1); // Default actionTaken value
                    break;
                case 2:
                    calculateRate();
                    break;
                case 3:
                    packageMortgages();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    @ShellMethod("Search for mortgages by action taken")
    public void searchMortgages(@ShellOption(defaultValue = "1") Short actionTaken) {
        List<Application> applications = applicationService.getApplicationsWithActionTaken(actionTaken);
        applications.forEach(application -> {
            System.out.println("Application ID: " + application.getApplicationId());
            System.out.println("Applicant Income: " + application.getApplicantIncome000s());
            System.out.println("Rate Spread: " + application.getRateSpread());
            System.out.println("Location Code: " + application.getLocationCode());
            System.out.println("----------------------------");
        });
    }

    @ShellMethod("Calculate the rate")
    public void calculateRate() {
        // Implement rate calculation logic here
        System.out.println("Rate calculation is not implemented yet.");
    }

    @ShellMethod("Package the mortgages")
    public void packageMortgages() {
        // Implement mortgage packaging logic here
        System.out.println("Mortgage packaging is not implemented yet.");
    }
}