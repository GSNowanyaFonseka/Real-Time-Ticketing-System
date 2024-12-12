package org.example.backend.SystemThreads;

import org.example.backend.SystemService.TicketPool;
import org.example.backend.SystemService.TicketingService;

public class Vendor implements Runnable{

    // define instance variables
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int vendorID;
    private final int maxTicketCpacity;

    /**
     * Constructor for vendor
     * @param ticketPool ticketPool the shared ticket pool
     * @param ticketReleaseRate rate of which tickets are released
     * @param maxTicketCapacity maximum ticket capacity
     * @param vendorID vendorId of the vendor
     */
    public Vendor (TicketPool ticketPool, int ticketReleaseRate, int maxTicketCapacity, int vendorID) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorID = vendorID;
        this.maxTicketCpacity = maxTicketCapacity;
    }


    @Override
    public void run() {

        while (TicketingService.getSystemStatus() && !Thread.currentThread().isInterrupted()) {
            try {
                ticketPool.addTicket(vendorID);
                Thread.sleep(1000 / ticketReleaseRate);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
        }
    }
}
}
