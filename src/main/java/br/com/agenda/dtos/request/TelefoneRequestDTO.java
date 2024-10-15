package br.com.agenda.dtos.request;

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
public class TelefoneRequestDTO {

    private List<String> numeros = new ArrayList<>();
    private Long contatoId;
}
