CREATE DATABASE IF NOT EXISTS ev_charging_db;
USE ev_charging_db;
CREATE TABLE IF NOT EXISTS users (
 user_id VARCHAR(15) PRIMARY KEY, name VARCHAR(60) NOT NULL,
 role VARCHAR(15) NOT NULL, password VARCHAR(60) NOT NULL);
CREATE TABLE IF NOT EXISTS charging_slot (
 slot_id INT PRIMARY KEY, station_name VARCHAR(60) NOT NULL,
 charger_type VARCHAR(20) NOT NULL, rate_per_unit DECIMAL(8,2) NOT NULL,
 available_ports INT NOT NULL DEFAULT 0);
CREATE TABLE IF NOT EXISTS bookings (
 booking_id INT AUTO_INCREMENT PRIMARY KEY, user_id VARCHAR(15) NOT NULL,
 status VARCHAR(15) NOT NULL, gross DECIMAL(10,2) NOT NULL,
 discount DECIMAL(10,2) NOT NULL, net DECIMAL(10,2) NOT NULL,
 booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
 FOREIGN KEY(user_id) REFERENCES users(user_id));
CREATE TABLE IF NOT EXISTS booking_item (
 id INT AUTO_INCREMENT PRIMARY KEY, booking_id INT NOT NULL, slot_id INT NOT NULL,
 units_kwh DECIMAL(6,2) NOT NULL, line_total DECIMAL(10,2) NOT NULL,
 FOREIGN KEY(booking_id) REFERENCES bookings(booking_id),
 FOREIGN KEY(slot_id) REFERENCES charging_slot(slot_id));
INSERT IGNORE INTO users VALUES
('U101','Regular User','REGULAR','pw'),('U201','Premium User','PREMIUM','pw'),
('U301','Corporate Fleet','CORPORATE','pw');
INSERT IGNORE INTO charging_slot VALUES
(1,'MG Road','DC-Fast',18.00,4),(2,'Anna Nagar','AC-Slow',8.00,6),
(3,'T-Nagar','DC-Fast',20.00,2),(4,'Velachery','AC-Slow',7.00,8),
(5,'Guindy','DC-Fast',19.00,3);
