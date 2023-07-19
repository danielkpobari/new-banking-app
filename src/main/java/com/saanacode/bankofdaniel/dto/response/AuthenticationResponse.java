package com.saanacode.bankofdaniel.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -8637732983430873948L;


    @JsonProperty("access_token")
    private String accessToken;

}
