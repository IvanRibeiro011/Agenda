package br.com.agenda.controllers;

import br.com.agenda.dtos.request.LoginRequest;
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
    public ResponseEntity<Usuario> findById(Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(LoginRequest loginRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(usuarioService.login(loginRequest));
    }

    @PostMapping
    public ResponseEntity<Void> save(Usuario u) {
        usuarioService.save(u);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> update(Usuario u) {
        usuarioService.update(u);
        return ResponseEntity.noContent().build();
    }
}
