package org.example.backend.TicketingConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ConfigurationService {

    private static final String CONFIG_FILE = "config.json"; // File to store configuration
    private static ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper for JSON processing

    // Singleton instance
    private static ConfigurationService instance;

    public ConfigurationService() {
        this.objectMapper = new ObjectMapper(); // Initialize ObjectMapper
    }

    // Public method to provide the Singleton instance
    public static synchronized ConfigurationService getInstance() {
        if(instance == null) {
            instance = new ConfigurationService();
        }
        return instance;
    }

    /**
     * Saves the given configuration to a JSON file.
     *
     * @param config the configuration to save
     * @throws IOException if there is an error during file writing
     */
    public static void saveConfiguration(Configuration config) throws IOException {
        // Serialize the configuration object to JSON and write it to the file
        objectMapper.writeValue(new File(CONFIG_FILE), config);
    }

    /**
     * Loads the configuration from the JSON file.
     *
     * @return the loaded configuration object
     * @throws IOException if the file is not found or cannot be read
     */
    public static Configuration loadConfiguration() throws IOException {
        // Check if the configuration file exists
        File file = new File(CONFIG_FILE);

        // Deserialize the JSON file into a SystemConfiguration object
        return objectMapper.readValue(file, Configuration.class);
    }
}
