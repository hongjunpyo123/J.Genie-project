package com.project.JGenie.domain.serverstatus;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerStatusController {
    private final HttpSession session;

    public ServerStatusController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/server/status")
    public ResponseEntity<String> serverStatus() {
        session.setAttribute("serverStatus", "running");
        return ResponseEntity.ok("Server is running");
    }
}
