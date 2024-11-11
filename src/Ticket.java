public class Ticket {
    private static int nextId = 1;
    private final int id;
    private int eventId;
    private int customerId;

    public Ticket() {
        this.id = nextId++;
    }

    public static int getNextId() {
        return nextId;
    }

    public int getId() {
        return id;
    }

    public int getEventId() {
        return eventId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", customerId=" + customerId +
                '}';
    }
}
