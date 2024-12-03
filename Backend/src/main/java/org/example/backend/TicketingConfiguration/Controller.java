package org.example.backend.TicketingConfiguration;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/configuration")

public class Controller {

    private final ConfigurationService configurationService;

    private Configuration currentconfig;

    public Controller(){
        this.configurationService = new ConfigurationService();
        try{
            this.currentconfig = configurationService.loadConfiguration();
        }catch (IOException e){
            this.currentconfig = new Configuration();
        }
    }

    @PostMapping
    public ResponseEntity<String> setConfiguration(@Valid @RequestBody Configuration config) {

        if(config.getTotalTickets() <= 0 || config.getTicketReleaseRate() <= 0 ||
                config.getCustomerRetrievalRate() <= 0 || config.getMaxTicketCapacity() <=0){
            return ResponseEntity.badRequest().body("All values must be positive integers");
        }

        if(config.getMaxTicketCapacity() < config.getTicketReleaseRate()){
            return ResponseEntity.badRequest().body("Max ticket capacity must be greater than or equal to ticket release rate");
        }

        this.currentconfig = config;

        try{
         configurationService.saveConfiguration(currentconfig);

         // Display in terminal
            System.out.println("Total tickets : " + this.currentconfig.getTotalTickets());
            System.out.println("Customer retrival rate : " + this.currentconfig.getCustomerRetrievalRate());
            System.out.println("Max ticket capacity : " + this.currentconfig.getMaxTicketCapacity());
            System.out.println("Ticket release rate : " + this.currentconfig.getTicketReleaseRate());

        }catch (IOException e){
            return ResponseEntity.badRequest().body("Failed to save configuration");
        }

        return ResponseEntity.ok().body("Configuration saved successfully!");
    }

    @GetMapping
    public ResponseEntity<Configuration> getConfiguration() {
        return ResponseEntity.ok(this.currentconfig);
    }
}
