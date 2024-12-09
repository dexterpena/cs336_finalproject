package com.rudbmsgroupproject.project_2.cli;

import com.rudbmsgroupproject.project_2.model.Preliminary;
import com.rudbmsgroupproject.project_2.service.DatabaseService;
import com.rudbmsgroupproject.project_2.service.PreliminaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@ShellComponent
public class AppCommands {

    @Autowired
    private PreliminaryService preliminaryService;

    @Autowired
    private DatabaseService databaseService;

    private List<Integer> msamd = new ArrayList<>();
    private Double minIncomeDebtRatio;
    private Double maxIncomeDebtRatio;
    private List<Integer> counties = new ArrayList<>();
    private List<Integer> loanTypes = new ArrayList<>();
    private Double minTractIncome;
    private Double maxTractIncome;
    private List<Integer> loanPurposes = new ArrayList<>();
    private List<Integer> propertyTypes = new ArrayList<>();
    private Integer ownerOccupancy;

    private List<Integer> msamdIds = new ArrayList<>();
    private List<Integer> countyIds = new ArrayList<>();
    private List<Integer> loanTypeIds = new ArrayList<>();
    private List<Integer> loanPurposeIds = new ArrayList<>();
    private List<Integer> propertyTypeIds = new ArrayList<>();

    @ShellMethod("Display the main menu")
    public void menu() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n*** Main Menu: ***");
                System.out.println("1) Search for mortgages");
                System.out.println("2) Create a mortgage");
                System.out.println("0) Exit");
                System.out.print("\nEnter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        searchMortgagesMenu(scanner);
                        break;
                    case 2:
                        createMortgage();
                        break;
                    case 0:
                        System.out.println("\nExiting...");
                        return;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            }
        }
    }

    private void searchMortgagesMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n*** Search Mortgages Menu: *** ");
            displayCurrentFilters();
            System.out.println();
            System.out.println("1) Add filter");
            System.out.println("2) Apply filter");
            System.out.println("3) Clear filter");
            System.out.println("4) Calculate Rate");
            System.out.println("0) Back to main menu");
            System.out.print("\nEnter your choice: ");
            try {
                if (!scanner.hasNextInt()) {
                    throw new InputMismatchException("Invalid input. Please enter a valid number.");
                }
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                switch (choice) {
                    case 1:
                        addFilterMenu(scanner);
                        break;
                    case 2:
                        applyFilter();
                        break;
                    case 3:
                        clearFilter(scanner);
                        break;
                    case 4:
                        calculateRate();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("\nInvalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nInvalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            } catch (NoSuchElementException e) {
                System.out.println("\nNo input found. Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private void addFilterMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n*** Add Filter Menu: ***");
            System.out.println("1) Add MSAMD filter");
            System.out.println("2) Add Income Debt Ratio filter");
            System.out.println("3) Add County filter");
            System.out.println("4) Add Loan Type filter");
            System.out.println("5) Add Tract Income filter");
            System.out.println("6) Add Loan Purpose filter");
            System.out.println("7) Add Property Type filter");
            System.out.println("8) Add Owner Occupancy filter");
            System.out.println("0) Back to search mortgages menu");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayMsamdList();
                    addMsamdFilter(scanner);
                    break;
                case 2:
                    addIncomeDebtRatioFilter(scanner);
                    break;
                case 3:
                    displayCountyList();
                    addCountyFilter(scanner);
                    break;
                case 4:
                    displayLoanTypeList();
                    addLoanTypeFilter(scanner);
                    break;
                case 5:
                    addTractIncomeFilter(scanner);
                    break;
                case 6:
                    displayLoanPurposeList();
                    addLoanPurposeFilter(scanner);
                    break;
                case 7:
                    displayPropertyTypeList();
                    addPropertyTypeFilter(scanner);
                    break;
                case 8:
                    addOwnerOccupancyFilter(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private void displayMsamdList() {
        List<String> msamdList = databaseService.getMsamdList();
        msamdIds.clear();
        System.out.println("\nMSAMD List:");
        for (int i = 0; i < msamdList.size(); i++) {
            msamdIds.add(databaseService.getMsamdIdByName(msamdList.get(i)));
            System.out.println((i + 1) + ") " + msamdList.get(i));
        }
    }

    private void displayCountyList() {
        List<String> countyList = databaseService.getCountyList();
        countyIds.clear();
        System.out.println("\nCounty List:");
        for (int i = 0; i < countyList.size(); i++) {
            countyIds.add(databaseService.getCountyIdByName(countyList.get(i)));
            System.out.println((i + 1) + ") " + countyList.get(i));
        }
    }

    private void displayLoanTypeList() {
        List<String> loanTypeList = databaseService.getLoanTypeList();
        loanTypeIds.clear();
        System.out.println("\nLoan Type List:");
        for (int i = 0; i < loanTypeList.size(); i++) {
            loanTypeIds.add(databaseService.getLoanTypeIdByName(loanTypeList.get(i)));
            System.out.println((i + 1) + ") " + loanTypeList.get(i));
        }
    }

    private void displayLoanPurposeList() {
        List<String> loanPurposeList = databaseService.getLoanPurposeList();
        loanPurposeIds.clear();
        System.out.println("\nLoan Purpose List:");
        for (int i = 0; i < loanPurposeList.size(); i++) {
            loanPurposeIds.add(databaseService.getLoanPurposeIdByName(loanPurposeList.get(i)));
            System.out.println((i + 1) + ") " + loanPurposeList.get(i));
        }
    }

    private void displayPropertyTypeList() {
        List<String> propertyTypeList = databaseService.getPropertyTypeList();
        propertyTypeIds.clear();
        System.out.println("\nProperty Type List:");
        for (int i = 0; i < propertyTypeList.size(); i++) {
            propertyTypeIds.add(databaseService.getPropertyTypeIdByName(propertyTypeList.get(i)));
            System.out.println((i + 1) + ") " + propertyTypeList.get(i));
        }
    }

    private void addMsamdFilter(Scanner scanner) {
        System.out.print("\nEnter MSAMD number: ");
        int number = scanner.nextInt();
        if (number > 0 && number <= msamdIds.size()) {
            msamd.add(msamdIds.get(number - 1));
            System.out.println("\nMSAMD filter added.");
        } else {
            System.out.println("\nInvalid MSAMD number.");
        }
    }

    private void addIncomeDebtRatioFilter(Scanner scanner) {
        System.out.print("\nEnter minimum income debt ratio: ");
        minIncomeDebtRatio = scanner.nextDouble();
        System.out.print("\nEnter maximum income debt ratio: ");
        maxIncomeDebtRatio = scanner.nextDouble();
        System.out.println("\nIncome debt ratio filter added.");
    }

    private void addCountyFilter(Scanner scanner) {
        System.out.print("\nEnter County number: ");
        int number = scanner.nextInt();
        if (number > 0 && number <= countyIds.size()) {
            counties.add(countyIds.get(number - 1));
            System.out.println("\nCounty filter added.");
        } else {
            System.out.println("\nInvalid County number.");
        }
    }

    private void addLoanTypeFilter(Scanner scanner) {
        System.out.print("\nEnter Loan Type number: ");
        int number = scanner.nextInt();
        if (number > 0 && number <= loanTypeIds.size()) {
            loanTypes.add(loanTypeIds.get(number - 1));
            System.out.println("\nLoan Type filter added.");
        } else {
            System.out.println("\nInvalid Loan Type number.");
        }
    }

    private void addTractIncomeFilter(Scanner scanner) {
        System.out.print("\nEnter minimum tract income: ");
        minTractIncome = scanner.nextDouble();
        System.out.print("\nEnter maximum tract income: ");
        maxTractIncome = scanner.nextDouble();
        System.out.println("\nTract income filter added.");
    }

    private void addLoanPurposeFilter(Scanner scanner) {
        System.out.print("\nEnter Loan Purpose number: ");
        int number = scanner.nextInt();
        if (number > 0 && number <= loanPurposeIds.size()) {
            loanPurposes.add(loanPurposeIds.get(number - 1));
            System.out.println("\nLoan Purpose filter added.");
        } else {
            System.out.println("\nInvalid Loan Purpose number.");
        }
    }

    private void addPropertyTypeFilter(Scanner scanner) {
        System.out.print("\nEnter Property Type number: ");
        int number = scanner.nextInt();
        if (number > 0 && number <= propertyTypeIds.size()) {
            propertyTypes.add(propertyTypeIds.get(number - 1));
            System.out.println("\nProperty Type filter added.");
        } else {
            System.out.println("\nInvalid Property Type number.");
        }
    }

    private void addOwnerOccupancyFilter(Scanner scanner) {
        System.out.print("\nEnter owner occupancy: ");
        ownerOccupancy = scanner.nextInt();
        System.out.println("\nOwner occupancy filter added.");
    }

    private void displayCurrentFilters() {
        System.out.println("*** Current Filters: ***");

        boolean hasFilters = false;

        if (!msamd.isEmpty()) {
            List<String> msamdNames = msamd.stream()
                    .map(id -> id + ": " + databaseService.getMsamdNameById(id))
                    .collect(Collectors.toList());
            System.out.println("MSAMD: " + String.join(", ", msamdNames));
            hasFilters = true;
        }
        if (minIncomeDebtRatio != null && maxIncomeDebtRatio != null) {
            System.out.println("Income Debt Ratio: " + minIncomeDebtRatio + " - " + maxIncomeDebtRatio);
            hasFilters = true;
        }
        if (!counties.isEmpty()) {
            List<String> countyNames = counties.stream()
                    .map(id -> id + ": " + databaseService.getCountyNameById(id))
                    .collect(Collectors.toList());
            System.out.println("Counties: " + String.join(", ", countyNames));
            hasFilters = true;
        }
        if (!loanTypes.isEmpty()) {
            List<String> loanTypeNames = loanTypes.stream()
                    .map(id -> id + ": " + databaseService.getLoanTypeNameById(id))
                    .collect(Collectors.toList());
            System.out.println("Loan Types: " + String.join(", ", loanTypeNames));
            hasFilters = true;
        }
        if (minTractIncome != null && maxTractIncome != null) {
            System.out.println("Tract Income: " + minTractIncome + " - " + maxTractIncome);
            hasFilters = true;
        }
        if (!loanPurposes.isEmpty()) {
            List<String> loanPurposeNames = loanPurposes.stream()
                    .map(id -> id + ": " + databaseService.getLoanPurposeNameById(id))
                    .collect(Collectors.toList());
            System.out.println("Loan Purposes: " + String.join(", ", loanPurposeNames));
            hasFilters = true;
        }
        if (!propertyTypes.isEmpty()) {
            List<String> propertyTypeNames = propertyTypes.stream()
                    .map(id -> id + ": " + databaseService.getPropertyTypeNameById(id))
                    .collect(Collectors.toList());
            System.out.println("Property Types: " + String.join(", ", propertyTypeNames));
            hasFilters = true;
        }
        if (ownerOccupancy != null) {
            System.out.println("Owner Occupancy: " + ownerOccupancy);
            hasFilters = true;
        }

        if (!hasFilters) {
            System.out.println("None");
        }
    }

    private void applyFilter() {
        System.out.println();
        displayCurrentFilters();
        List<Preliminary> preliminaries;

        preliminaries = preliminaryService.getFilteredPreliminaries(
                msamd.isEmpty() ? null : msamd,
                minIncomeDebtRatio,
                maxIncomeDebtRatio,
                counties.isEmpty() ? null : counties,
                loanTypes.isEmpty() ? null : loanTypes,
                minTractIncome,
                maxTractIncome,
                loanPurposes.isEmpty() ? null : loanPurposes,
                propertyTypes.isEmpty() ? null : propertyTypes,
                ownerOccupancy
        );

        int numberOfRows = preliminaries.size();
        int sumOfLoanAmounts = preliminaries.stream()
                .mapToInt(preliminary -> preliminary.getLoanAmount000s() != null ? preliminary.getLoanAmount000s() : 0)
                .sum();

        if (preliminaries.isEmpty()) {
            System.out.println("\nNo results found.");
        } else {
            preliminaries.forEach(preliminary -> {
                System.out.println("\nApplication ID: " + preliminary.getApplicationId());
                System.out.println("As Of Year: " + preliminary.getAsOfYear());
                System.out.println("Respondent ID: " + preliminary.getRespondentId());
                System.out.println("Agency Name: " + preliminary.getAgencyName());
                System.out.println("Agency Abbr: " + preliminary.getAgencyAbbr());
                System.out.println("Loan Type Name: " + preliminary.getLoanTypeName());
                System.out.println("Property Type Name: " + preliminary.getPropertyTypeName());
                System.out.println("Loan Purpose Name: " + preliminary.getLoanPurposeName());
                System.out.println("Owner Occupancy Name: " + preliminary.getOwnerOccupancyName());
                System.out.println("Loan Amount (000s): " + preliminary.getLoanAmount000s());
                System.out.println("Preapproval Name: " + preliminary.getPreapprovalName());
                System.out.println("Action Taken Name: " + preliminary.getActionTakenName());
                System.out.println("MSAMD Name: " + preliminary.getMsamdName());
                System.out.println("MSAMD: " + preliminary.getMsamd());
                System.out.println("State Name: " + preliminary.getStateName());
                System.out.println("State Abbr: " + preliminary.getStateAbbr());
                System.out.println("County Name: " + preliminary.getCountyName());
                System.out.println("Census Tract Number: " + preliminary.getCensusTractNumber());
                System.out.println("Applicant Ethnicity Name: " + preliminary.getApplicantEthnicityName());
                System.out.println("Co-Applicant Ethnicity Name: " + preliminary.getCoApplicantEthnicityName());
                System.out.println("Applicant Race Name 1: " + preliminary.getApplicantRaceName1());
                System.out.println("Applicant Race Name 2: " + preliminary.getApplicantRaceName2());
                System.out.println("Applicant Race Name 3: " + preliminary.getApplicantRaceName3());
                System.out.println("Applicant Race Name 4: " + preliminary.getApplicantRaceName4());
                System.out.println("Applicant Race Name 5: " + preliminary.getApplicantRaceName5());
                System.out.println("Co-Applicant Race Name 1: " + preliminary.getCoApplicantRaceName1());
                System.out.println("Co-Applicant Race Name 2: " + preliminary.getCoApplicantRaceName2());
                System.out.println("Co-Applicant Race Name 3: " + preliminary.getCoApplicantRaceName3());
                System.out.println("Co-Applicant Race Name 4: " + preliminary.getCoApplicantRaceName4());
                System.out.println("Co-Applicant Race Name 5: " + preliminary.getCoApplicantRaceName5());
                System.out.println("Applicant Sex Name: " + preliminary.getApplicantSexName());
                System.out.println("Co-Applicant Sex Name: " + preliminary.getCoApplicantSexName());
                System.out.println("Applicant Income (000s): " + preliminary.getApplicantIncome000s());
                System.out.println("Purchaser Type Name: " + preliminary.getPurchaserTypeName());
                System.out.println("Denial Reason Name 1: " + preliminary.getDenialReasonName1());
                System.out.println("Denial Reason Name 2: " + preliminary.getDenialReasonName2());
                System.out.println("Denial Reason Name 3: " + preliminary.getDenialReasonName3());
                System.out.println("Rate Spread: " + preliminary.getRateSpread());
                System.out.println("HOEPA Status Name: " + preliminary.getHoepaStatusName());
                System.out.println("Lien Status Name: " + preliminary.getLienStatusName());
                System.out.println("Edit Status Name: " + preliminary.getEditStatusName());
                System.out.println("Sequence Number: " + preliminary.getSequenceNumber());
                System.out.println("Population: " + preliminary.getPopulation());
                System.out.println("Minority Population: " + preliminary.getMinorityPopulation());
                System.out.println("HUD Median Family Income: " + preliminary.getHudMedianFamilyIncome());
                System.out.println("Tract to MSAMD Income: " + preliminary.getTractToMsamdIncome());
                System.out.println("Number of Owner Occupied Units: " + preliminary.getNumberOfOwnerOccupiedUnits());
                System.out.println("Number of 1 to 4 Family Units: " + preliminary.getNumberOf1To4FamilyUnits());
                System.out.println("Application Date Indicator: " + preliminary.getApplicationDateIndicator());
                System.out.println("----------------------------");
            });
            System.out.println("\nNumber of rows: " + numberOfRows);
            System.out.println("Sum of Loan Amounts (000s): " + sumOfLoanAmounts);
        }
    }

    private void clearFilter(Scanner scanner) {
        while (true) {
            System.out.println();
            displayCurrentFilters();
            System.out.println("\n*** Clear Filter Menu: ***");
            System.out.println("1) Clear all filters");
            System.out.println("2) Clear MSAMD filters");
            System.out.println("3) Clear Income Debt Ratio filter");
            System.out.println("4) Clear County filters");
            System.out.println("5) Clear Loan Type filters");
            System.out.println("6) Clear Tract Income filter");
            System.out.println("7) Clear Loan Purpose filters");
            System.out.println("8) Clear Property Type filters");
            System.out.println("9) Clear Owner Occupancy filter");
            System.out.println("0) Back to search mortgages menu");
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    clearAllFilters();
                    System.out.println("\nAll filters cleared.");
                    break;
                case 2:
                    msamd.clear();
                    System.out.println("\nMSAMD filters cleared.");
                    break;
                case 3:
                    minIncomeDebtRatio = null;
                    maxIncomeDebtRatio = null;
                    System.out.println("\nIncome Debt Ratio filter cleared.");
                    break;
                case 4:
                    counties.clear();
                    System.out.println("\nCounty filters cleared.");
                    break;
                case 5:
                    loanTypes.clear();
                    System.out.println("\nLoan Type filters cleared.");
                    break;
                case 6:
                    minTractIncome = null;
                    maxTractIncome = null;
                    System.out.println("\nTract Income filter cleared.");
                    break;
                case 7:
                    loanPurposes.clear();
                    System.out.println("\nLoan Purpose filters cleared.");
                    break;
                case 8:
                    propertyTypes.clear();
                    System.out.println("\nProperty Type filters cleared.");
                    break;
                case 9:
                    ownerOccupancy = null;
                    System.out.println("\nOwner Occupancy filter cleared.");
                    break;
                case 0:
                    return;
                default:
                    System.out.println("\nInvalid choice. Please try again.");
            }
        }
    }

    private void clearAllFilters() {
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
    }

    @ShellMethod("Calculate the rate")
    public void calculateRate() {
        List<Preliminary> preliminaries = preliminaryService.getFilteredPreliminaries(
                msamd.isEmpty() ? null : msamd,
                minIncomeDebtRatio,
                maxIncomeDebtRatio,
                counties.isEmpty() ? null : counties,
                loanTypes.isEmpty() ? null : loanTypes,
                minTractIncome,
                maxTractIncome,
                loanPurposes.isEmpty() ? null : loanPurposes,
                propertyTypes.isEmpty() ? null : propertyTypes,
                ownerOccupancy
        );

        if (preliminaries.isEmpty()) {
            System.out.println("\nNo data available to calculate the rate.");
            return;
        }

        double baseRate = 2.33;
        double totalWeightedRate = 0;
        double totalLoanAmount = 0;

        for (Preliminary preliminary : preliminaries) {
            Integer loanAmount = preliminary.getLoanAmount000s();
            if (loanAmount == null) {
                continue; // Skip this entry if loanAmount is null
            }

            double rateSpread = preliminary.getRateSpread() != null ? preliminary.getRateSpread() : 0;
            if (rateSpread == 0) {
                if (preliminary.getLienStatus() == 1) {
                    rateSpread = 1.5;
                } else if (preliminary.getLienStatus() == 2) {
                    rateSpread = 3.5;
                }
            }
            double rate = baseRate + rateSpread;
            totalWeightedRate += rate * loanAmount;
            totalLoanAmount += loanAmount;
        }

        if (totalLoanAmount == 0) {
            System.out.println("\nNo valid loan amounts available to calculate the rate.");
            return;
        }

        double weightedAverageRate = totalWeightedRate / totalLoanAmount;
        weightedAverageRate = Math.round(weightedAverageRate * 100.0) / 100.0;
        totalLoanAmount = Math.round(totalLoanAmount * 100.0) / 100.0;

        System.out.println("\nWeighted Average Rate: " + weightedAverageRate + "%");
        System.out.println("Total Cost of Securitization: " + totalLoanAmount + " (000s)");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Do you accept the rate and total cost? (yes/no): ");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                try (Connection connection = databaseService.connectToDatabase()) {
                    connection.setAutoCommit(false);
                    try {
                        for (Preliminary preliminary : preliminaries) {
                            String updateQuery = "UPDATE preliminary SET purchaser_type = ? WHERE application_id = ?";
                            try (PreparedStatement statement = connection.prepareStatement(updateQuery)) {
                                statement.setShort(1, (short) 5); // Assuming 5 is the code for private securitization
                                statement.setInt(2, preliminary.getApplicationId());
                                statement.executeUpdate();
                            }
                        }
                        connection.commit();
                        System.out.println("Rate accepted and database updated.");
                    } catch (SQLException e) {
                        connection.rollback();
                        System.err.println("Error updating database: " + e.getMessage());
                    }
                } catch (SQLException e) {
                    System.err.println("Database connection error: " + e.getMessage());
                }
            } else {
                System.out.println("Rate declined. Returning to main menu.");
            }
        }
    }

    @ShellMethod("Create a mortgage")
    public void createMortgage() {
        // Implement mortgage creation logic here
        System.out.println("\nMortgage creation is not implemented yet.");
    }

} // End of class AppCommands