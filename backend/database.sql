CREATE TABLE `users` (
  `user_id` int PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) UNIQUE NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `date_of_birth` Date NOT NULL,
  `admin` boolean DEFAULT false
);

CREATE TABLE `cars` (
  `car_id` int PRIMARY KEY AUTO_INCREMENT,
  `model_id` int NOT NULL,
  `location` ENUM ('Ålesund') NOT NULL,
  `available` boolean DEFAULT true,
  `year` int NOT NULL,
  `fuel_type` ENUM ('Petrol', 'Diesel', 'Hybrid', 'Electric') NOT NULL,
  `transmissionType` ENUM ('Manual', 'Automatic') NOT NULL,
  `numberOfSeats` int NOT NULL,
  `extraFeatures` varchar(255),
  `vendorList` varchar(255),
  `img_source` varchar(255)
);

CREATE TABLE `car_model` (
  `model_id` int PRIMARY KEY AUTO_INCREMENT,
  `carMaker` ENUM ('BMW', 'Volkswagen', 'Tesla', 'Nissan', 'Mazda', 'Skoda', 'Peugeot') NOT NULL,
  `carModel` varchar(255) NOT NULL
);

CREATE TABLE `rentals` (
  `rent_id` int PRIMARY KEY NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `car_id` int NOT NULL,
  `start_date` Date NOT NULL,
  `end_date` Date NOT NULL,
  `total_price` int NOT NULL
);

ALTER TABLE `cars` ADD FOREIGN KEY (`model_id`) REFERENCES `car_model` (`model_id`);

ALTER TABLE `rentals` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

ALTER TABLE `rentals` ADD FOREIGN KEY (`car_id`) REFERENCES `cars` (`car_id`);
