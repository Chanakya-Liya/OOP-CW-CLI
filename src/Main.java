//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Util.generateSimulatedUsers();
        for(int i = 0; i < Util.getCustomers().size(); i++){
            Thread customerThread = new Thread(Util.getCustomers().get(i));
            customerThread.start();
        }

        for(int i = 0; i < Util.getEvents().size(); i++){
            Thread eventThread = new Thread(Util.getEvents().get(i));
            eventThread.start();
          }
    }
}