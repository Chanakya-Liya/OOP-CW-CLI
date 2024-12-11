# OOP-CW-CLI

Creating a simulation using pure Java to figure out how I want the Spring Boot application to work.

## Table of Contents

- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
-[Key Features](#key Features)

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
```

## Installation

To install and run the project locally:

1. Clone the repository:
    ```sh
    git clone https://github.com/Chanakya-Liya/OOP-CW-CLI.git
    ```
2. Navigate to the project directory:
    ```sh
    cd OOP-CW-CLI
    ```
3. Compile the project:
    ```sh
    javac -d bin src/*.java
    ```
4. Run the application:
    ```sh
    java -cp bin Main
    ```

## Usage
Run the Main class to start the simulation. The application will validate the configuration, generate users, events, and vendors, and start their respective threads to simulate interactions.

## Features
User Simulation: Generates and manages simulated users. Users are created with various attributes and behaviors, making the simulation dynamic and realistic.
Event Simulation: Initializes events and starts vendor threads associated with them. Events are designed to interact with both users and vendors, creating a complex web of interactions.
Vendor Simulation: Runs vendor threads to simulate their activities. Vendors provide services or goods to users and interact with events, adding another layer of complexity to the simulation.
Concurrency Handling: Utilizes Java threads to run users, events, and vendors concurrently, ensuring that the simulation runs smoothly and efficiently.
Configuration Validation: Ensures that all configurations are valid before starting the simulation, preventing errors and ensuring consistency.
Comprehensive Simulation End: Gracefully ends the simulation, ensuring that all threads are properly terminated and resources are released. 

## Key Features
Pure Java Implementation: The project uses pure Java without any external frameworks, showcasing the power and flexibility of the language.
Complex Simulations: It simulates real-world interactions between users, events, and vendors, demonstrating the ability to handle complex scenarios.
Concurrency Management: By effectively managing multiple threads, the project highlights advanced Java concurrency capabilities.
Foundation for Spring Boot: This project serves as a prototype for a future Spring Boot application, providing valuable insights and groundwork for further development.
Dynamic and Scalable: The simulation can be expanded with more users, events, and vendors, making it scalable and adaptable to various scenarios.
