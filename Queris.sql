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





CREATE TABLE Warehouse (    -- I think the name Products would be clearer. Since that is not the table of Warehouses, but rather table of products in warehouses.
                   id BIGINT auto_increment PRIMARY KEY,
                   product VARCHAR(255) NOT NULL,
                   quantity BIGINT NOT NULL,
  --I suggest to use same convention for the columns
  -- the previous columns are lowercase, and below columns are CamelCase
  -- Actually in DB you will frequently find underscore separated case:
  -- expiration_date
  -- id_location (or maybe better location_id, since it is easier to read)
                   ExpDate date   NOT NULL,
                   IDLocation BIGINT NOT NULL);   -- This should be a FK (see comments below) Because each product has a specific location, not that each location has specific product (single one).

INSERT INTO Warehouse(product,quantity,ExpDate,IDLocation)
 VALUES


('Sour_cream', 2, date '2021-05-31',1),
('Eggs', 10,date '2021-06-01',1),
('Bread', 2,date '2021-05-20',3),
('Zucchini', 3,date '2021-05-30',1),
('Chicken', 4,date '2021-05-31',2),
('Salad', 2, date '2021-06-01',1),
('Ice_cream', 5, date'2021-06-20',2),
('Pasta', 1,date '2021-06-05',3),
('Cucumber', 2,date '2021-06-01',1),
('Onion', 7,date '2021-06-18',1);

CREATE TABLE Location (
                   IDLocation BIGINT auto_increment PRIMARY KEY,
                   Location1 VARCHAR(20) NOT NULL, -- i think this field better to call name (since it is location name, right)? anyway it is a bad idea to have location_1 (not clear what does 1 mean, though I know why you put it)
                   Storagetemperature VARCHAR(20) NOT NULL);    -- storage_temperature ? 
                   -- Would it be better to have  instead of Storagetemperature 2 columns (min_temperature and max_temperature both of numeric type) this way it will be a lot easier working with that 
                  foreign key (Location) references Warehouse(IDLocation),  -- Location1 - typo??
                  -- Anyway i think that foreitn key should be in Warehouse table, not here. Because each product in warehouse has it's own locaion,
                  -- not that each location have one product. Meaning IDLocation in Warehouse whould be a foreign key for a Location table IDLocation
INSERT INTO Location(Location1,Storagetemperature)
 VALUES
('Refrigerator','-18C'),
('Freezer','+2 -+8C'),
('Shelves','+20C');

SELECT(Product) FROM Warehouse;     ---	Obtain the full list of products

SELECT w.product,l.location1, w.IDLocation
FROM Warehouse w
JOIN Location l ON w.IDLocation = l.IDLocation; --Obtain the products stored under -18C, or +2+8, or +20

SELECT * FROM Warehouse
WHERE ExpDate < NOW()
ORDER BY ExpDate ASC                                      --	Obtain products according to the set expiry date

INSERT INTO Warehouse(product,quantity,ExpDate,IDLocation)     --Add products
 VALUES
('Cheese', 2, date '2021-05-31',1);

DELETE FROM Warehouse WHERE id = 1;      --Remove products


SELECT * FROM Warehouse                               --Order products
WHERE quantity
ORDER BY quantity ASC

SELECT *
FROM  Warehouse
WHERE  ExpDate <  TRUNC( SYSDATE ) + INTERVAL '3' DAY   --If expiration date is less than 3 days user will be warned but product will be entered into the order list
AND    ExpDate >= SYSDATE;
