--Warehouse manager

--This program is designed to facilitate user to enter the required list of products into 3 types of storage available,
--with 3 different temperature regimes. In the course of communication between program and user the following actions will be permitted:
---	Obtain the full list of products
---	Obtain the products stored under -18C, or +2+8, or +20
---	Obtain products according to the set expiry date
---	Add products
---	Remove products
---	Order products

--While ordering products the following assistance will be provided to the user:
---	Information that the entered product is available and its quantity
---	If expiration date is less than 3 days user will be warned but product will be entered into the order list
---	If expiration date is more than 3 days product will not be entered into the order list, warning message will be provided.





CREATE TABLE Products (    -- I think the name Products would be clearer. Since that is not the table of Warehouses, but rather table of products in warehouses.
                   id BIGINT auto_increment PRIMARY KEY,
                   product VARCHAR(255) NOT NULL,
                   quantity BIGINT NOT NULL,
 
                                                    --I suggest to use same convention for the columns
                                                    -- the previous columns are lowercase, and below columns are CamelCase
                                                    -- Actually in DB you will frequently find underscore separated case:
                                                    -- expiration_date
                                                    -- id_location (or maybe better location_id, since it is easier to read)
                   expiration_date date   NOT NULL,
                   location_id BIGINT NOT NULL,   -- This should be a FK (see comments below) Because each product has a specific location, not that each location has specific product (single one).
FOREIGN KEY  (location_id) REFERENCES location(location_id));
                                               
INSERT INTO Products (product,quantity,expiration_date,location_id)
 VALUES


('Sour_cream', 2, date '2021-05-31',1),
('Eggs', 10,date '2021-06-01',1),
('Bread', 2,date '2021-05-20',3),
('Zucchini', 3,date '2021-05-30',1),
('Chicken', 4,date '2021-05-31',2),
('Salad', 2, date '2021-06-01',1),
('Ice_cream', 5, date '2021-06-20',2),
('Pasta', 1,date '2021-06-05',3),
('Cucumber', 2,date '2021-06-01',1),
('Onion', 7,date '2021-06-18',1);

CREATE TABLE Location (
                   location_id BIGINT auto_increment PRIMARY KEY,
                   location_name VARCHAR(20) NOT NULL, -- i think this field better to call name (since it is location name, right)? anyway it is a bad idea to have location_1 (not clear what does 1 mean, though I know why you put it)
                   min_temperature INT NOT NULL,
                   max_temperature INT NOT NULL
                    );    -- storage_temperature ? 
                   -- Would it be better to have  instead of Storagetemperature 2 columns (min_temperature and max_temperature both of numeric type) this way it will be a lot easier working with that 
                  
                  -- Anyway i think that foreitn key should be in Warehouse table, not here. Because each product in warehouse has it's own locaion,
                  -- not that each location have one product. Meaning IDLocation in Warehouse whould be a foreign key for a Location table IDLocation
INSERT INTO Location(location_id,location_name, min_temperature,max_temperature)
 VALUES
(1,'Refrigerator',+2,+8),
(2,'Freezer',-6, -24),
(3,'Shelves',+18,+20);


SELECT(Product) FROM Products; ;     ---	Obtain the full list of products

SELECT p.product,l.location_name, p.location_id
FROM Products p
JOIN location l ON p.location_id = l.location_id; --Obtain the products stored under (-6C-24), or (+2+8), or (+18+20)

SELECT * FROM Products
WHERE expiration_date > NOW()
ORDER BY expiration_date ASC                                        --	Obtain products according to the set expiry date

INSERT INTO Products (product,quantity,expiration_date,location_id)   --Add products
 VALUES
('Cheese', 2, date '2021-05-31',1);

DELETE FROM Products WHERE id = 1;      --Remove products


SELECT * FROM Products                  --Order products
WHERE quantity
ORDER BY quantity ASC

SELECT *
FROM  Products
WHERE  expiration_date <  TRUNC( SYSDATE ) + INTERVAL '3' DAY      --	If expiration date is less than 3 days
AND    expiration_date >= SYSDATE;
