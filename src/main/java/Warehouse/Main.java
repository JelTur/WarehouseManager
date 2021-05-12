package Warehouse;

import java.sql.*;
import java.util.Scanner;

import static Warehouse.SQL.*;


public class Main {
    // JDBC driver name and database URL
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:./Warehouse";

    //  Database credentials
    private static final String USER = "sa";
    private static final String PASS = "";

    private static Scanner scanner = new Scanner(System.in);

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
        try (Connection connection = getConnection()) {
            prepareDatabase(connection);
            workWithConnection(connection);
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            throwables.printStackTrace();
        }
    }

    private static void prepareDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE_LOCATION);
            statement.executeUpdate(CREATE_TABLE_PRODUCT);
            statement.executeUpdate(CREATE_TABLE_LOCATION);
            statement.executeUpdate(INSERT_INTO_LOCATION);
            statement.executeUpdate(INSERT_INTO_PRODUCT);
        }
    }

    private static void workWithConnection(Connection connection) throws SQLException {
        while (true) {
            WarehouseAction nextAction = getActionFromUser();
            switch (nextAction) {
                case PRINT_ALL_PRODUCTS:
                    break;
                case PRINT_ALL_REFRIDGERATOR_PRODUCTS:
                    break;
                case PRINT_ALL_FREEZER_PRODUCTS:
                    break;
                case PRINT_ALL_SHELVES_PRODUCTS:
                    break;
                case ADD_PRODUCTS:
                    break;
                case REMOVE_PRODUCTS:
                    break;
                case PRINT_BY_EXPIRY_DATE:
                    break;
                case PRINT_ALL_PRODUCTS_WITH_EXPIRY_DATE_LESS_THAN_3_DAYS:
                    break;
                case ORDER_PRODUCTS:
                    break;
                case EXIT:
                    return;
            }
        }
    }

    private static WarehouseAction getActionFromUser() {
        while (true) {
            System.out.println("Please select an action");
            for (int i = 0; i < WarehouseAction.values().length; i++) {
                System.out.println(i + "\t" + WarehouseAction.values()[i]);
            }
            // scanner.nextInt(); - would not read new line characters
            int selected = Integer.parseInt(scanner.nextLine());
            if (WarehouseAction.values().length <= selected || selected < 0) {
                System.out.println("You entered incorrect value");
                continue;
            }
            return WarehouseAction.values()[selected];
        }
    }
}

