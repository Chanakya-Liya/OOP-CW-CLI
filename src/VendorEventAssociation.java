public class VendorEventAssociation implements Runnable {
    private Vendor vendor;
    private Event event;
    private int releaseRate;
    private int frequency;

    public VendorEventAssociation(Vendor vendor, Event event, int releaseRate, int frequency) {
        this.vendor = vendor;
        this.event = event;
        this.releaseRate = releaseRate;
        this.frequency = frequency;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public Event getEvent() {
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
                    if (event.getPoolTickets().size() + releaseRate <= event.getPoolSize()) {
                        for (int i = 0; i < releaseRate; i++) {
                            if (event.getAvailableTickets().isEmpty()) break; // Stop if no tickets are available
                            Ticket ticket = event.getAvailableTickets().remove(0); // Remove from available tickets
                            event.getPoolTickets().add(ticket); // Add to pool tickets
                        }
                        System.out.println("Vendor " + vendor.getVendorId() +
                                " added " + releaseRate + " tickets to Event " + event.getId() +
                                ". Pool size: " + event.getPoolTickets().size());
                    } else {
                        System.out.println("Ticket pool is at capacity for Event " + event.getId() +
                                ". Vendor " + vendor.getVendorId() + " cannot add more tickets.");
                    }
                }
                // Sleep for the specified frequency before the next addition cycle
                Thread.sleep(frequency * 1000L);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("VendorEventAssociation for Vendor " + vendor.getVendorId() +
                        " interrupted: " + e.getMessage());
                return;
            }
        }
        System.out.println("Vendor " + vendor.getVendorId() + " completed ticket addition for Event " + event.getId());
    }

    @Override
    public String toString() {
        return "VendorEventAssociation{" +
                "vendor=" + vendor +
                ", event=" + event +
                ", releaseRate=" + releaseRate +
                ", frequency=" + frequency +
                '}';
    }
}

