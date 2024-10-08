package br.com.agenda.repositories;

import br.com.agenda.entities.Contato;
import br.com.agenda.entities.Telefone;
import br.com.agenda.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ContatoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TelefoneRepository telefoneRepository;

    public void save(Contato contato) {
        jdbcTemplate.update("INSERT INTO contato (nome, email,endereco,usuario_id) " +
                "VALUES (?, ?,?,?)", contato.getNome(), contato.getEmail(), contato.getEndereco(), contato.getUsuario().getId());
    }

    public void update(Contato contato) {
        jdbcTemplate.update("UPDATE contato SET nome = ?, email = ?, endereco = ? WHERE id = ?",
                contato.getNome(), contato.getEmail(), contato.getEndereco(), contato.getId());
    }

    public void associateTelefone(Contato c){
        for(Telefone t : c.getTelefone()) {
            jdbcTemplate.update("INSERT INTO telefone (numero, contato_id) VALUES (?, ?)", t.getNumero(), c.getId());
        }
    }

    public void updateTelefoneByContatoId(Contato c){
        jdbcTemplate.update("UPDATE telefone set numero = ? WHERE contato_id = ?", c.getId());
    }

    public void delete(Contato c) {
        if(c.getTelefone() != null){
            jdbcTemplate.update("DELETE FROM telefone WHERE contato_id = ?", c.getId());
        }
        jdbcTemplate.update("DELETE FROM contato WHERE id = ?", c.getId());
    }

    public Contato findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM contato WHERE id = ?", this::mapRowToContato, id);
    }

    public List<Contato> findByUsuarioId(Long id) {
        return jdbcTemplate.query("SELECT * FROM contato WHERE usuario_id = ?", this::mapRowToContato, id);
    }

    private Contato mapRowToContato(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Contato contato = new Contato();
        contato.setId(rs.getLong("id"));
        contato.setNome(rs.getString("nome"));
        contato.setEmail(rs.getString("email"));
        contato.setEndereco(rs.getString("endereco"));

        List<Telefone> telefones = telefoneRepository.findByContatoId(contato.getId());
        contato.setTelefone(telefones);

        Usuario u = new Usuario();
        u.setId(rs.getLong("usuario_id"));
        contato.setUsuario(u);

        return contato;
    }

}
