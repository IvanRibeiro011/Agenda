package br.com.agenda.controllers;

import br.com.agenda.dtos.request.AtualizarSenhaRequest;
import br.com.agenda.dtos.request.LoginRequest;
import br.com.agenda.dtos.response.LoginResponse;
import br.com.agenda.entities.Usuario;
import br.com.agenda.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.login(loginRequest));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LoginResponse response) {
        usuarioService.logout(response);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/esqueceu-senha")
    public ResponseEntity<String> esqueceuSenha(@RequestBody String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Senha alterada com sucesso para: " +
                usuarioService.esqueceuSenha(email));
    }

    @PostMapping("/atualizar-senha")
    public ResponseEntity<Void> atualizarSenha(@RequestBody AtualizarSenhaRequest request) {
        usuarioService.atualizarSenha(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/salvar")
    public ResponseEntity<Void> save(@RequestBody Usuario u) {
        usuarioService.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> update(@RequestBody Usuario u) {
        usuarioService.update(u);
        return ResponseEntity.noContent().build();
    }
}
