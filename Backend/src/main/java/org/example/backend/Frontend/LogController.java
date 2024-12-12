package org.example.backend.Frontend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Handling log related API requests

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:3000")
public class LogController {

    private final LogService logService;

    /**
     * constructor for LogController
     * @param logService the service responsible for handling log operations
     */
    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * Endpoints to fetch all logs
     * @return a ResponseEntity containing a list of log entries
     */
    @GetMapping
    public ResponseEntity<List<String>> getLogs() {
        List<String> logs = logService.getLogs();
        return ResponseEntity.ok(logs);
    }

}


