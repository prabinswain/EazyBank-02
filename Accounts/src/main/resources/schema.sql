CREATE TABLE IF NOT EXISTS customer (
                                        customer_id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(10) NOT NULL,
    updated_date DATE DEFAULT NULL,
    updated_by VARCHAR(10) NOT NULL
    );

CREATE TABLE IF NOT EXISTS accounts (
                                        account_number INT AUTO_INCREMENT PRIMARY KEY,
                                        customer_id INT NOT NULL,
                                        account_type VARCHAR(100) NOT NULL,
    branch_address VARCHAR(100) NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(10) NOT NULL,
    updated_date DATE DEFAULT NULL,
    updated_by VARCHAR(10) NOT NULL
    );
