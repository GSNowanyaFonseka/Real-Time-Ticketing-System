package org.example.backend.TicketingConfiguration;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketingService {

    private TicketPool ticketPool;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();
    private boolean isRunning = false;

    // Initialize TicketPool with the current configuration
    public void initializeSystem(Configuration config) {
        this.ticketPool = new TicketPool(config);
    }

    public synchronized String startSystem() {
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
            Vendor vendor = new Vendor(ticketPool, ticketPool.getTicketReleaseRate(), ticketPool.getMaxTicketCapacity(), vendorID);
            Thread vendorThread = new Thread(vendor, "Vendor " + vendorID);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads
        for (int i = 1; i <= 5; i++) {
            int customerID = i; // Logical customer ID
            Customer customer = new Customer(ticketPool, ticketPool.getCustomerRetrievalRate(), customerID);
            Thread customerThread = new Thread(customer, "Customer " + customerID);
            customerThreads.add(customerThread);
            customerThread.start();
        }

        return "Ticketing system started with 5 vendors and 5 customers!";
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

        // Interrupt and clear customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
        customerThreads.clear();

        return "Ticketing system stopped!";
    }

//    public synchronized String getSystemStatus() {
//        return isRunning ? "The system is running." : "The system is stopped.";
//    }
}
