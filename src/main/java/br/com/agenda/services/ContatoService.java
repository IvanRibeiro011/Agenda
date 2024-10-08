package br.com.agenda.services;

import br.com.agenda.entities.Contato;
import br.com.agenda.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public Contato findById(Long id) {
        return contatoRepository.findById(id);
    }

    public List<Contato> findContatoByUsuarioId(Long id) {
        return contatoRepository.findByUsuarioId(id);
    }

    public void save(Contato contato) {
        contatoRepository.save(contato);
        contatoRepository.associateTelefone(contato);
    }

    public void update(Contato contato) {
        contatoRepository.update(contato);
        contatoRepository.updateTelefoneByContatoId(contato);
    }

    public void delete(Contato contato) {
        contatoRepository.delete(contato);
    }
}
