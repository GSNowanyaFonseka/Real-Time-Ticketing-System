//package org.example.backend.CLI;
//
//import org.example.backend.TicketingConfiguration.Configuration;
//import org.example.backend.TicketingConfiguration.ConfigurationService;
//import org.example.backend.TicketingConfiguration.TicketingService;
//
//import java.util.Scanner;
//
//public class TicketingCLI {
//
//    public static void main(String[] args) {
//        System.out.println("Welcome to the Real-Time Event Ticketing System!");
//        Configuration config = setupConfiguration();
//        TicketingService ticketingService = new TicketingService();
//        ticketingService.initializeSystem(config);
//
//        Scanner input = new Scanner(System.in);
//
//        boolean start = true;
//
//        while(start) {
//            System.out.println(".........Main Menu.........");
//            System.out.println("\n1.  Start the System");
//            System.out.println("\n2.  Stop the System");
//            System.out.println("\n3.  View System Status");
//            System.out.println("\n4.  Exit");
//
//            int option = input.nextInt();
//
//            System.out.println("\nChoose an option: " + option);
//
//            switch(option) {
//                case 1: systemStart();
//                break;
//
//                case 2: systemStop();
//                break;
//
//                case 3: viewStatus();
//                break;
//
//                case 4: {System.out.println("Exiting...");
//                    System.exit(0);}
//                    break;
//                default:
//                    System.out.println("Invalid option! Please try again.");
//            }
//        }
//    }
//
//    private static Configuration setupConfiguration() {
//        try{
//            System.out.println("\n Setting up configuration ");
//            Configuration config = ConfigurationService.loadConfiguration();
//            System.out.println("Configuration loaded from file.");
//            return config;
//        } catch (Exception e) {
//            System.out.println("Unable to load configuration from file.");
//            Configuration config = Configuration.getInstance();
//
//            System.out.println("Enter total number of tickets: ");
//            config.setTotalTickets(input.nextInt());
//
//            System.out.println("Enter ticket release rate: ");
//            config.setTicketReleaseRate(input.nextInt());
//
//            System.out.println("Enter customer retrieval rate: ");
//            config.setCustomerRetrievalRate(input.nextInt());
//
//            System.out.println("Enter max ticket capacity: ");
//            config.setMaxTicketCapacity(input.nextInt());
//
//            input.nextLine();
//
//            try{
//                ConfigurationService.saveConfiguration(config);
//                System.out.println("Configuration saved successfully.");
//            } catch(Exception e){
//                System.out.println("Failed to save configuration.");
//            }
//
//            return config;
//        }
//
//        public static void startSystem(){
//            String response = ticketingService.startSystem();
//            System.out.println(response);
//        }
//
//        public static void stopSystem(){
//            String response = ticketingService.stopSystem();
//            System.out.println(response);
//        }
//
//        public static void viewStatus(){
//            String status = ticketingService.getSystemStatus();
//            System.out.println("System Status: " + status);
//        }
//    }
//}
