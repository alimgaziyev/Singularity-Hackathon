package kz.singularity.hackaton.backendhackatonvegetables.payload.response;

import java.util.List;

public class JwtResponse {
  private String token;

  public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
    this.token = accessToken;

  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }


}
