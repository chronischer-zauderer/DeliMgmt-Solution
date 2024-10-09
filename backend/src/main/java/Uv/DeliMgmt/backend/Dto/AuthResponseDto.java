package Uv.DeliMgmt.backend.Dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String accesstoken;
    private String TokenType = "Baerer ";

    public AuthResponseDto(String accesstoken) {
        this.accesstoken = accesstoken;
    }
}
