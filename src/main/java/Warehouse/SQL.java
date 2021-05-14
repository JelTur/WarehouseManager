package Warehouse;


public class SQL {
    public static final String INSERT_INTO_PRODUCTS =
            "INSERT INTO Products" +
                    "(product_name,quantity,ExpDate,location_id) VALUES" +
                    "('Sour_cream', 2, date '2021-05-31',1)," +
                    "('Eggs', 10,date '2021-05-01',1)," +
                    "('Zucchini', 3,date '2021-05-30',1)," +
                    "('Chicken', 4,date '2021-05-31',2)," +
                    "('Salad', 2, date '2021-05-01',1)," +
                    "('Ice_cream', 5, date '2021-06-20',2)," +
                    "('Pasta', 1, date '2021-06-05',3)," +
                    "('Cucumber', 2,date '2021-06-01',1)," +
                    "('Onion', 7,date '2021-06-18',1)," +
                    "('Bread', 2,date '2021-05-20',3);";

    public static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE IF NOT EXISTS Products (" +
                    "id BIGINT auto_increment PRIMARY KEY," +
                    "product_name VARCHAR(255) NOT NULL," +
                    "quantity BIGINT NOT NULL," +
                    "ExpDate date   NOT NULL," +
                    "location_id BIGINT NOT NULL," +
                    "foreign key (location_id) references Location(id)" +
                    ");";

    public static final String CREATE_TABLE_LOCATION =
            "CREATE TABLE IF NOT EXISTS Location (" +
                    "id BIGINT auto_increment PRIMARY KEY," +
                    "location_name VARCHAR(20) NOT NULL," +
                    "min_temperature INT NOT NULL," +
                    "max_temperature INT NOT NULL" +
                    ");";

    public static final String INSERT_INTO_LOCATION =
            "INSERT INTO Location" +
                    "(id, location_name,min_temperature, max_temperature) VALUES" +
                    "(1,'Refrigerator',2,8)," +
                    "(2,'Freezer',-6, -24)," +
                    "(3,'Shelves',18,20);";


    public static final String PRINT_ALL_PRODUCTS = "SELECT id, product_name FROM Products;";
    public static final String PRINT_ALL_REFRIDGERATOR_PRODUCTS = "SELECT p.product_name,l.location_name " +
            "FROM Products p " +
            "JOIN Location l ON p.location_id = l.id " +
            "WHERE l.location_name='Refrigerator'; ";

    public static final String PRINT_ALL_FREEZER_PRODUCTS = "SELECT p.product_name,l.location_name " +
            "FROM Products p " +
            "JOIN Location l ON p.location_id = l.id " +
            "WHERE l.location_name='Freezer'; ";
    public static final String PRINT_ALL_SHELVES_PRODUCTS = "SELECT p.product_name,l.location_name " +
            "FROM Products p " +
            "JOIN Location l ON p.location_id = l.id " +
            "WHERE l.location_name='Shelves'; ";
    public static final String PRINT_BY_EXPIRED_DATE = "SELECT * FROM Products " +
            "    WHERE ExpDate < NOW() " +
            "    ORDER BY ExpDate ASC;";
    public static final String ADD_PRODUCTS = "INSERT INTO Products" +
            " (product_name,quantity,ExpDate,location_id) VALUES" +
            "(?, ?, ?, ?);";
    public static final String REMOVE_PRODUCTS = "DELETE FROM Products WHERE id = ?;";
    public static final String PRINT_ALL_PRODUCTS_WITH_EXPIRY_DATE_LESS_THAN_3_DAYS = "SELECT *\n" +
            "    FROM  Products\n" +
            "    WHERE  ExpDate <  TRUNC( SYSDATE ) + INTERVAL '3' DAY   \n" +
            "    AND    ExpDate >= SYSDATE;";
    public static final String ORDER_PRODUCTS = "SELECT * FROM Products \n" +
            "    WHERE quantity\n" +
            "    ORDER BY quantity ASC;";
}
