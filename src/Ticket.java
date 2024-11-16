
public class Ticket {
    private static int nextId = 1;
    private final int id;
    private Event event;
    private Customer customer = null;
    private TicketStatus status;

    public Ticket() {
        this.id = nextId++;
    }

    public static int getNextId() {
        return nextId;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Event getEvent() {
        return event;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event +
                ", customer=" + (customer != null ? customer : "Not Sold") +
                ", status=" + status +
                '}';
    }
}
