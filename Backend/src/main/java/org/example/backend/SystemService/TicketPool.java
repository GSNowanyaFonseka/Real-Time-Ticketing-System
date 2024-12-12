package org.example.backend.SystemService;

import lombok.Getter;
import org.example.backend.Frontend.LogService;
import org.example.backend.TicketingConfiguration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter

// class representing a pool of tickets managed by vendors and customers
public class TicketPool {

    private final LogService logService;

    private static final Logger log = LoggerFactory.getLogger(TicketPool.class);
    private final int maxTicketCapacity;
    private final int totalTickets;
    private final int customerRetrievalRate;
    private final int ticketReleaseRate;
    private int currentListSize;
    private int ticketCount;

    private boolean allTicketAdded = false;
    private boolean ticketPoolEmpty = false;

    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<>());


    // Constructor that initializes fields from the Configuration Singleton instance
    // Constructor that accepts configuration values
    public TicketPool(Configuration config, LogService logService) {

        this.maxTicketCapacity = config.getMaxTicketCapacity();
        this.totalTickets = config.getTotalTickets();
        this.customerRetrievalRate = config.getCustomerRetrievalRate();
        this.ticketReleaseRate = config.getTicketReleaseRate();

        this.logService = logService; // for polling

        this.currentListSize = 0;
        this.ticketCount = 0;

        // Print values to the terminal (for debugging)
        System.out.println("TicketPool initialized with the following values:");
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
    }

    /**
     * adds a ticket to the pool
     * @param vendorID id of the vendor
     */
    public synchronized void addTicket(int vendorID){

        while(currentListSize >= maxTicketCapacity ||
                ticketCount >= totalTickets){

            if(!TicketingService.getSystemStatus())
                return;

            try{
                if(ticketCount >= totalTickets && !allTicketAdded){
                    log.info("All tickets have added to the pool, vendors are now waiting for customer transactions.");
                    String customLog ="All tickets have added to the pool, vendors are now waiting for customer transactions.";
                    logService.addLog(customLog);


                    allTicketAdded = true;
                }

                // Vendors are waiting
                wait();

            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }

        }

        // add new ticket to the pool
        ticketCount++;
        String ticket = "Ticket : " + ticketCount;
        ticketList.add(ticket);
        currentListSize++;
        log.info("Vendor " + vendorID + " added " +ticket+
                " ticket. Remaining number of tickets :  " + currentListSize + "/" + maxTicketCapacity );

        String customLog= "Vendor " + vendorID + " added " +ticket+
                " ticket. Remaining number of tickets :  " + currentListSize + "/" + maxTicketCapacity;
        logService.addLog(customLog);
        allTicketAdded = false;  //reset the flag if conditions change
        notifyAll();
    }

    // remove ticket from the pool
    public synchronized void removeTickets(int customerID) {

        while(currentListSize <= 0){

            if(!TicketingService.getSystemStatus())
                return;

            try{
                if(!ticketPoolEmpty){
                    String customLog= "All tickets have been sold. Customers are in a waiting state.";
                    logService.addLog(customLog);
                    ticketPoolEmpty = true;  // ensure the message display only once
                }

                // customers wait for vendors to add tickets
                wait();

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        // remove ticket from the pool
        String ticket = ticketList.remove(0);
        currentListSize--;
        String customLog = "Customer " + customerID + " bought " + ticket+ " ticket. Remaining number of tickets :  " + currentListSize + "/" + maxTicketCapacity;
        logService.addLog(customLog);
        ticketPoolEmpty = false; // reset the flag if conditions change
        notifyAll();
    }
}