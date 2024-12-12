import java.lang.Thread;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {


    private int ticketCount = 0;
    private int currentListSize = 0;

    private boolean allTicketAdded = false;
    private boolean ticketPoolEmpty = false;

    SystemConfig config = SystemConfig.getInstance();


    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<>());

    public synchronized void addTicket(int vendorID) {

        while (currentListSize >= config.getMaxTicketCapacity() ||
                ticketCount >= config.getTotalTickets()) {

            if (ThreadClass.getSystemStatus())
                return;

            try {
                if (ticketCount >= config.getTotalTickets() && !allTicketAdded) {
                    System.out.println("All tickets have added to the pool, vendors are now waiting for customer transactions.");
                    allTicketAdded = true;
                }

                else{
                    System.out.println("Ticket Pool is Full ."+ vendorID+" is waiting ");

                }
                // Vendors are waiting
                wait();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

        }

        // add new ticket to the pool
        ticketCount++;
        String ticket = "Ticket : " + ticketCount;
        ticketList.add(ticket);
        currentListSize++;
        System.out.println("Vendor " + vendorID + " added " + ticket +
                " ticket. Remaining number of tickets :  " + currentListSize + "/" + config.getMaxTicketCapacity());

        allTicketAdded = false;  //reset the flag if conditions change
        notifyAll();
    }
    // remove ticket from the pool
    public synchronized void removeTickets(int customerID) {

        while (currentListSize <= 0) {
            if (ThreadClass.getSystemStatus()) {
                return; // Exit if system status indicates termination
            }

            try {
                if (!ticketPoolEmpty) {
                    System.out.println("All tickets have been sold. Customers are in a waiting state.");
                    ticketPoolEmpty = true;  // Display the message only once
                }

                // Wait for tickets to be added
                wait();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        // Remove ticket from the pool
        String ticket = ticketList.remove(0);
        currentListSize--;
        System.out.println("Customer " + customerID + " bought " + ticket +
                " ticket. Remaining number of tickets: " +
                currentListSize + "/" + config.getMaxTicketCapacity());

        ticketPoolEmpty = false; // Reset the flag when tickets are available
        notifyAll(); // Notify all waiting threads
    }



}