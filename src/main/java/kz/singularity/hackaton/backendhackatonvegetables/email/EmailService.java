package kz.singularity.hackaton.backendhackatonvegetables.email;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendSimpleMessage(
            String to,
            String subject,
            String text);
}
