package kz.singularity.hackaton.backendhackatonvegetables.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import kz.singularity.hackaton.backendhackatonvegetables.models.ETime;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.service.ListingFreeTimesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "bearer")
@RequestMapping("/api/booking")
@RequiredArgsConstructor
public class BookingController {
  private final ListingFreeTimesService listingFreeTimesService;
  @GetMapping("/getfreetime")
  public ResponseEntity<?> getFreeTime(@RequestBody GetFreeTimeRequest getFreeTimeRequest) {
    try {
      List<ETime> freeTimes =
              listingFreeTimesService
                      .getFreeTimesByRoomAndDay(
                              getFreeTimeRequest.getRoomId(),
                              getFreeTimeRequest.getWeekDay());
    } catch (Exception e) {

    }
    return ResponseEntity.ok("");
  }

  @PostMapping("/booking-room")
  public ResponseEntity<?> bookingRoom(@RequestBody BookingRequest bookingRequest) {

    return ResponseEntity.ok("");
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
