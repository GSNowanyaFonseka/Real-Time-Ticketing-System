package org.example.backend.TicketingConfiguration;

import lombok.Getter;
import org.hibernate.annotations.SecondaryRow;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<String>());

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

            try{
                if (currentListSize >= maxTicketCapacity) {
                    System.out.println("TicketPool is full, "+" Vendor" + vendorID + " is waiting");
                }
                else if(ticketCount >= totalTickets) {
                    System.out.println("All the tikets are added to the pool vendor " + vendorID + " is waiting");
                }

                // Vendors are waiting
                wait();

            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                System.out.println("vendor " + vendorID + " thread interrupted");
            }
        }

        ticketCount++;
        String ticket = "Ticket : " + ticketCount;
        ticketList.add(ticket);
        currentListSize++;
        System.out.println("Vendor " + vendorID + " added " +ticket+ " tickets. Current Pool Size: " + currentListSize + "/" + maxTicketCapacity);
        notifyAll();
    }

    public synchronized void removeTickets(long customerID) {

        while(currentListSize <= 0){
            try{
                System.out.println("Ticket pool is empty customer " + customerID + " is waiting");

                wait();

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        String ticket = ticketList.remove(0);
        currentListSize--;
        System.out.println("Customer " + customerID + " bought " + ticket+ " tickets " +  ". Current Pool Size: " + currentListSize+ "/" + maxTicketCapacity);
        notifyAll();
    }


}
