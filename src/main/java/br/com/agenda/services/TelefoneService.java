package br.com.agenda.services;

import br.com.agenda.dtos.request.TelefoneRequestDTO;
import br.com.agenda.entities.Contato;
import br.com.agenda.entities.Telefone;
import br.com.agenda.repositories.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Telefone telefone = new Telefone();
        telefone.setNumero(dto.getNumero());
        Contato contato = new Contato();
        contato.setId(dto.getContatoId());
        telefone.setContato(contato);
        telefoneRepository.save(telefone);
    }

    public void update(Telefone t) {
        telefoneRepository.update(t);
    }

    public void delete(Long id) {
        telefoneRepository.delete(id);
    }
}
