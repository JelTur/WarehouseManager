package Warehouse;


public class SQL {
    public static final String INSERT_INTO_PRODUCT =
            "INSERT INTO Product" +
                    "(product,quantity,ExpDate,IDLocation) VALUES" +
                    "('Sour_cream', 2, date '2021-05-31',1)," +
                    "('Eggs', 10,date '2021-06-01',1)," +
                    "('Zucchini', 3,date '2021-05-30',1)," +
                    "('Chicken', 4,date '2021-05-31',2)," +
                    "('Salad', 2, date '2021-06-01',1)," +
                    "('Ice_cream', 5, date '2021-06-20',2)," +
                    "('Pasta', 1, date '2021-06-05',3)," +
                    "('Cucumber', 2,date '2021-06-01',1)," +
                    "('Ice_cream', 5, date '2021-06-20',2)," +
                    "('Onion', 7,date '2021-06-18',1)," +
                    "('Bread', 2,date '2021-05-20',3);";

    public static final String CREATE_TABLE_PRODUCT =
            "CREATE TABLE IF NOT EXISTS Product (" +
                    "id BIGINT auto_increment PRIMARY KEY," +
                    "product VARCHAR(255) NOT NULL," +
                    "quantity BIGINT NOT NULL," +
                    "ExpDate date   NOT NULL," +
                    "IDLocation BIGINT NOT NULL," +
                    "foreign key (IDLocation) references Location(id)" +
                    ");";

    public static final String CREATE_TABLE_LOCATION =
            "CREATE TABLE IF NOT EXISTS Location (" +
                    "id BIGINT auto_increment PRIMARY KEY," +
                    "name VARCHAR(20) NOT NULL," +
                    "temperature_min INT NOT NULL," +
                    "temperature_max INT NOT NULL" +
                    ");";

    public static final String INSERT_INTO_LOCATION =
            "INSERT INTO Location" +
                    "(name,temperature_min, temperature_max) VALUES" +
                    "('Freezer',-18,-18)," +
                    "('Refrigerator',2, 8)," +
                    "('Shelves',20, 25);";

    public static final String PRINT_ALL_PRODUCTS = "SELECT(Product) FROM Product;";
    public static final String PRINT_ALL_REFRIDGERATOR_PRODUCTS = "SELECT (w.product,l.name, w.IDLocation) FROM Product w\\n\" +\n" +
            "            \"    JOIN Location l ON w.IDLocation = l.IDLocation;";
    public static final String PRINT_ALL_FREEZER_PRODUCTS = "SELECT (w.product,l.name, w.IDLocation) FROM Product w\n" +
            "    JOIN Location l ON w.IDLocation = l.IDLocation;";
    public static final String PRINT_ALL_SHELVES_PRODUCTS = "SELECT (w.product,l.name, w.IDLocation) FROM Product w\\n\" +\n" +
            "            \"    JOIN Location l ON w.IDLocation = l.IDLocation;";
    public static final String PRINT_BY_EXPIRY_DATE = "SELECT * FROM Product\n" +
            "    WHERE ExpDate < NOW()\n" +
            "    ORDER BY ExpDate ASC;";
    public static final String ADD_PRODUCTS = "INSERT INTO Product" +
            " (product,quantity,ExpDate,IDLocation) VALUES" +
            "(?, ?, ?, ?);";
    public static final String REMOVE_PRODUCTS = "DELETE FROM Product WHERE id = ?;";
    public static final String PRINT_ALL_PRODUCTS_WITH_EXPIRY_DATE_LESS_THAN_3_DAYS = "SELECT *\n" +
            "    FROM  Product\n" +
            "    WHERE  ExpDate <  TRUNC( SYSDATE ) + INTERVAL '3' DAY   \n" +
            "    AND    ExpDate >= SYSDATE;";
    public static final String ORDER_PRODUCTS = "SELECT * FROM Product \n" +
            "    WHERE quantity\n" +
            "    ORDER BY quantity ASC;";
}
