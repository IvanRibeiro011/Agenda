package br.com.agenda.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContatoRequestDTO {

    private String nome;
    private String email;
    private String endereco;
    private Long usuarioId;
}
