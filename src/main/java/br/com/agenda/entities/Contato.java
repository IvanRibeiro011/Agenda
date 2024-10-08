package br.com.agenda.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contato {

    private Long id;
    private String nome;
    private String email;
    private List<Telefone> telefone;
    private String endereco;
    private Usuario usuario;
}
