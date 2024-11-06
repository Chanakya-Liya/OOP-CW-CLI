public class Event implements Runnable {
    private static int nextId = 1;
    private final int id;
    private int vendorId;
    private int poolTicketsAvailable; //tickets left in the ticket pool
    private int ticketPoolSize; //ticket pool size
    private int releaseRate;
    private int frequency;
    private int ticketsSold = 0;
    private int totalEventTickets; //total number of tickets for the event

    public static final String GREEN = "\033[0;32m";
    public static final String RESET = "\033[0m";

    public Event(int ticketPoolSize, int releaseRate, int totalEventTickets, int frequency) {
        this.id = nextId++;
        this.poolTicketsAvailable = ticketPoolSize;
        this.ticketPoolSize = ticketPoolSize + (2 * releaseRate);
        this.releaseRate = releaseRate;
        this.totalEventTickets = totalEventTickets + (2 * ticketPoolSize);
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public int getPoolTicketsAvailable() {
        return poolTicketsAvailable;
    }

    public void removeTicketFromPool(){
        this.poolTicketsAvailable -= 1;
        this.ticketsSold ++;
    }

    public int getTicketPoolSize() {
        return ticketPoolSize;
    }

    public int getReleaseRate() {
        return releaseRate;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public int getTotalEventTickets() {
        return totalEventTickets;
    }

    public void setTicketPoolSize(int ticketPoolSize) {
        this.ticketPoolSize = ticketPoolSize;
    }

    public void setReleaseRate(int releaseRate) {
        this.releaseRate = releaseRate;
    }

    public void setTotalEventTickets(int totalEventTickets) {
        this.totalEventTickets = totalEventTickets;
    }

    public void setPoolTicketsAvailable(int poolTicketsAvailable) {
        this.poolTicketsAvailable = poolTicketsAvailable;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public void run(){
        try {
            System.out.println(GREEN + frequency + RESET);

            while (totalEventTickets > ticketsSold) {
                synchronized (this) {
                    // Check if there’s room in the pool to release more tickets
                    while (poolTicketsAvailable + releaseRate > ticketPoolSize) {
                        // Wait if the pool is at capacity
                        System.out.println(GREEN + toString() + "No SELL - Waiting" + RESET);
                        wait();
                    }

                    // Add tickets to the pool
                    poolTicketsAvailable += releaseRate;
                    System.out.println(GREEN + toString() + " Released Tickets" + RESET);

                    // Notify other threads in case they're waiting to purchase tickets
                    notifyAll();
                }

                // Only sleep for pacing; this will not block other threads since it's outside synchronized
                Thread.sleep(frequency * 1000L);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", vendorId=" + vendorId +
                ", poolTicketsAvailable=" + poolTicketsAvailable +
                ", ticketPoolSize=" + ticketPoolSize +
                ", releaseRate=" + releaseRate +
                ", frequency=" + frequency +
                ", ticketsSold=" + ticketsSold +
                ", totalEventTickets=" + totalEventTickets +
                '}';
    }
}
