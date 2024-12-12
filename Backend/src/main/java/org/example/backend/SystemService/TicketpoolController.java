package org.example.backend.SystemService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// handle API requests related to ticketpool status

@RestController
@RequestMapping("/api/tickets")


public class TicketpoolController {

    private TicketPool ticketPool;

    /**
     * check if ticket are available
     * @return a ResponseEntity with the status messages
     */
    @GetMapping("/status")
    public ResponseEntity<?> getTicketStatus() {
        String statusMessage;
        if (ticketPool.getCurrentListSize() > 0) {
            statusMessage = "Tickets are available";
        } else if (TicketingService.getSystemStatus()) {
            statusMessage = "Tickets are unavailable";
        } else {
            statusMessage = "System has stopped";
        }

        return ResponseEntity.ok().body("{\"status\": \"" + statusMessage + "\"}");
    }
}
