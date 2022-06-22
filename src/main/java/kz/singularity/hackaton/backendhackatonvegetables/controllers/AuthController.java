package kz.singularity.hackaton.backendhackatonvegetables.controllers;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERole;
import kz.singularity.hackaton.backendhackatonvegetables.models.Role;
import kz.singularity.hackaton.backendhackatonvegetables.models.User;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.LoginRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.SignupRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.JwtResponse;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.MessageResponse;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import kz.singularity.hackaton.backendhackatonvegetables.repository.RoleRepository;
import kz.singularity.hackaton.backendhackatonvegetables.repository.UserRepository;
import kz.singularity.hackaton.backendhackatonvegetables.security.jwt.JwtUtils;
import kz.singularity.hackaton.backendhackatonvegetables.security.services.UserDetailsImpl;
import kz.singularity.hackaton.backendhackatonvegetables.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        ResponseOutputBody response = authService.signInUser(loginRequest);
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        ResponseOutputBody response = authService.signUpUser(signUpRequest);
        return response.getStatusCode() != Response.Status.OK ? ResponseEntity.badRequest().body(response) : ResponseEntity.ok(response);
    }
}
