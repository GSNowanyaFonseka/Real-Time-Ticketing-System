package org.example.backend.TicketingConfiguration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/config")
@CrossOrigin(origins="http://localhost:3000")

public class Controller {

    private final ConfigurationService configurationService;
    private final TicketingService ticketingService; // Use TicketingSystem to manage threads
    private Configuration currentConfig;

    public Controller() {
        this.configurationService = ConfigurationService.getInstance(); // Use singleton instance
        this.ticketingService = new TicketingService();  // Initialize TicketingSystem
        try{
            this.currentConfig = configurationService.loadConfiguration();
            this.ticketingService.initializeSystem(currentConfig); // Initialize the ticketing sytem
        } catch (IOException e) {
            this.currentConfig = Configuration.getInstance();
        }
    }

    @PostMapping
    public ResponseEntity<String> setConfiguration(@RequestBody Configuration config){
        // Validation
        if (config.getTotalTickets() <= 0 || config.getTicketReleaseRate() <= 0 ||
                config.getCustomerRetrievalRate() <= 0 || config.getMaxTicketCapacity() <= 0) {
            return ResponseEntity.badRequest().body("All values must be positive integers.");
        }

        this.currentConfig = config;

        try{
            configurationService.saveConfiguration(config); // Save to JSON file
            ticketingService.initializeSystem(config); // Again initialize TicketingSystem with new config
        }catch (IOException e){
            return ResponseEntity.badRequest().body("Failed to save configuration.");
        }

        return ResponseEntity.ok().body("Configuration saved successfully!");
    }

    @GetMapping()
    public ResponseEntity<Configuration> getConfiguration() {
        // Always return the latest Configuration instance
        return ResponseEntity.ok(this.currentConfig);
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSystem() {
        String response = ticketingService.startSystem(); // Starts the system
        return ResponseEntity.ok(response);
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        String response = ticketingService.stopSystem();
        return ResponseEntity.ok(ticketingService.stopSystem()); // Stops hte system
    }

    @GetMapping("/status")
    public ResponseEntity<String> getSystemStatus() {
        String status = ticketingService.getSystemStatus(); // Get the current system status
        return ResponseEntity.ok(status);
    }
}