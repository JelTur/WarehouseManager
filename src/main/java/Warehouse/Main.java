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
            ResultSet result = statement.executeQuery("SHOW TABLES;");
            boolean locationTableExists = false;
            boolean productsTableExists = false;
            while (result.next()) {
                if (!locationTableExists) {
                    locationTableExists = result.getString("TABLE_NAME").equalsIgnoreCase("location");
                }
                if (!productsTableExists) {
                    productsTableExists = result.getString("TABLE_NAME").equalsIgnoreCase("products");
                }
            }

            if (!locationTableExists) {
                statement.executeUpdate(CREATE_TABLE_LOCATION);
                statement.executeUpdate(INSERT_INTO_LOCATION);
            }
            if (!productsTableExists) {
                statement.executeUpdate(CREATE_TABLE_PRODUCTS);
                statement.executeUpdate(INSERT_INTO_PRODUCTS);
            }
        }
    }

    private static void workWithConnection(Connection connection) throws SQLException {
        while (true) {
            WarehouseAction nextAction = getActionFromUser();
            switch (nextAction) {
                case PRINT_ALL_PRODUCTS:
                    printAllProducts(connection);
                    break;
                case PRINT_ALL_REFRIDGERATOR_PRODUCTS:
                    printAllRefrigeratorProducts(connection);
                    break;
                case PRINT_ALL_FREEZER_PRODUCTS:
                    printAllFreezerProducts(connection);
                    break;
                case PRINT_ALL_SHELVES_PRODUCTS:
                    printAllShelvesProducts(connection);
                    break;
                case ADD_PRODUCTS:
                    addProducts(connection);
                    break;
                case REMOVE_PRODUCTS:
                    removeProducts(connection);
                    break;
                case PRINT_BY_EXPIRED_DATE:
                    printByExpiryDate(connection);
                    break;
                case PRINT_ALL_PRODUCTS_WITH_EXPIRY_DATE_LESS_THAN_3_DAYS:
                    printAllProductsExpiry3days(connection);
                    break;
                case ORDER_PRODUCTS:
                    orderProducts(connection);
                    break;

                case EXIT:
                    return;
            }
        }
    }

    private static void printAllProducts(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PRINT_ALL_PRODUCTS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void printAllRefrigeratorProducts(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PRINT_ALL_REFRIDGERATOR_PRODUCTS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void printAllFreezerProducts(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PRINT_ALL_FREEZER_PRODUCTS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void printAllShelvesProducts(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PRINT_ALL_SHELVES_PRODUCTS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void printByExpiryDate(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PRINT_BY_EXPIRED_DATE)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void orderProducts(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(ORDER_PRODUCTS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void printAllProductsExpiry3days(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(PRINT_ALL_PRODUCTS_WITH_EXPIRY_DATE_LESS_THAN_3_DAYS)) {
                printAllColumns(resultSet);
            }
        }
    }

    private static void removeProducts(Connection connection) throws SQLException {
        System.out.println("Select which product to remove...");
        printAllProducts(connection);
        System.out.print("Enter an ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_PRODUCTS)) {
            preparedStatement.setInt(1, id);
            int update = preparedStatement.executeUpdate();
            if (update == 1) {
                System.out.println("Successfully deleted one row");
            } else if (update == 0) {
                System.out.println("Nothing was deleted. Probably ID is wrong!");
            } else {
                System.out.println("Several products were deleted, how is that possible?");
            }
        }
    }


    private static void printAllColumns(ResultSet resultSet) {
        TableforPrint.printResultSet(resultSet);
    }

    private static void addProducts(Connection connection) throws SQLException {
        System.out.println("Entering a new product");
        // product,quantity,ExpDate,IDLocation
        System.out.print("Product: ");
        String product = scanner.nextLine();

        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("ExpDate YYYY-MM-DD: ");
        String expDate = scanner.nextLine();

        System.out.print("IDLocation (where 1 - Refrigerator (+2+8), 2 - Freezer (-6, -24),3 - Shelves (+18+20)):");
        String idLocation = scanner.nextLine();

        try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCTS)) {
            preparedStatement.setString(1, product);
            preparedStatement.setInt(2, quantity);
            preparedStatement.setString(3, expDate);
            preparedStatement.setString(4, idLocation);

            int update = preparedStatement.executeUpdate();
            System.out.println("Executed update: " + update);
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

