package kz.singularity.hackaton.backendhackatonvegetables.service;


import kz.singularity.hackaton.backendhackatonvegetables.payload.request.LoginRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.SignupRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.response.ResponseOutputBody;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    ResponseOutputBody signInUser(LoginRequest loginRequest);

    ResponseOutputBody signUpUser(SignupRequest signupRequest);
}
