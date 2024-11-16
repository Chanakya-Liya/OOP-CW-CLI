import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Event{
    private static int nextId = 1;
    private int id;
    private Vendor vendor;
    private List<Ticket> tickets = new ArrayList<>();
    private int poolSize;
    private int totalTickets;

    public Event(int poolSize, int totalTickets) {
        this.id = nextId++;
        this.poolSize = poolSize;
        this.totalTickets = totalTickets;
        for (int i = 0; i < poolSize; i++) {
            addTicket(TicketStatus.POOL);
        }

        for (int i = 0; i < (totalTickets - poolSize); i++) {
            addTicket(TicketStatus.AVAILABLE);
        }
    }

    public Event(){}

    private void addTicket(TicketStatus status) {
        Ticket ticket = new Ticket();
        ticket.setEvent(this); // Very Important! Set bidirectional relationship
        ticket.setStatus(status);
        tickets.add(ticket);
    }

    public List<Ticket> getPoolTickets() {
        return tickets.stream().filter(t -> t.getStatus() == TicketStatus.POOL).collect(Collectors.toList());
    }

    public List<Ticket> getAvailableTickets() {
        return tickets.stream().filter(t -> t.getStatus() == TicketStatus.AVAILABLE).collect(Collectors.toList());
    }

    public List<Ticket> getSoldTickets() {
        return tickets.stream().filter(t -> t.getStatus() == TicketStatus.SOLD).collect(Collectors.toList());
    }

    public int getId() {
        return id;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public void removeTicketFromPool() {
        tickets.stream().filter(t -> t.getStatus() == TicketStatus.POOL).findFirst()
                .ifPresent(t -> t.setStatus(TicketStatus.SOLD));
    }

    public void addTicketToPool() {
        tickets.stream().filter(t -> t.getStatus() == TicketStatus.AVAILABLE).findFirst()
                .ifPresent(t -> t.setStatus(TicketStatus.POOL));
    }

    public boolean allTicketsSold() {
        return getPoolTickets().isEmpty() && getAvailableTickets().isEmpty();
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", vendor=" + vendor.getId() +
                ", pool tickets=" + tickets.stream().filter(t -> t.getStatus() == TicketStatus.POOL).count() +
                ", sold tickets=" + tickets.stream().filter(t -> t.getStatus() == TicketStatus.SOLD).count() +
                ", available tickets=" + tickets.stream().filter(t -> t.getStatus() == TicketStatus.AVAILABLE).count() +
                ", poolSize=" + poolSize +
                ", totalTickets=" + totalTickets +
                '}';
    }
}

