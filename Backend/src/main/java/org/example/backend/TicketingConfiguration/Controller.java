package org.example.backend.TicketingConfiguration;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/configuration")

public class Controller {

    private final ConfigurationService configurationService;

    private Configuration currentconfig = new Configuration();

    public Controller(){
        this.configurationService = new ConfigurationService();
        try{
            this.currentconfig = configurationService.loadConfiguration();
        }catch (IOException e){
            this.currentconfig = new Configuration();
        }
    }

    @PostMapping
    public ResponseEntity<String> setConfiguration(@Valid @RequestBody Configuration newconfig) {

        if(newconfig.getTotalTickets() <= 0 || newconfig.getTicketReleaseRate() <= 0 ||
                newconfig.getCustomerRetrievalRate() <= 0 || newconfig.getMaxTicketCapacity() <=0){
            return ResponseEntity.badRequest().body("All values must be positive integers");
        }

        if(newconfig.getMaxTicketCapacity() < newconfig.getTicketReleaseRate()){
            return ResponseEntity.badRequest().body("Max ticket capacity must be greater than or equal to ticket release rate");
        }

        this.currentconfig = newconfig;

        try{
         configurationService.saveConfiguration(newconfig);
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
