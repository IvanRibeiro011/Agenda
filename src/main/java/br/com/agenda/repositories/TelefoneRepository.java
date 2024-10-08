package br.com.agenda.repositories;

import br.com.agenda.entities.Contato;
import br.com.agenda.entities.Telefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TelefoneRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void save(Telefone t) {
        jdbcTemplate.update("INSERT INTO telefone (numero, id_contato) VALUES (?, ?)", t.getNumero(), t.getContato().getId());
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM telefone WHERE id = ?", id);
    }

    public void deleteByContatoId(Long idContato) {
        jdbcTemplate.update("DELETE FROM telefone WHERE id_contato = ?", idContato);
    }

    public void update(Telefone t) {
        jdbcTemplate.update("UPDATE telefone SET numero = ? WHERE id = ?", t.getNumero(), t.getId());
    }

    public Telefone findTelefoneById(Long id) {
        return jdbcTemplate.queryForObject("SELECT t.id,t.numero,t.contato_id,c.id as contato_id,c.nome as contato_nome" +
                " FROM telefone t Inner Join Contato c on t.contato_id = c.id " +
                " WHERE id = ?", this::mapRowToTelefone, id);
    }

    private Telefone mapRowToTelefone(ResultSet rs, int rowNum) throws SQLException {
        Telefone telefone = new Telefone();
        telefone.setId(rs.getLong("id"));
        telefone.setNumero(rs.getString("numero"));

        Long contatoId = rs.getLong("contato_id");
        Contato contato = new Contato();
        contato.setId(contatoId);
        contato.setNome(rs.getString("contato_nome"));
        telefone.setContato(contato);

        return telefone;
    }

    public List<Telefone> findByContatoId(Long id) {
        return jdbcTemplate.query("SELECT * FROM telefone WHERE contato_id = ?", this::mapRowToTelefone, id);
    }
}
