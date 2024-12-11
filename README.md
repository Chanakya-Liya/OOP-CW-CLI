Based on the gathered information, here is an extensive README file for your project:

```
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

Run the `Main` class to start the simulation. The application will validate the configuration, generate users, events, and vendors, and start their respective threads to simulate interactions.

## Features

- **User Simulation**: Generates and manages simulated users.
- **Event Simulation**: Initializes events and starts vendor threads associated with them.
- **Vendor Simulation**: Runs vendor threads to simulate their activities.

## Contributing

If you would like to contribute to this project, please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -m 'Add some feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Create a new Pull Request.

## License

This project is currently not licensed. Please contact the repository owner for more details.

```
