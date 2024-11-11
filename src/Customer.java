import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Customer extends User implements Runnable {
    private static int nextId = 1;
    private final int CustomerId;
    private int retrievalRate;
    private int frequency;
    private static final Logger logger = Logger.getLogger(Customer.class.getName());

    static {
        try {
            String filePath = Util.getCustomerLog();
            FileHandler fileHandler = new FileHandler(filePath, 200 * 1024 * 1024, 1,  true); // "true" to append to the file
            fileHandler.setFormatter(new SimpleFormatter());  // Sets a simple text format for logs
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false); // Disables logging to console
        } catch (IOException e) {
            logger.warning("Failed to set up file handler for logger: " + e.getMessage());
        }catch(InvalidPathException e){
            logger.warning("Failed to set up file handler for logger hehe: " + e.getMessage());
        }
    }

    public Customer(String fName, String lName, String username, String password, String email, boolean simulated, int retrievalRate, int frequency) {
        super(fName, lName, username, password, email, simulated);
        CustomerId = nextId++;
        this.retrievalRate = retrievalRate;
        this.frequency = frequency;
    }

    public int getRetrievalRate() {
        return retrievalRate;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setRetrievalRate(int retrievalRate) {
        this.retrievalRate = retrievalRate;
    }

    public int getTotalTicket(){
        int totalTickets = 0;
        for(Event event : Util.getEvents()){
            totalTickets += event.getTotalEventTickets();
        }
        return totalTickets;
    }

    @Override
    public void run(){
        int totalTickets = getTotalTicket();
        while(totalTickets > 0){
            try {
                for(int i = 0; i < retrievalRate; i++){
                    boolean flag = true;
                    while(flag) {
                        int eventId = Util.generateRandomInt(0, Util.getEvents().size());
                        Event selectedEvent = Util.getEvents().get(eventId);
                        String messageTemplate = "event id: %d ticket sold customer with id: %d, available tickets: %d";
                        synchronized (selectedEvent){
                            if(!Util.getEvents().get(eventId).getPoolTickets().isEmpty()){
                                Util.getEvents().get(eventId).removeTicketFromPool();
                                String message = String.format(messageTemplate, Util.getEvents().get(eventId).getId(), CustomerId, Util.getEvents().get(eventId).getPoolTickets().size());
                                logger.info(message);
                                flag = false;
                            }
                        }
                    }
                }
                Thread.sleep(frequency * 1000L);
                totalTickets = getTotalTicket();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CustomerId=" + CustomerId +
                ", retrievalRate=" + retrievalRate +
                ", frequency=" + frequency +
                "} " + super.toString();
    }
}
