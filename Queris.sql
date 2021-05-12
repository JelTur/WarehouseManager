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

CREATE TABLE Products (   
                   id BIGINT auto_increment PRIMARY KEY,
                   product_name VARCHAR(255) NOT NULL,   -- yeah. I guess that is better to be product_name, same as in location.
                   quantity BIGINT NOT NULL,                                  
                   expiration_date date   NOT NULL,
                   location_id BIGINT NOT NULL,   
FOREIGN KEY  (location_id) REFERENCES Location(location_id)); --- location(location_id) should be Location(location_id). The table name is with capital letter. It might be irrelevant in some DBs, but relevant for others. Better to get used to be precise.
                                               
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
                    );   
                 
INSERT INTO Location(location_id,location_name, min_temperature,max_temperature)
 VALUES
(1,'Refrigerator',+2,+8),
(2,'Freezer',-6, -24),
(3,'Shelves',+18,+20);


SELECT product FROM Products; ;     ---	Obtain the full list of products
-- you don't need to have () . And I suggest to keep correct case as well.

SELECT p.product,l.location_name, p.location_id
FROM Products p
JOIN Location l ON p.location_id = l.location_id
WHERE l.min_temperature=2;                     --Obtain the products stored under (-6C-24), or (+2+8), or (+18+20)
                                               -- Here you are missing where statement.
                                               -- You will actually get here each product and its location name and id.
                                               -- but using WHERE you can actually filter by interesting criteria, like l.min_temperature = ... 

SELECT * FROM Products
WHERE expiration_date > NOW()
ORDER BY expiration_date ASC                                        --	Obtain products according to the set expiry date

INSERT INTO Products (product,quantity,expiration_date,location_id)   --Add products
 VALUES
('Cheese', 2, date '2021-05-31',1);

DELETE FROM Products WHERE id = 1;      --Remove products


SELECT * FROM Products                  --Order products
                                        -- Something is missing here. And do you in general need a WHERE clause here? Do you need to exclude some of the products from the result?
ORDER BY quantity ASC

SELECT *
FROM  Products
WHERE  expiration_date <  TRUNC( SYSDATE ) + INTERVAL '3' DAY      --	If expiration date is less than 3 days
AND    expiration_date >= SYSDATE;
