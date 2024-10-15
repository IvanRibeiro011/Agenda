package br.com.agenda.services;

import br.com.agenda.dtos.request.ContatoRequestDTO;
import br.com.agenda.entities.Contato;
import br.com.agenda.entities.Usuario;
import br.com.agenda.repositories.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContatoService {

    @Autowired
    private ContatoRepository contatoRepository;

    public Contato findById(Long id) {
        Contato contato = contatoRepository.findById(id);
        if(contato != null){
            return contato;
        } else {
            throw new RuntimeException("Contato não encontrado");
        }
    }

    public List<Contato> findContatoByUsuarioId(Long id) {
        return contatoRepository.findByUsuarioId(id);
    }

    public void save(ContatoRequestDTO dto) {
        Contato contato = new Contato();
        contato.setNome(dto.getNome());
        contato.setEmail(dto.getEmail());
        contato.setEndereco(dto.getEndereco());
        if(dto.getUsuarioId() != null) {
            Usuario usuario = new Usuario();
            usuario.setId(dto.getUsuarioId());
            contato.setUsuario(usuario);
        }
        else{
            throw new RuntimeException("Usuário não informado");
        }
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
