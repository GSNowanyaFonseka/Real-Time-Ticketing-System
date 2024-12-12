public class Customer implements Runnable{

    private final TicketPool ticketPool;
    private final int customerID;
    private final int  customerRetrievalRate;

    /**
     * Constructor for customer
     *
     * @param ticketPool the share ticket pool
     * @param customerRetrievalRate rate at which tickets are retrieved
     * @param customerID ID of the customer
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int customerID) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.customerID = customerID;
    }

    @Override
    public void run() {
        while(ThreadClass.getSystemStatus() && !Thread.currentThread().isInterrupted()){
            try{
                ticketPool.removeTickets(customerID);
                Thread.sleep(1000/customerRetrievalRate);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
