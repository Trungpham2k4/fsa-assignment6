-- CREATE DATABASE sms;

-- CREATE SCHEMA dbo;


CREATE TABLE IF NOT EXISTS dbo.Customer(
	customer_id SERIAL PRIMARY KEY,
	customer_name VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS dbo.Employee(
	employee_id SERIAL PRIMARY KEY,
	employee_name VARCHAR(200),
	salary NUMERIC(10,2),
	supervisor_id INT 
);

CREATE TABLE IF NOT EXISTS dbo.Product(
	product_id SERIAL PRIMARY KEY,
	product_name VARCHAR(200),
	list_price NUMERIC(10,2)
);

CREATE TABLE IF NOT EXISTS dbo.Orders(
	order_id SERIAL PRIMARY KEY,
	order_date DATE,
	total NUMERIC(10,2),
	customer_id INT,
	employee_id INT,
	FOREIGN KEY (customer_id) REFERENCES dbo.Customer(customer_id),
	FOREIGN KEY (employee_id) REFERENCES dbo.Employee(employee_id)
);

CREATE TABLE IF NOT EXISTS dbo.LineItem(
	order_id INT,
	product_id INT,
	quantity INT,
	price NUMERIC(10,2),
	PRIMARY KEY(order_id, product_id),
	FOREIGN KEY (order_id) REFERENCES dbo.Orders(order_id),
	FOREIGN KEY (product_id) REFERENCES dbo.Product(product_id)
);

CREATE OR REPLACE PROCEDURE dbo.insert_customer(
	IN p_customer_name VARCHAR(200)
) 
LANGUAGE plpgsql
AS $$
BEGIN 
	INSERT INTO dbo.customer(customer_name) VALUES (p_customer_name);
END;
$$;

CREATE OR REPLACE PROCEDURE dbo.update_customer(
	IN p_customer_id INT,
	IN p_customer_name VARCHAR(200)
)
LANGUAGE plpgsql
AS $$
BEGIN 
	UPDATE dbo.customer SET customer_name = p_customer_name WHERE customer_id = p_customer_id;
END;
$$;

CREATE OR REPLACE PROCEDURE dbo.delete_customer(
	IN p_customer_id INT
)
LANGUAGE plpgsql
AS $$
BEGIN
	DELETE FROM dbo.lineitem
    WHERE order_id IN (
        SELECT order_id
        FROM dbo.orders
        WHERE customer_id = p_customer_id
    );

    DELETE FROM dbo.orders
    WHERE customer_id = p_customer_id;

    DELETE FROM dbo.customer
    WHERE customer_id = p_customer_id;
END;
$$;

CREATE OR REPLACE FUNCTION dbo.compute_order_total(
    p_order_id INT
)
RETURNS NUMERIC(10,2)
LANGUAGE plpgsql
AS $$
DECLARE
    v_total NUMERIC(10,2);
BEGIN

    SELECT COALESCE(SUM(quantity * price), 0)
    INTO v_total
    FROM dbo.lineitem
    WHERE order_id = p_order_id;

    RETURN v_total;

END;
$$;



