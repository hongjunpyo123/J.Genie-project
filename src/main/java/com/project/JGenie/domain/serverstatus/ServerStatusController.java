package com.project.JGenie.domain.serverstatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerStatusController {
    @GetMapping("/server/status")
    public ResponseEntity<String> serverStatus() {
        return ResponseEntity.ok("Server is running");
    }
}
