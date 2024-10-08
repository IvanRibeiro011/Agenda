package br.com.agenda.controllers;

import br.com.agenda.entities.Contato;
import br.com.agenda.services.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contato")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping("/{id}")
    public ResponseEntity<Contato> findById(Long id) {
        return ResponseEntity.ok(contatoService.findById(id));
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Contato>> findByUsuarioId(Long id) {
        return ResponseEntity.ok(contatoService.findContatoByUsuarioId(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Void> save(Contato c) {
        contatoService.save(c);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> update(Contato c) {
        contatoService.update(c);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> delete(Contato c) {
        contatoService.delete(c);
        return ResponseEntity.noContent().build();
    }
}
