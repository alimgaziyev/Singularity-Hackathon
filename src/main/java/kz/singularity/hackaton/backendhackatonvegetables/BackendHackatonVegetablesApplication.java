package kz.singularity.hackaton.backendhackatonvegetables;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SecurityScheme(name = "bearer",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT")
public class BackendHackatonVegetablesApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendHackatonVegetablesApplication.class, args);
    }

}
