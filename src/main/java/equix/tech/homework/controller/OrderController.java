package equix.tech.homework.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger("equix.OrderController");

    @PostMapping("/create")
    public ResponseEntity<String> create(@RequestBody Object request) {
        log.debug("[Create] receipt request {}", request.toString());
        return ResponseEntity.ok("Create completed");
    }
}
