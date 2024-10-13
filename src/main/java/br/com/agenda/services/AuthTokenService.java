package br.com.agenda.services;

import br.com.agenda.entities.AuthToken;
import br.com.agenda.entities.Usuario;
import br.com.agenda.repositories.AuthTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@EnableScheduling
public class AuthTokenService {

    @Autowired
    private AuthTokenRepository authTokenRepository;


    public String save(Usuario u) {
        AuthToken authToken = new AuthToken();
        authToken.setUsuarioId(u.getId());
        authToken.setAtivo(true);
        authToken.setToken(java.util.UUID.randomUUID().toString());
        authToken.setExpiracao(LocalDateTime.now().plusMinutes(10));

        authTokenRepository.save(authToken);

        return authToken.getToken();
    }

    public AuthToken findByToken(String token) {
        return authTokenRepository.findByToken(token);
    }

    public AuthToken findByTokenAndUsuarioId(String token, Long usuarioId) {
        return authTokenRepository.findByTokenAndUsuarioId(token, usuarioId);
    }

    public void deactivateToken(String token) {
        authTokenRepository.deactivateToken(token);
    }


    @Scheduled(fixedRate = 60000)
    public void deleteTokensInativos() {
        List<AuthToken> inativos = authTokenRepository.findAllByAtivoIsFalse();
        inativos.forEach(authToken -> authTokenRepository.deleteById(authToken.getId()));
    }
}
