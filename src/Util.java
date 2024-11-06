import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Util {
    private static ArrayList<Customer> customers = new ArrayList<Customer>();
    private static ArrayList<Vendor> vendors = new ArrayList<Vendor>();
    private static ArrayList<Event> events = new ArrayList<Event>();

    public static ArrayList<Customer> getCustomers() {
        return customers;
    }

    public static ArrayList<Vendor> getVendors() {
        return vendors;
    }

    public static ArrayList<Event> getEvents() {
        return events;
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

    public static int generateRandomSmallInt(int range){
        if(range == 1){
            return 1;
        }
        Random random = new Random();
        return random.nextInt(1, range);
    }

    public static int generateRandomInt(int start, int end){
        Random random = new Random();
        return random.nextInt(start, end);
    }

    public static void generateSimulatedUsers(){
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Welcome");
            System.out.print("How many Customers would you like to simulate: ");
            int simulateCustomers = scanner.nextInt();
            System.out.print("How many Vendors would you like to simulate: ");
            int simulateVendors = scanner.nextInt();
            System.out.print("How many events would you like to simulate: ");
            int simulatedEvents= scanner.nextInt();
            System.out.print("How many tickets would you like the customers to buy per frequency(ticket amount) or type 0 to randomize it to each customer: ");
            int customerRetrieve = scanner.nextInt();
            System.out.print("How frequently would you like the customers to buy tickets (sec) or type 0 to randomize it to each customer: ");
            int customerFrequency = scanner.nextInt();
            System.out.print("How frequently would you like an event to add tickets to the pool (sec) or type 0 to randomize it to each customer: ");
            int eventFrequency = scanner.nextInt();

            for(int i = 0; i < simulateCustomers; i++){
                Customer customer = new Customer(Util.generateRandomString("fname"), Util.generateRandomString("lname"), Util.generateRandomString("username"), Util.generateRandomString("password"), Util.generateRandomString("email"), true, Util.generateRandomSmallInt(50), Util.generateRandomSmallInt(30));
                if(customerFrequency != 0){
                    customer.setFrequency(customerFrequency);
                }
                if(customerRetrieve != 0){
                    customer.setRetrievalRate(customerRetrieve);
                }
                customers.add(customer);
            }
            for(int i = 0; i < simulateVendors; i++){
                Vendor vendor = new Vendor(Util.generateRandomString("fname"), Util.generateRandomString("lname"), Util.generateRandomString("username"), Util.generateRandomString("password"), Util.generateRandomString("email"), true);
                vendors.add(vendor);
            }
            for(int i = 0; i < simulatedEvents; i++){
                Event event = new Event(Util.generateRandomInt(10000, 30000), Util.generateRandomSmallInt(4000), Util.generateRandomInt(100000, 1000000), Util.generateRandomSmallInt(60));
                if(eventFrequency != 0){
                    event.setFrequency(eventFrequency);
                }
                int vendorId = Util.generateRandomSmallInt(vendors.size() + 1);
                vendors.get(vendorId - 1).setEvents(event);
                events.add(event);
                event.setVendorId(vendorId);
            }

        } catch (Exception e) {
            System.out.println("fuck");
            System.out.println(e);
        }
    }
}
