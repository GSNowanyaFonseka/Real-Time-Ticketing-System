package org.example.backend.Frontend;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class LogService {

    // list to store logs
    private final List<String> logs = Collections.synchronizedList(new LinkedList<>());

    public void addLog(String log) {
        logs.add(log);
        // Limit the size of logs to avoid memory issues
        if (logs.size() > 1000) {
            logs.remove(0);
        }
    }

    public List<String> getLogs() {
        return new LinkedList<>(logs);
    }

//    public void clearLogs() {
//        logs.clear();
//    }
}


