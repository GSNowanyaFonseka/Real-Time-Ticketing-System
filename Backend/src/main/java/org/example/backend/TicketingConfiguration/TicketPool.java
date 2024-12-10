package org.example.backend.TicketingConfiguration;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
public class TicketPool {

    private final int maxTicketCapacity;  // Maximum tickets allowed in ticketPool
    private final int totalTickets; // How many tickets have been added to the system
    private int currentListSize; // get the current size of ticketPool
    private int ticketCount; // How many tickets have been added to the system
    private int customerRetrievalRate;
    private int ticketReleaseRate;


    private boolean allTicketAdded = false;
    private boolean ticketPoolEmpty = false;

    //    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<String>());
    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<>());


    // Constructor that initializes fields from the Configuration Singleton instance
    // Constructor that accepts configuration values
    public TicketPool(Configuration config) {
        this.maxTicketCapacity = config.getMaxTicketCapacity();
        this.totalTickets = config.getTotalTickets();
        this.customerRetrievalRate = config.getCustomerRetrievalRate();
        this.ticketReleaseRate = config.getTicketReleaseRate();
        this.currentListSize = 0;
        this.ticketCount = 0;

        // Print values to the terminal
        System.out.println("TicketPool initialized with the following values:");
        System.out.println("Max Ticket Capacity: " + maxTicketCapacity);
        System.out.println("Total Tickets: " + totalTickets);
        System.out.println("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.println("Ticket Release Rate: " + ticketReleaseRate);
    }

    public synchronized void addTicket(long vendorID){

        while(currentListSize >= maxTicketCapacity ||
                ticketCount >= totalTickets){

            if(!TicketingService.getSystemStatus())
                return;

            try{
//                if (currentListSize >= maxTicketCapacity) {
//                    System.out.println("TicketPool is full, "+" Vendor" + vendorID + " is waiting");
//                }
//                else if(ticketCount >= totalTickets) {
//                    System.out.println("All the tikets are added to the pool vendor " + vendorID + " is waiting");
//                }

                if(ticketCount >= totalTickets && !allTicketAdded){
                    System.out.println("All tickets added, vendors are waiting");
                    allTicketAdded = true;
                }

                // Vendors are waiting
                wait();

            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
//                System.out.println("vendor " + vendorID + " thread stopped");
                return;
            }

        }


        ticketCount++;
        String ticket = "Ticket : " + ticketCount;
        ticketList.add(ticket);
        currentListSize++;
        System.out.println("Vendor " + vendorID + " added " +ticket+ " ticket. Current Pool Size: " + currentListSize + "/" + maxTicketCapacity);
        allTicketAdded = false;  //** reset the flag if conditions change
        notifyAll();
    }

    public synchronized void removeTickets(long customerID) {

        while(currentListSize <= 0){

            if(!TicketingService.getSystemStatus())
                return;

            try{
                if(!ticketPoolEmpty){
                    System.out.println("Ticket pool is empty, customers are waiting");
                    ticketPoolEmpty = true;  // ensure the message is display only once
                }
//                System.out.println("Ticket pool is empty customer " + customerID + " is waiting");

                wait();

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
//                System.out.println("customer " + customerID + " thread stopped");
            }
        }

        String ticket = ticketList.remove(0);
        currentListSize--;
        System.out.println("Customer " + customerID + " bought " + ticket+ " ticket. " +  " Current Pool Size: " + currentListSize+ "/" + maxTicketCapacity);
        ticketPoolEmpty = false; // reset the flag if conditions change
        notifyAll();
    }
}