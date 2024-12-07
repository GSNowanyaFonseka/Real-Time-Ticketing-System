package org.example.backend.TicketingConfiguration;

public class Customer implements Runnable{

    private final TicketPool ticketPool;
    private final int customerID;
    private final int  customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customerID) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerID = customerID;
    }

    @Override
    public void run() {
        while(true){
            try{
                ticketPool.removeTickets(customerID);
                Thread.sleep(1000/customerRetrievalRate);

            } catch (InterruptedException e) {
                System.out.println("Customer " + customerID + " interrupted");
                throw new RuntimeException(e);
            }
        }
    }
}
