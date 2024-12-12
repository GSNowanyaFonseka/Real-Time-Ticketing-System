package org.example.backend.TicketingConfiguration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// This class representing the configuration settings for the real-time ticketing system

@Getter
@Setter
@Data

public class Configuration {

    // Define instance variables
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // singleton instance
    private static Configuration instance;

    /**
     * Private Constructor - to prevent creating objects
     */
    private Configuration(){
    }

    /**
     * Returns the sinleton instance of the class
     * @return singleton istance of Configuration
     */
    public static synchronized Configuration getInstance(){
        if(instance == null){
            instance = new Configuration(); // create a new instance if it doesn't exist
        }
        return instance;
    }
}

