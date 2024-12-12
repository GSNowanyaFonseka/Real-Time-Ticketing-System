import java.util.ArrayList;
import java.util.List;

public class ThreadClass {

    private TicketPool ticketPool=new TicketPool();

    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();

    private static boolean isRunning = false;


    public synchronized String start() {
        if (isRunning) {
            return "The system is already running!";
        }

        if (ticketPool == null) {
            return "System is not configured. Please set the configuration first.";
        }

        isRunning = true;

        // Create and start vendor threads
        for (int i = 1; i <= 5; i++) {
            int vendorID = i; // Logical vendor ID
            Vendor vendor = new Vendor(ticketPool, SystemConfig.getInstance().getCustomerRetrievalRate(),vendorID);
            Thread vendorThread = new Thread(vendor);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads
        for (int i = 1; i <= 5; i++) {
            int customerID = i; // Logical customer ID
            Customer customer = new Customer(ticketPool, SystemConfig.getInstance().getCustomerRetrievalRate(),customerID);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
        }

        return "Ticketing system has started with 5 vendors and 5 customers.!";
    }

    public synchronized String stopSystem() {
        if (!isRunning) {
            return "The system is not running!";
        }

        isRunning = false;

        // Interrupt and clear vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        vendorThreads.clear();


        System.out.println("All Vendor operations are stopped!");


        // Interrupt and clear customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
        customerThreads.clear();

        System.out.println("All Customer operations are stopped!");

        return "Ticketing system has been stopped!";
    }


    public static boolean getSystemStatus() {
        return isRunning;
    }

}
