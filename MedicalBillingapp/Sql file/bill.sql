CREATE DATABASE Bill;
use Bill;
CREATE TABLE Patient (
    patient_id INT PRIMARY KEY,
    name VARCHAR(100),
    address VARCHAR(255),
    contact VARCHAR(15)
);


CREATE TABLE Invoice (
    invoice_id INT PRIMARY KEY,
    patient_id INT,
    amount DECIMAL(10, 2),
    status VARCHAR(20),
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);


CREATE TABLE Payment (
    payment_id INT PRIMARY KEY,
    invoice_id INT,
    amount DECIMAL(10, 2),
    payment_type VARCHAR(20),
    payment_date DATE,
    FOREIGN KEY (invoice_id) REFERENCES Invoice(invoice_id)
);


CREATE TABLE BillingHistory (
    history_id INT PRIMARY KEY,
    patient_id INT,
    details VARCHAR(255),
    FOREIGN KEY (patient_id) REFERENCES Patient(patient_id)
);


DELIMITER //
CREATE PROCEDURE ProcessPayment(IN invoice_id INT, IN payment_amount DECIMAL(10, 2))
BEGIN
    UPDATE Invoice SET status = 'Paid' WHERE invoice_id = invoice_id AND amount <= payment_amount;
END //
DELIMITER ;

CREATE TRIGGER update_payment_status AFTER INSERT ON Payment
FOR EACH ROW
UPDATE Invoice 
SET status = 'Paid' WHERE invoice_id = NEW.invoice_id;

