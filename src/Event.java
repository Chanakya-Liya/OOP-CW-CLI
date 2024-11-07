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
    private int releaseRate;
    private int frequency;
    private int totalEventTickets;
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

    public Event(int poolSize, int releaseRate, int totalEventTickets, int frequency) {
        this.id = nextId++;
        this.poolSize = poolSize;
        this.releaseRate = releaseRate;
        this.frequency = frequency;
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

    public int getReleaseRate() {
        return releaseRate;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getTotalEventTickets() {
        return totalEventTickets;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public void removeTicketFromPool(){
        Ticket ticket = poolTickets.getFirst();
        poolTickets.removeFirst();
        soldTickets.add(ticket);
    }

    public void addTicketsToPool(){
        if(poolTickets.size() + releaseRate <= poolSize){
            if(availableTickets.size() >= releaseRate){
                for(int i = 0; i < releaseRate; i++){
                    Ticket ticket = availableTickets.getFirst();
                    poolTickets.add(ticket);
                    availableTickets.removeFirst();
                }
            }else{
                for(int i = 0; i < availableTickets.size(); i++){
                    Ticket ticket = availableTickets.getFirst();
                    poolTickets.add(ticket);
                    availableTickets.removeFirst();
                }
            }
        }
    }

    @Override
    public void run(){
        while(!availableTickets.isEmpty()){
            try {
                int before = this.poolTickets.size();
                String stringBefore = toString();
                addTicketsToPool();
                int after = this.poolTickets.size();
                if(before != after){
                    logger.warning("\nBefore: " + stringBefore + "\nAfter: " + toString());
                }
                Thread.sleep(frequency * 1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", poolTickets=" + poolTickets.size() +
                ", soldTickets=" + soldTickets.size() +
                ", availableTickets=" + availableTickets.size() +
                ", poolSize=" + poolSize +
                ", releaseRate=" + releaseRate +
                ", frequency=" + frequency +
                ", totalEventTickets=" + totalEventTickets +
                '}';
    }
}
