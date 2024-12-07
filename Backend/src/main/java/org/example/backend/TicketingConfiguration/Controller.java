package org.example.backend.TicketingConfiguration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/config")

public class Controller {

    private final ConfigurationService configurationService;

    private Configuration currentConfig;

    public Controller() {
        this.configurationService = new ConfigurationService();
        try{
            this.currentConfig = configurationService.loadConfiguration();
        }catch (IOException e){
            this.currentConfig = Configuration.getInstance();
        }
    }

    @PostMapping
    public ResponseEntity<String> setConfiguration( @RequestBody Configuration config) {

        // validation
        if(config.getTotalTickets() <= 0 || config.getTicketReleaseRate() <= 0 ||
                config.getCustomerRetrievalRate() <= 0 || config.getMaxTicketCapacity() <=0){
            return ResponseEntity.badRequest().body("All values must be positive integers");
        }

//        if(config.getMaxTicketCapacity() < config.getTicketReleaseRate()){
//            return ResponseEntity.badRequest().body("Max ticket capacity must be greater than or equal to ticket release rate");
//        }

        this.currentConfig = config;

        try{
            configurationService.saveConfiguration(config); // save to Json file

            // Display in terminal
            System.out.println(config.toString());


        }catch (IOException e){
            return ResponseEntity.badRequest().body("Failed to save configuration");
        }

        return ResponseEntity.ok().body("Configuration saved successfully!");
    }

    @GetMapping
    public ResponseEntity<Configuration> getConfiguration() {
        return ResponseEntity.ok(this.currentConfig);
    }
}
