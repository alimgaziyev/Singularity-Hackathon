package kz.singularity.hackaton.backendhackatonvegetables.payload.response;

import lombok.Data;

import javax.ws.rs.core.Response.Status;

@Data
public class ResponseOutputBody {
    private String message;
    private String timestamp;
    private Status statusCode;
    private Object body;

    public ResponseOutputBody(String message, String timestamp, Status statusCode, Object body) {
        this.message = message;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
        this.body = body;
    }
}
