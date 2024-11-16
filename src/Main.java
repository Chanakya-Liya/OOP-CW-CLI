//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Util.validateConfig();
        Util.generateSimulatedUsers();

        for(Customer customer : Util.getCustomers()){
            Thread customerThread = new Thread(customer);
            customerThread.start();
        }

        for(Vendor vendor : Util.getVendors()){
            Thread vendorThread = new Thread(vendor);
            vendorThread.start();
        }
        System.out.println("Simulation Running");

        Util.endProgram();
    }
}