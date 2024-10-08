package br.com.agenda.services;

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
        return telefoneRepository.findTelefoneById(id);
    }

    public List<Telefone> findByContatoId(Long id) {
        return telefoneRepository.findByContatoId(id);
    }

    public void save(Telefone t) {
        telefoneRepository.save(t);
    }

    public void update(Telefone t) {
        telefoneRepository.update(t);
    }

    public void delete(Long id) {
        telefoneRepository.delete(id);
    }
}
