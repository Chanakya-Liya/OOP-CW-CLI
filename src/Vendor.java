import java.util.ArrayList;

public class Vendor extends User{
    private final ArrayList<Event> events = new ArrayList<Event>();
    private static int nextId = 1;
    private final int vendorId;

    public Vendor(String fName, String lName, String username, String password, String email, boolean simulated) {
        super(fName, lName, username, password, email, simulated);
        this.vendorId = nextId++;
    }

    public int getVendorId() {
        return vendorId;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(Event event) {
       this.events.add(event);
    }

    @Override
    public String toString() {
        StringBuilder eventIdBuilder = new StringBuilder("[");
        for (Event event : events) {
            eventIdBuilder.append(event.getId()).append(", ");
        }

        if (!events.isEmpty()) {
            eventIdBuilder.setLength(eventIdBuilder.length() - 2);
        }
        eventIdBuilder.append("]");
        return "Vendor{" +
                "eventIds=" + eventIdBuilder +
                ", id=" + vendorId +
                "} " + super.toString();
    }
}
