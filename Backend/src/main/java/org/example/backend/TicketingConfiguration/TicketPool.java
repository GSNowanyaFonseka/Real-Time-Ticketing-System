package org.example.backend.TicketingConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TicketPool {

    private final int maxTicketCapacity;  // Maximum tickets allowed in ticketPool
    private final int totalTickets; // How many tickets have been added to the system
    private int currentListSize; // get the current size of ticketPool
    private int ticketCount; // How many tickets have been added to the system
    private int customerRetrievalRate;
    private int ticketReleaseRate;

    private final List<String> ticketList = Collections.synchronizedList(new ArrayList<String>());

    public TicketPool(int totalTickets, int maxCapacity, int ticketRleaseRate, int customerRetrievalRate) {
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxCapacity;
        this.ticketReleaseRate = ticketRleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;

        currentListSize = 0;
        ticketCount = 0;
    }

    public synchronized void addTicket(int vendorID){

        while(currentListSize >= maxTicketCapacity ||
                ticketCount >= totalTickets){

            try{
                if(ticketCount >= totalTickets) {
                    System.out.println("All the tikets are added to the pool " + vendorID + " is waiting");
                }else if (currentListSize >= maxTicketCapacity) {
                    System.out.println("TicketPool is full, " + vendorID + " is waiting");
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
    }

    public synchronized void removeTickets(int customerID) throws InterruptedException {

        while(ticketList.isEmpty()){
            try{
                System.out.println("Ticket pool is empty " + customerID + " is waiting");

                wait();

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }

        String ticket = ticketList.remove(0);
        currentListSize--;
    }

}
