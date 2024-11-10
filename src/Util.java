import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Util {
    private final static ArrayList<Customer> customers = new ArrayList<Customer>();
    private final static ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    private final static ArrayList<Event> events = new ArrayList<Event>();
    private static final String DirectoryPath = "src/Logs/"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd _ HH-mm-ss"));
    private static final String CustomerLog = DirectoryPath + File.separator +"/customer.log";;
    private static final String EventLog = DirectoryPath + File.separator +"/event.log";
    private static final String VendorLog =DirectoryPath + File.separator +"/vendor.log";;
    private static final String simulationLog = DirectoryPath + File.separator +"/simulation.log";;
    private static final Logger logger = Logger.getLogger(Util.class.getName());

    static {
        try {
            File directory = new File(DirectoryPath);
            if (directory.mkdir()) {
                System.out.println("Directory created successfully.");
            } else {
                System.out.println("Failed to create directory.");

            }
            String filePath = simulationLog;
            FileHandler fileHandler = new FileHandler(filePath, true); // "true" to append to the file
            fileHandler.setFormatter(new SimpleFormatter());  // Sets a simple text format for logs
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Disables logging to console
        } catch (IOException e) {
            logger.warning("Failed to set up file handler for logger: " + e.getMessage());
        }catch(InvalidPathException e){
            logger.warning("Failed to set up file handler for logger hehe: " + e.getMessage());
        }
    }

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static ArrayList<Vendor> getVendors() {
        return vendors;
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static String getCustomerLog() {
        return CustomerLog;
    }

    public static String getEventLog() {
        return EventLog;
    }

    public static String getVendorLog() {
        return VendorLog;
    }

    public static String generateRandomString(String stringRequired) throws IOException {
        String fNameFile = "src/Static/fName.txt";
        String lNameFIle = "src/Static/lName.txt";
        String passwordFile = "src/Static/passwords.txt";
        String email = "src/Static/email.txt";
        String username = "src/Static/username.txt";
        Random random = new Random();
        String filePath;
        int size;
        if(stringRequired.equalsIgnoreCase("fname")){
            filePath = fNameFile;
            size = 4945;
        } else if (stringRequired.equalsIgnoreCase("lname")) {
            filePath = lNameFIle;
            size = 21985;
        } else if(stringRequired.equalsIgnoreCase("password")){
            filePath = passwordFile;
            size = 10000;
        }else if(stringRequired.equalsIgnoreCase("email")){
            filePath = email;
            size = 8844;
        }else{
            filePath = username;
            size = 81475;
        }
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int fNameValue = random.nextInt(1, size);
        int currentLine = 0;
        while (reader.readLine() != null) {
            currentLine++;
            if (currentLine == fNameValue) {
                return reader.readLine();
            }
        }
        return null;
    }

    public static int generateRandomInt(int start, int end){
        if(start == end){
            return end;
        }
        Random random = new Random();
        return random.nextInt(start, end);
    }

    public static void generateSimulatedUsers(){
        try {
            System.out.println("Welcome to the ticket vendor simulation CLI");
            System.out.println("Make sure you have altered the config file to your liking before starting");
            System.out.println("Please select one of the following");
            System.out.println("1. Simulation");
            System.out.println("2. Thread Testing");
            System.out.println("3. Exit");
            int startOption = validateUserInput("Option: ");
            int simulatedCustomers;
            int simulatedVendors;
            int simulatedEvents;

            if(startOption == 1){
                int EventCountMin = readJsonFile("Simulation", "event", "EventCountMin");
                int EventCountMax = readJsonFile("Simulation", "event", "EventCountMax");
                int PoolSizeMin = readJsonFile("Simulation", "event", "PoolSizeMin");
                int PoolSizeMax = readJsonFile("Simulation", "event", "PoolSizeMax");
                int TotalEventTicketsMin = readJsonFile("Simulation", "event", "TotalEventTicketsMin");
                int TotalEventTicketsMax = readJsonFile("Simulation", "event", "TotalEventTicketsMax");

                int CustomerCountMin = readJsonFile("Simulation", "customer", "CustomerCountMin");
                int CustomerCountMax = readJsonFile("Simulation", "customer", "CustomerCountMax");
                int RetrievalRateMin = readJsonFile("Simulation", "customer", "RetrievalRateMin");
                int RetrievalRateMax = readJsonFile("Simulation", "customer", "RetrievalRateMax");
                int CustomerFrequencyMin = readJsonFile("Simulation", "customer", "FrequencyMin");
                int CustomerFrequencyMax = readJsonFile("Simulation", "customer", "FrequencyMax");

                int VendorCountMin = readJsonFile("Simulation", "vendor", "VendorCountMin");
                int VendorCountMax = readJsonFile("Simulation", "vendor", "VendorCountMax");
                int ReleaseRateMin = readJsonFile("Simulation", "vendor", "ReleaseRateMin");
                int ReleaseRateMax = readJsonFile("Simulation", "vendor", "ReleaseRateMax");
                int VendorFrequencyMin = readJsonFile("Simulation", "vendor", "FrequencyMin");
                int VendorFrequencyMax = readJsonFile("Simulation", "vendor", "FrequencyMax");

                simulatedVendors = generateRandomInt(VendorCountMin, VendorCountMax);
                simulatedCustomers = generateRandomInt(CustomerCountMin, CustomerCountMax);
                simulatedEvents = generateRandomInt(EventCountMin, EventCountMax);
                simulateCustomersForSimulationTesting(simulatedCustomers, RetrievalRateMin, RetrievalRateMax, CustomerFrequencyMin, CustomerFrequencyMax);
                simulateVendors(simulatedVendors);
                simulateEventsForSimulationTesting(simulatedEvents, PoolSizeMin, PoolSizeMax,TotalEventTicketsMin, TotalEventTicketsMax, ReleaseRateMin, ReleaseRateMax, VendorFrequencyMin, VendorFrequencyMax);

            }else if(startOption == 2){

                simulatedEvents = readJsonFile("ThreadTesting", "event", "EventCount");
                int PoolSize = readJsonFile("ThreadTesting", "event", "PoolSize");
                int TotalEventTickets = readJsonFile("ThreadTesting", "event", "TotalTicketCount");

                simulatedCustomers = readJsonFile("ThreadTesting", "customer", "CustomerCount");
                int RetrievalRate = readJsonFile("ThreadTesting", "customer", "RetrievalRate");;
                int CustomerFrequency = readJsonFile("ThreadTesting", "customer", "Frequency");

                simulatedVendors = readJsonFile("ThreadTesting", "vendor", "VendorCount");
                int ReleaseRate = readJsonFile("ThreadTesting", "vendor", "ReleaseRate");
                int VendorFrequency = readJsonFile("ThreadTesting", "vendor", "Frequency");

                simulateCustomersForThreadTesting(simulatedCustomers, RetrievalRate, CustomerFrequency);
                simulateVendors(simulatedVendors);
                simulateEventsForThreadTesting(simulatedEvents, PoolSize, TotalEventTickets, ReleaseRate, VendorFrequency);

            }else if(startOption == 3){
                System.out.println("Exiting Program");
                System.exit(1);
            }

            for(Customer customer : customers){
                logger.info(customer.toString());
            }
            logger.info("==================================================");
            for(Vendor vendor : vendors){
                logger.info(vendor.toString());
            }
            logger.info("==================================================");
            for(Event event : events){
                logger.info(event.toString());
            }

        } catch (Exception e) {
            System.out.println("fuck");
            System.out.println(e);
        }
    }

    public static int validateUserInput(String prompt){
        Scanner scanner = new Scanner(System.in);
        int option = -1;

        System.out.printf("%s: ", prompt);

        while (option < 0 || option >= 4) {
            try {
                option = scanner.nextInt();
                if (option < 0) {
                    System.out.print("Invalid input. Please enter 1, 2 or 3: ");
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter 1, 2 or 3: ");
                scanner.nextLine();  // Clear the invalid input
            }
        }
        return option;
    }

    public static void simulateCustomersForThreadTesting(int simulateCustomers, int customerFrequency, int customerRetrieve) throws IOException {
        for(int i = 0; i < simulateCustomers; i++){
            Customer customer = new Customer(Util.generateRandomString("fname"), Util.generateRandomString("lname"), Util.generateRandomString("username"), Util.generateRandomString("password"), Util.generateRandomString("email"), true, customerRetrieve, customerFrequency);
            customers.add(customer);
        }
    }

    public static void simulateCustomersForSimulationTesting(int simulateCustomers, int customerFrequencyMin, int customerFrequencyMax, int customerRetrieveMin, int customerRetrieveMax) throws IOException {
        for(int i = 0; i < simulateCustomers; i++){
            Customer customer = new Customer(Util.generateRandomString("fname"), Util.generateRandomString("lname"), Util.generateRandomString("username"), Util.generateRandomString("password"), Util.generateRandomString("email"), true, Util.generateRandomInt(customerRetrieveMin, customerRetrieveMax), Util.generateRandomInt(customerFrequencyMin, customerFrequencyMax));
            customers.add(customer);
        }
    }

    public static void simulateVendors(int simulateVendors) throws IOException {
        for(int i = 0; i < simulateVendors; i++){
            Vendor vendor = new Vendor(Util.generateRandomString("fname"), Util.generateRandomString("lname"), Util.generateRandomString("username"), Util.generateRandomString("password"), Util.generateRandomString("email"), true);
            vendors.add(vendor);
        }
    }

    public static void simulateEventsForThreadTesting(int simulatedEvent, int poolSize, int totalEventTickets, int releaseRate, int frequency){
        for(int i = 0; i < simulatedEvent; i++){
            boolean flag = true;
            Event event = new Event(poolSize, totalEventTickets);
            int vendorCount = generateRandomInt(-5,vendors.size());
            ArrayList<Integer> addedVendors = new ArrayList<Integer>();
            if(vendorCount <= 0){
                vendorCount = 1;
            }
            for(int j = 0; j < vendorCount; j++){
                int vendorPosition;
                do{
                     vendorPosition = generateRandomInt(0,vendors.size());
                }while(addedVendors.contains(vendorPosition));

                if(flag){
                   event.setVendor(vendors.get(vendorPosition));
                }
                VendorEventAssociation vendorEventAssociation = new VendorEventAssociation(vendors.get(vendorPosition), event, releaseRate, frequency);
                event.addVendorEventAssociations(vendorEventAssociation);
                vendors.get(vendorPosition).setEvents(event);
                addedVendors.add(vendorPosition);
                flag = false;
            }
            events.add(event);
        }
    }

    public static void simulateEventsForSimulationTesting(int simulatedEvent, int poolSizeMin, int poolSizeMax, int totalEventTicketsMin, int totalEventTicketsMax, int releaseRateMin, int releaseRateMax, int frequencyMin, int frequencyMax){
        for(int i = 0; i < simulatedEvent; i++){
            boolean flag = true;
            Event event = new Event(generateRandomInt(poolSizeMin, poolSizeMax), generateRandomInt(totalEventTicketsMin, totalEventTicketsMax));
            int vendorCount = generateRandomInt(-5,vendors.size());
            ArrayList<Integer> addedVendors = new ArrayList<Integer>();
            if(vendorCount <= 0){
                vendorCount = 1;
            }
            for(int j = 0; j < vendorCount; j++){
                int vendorPosition;
                do{
                    vendorPosition = generateRandomInt(0,vendors.size());
                }while(addedVendors.contains(vendorPosition));

                if(flag){
                    event.setVendor(vendors.get(vendorPosition));
                }
                VendorEventAssociation vendorEventAssociation = new VendorEventAssociation(vendors.get(vendorPosition), event, generateRandomInt(releaseRateMin, releaseRateMax), generateRandomInt(frequencyMin, frequencyMax));
                event.addVendorEventAssociations(vendorEventAssociation);
                vendors.get(vendorPosition).setEvents(event);
                addedVendors.add(vendorPosition);
                flag = false;
            }
            events.add(event);
        }
    }

    public static int readJsonFile(String type, String option ,String config) {
        try (FileReader reader = new FileReader("src/Config/config.json")) {
            JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
            return root.getAsJsonObject(type).getAsJsonObject(option).get(config).getAsInt();
        } catch (IOException e) {
            return 0;
        }
    }
}
