package com.rudbmsgroupproject.project_2.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class DatabaseService {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String dbUsername;

    @Value("${DB_PASSWORD}")
    private String dbPassword;

    public Connection connectToDatabase() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);

        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();  // Optionally print the full stack trace for debugging
        }
        return connection;
    }

    public List<String> getMsamdList() {
        return getListFromTable("msamd", "msamd_code", "msamd_name");
    }

    public List<String> getCountyList() {
        return getListFromTable("county", "county_code", "county_name");
    }

    public List<String> getLoanTypeList() {
        return getListFromTable("loan_type", "loan_type", "loan_type_name");
    }

    public List<String> getLoanPurposeList() {
        return getListFromTable("loan_purpose", "loan_purpose", "loan_purpose_name");
    }

    public List<String> getPropertyTypeList() {
        return getListFromTable("property_type", "property_type", "property_type_name");
    }

    private List<String> getListFromTable(String tableName, String idColumn, String nameColumn) {
        List<String> list = new ArrayList<>();
        String query = "SELECT " + idColumn + ", " + nameColumn + " FROM " + tableName;
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt(idColumn);
                String name = resultSet.getString(nameColumn);
                list.add(name);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data from " + tableName + ": " + e.getMessage());
            e.printStackTrace();  // Optionally print the full stack trace for debugging
        }
        return list;
    }

    public String getMsamdNameById(int id) {
        return getNameById("msamd", "msamd_code", "msamd_name", id);
    }

    public String getCountyNameById(int id) {
        return getNameById("county", "county_code", "county_name", id);
    }

    public String getLoanTypeNameById(int id) {
        return getNameById("loan_type", "loan_type", "loan_type_name", id);
    }

    public String getLoanPurposeNameById(int id) {
        return getNameById("loan_purpose", "loan_purpose", "loan_purpose_name", id);
    }

    public String getPropertyTypeNameById(int id) {
        return getNameById("property_type", "property_type", "property_type_name", id);
    }

    private String getNameById(String tableName, String idColumn, String nameColumn, Number id) {
        String query = "SELECT " + nameColumn + " FROM " + tableName + " WHERE " + idColumn + " = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString(nameColumn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving name from " + tableName + ": " + e.getMessage());
            e.printStackTrace();  // Optionally print the full stack trace for debugging
        }
        return null;
    }

    public Integer getMsamdIdByName(String name) {
        return getIdByName("msamd", "msamd_code", "msamd_name", name);
    }

    public Integer getCountyIdByName(String name) {
        return getIdByName("county", "county_code", "county_name", name);
    }

    public Integer getLoanTypeIdByName(String name) {
        return getIdByName("loan_type", "loan_type", "loan_type_name", name);
    }

    public Integer getLoanPurposeIdByName(String name) {
        return getIdByName("loan_purpose", "loan_purpose", "loan_purpose_name", name);
    }

    public Integer getPropertyTypeIdByName(String name) {
        return getIdByName("property_type", "property_type", "property_type_name", name);
    }

    private <T> T getIdByName(String tableName, String idColumn, String nameColumn, String name) {
        String query = "SELECT " + idColumn + " FROM " + tableName + " WHERE " + nameColumn + " = ?";
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return (T) resultSet.getObject(idColumn);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving ID from " + tableName + ": " + e.getMessage());
            e.printStackTrace();  // Optionally print the full stack trace for debugging
        }
        return null;
    }
}