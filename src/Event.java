import java.util.ArrayList;

public class Event{
    private static int nextId = 1;
    private final int id;
    private Vendor vendor;
    private ArrayList<Ticket> poolTickets = new ArrayList<Ticket>();
    private ArrayList<Ticket> soldTickets = new ArrayList<Ticket>();
    private ArrayList<Ticket> availableTickets = new ArrayList<Ticket>();
    private final int poolSize;
    private final int totalEventTickets;
    private ArrayList<VendorEventAssociation> vendorEventAssociations = new ArrayList<VendorEventAssociation>();

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

    public Vendor getVendor() {
        return vendor;
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

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void removeTicketFromPool(){
        Ticket ticket = poolTickets.getFirst();
        poolTickets.removeFirst();
        soldTickets.add(ticket);
    }

    public void addTicketToPool(){
        Ticket ticket = availableTickets.getFirst();
        poolTickets.add(ticket);
        availableTickets.removeFirst();
    }

    public boolean allTicketsSold(){
        return poolTickets.isEmpty() && availableTickets.isEmpty();
    }

    public void startVendorThreads() {
        for (VendorEventAssociation association : vendorEventAssociations) {
            Thread thread = new Thread(association);
            thread.start();
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", vendor=" + vendor.getVendorId() +
                ", poolTickets=" + poolTickets.size() +
                ", soldTickets=" + soldTickets.size() +
                ", availableTickets=" + availableTickets.size() +
                ", poolSize=" + poolSize +
                ", totalEventTickets=" + totalEventTickets +
                ", vendorEventAssociations=" + vendorEventAssociations +
                '}';
    }
}
