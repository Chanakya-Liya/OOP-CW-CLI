# OOP-CW-CLI

Creating a simulation using pure Java to figure out how I want the Spring Boot application to work.

## Table of Contents

- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project simulates a system using pure Java, laying the groundwork for a future Spring Boot application. The simulation involves generating users, events, and vendors, and running them concurrently to mimic real-world interactions.

## Project Structure

The main entry point of the project is the `Main.java` file, which initializes and starts the simulation.

### Main.java

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Main{
    public static void main(String[] args) throws InterruptedException {
        Util.validateConfig();
        Util.generateSimulatedUsers();

        for(Customer customer : Util.getCustomers()){
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }

        for(Event event : Util.getEvents()){
            event.startVendorThreads();
        }

        for(Vendor vendor : Util.getVendors()){
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
        }
        System.out.println("Simulation Running");

        Util.endProgram();
    }
}
Public code references from 1 repository
Installation
To install and run the project locally:

Clone the repository:
git clone https://github.com/Chanakya-Liya/OOP-CW-CLI.git
Public code references from 1 repository
Navigate to the project directory:
cd OOP-CW-CLI
Public code references from 1 repository
Compile the project:
javac -d bin src/*.java
Public code references from 1 repository
Run the application:
java -cp bin Main
Public code references from 1 repository
Usage
Run the Main class to start the simulation. The application will validate the configuration, generate users, events, and vendors, and start their respective threads to simulate interactions.

Features
User Simulation: Generates and manages simulated users.
Event Simulation: Initializes events and starts vendor threads associated with them.
Vendor Simulation: Runs vendor threads to simulate their activities.
Contributing
If you would like to contribute to this project, please follow these steps:

Fork the repository.
Create a new branch (git checkout -b feature-branch).
Make your changes.
Commit your changes (git commit -m 'Add some feature').
Push to the branch (git push origin feature-branch).
Create a new Pull Request.
License
This project is licensed under the MIT License.
