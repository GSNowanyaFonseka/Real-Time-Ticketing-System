package org.example.backend.TicketingConfiguration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Configuration {

    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;

    // singleton instance
    private static Configuration instance;

    private Configuration(){
        this.totalTickets = 0;
        this.ticketReleaseRate = 0;
        this.customerRetrievalRate = 0;
        this.maxTicketCapacity = 0;
    }

    public static synchronized Configuration getInstance(){
        if(instance == null){
            instance = new Configuration();
        }
        return instance;
    }
}

