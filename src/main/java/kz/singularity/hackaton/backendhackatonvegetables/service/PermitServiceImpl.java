package kz.singularity.hackaton.backendhackatonvegetables.service;

import kz.singularity.hackaton.backendhackatonvegetables.models.ERole;
import kz.singularity.hackaton.backendhackatonvegetables.models.Role;
import kz.singularity.hackaton.backendhackatonvegetables.models.User;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.BookingRequest;
import kz.singularity.hackaton.backendhackatonvegetables.payload.request.GetFreeTimeRequest;
import kz.singularity.hackaton.backendhackatonvegetables.repository.RoleRepository;
import kz.singularity.hackaton.backendhackatonvegetables.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermitServiceImpl implements PermitService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Override
    public void sendToPermitForRoomOnWeekDay(BookingRequest bookingRequest, User user) {
        List<User> admins = userRepository.findUsersByRoles(roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow());
        System.out.println(admins);
    }
}
