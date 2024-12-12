
public class Vendor implements Runnable{

    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final int vendorID;


    public Vendor(TicketPool ticketPool, int ticketReleaseRate, int vendorID) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.vendorID = vendorID;
    }

    @Override
    public void run() {

        while (ThreadClass.getSystemStatus()&& !Thread.currentThread().isInterrupted()) {
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
