package org.example.backend.SystemService;

import org.example.backend.Frontend.LogService;
import org.example.backend.SystemThreads.Customer;
import org.example.backend.SystemThreads.Vendor;
import org.example.backend.TicketingConfiguration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for managing ticketing system
 */
@Service
public class TicketingService {

    private static final Logger log = LoggerFactory.getLogger(TicketingService.class);
    private TicketPool ticketPool;
    private final List<Thread> vendorThreads = new ArrayList<>();
    private final List<Thread> customerThreads = new ArrayList<>();
    private static boolean isRunning = false;

    private final LogService logService;

    /**
     * Constructor
     * @param logService the service for logging events
     */
    public TicketingService(LogService logService) {
        this.logService = logService;
    }

    /**
     * Initializes the ticketPool with the current configuration
     * @param config the configuration for the ticket pool
     */
    public void initializeSystem(Configuration config) {
        this.ticketPool = new TicketPool(config, logService);
    }

    /**
     * Starts the ticketing system by creating and starting vendor and customer threads
     * @return a message indicating the result of the operation
     */
    public synchronized String startSystem() {
        // check if the system is already running
        if (isRunning) {
            return "The system is already running!";
        }

        // check if the ticket pool if configured (not null)
        if (ticketPool == null) {
            return "System is not configured. Please set the configuration first.";
        }

        isRunning = true;

        // Create and start vendor threads
        for (int i = 1; i <= 5; i++) {
            int vendorID = i;
            Vendor vendor = new Vendor(ticketPool, ticketPool.getTicketReleaseRate(), ticketPool.getMaxTicketCapacity(), vendorID);
            Thread vendorThread = new Thread(vendor, "Vendor " + vendorID);
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }

        // Create and start customer threads
        for (int i = 1; i <= 5; i++) {
            int customerID = i;
            Customer customer = new Customer(ticketPool, ticketPool.getCustomerRetrievalRate(), customerID);
            Thread customerThread = new Thread(customer, "Customer " + customerID);
            customerThreads.add(customerThread);
            customerThread.start();
        }

        return "Ticketing system has started with 5 vendors and 5 customers.!";
    }

    /**
     * stops the ticketing system by interrupting and clearing vendor and customer threads
     * @return a message indicating the result of the operation
     */
    public synchronized String stopSystem() {

        // check if the system is running
        if (!isRunning) {
            return "The system is not running!";
        }

        isRunning = false;

        // Interrupt and clear vendor threads
        for (Thread vendorThread : vendorThreads) {
            vendorThread.interrupt();
        }
        vendorThreads.clear();


        logService.addLog("All Vendor operations are stopped!");


        // Interrupt and clear customer threads
        for (Thread customerThread : customerThreads) {
            customerThread.interrupt();
        }
        customerThreads.clear();

        logService.addLog("All Customer operations are stopped!");

        return "Ticketing system has been stopped!";
    }

    /**
     * gets the status of the ticketing system
     * @return true if the system is running or else false
     */
    public static boolean getSystemStatus() {
        return isRunning;
    }
}
