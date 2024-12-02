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

}

