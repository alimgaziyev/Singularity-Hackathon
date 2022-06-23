package kz.singularity.hackaton.backendhackatonvegetables.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kz.singularity.hackaton.backendhackatonvegetables.models.QueryToPermit;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import kz.singularity.hackaton.backendhackatonvegetables.repository.PermitRepository;
import kz.singularity.hackaton.backendhackatonvegetables.service.BookingService;
import kz.singularity.hackaton.backendhackatonvegetables.service.PermitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "admins")
@RequestMapping("/api/permit")
@RequiredArgsConstructor
public class AdminController {
    private final PermitService permitService;


    @GetMapping("/get-to-permit")
    public ResponseEntity<?> getToPermit() {
        QueryToPermit queryToPermit = permitService.getUserToPermit();
        return ResponseEntity.ok(queryToPermit);
    }

    @PostMapping("/permission")
    public ResponseEntity<?> bookingRoomByPermission(@RequestBody String permission) {
        ResponseOutputBody response = permitService.doPermission(permission);
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }
}
