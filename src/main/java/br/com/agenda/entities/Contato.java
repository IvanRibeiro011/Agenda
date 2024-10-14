package br.com.agenda.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Contato {

    private Long id;
    private String nome;
    private String email;
    private List<Telefone> telefone = new ArrayList<>();
    private String endereco;
    @JsonIgnore
    private Usuario usuario;
}
