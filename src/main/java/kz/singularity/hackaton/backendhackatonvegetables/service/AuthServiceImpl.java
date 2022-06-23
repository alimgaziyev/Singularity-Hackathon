package kz.singularity.hackaton.backendhackatonvegetables.service;

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
import kz.singularity.hackaton.backendhackatonvegetables.util.ConstantMessages;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    private final String timestamp;

    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        timestamp = dtf.format(localDateTime);
    }

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public ResponseOutputBody signInUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());


        return new ResponseOutputBody(
                ConstantMessages.USER_AUTHENTICATED_SUCCESS,
                timestamp,
                Response.Status.OK,
                new JwtResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        roles
                )
        );
    }

    @Override
    public ResponseOutputBody signUpUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            new ResponseOutputBody(
                    ConstantMessages.USER_CREATE_FAIL,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            new ResponseOutputBody(
                    ConstantMessages.USER_CREATE_FAIL,
                    timestamp,
                    Response.Status.BAD_REQUEST,
                    null
            );
        }

        // Create new user's account
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_STUDENT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseOutputBody(
                ConstantMessages.USER_CREATED,
                timestamp,
                Response.Status.OK,
                null
        );
    }
}
