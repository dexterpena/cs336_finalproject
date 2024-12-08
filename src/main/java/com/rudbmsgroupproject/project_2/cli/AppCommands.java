package com.rudbmsgroupproject.project_2.cli;

import com.rudbmsgroupproject.project_2.model.Preliminary;
import com.rudbmsgroupproject.project_2.service.PreliminaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ShellComponent
public class AppCommands {

    @Autowired
    private PreliminaryService preliminaryService;

    private List<Integer> msamd = new ArrayList<>();
    private Double minIncomeDebtRatio;
    private Double maxIncomeDebtRatio;
    private List<Integer> counties = new ArrayList<>();
    private List<Short> loanTypes = new ArrayList<>();
    private Double minTractIncome;
    private Double maxTractIncome;
    private List<Short> loanPurposes = new ArrayList<>();
    private List<Short> propertyTypes = new ArrayList<>();
    private Short ownerOccupancy;

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
                    searchMortgagesMenu();
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

    private void searchMortgagesMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Search Mortgages Menu:");
            System.out.println("1) Add filter");
            System.out.println("2) Apply filter");
            System.out.println("3) Clear filter");
            System.out.println("0) Back to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addFilterMenu();
                    break;
                case 2:
                    applyFilter();
                    break;
                case 3:
                    clearFilter();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addFilterMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Add Filter Menu:");
            System.out.println("1) Add MSAMD filter");
            System.out.println("2) Add Income Debt Ratio filter");
            System.out.println("3) Add County filter");
            System.out.println("4) Add Loan Type filter");
            System.out.println("5) Add Tract Income filter");
            System.out.println("6) Add Loan Purpose filter");
            System.out.println("7) Add Property Type filter");
            System.out.println("8) Add Owner Occupancy filter");
            System.out.println("0) Back to search mortgages menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    addMsamdFilter();
                    break;
                case 2:
                    addIncomeDebtRatioFilter();
                    break;
                case 3:
                    addCountyFilter();
                    break;
                case 4:
                    addLoanTypeFilter();
                    break;
                case 5:
                    addTractIncomeFilter();
                    break;
                case 6:
                    addLoanPurposeFilter();
                    break;
                case 7:
                    addPropertyTypeFilter();
                    break;
                case 8:
                    addOwnerOccupancyFilter();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addMsamdFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter MSAMD code: ");
        int code = scanner.nextInt();
        msamd.add(code);
        System.out.println("MSAMD filter added.");
    }

    private void addIncomeDebtRatioFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum income debt ratio: ");
        minIncomeDebtRatio = scanner.nextDouble();
        System.out.print("Enter maximum income debt ratio: ");
        maxIncomeDebtRatio = scanner.nextDouble();
        System.out.println("Income debt ratio filter added.");
    }

    private void addCountyFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter county code: ");
        int code = scanner.nextInt();
        counties.add(code);
        System.out.println("County filter added.");
    }

    private void addLoanTypeFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter loan type: ");
        short type = scanner.nextShort();
        loanTypes.add(type);
        System.out.println("Loan type filter added.");
    }

    private void addTractIncomeFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter minimum tract income: ");
        minTractIncome = scanner.nextDouble();
        System.out.print("Enter maximum tract income: ");
        maxTractIncome = scanner.nextDouble();
        System.out.println("Tract income filter added.");
    }

    private void addLoanPurposeFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter loan purpose: ");
        short purpose = scanner.nextShort();
        loanPurposes.add(purpose);
        System.out.println("Loan purpose filter added.");
    }

    private void addPropertyTypeFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter property type: ");
        short type = scanner.nextShort();
        propertyTypes.add(type);
        System.out.println("Property type filter added.");
    }

    private void addOwnerOccupancyFilter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter owner occupancy: ");
        ownerOccupancy = scanner.nextShort();
        System.out.println("Owner occupancy filter added.");
    }

    private void applyFilter() {
        List<Preliminary> preliminaries = preliminaryService.getFilteredPreliminaries(
                msamd, minIncomeDebtRatio, maxIncomeDebtRatio, counties, loanTypes, minTractIncome, maxTractIncome, loanPurposes, propertyTypes, ownerOccupancy);
        preliminaries.forEach(preliminary -> {
            System.out.println("Application ID: " + preliminary.getApplicationId());
            System.out.println("Applicant Income: " + preliminary.getApplicantIncome000s());
            System.out.println("Rate Spread: " + preliminary.getRateSpread());
            System.out.println("Location Code: " + preliminary.getLocationCode());
            System.out.println("----------------------------");
        });
    }

    private void clearFilter() {
        msamd.clear();
        minIncomeDebtRatio = null;
        maxIncomeDebtRatio = null;
        counties.clear();
        loanTypes.clear();
        minTractIncome = null;
        maxTractIncome = null;
        loanPurposes.clear();
        propertyTypes.clear();
        ownerOccupancy = null;
        System.out.println("All filters cleared.");
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