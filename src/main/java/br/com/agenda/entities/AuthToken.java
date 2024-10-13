package br.com.agenda.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken {

    private Long id;
    private String token;
    private Long usuarioId;
    private LocalDateTime expiracao;
    private boolean ativo;
}
