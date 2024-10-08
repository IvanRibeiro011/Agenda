package br.com.agenda.controllers;

import br.com.agenda.entities.Telefone;
import br.com.agenda.services.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneController {

    @Autowired
    private TelefoneService telefoneService;

    @GetMapping("/{id}")
    public ResponseEntity<Telefone> findById(Long id) {
        return ResponseEntity.ok(telefoneService.findById(id));
    }

    @GetMapping("/contato/{id}")
    public ResponseEntity<List<Telefone>> findByContatoId(Long id) {
        return ResponseEntity.ok(telefoneService.findByContatoId(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Void> save(Telefone t) {
        telefoneService.save(t);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar")
    public ResponseEntity<Void> update(Telefone t) {
        telefoneService.update(t);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<Void> delete(Long id) {
        telefoneService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
