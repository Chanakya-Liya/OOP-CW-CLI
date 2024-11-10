import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Util.generateSimulatedUsers();

        for(Customer customer : Util.getCustomers()){
            Thread custoemrThread = new Thread(customer);
            custoemrThread.start();
        }

        for(Event event : Util.getEvents()){
            event.startVendorThreads();
        }

        for(Vendor vendor : Util.getVendors()){

        }
        System.out.println("Simulation Running");
    }
}