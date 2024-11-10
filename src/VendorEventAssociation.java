import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class VendorEventAssociation implements Runnable {
    private final Vendor vendor;
    private final Event event;
    private int releaseRate;
    private int frequency;
    private static final Logger logger = Logger.getLogger(VendorEventAssociation.class.getName());

    static {
        try {
            String filePath = Util.getEventLog();
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

    public VendorEventAssociation(Vendor vendor, Event event, int releaseRate, int frequency) {
        this.vendor = vendor;
        this.event = event;
        this.releaseRate = releaseRate;
        this.frequency = frequency;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public final Event getEvent() {
        return event;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public void run(){
        while (!event.allTicketsSold()) {  // Continue until all tickets are sold
            try {
                synchronized (event) {  // Lock the event while modifying ticket lists
                    if (event.getPoolTickets().size() + releaseRate <= event.getPoolSize() && !event.getAvailableTickets().isEmpty()) {
                        int before = event.getPoolTickets().size();
                        int added = Math.min(releaseRate, event.getAvailableTickets().size());
                        for (int i = 0; i < releaseRate; i++) {
                            if (event.getAvailableTickets().isEmpty()) break; // Stop if no tickets are available
                            event.addTicketToPool();
                        }
                        int after = event.getPoolTickets().size();
                        logger.info("Vendor " + vendor.getVendorId() +
                                " added " + added + " tickets to Event " + event.getId() +
                                ". Pool size: " + event.getPoolTickets().size() + " before: " + before + " after: " + after + " available tickets: " + event.getAvailableTickets().size());
                    }
                }
                // Sleep for the specified frequency before the next addition cycle
                Thread.sleep(frequency * 1000L);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.warning("VendorEventAssociation for Vendor " + vendor.getVendorId() +
                        " interrupted: " + e.getMessage());
                return;
            }
        }
        synchronized (this){
            System.out.println("Vendor " + vendor.getVendorId() + " completed ticket addition for Event " + event.getId());
        }
    }

    @Override
    public String toString() {
        return "VendorEventAssociation{" +
                "vendor=" + vendor.getVendorId() +
                ", event=" + event.getId() +
                ", releaseRate=" + releaseRate +
                ", frequency=" + frequency +
                '}';
    }
}

