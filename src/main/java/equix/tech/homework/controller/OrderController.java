package equix.tech.homework.controller;

import equix.tech.homework.application.dto.CommonResponse;
import equix.tech.homework.application.dto.order.OrderCreateRequest;
import equix.tech.homework.common.Messages;
import equix.tech.homework.common.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger("equix.OrderController");

    @PostMapping("/orders")
    public ResponseEntity<CommonResponse> create(@Valid @RequestBody OrderCreateRequest request) {
        log.debug("[Create] receipt request {}", Utils.toJsonString(request));
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new CommonResponse(Messages.ORDER_CREATE, Messages.SUCCESS, true, null, 1));
    }
}
