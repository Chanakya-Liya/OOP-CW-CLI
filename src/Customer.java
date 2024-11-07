public class Customer extends User implements Runnable {
    private static int nextId = 1;
    private final int CustomerId;
    private int retrievalRate;
    private int frequency;

    public static final String BLUE = "\033[0;34m";
    public static final String RESET = "\033[0m";

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

    @Override
    public void run(){
        int totalTickets = 0;
        for(Event event : Util.getEvents()){
            totalTickets += event.getTotalEventTickets();
        }
        while(totalTickets > 0){
            try {
                for(int i = 0; i < retrievalRate; i++){
                    boolean flag = true;
                    while(flag) {
                        int eventId = Util.generateRandomInt(0, Util.getEvents().size());
                        Event selectedEvent = Util.getEvents().get(eventId);
                        synchronized (selectedEvent){
                            if(!Util.getEvents().get(eventId).getPoolTickets().isEmpty()){
                                Util.getEvents().get(eventId).removeTicketFromPool();
                                System.out.println(BLUE + Util.getEvents().get(eventId) + " CustomerId: " + CustomerId + RESET);
                                flag = false;
                                totalTickets--;
                            }
                        }
                    }
                }
                Thread.sleep(frequency * 1000L);
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
