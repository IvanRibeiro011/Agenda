package br.com.agenda.services;

import br.com.agenda.dtos.request.LoginRequest;
import br.com.agenda.entities.Usuario;
import br.com.agenda.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void save(Usuario u) {
        usuarioRepository.save(u);
    }

    public void update(Usuario u) {
        usuarioRepository.update(u);
    }

    public void delete(Usuario u) {
        usuarioRepository.delete(u);
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario login(LoginRequest loginRequest) {
        Usuario u = usuarioRepository.findByEmailESenha(loginRequest.getEmail(),loginRequest.getSenha());
        if (u != null && u.getSenha().equals(loginRequest.getSenha())) {
            return u;
        }
        else{
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }
}
