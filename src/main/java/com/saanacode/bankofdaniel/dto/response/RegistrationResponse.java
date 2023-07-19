package com.saanacode.bankofdaniel.dto.response;

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
public class RegistrationResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = -6639385846337900540L;

    private String message;
}
