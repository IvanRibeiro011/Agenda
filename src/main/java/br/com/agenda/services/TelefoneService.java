package br.com.agenda.services;

import br.com.agenda.dtos.request.TelefoneRequestDTO;
import br.com.agenda.entities.Contato;
import br.com.agenda.entities.Telefone;
import br.com.agenda.repositories.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;

    public Telefone findById(Long id) {
        Telefone telefone = telefoneRepository.findTelefoneById(id);
        if (telefone != null) {
            return telefone;
        } else {
            throw new RuntimeException("Telefone não encontrado");
        }
    }

    public List<Telefone> findByContatoId(Long id) {
        return telefoneRepository.findByContatoId(id);
    }

    public void save(TelefoneRequestDTO dto) {
        List<Telefone> telefones = new ArrayList<>();
        if(!dto.getNumeros().isEmpty()){
            for (String numero: dto.getNumeros()){
                Telefone telefone = new Telefone();

                telefone.setNumero(numero);
                if(dto.getContatoId() != null) {
                    Contato contato = new Contato();
                    contato.setId(dto.getContatoId());
                    telefone.setContato(contato);
                    telefones.add(telefone);
                }
                else{
                    throw new RuntimeException("Contato não informado");
                }
            }
        }

        telefoneRepository.saveAll(telefones);
    }

    public void update(Telefone t) {
        telefoneRepository.update(t);
    }

    public void delete(Long id) {
        telefoneRepository.delete(id);
    }
}
