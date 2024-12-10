package org.example.backend.TicketingConfiguration;

import java.io.IOException;

public class Vendor implements Runnable{

    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
//    private static int ticketCounter = 0;
    private final int vendorID;
    private final int maxTicketCpacity;

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
                System.out.println("Vendor " +vendorID +" interrupted");
                break;
        }
    }
}
}
