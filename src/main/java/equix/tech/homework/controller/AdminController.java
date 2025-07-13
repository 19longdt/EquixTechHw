package equix.tech.homework.controller;

import equix.tech.homework.application.dto.CommonResponse;
import equix.tech.homework.application.service.AdminService;
import equix.tech.homework.common.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger("equix.AdminController");
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/orders/simulate-execution")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CommonResponse> simulate() {
        log.debug("[Simulate] request");
        adminService.simulateMatching();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse(Messages.SIMULATE_EXECUTION, Messages.SUCCESS, true));
    }
}
