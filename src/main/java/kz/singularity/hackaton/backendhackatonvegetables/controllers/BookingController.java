package kz.singularity.hackaton.backendhackatonvegetables.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@SecurityRequirement(name = "bearer")
@RequestMapping("/api/booking")
public class BookingController {
  @GetMapping("/getfreetime/{rood_id}/{week_day}")
  public ResponseEntity<?> getFreeTime(@PathVariable String rood_id, @PathVariable String week_day) {
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
