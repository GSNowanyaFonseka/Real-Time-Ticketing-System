package org.example.backend.TicketingConfiguration;

import org.example.backend.SystemService.TicketingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Controller class to handle API requests related
 * to configuration and ticketing system
 */

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins="http://localhost:3000")

public class Controller {

    // defining instance variables for configuration and ticketing
    private final ConfigurationFileService configurationService;
    private final TicketingService ticketingService;

    // current configuration instance
    private Configuration currentConfig;

    /**
     * Defualt constructor
     *
     * @param configurationService the service responsible for handling configuration files
     * @param ticketingService the service responsible for managing ticketing system
     */
    public Controller(ConfigurationFileService configurationService, TicketingService ticketingService) {
        this.configurationService = configurationService; // Use injected singleton instance
        this.ticketingService = ticketingService; // Use injected TicketingService

        try {
            this.currentConfig = configurationService.loadConfiguration(); // Load configuration
            this.ticketingService.initializeSystem(currentConfig); // Initialize the ticketing system
        } catch (IOException e) {
            this.currentConfig = Configuration.getInstance(); // Fallback to default configuration
        }
    }

    /**
     * Sets the configuration for the ticketing system
     *
     * @param config the new configuration to set
     * @return a ReposeEntity indicateing the result of the operation
     */
    @PostMapping
    public ResponseEntity<String> setConfiguration(@RequestBody Configuration config) {

        // Validation of configuration values
        if (config.getTotalTickets() <= 0 || config.getTicketReleaseRate() <= 0 ||
                config.getCustomerRetrievalRate() <= 0 || config.getMaxTicketCapacity() <= 0) {
            return ResponseEntity.badRequest().body("All configuration values must be positive integers.");
        }

        if(config.getTotalTickets() > config.getMaxTicketCapacity()){
            return ResponseEntity.badRequest().body("Max ticket capacity should be greater than or equal to the total tickets.");
        }

        this.currentConfig = config;  // update current configuration

        // check the state of running and stop before applying the new configuration
        if (TicketingService.getSystemStatus()) {
            ticketingService.stopSystem();
        }

        try {
            configurationService.saveConfiguration(config); // Save to JSON file
            ticketingService.initializeSystem(config); // Reinitialize TicketingService with new config
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to save configuration.");
        }

        return ResponseEntity.ok("Configuration saved successfully!");
    }

    /**
     * Return current configuration
     * @return
     */
    @GetMapping()
    public ResponseEntity<Configuration> getConfiguration() {
        // return always latest Configuration
        return ResponseEntity.ok(this.currentConfig);
    }

    /**
     * starts the ticketing system
     * @return
     */
    @PostMapping("/start")
    public ResponseEntity<String> startSystem() {
        String response = ticketingService.startSystem(); // Starts the system
        return ResponseEntity.ok(response);
    }

    /**
     * stops the system
     * @return
     */
    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        String response = ticketingService.stopSystem();
        return ResponseEntity.ok(response); // Stops hte system
    }
}


