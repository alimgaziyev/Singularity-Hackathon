package kz.singularity.hackaton.backendhackatonvegetables.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import kz.singularity.hackaton.backendhackatonvegetables.service.BookingService;
import kz.singularity.hackaton.backendhackatonvegetables.service.ListingFreeTimesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "bearer")
@RequestMapping("/api/booking")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/booking-room")
    public ResponseEntity<?> getFreeTime(@RequestParam("room") String room, @RequestParam("weekday") String weekday) {
        ResponseOutputBody response = bookingService.getFreeTimeOnDayAndRoom(new GetFreeTimeRequest(room, weekday));
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }

    @PostMapping("/booking-room")
    public ResponseEntity<?> bookingRoom(@RequestBody BookingRequest bookingRequest, HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        ResponseOutputBody response = bookingService.createBooking(bookingRequest, jwtToken);
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }

    @GetMapping("/all-day-activity")
    public ResponseEntity<?> getAllDayActivity(@RequestParam("room") String room, @RequestParam("weekday") String weekday) {
        ResponseOutputBody response = bookingService.getAllDayActivity(room, weekday);
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }

    @GetMapping("/show-my-reservation")
    public ResponseEntity<?> getMyReservation(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        System.out.println(12);
        ResponseOutputBody response = bookingService.getMyReservation(jwtToken);
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
