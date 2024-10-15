package br.com.agenda.exceptions.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorMessage {

    private Integer status;
    private Instant timestamp;
    private String error;
    private String message;
    private String path;
}
