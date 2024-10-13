package br.com.agenda.services;

import br.com.agenda.dtos.request.AtualizarSenhaRequest;
import br.com.agenda.dtos.request.LoginRequest;
import br.com.agenda.dtos.response.LoginResponse;
import br.com.agenda.dtos.response.UsuarioResponseDTO;
import br.com.agenda.entities.AuthToken;
import br.com.agenda.entities.Usuario;
import br.com.agenda.repositories.UsuarioRepository;
import br.com.agenda.util.PasswordGeneratorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AuthTokenService authTokenService;

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

    public LoginResponse login(LoginRequest loginRequest) {
        Usuario u = usuarioRepository.findByEmailESenha(loginRequest.getEmail(), loginRequest.getSenha());
        if (u != null && u.getSenha().equals(loginRequest.getSenha())) {
            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
            usuarioResponseDTO.setId(u.getId());
            usuarioResponseDTO.setNome(u.getNome());
            usuarioResponseDTO.setEmail(u.getEmail());

            String token = authTokenService.save(u);

            return new LoginResponse(token, usuarioResponseDTO);
        } else {
            throw new RuntimeException("Usuário ou senha inválidos");
        }
    }

    public String esqueceuSenha(String email) {
        Usuario u = usuarioRepository.findByEmail(email);
        if (u != null) {
            u.setSenha(PasswordGeneratorUtil.generateRandomPassword());
            usuarioRepository.update(u);
            return u.getSenha();
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void atualizarSenha(AtualizarSenhaRequest request) {
        Usuario u = usuarioRepository.findByEmailESenha(request.getEmail(), request.getSenha());
        if (u != null) {
            if (!request.getNovaSenha().isBlank()) {
                u.setSenha(request.getNovaSenha());
                usuarioRepository.update(u);
            } else {
                throw new RuntimeException("Nova senha não pode ser vazia");
            }
        } else {
            throw new RuntimeException("Usuário não encontrado");
        }
    }

    public void logout(LoginResponse response) {
        AuthToken token = authTokenService.findByTokenAndUsuarioId(response.getToken(),response.getUsuario().getId());
        if (token != null) {
            authTokenService.deactivateToken(response.getToken());
        } else {
            throw new RuntimeException("Token não encontrado");
        }
    }
}
