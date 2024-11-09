import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Event implements Runnable {
    private static int nextId = 1;
    private int id;
    private int vendorId;
    private ArrayList<Ticket> poolTickets = new ArrayList<Ticket>();
    private ArrayList<Ticket> soldTickets = new ArrayList<Ticket>();
    private ArrayList<Ticket> availableTickets = new ArrayList<Ticket>();
    private int poolSize;
    private int totalEventTickets;
    private ArrayList<VendorEventAssociation> vendorEventAssociations = new ArrayList<VendorEventAssociation>();
    private static final Logger logger = Logger.getLogger(Event.class.getName());

    static {
        try {
            LocalDateTime currentDateTime = LocalDateTime.now();
            // Configure the logger with a FileHandler and SimpleFormatter
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd _ HH-mm-ss");
            String formattedDateTime = currentDateTime.format(formatter);
            String filePath = "src/Logs/" + formattedDateTime + "-event.log";
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

    public Event(int poolSize, int totalEventTickets) {
        this.id = nextId++;
        this.poolSize = poolSize;
        this.totalEventTickets = totalEventTickets;
        floodTickets(poolTickets, "pool");
        floodTickets(availableTickets, "available");
    }

    public void floodTickets(ArrayList<Ticket> tickets, String status){
        if(status.equalsIgnoreCase("pool")){
            for(int i = 0; i < poolSize; i++){
                Ticket ticket = new Ticket();
                ticket.setEventId(this.id);
                this.poolTickets.add(ticket);
            }
        }else{
            for(int i = 0; i < (totalEventTickets - poolSize); i++){
                Ticket ticket = new Ticket();
                ticket.setEventId(this.id);
                this.availableTickets.add(ticket);
            }
        }
    }

    public ArrayList<VendorEventAssociation> getVendorEventAssociations() {
        return vendorEventAssociations;
    }

    public void addVendorEventAssociations(VendorEventAssociation vendorEventAssociations) {
        this.vendorEventAssociations.add(vendorEventAssociations);
    }

    public ArrayList<Ticket> getPoolTickets() {
        return poolTickets;
    }

    public ArrayList<Ticket> getAvailableTickets() {
        return availableTickets;
    }

    public int getId() {
        return id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public ArrayList<Ticket> getSoldTickets() {
        return soldTickets;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getTotalEventTickets() {
        return totalEventTickets;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public void removeTicketFromPool(){
        Ticket ticket = poolTickets.get(0);
        poolTickets.remove(0);
        soldTickets.add(ticket);
    }

    public boolean allTicketsSold(){
        return poolTickets.isEmpty() && availableTickets.isEmpty();
    }



    @Override
    public void run(){

    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", poolTickets=" + poolTickets +
                ", soldTickets=" + soldTickets +
                ", availableTickets=" + availableTickets +
                ", poolSize=" + poolSize +
                ", totalEventTickets=" + totalEventTickets +
                ", vendorEventAssociations=" + vendorEventAssociations +
                '}';
    }
}
