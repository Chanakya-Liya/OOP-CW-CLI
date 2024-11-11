import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
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