import java.util.InputMismatchException;
import java.util.Scanner;

public class TicketingSystemCLI {

    public static void main(String[] args) {
        boolean running = true;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Real-Time Event Ticketing System!");

        while (running) {
            displayMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    startSystem();
                    break;
                case 2:
                    stopSystem();
                    break;
                case 3:
                    updateConfigurationParameters(scanner);
                    break;
                case 4:
                    retrieveConfigurationParameters();
                    break;
                case 5:
                    System.out.println("Exiting from the system");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n.........Main Menu.........\n");
        System.out.println("1. Start the System");
        System.out.println("2. Stop the System");
        System.out.println("3. Update Configuration Parameters");
        System.out.println("4. Retrieve Configuration Parameters");
        System.out.println("5. Exit");
    }

    private static int getUserChoice(Scanner scanner) {
        System.out.print("\nSelect an option to continue the process: \n");
        try {
            return Integer.parseInt(scanner.next());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return -1;
        }
    }

    public static void startSystem() {
        System.out.println("System started.");
        boolean isRunning = true;

        if (SystemConfig.getInstance().getTotalTickets() <= 0) {
            System.out.println("System is not configured. Please set the configuration first.");
            return;
        }

       ThreadClass threadClass=new ThreadClass();
        threadClass.start();
        isRunning = true;


    }

    public static void stopSystem(){
        System.out.println("System Stopped");
        ThreadClass threadClass = new ThreadClass();
        threadClass.stopSystem();
    }
    public static void updateConfigurationParameters(Scanner scanner) {

        SystemConfig sysConfig = SystemConfig.getInstance();

        System.out.print("Total Tickets: ");
        int totalTickets = scanner.nextInt();
        sysConfig.setTotalTickets(totalTickets);


        System.out.print("Ticket Release Rate: ");
        int ticketReleaseRate = scanner.nextInt();
        sysConfig.setTicketReleaseRate(ticketReleaseRate);


        System.out.print("Customer Retrieval Rate: ");
        int customerRetrievalRate = scanner.nextInt();
        sysConfig.setCustomerRetrievalRate(customerRetrievalRate);

        System.out.print("Maximum Ticket Capacity : ");
        int maxticketCapacity = scanner.nextInt();
        sysConfig.setMaxTicketCapacity(maxticketCapacity);

        System.out.println("Configuration parameters updated successfully.");
    }

    public static void retrieveConfigurationParameters() {
        System.out.println(SystemConfig.getInstance().toString());
    }
}
