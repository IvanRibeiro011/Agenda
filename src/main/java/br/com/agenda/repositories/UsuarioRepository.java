package br.com.agenda.repositories;

import br.com.agenda.entities.Contato;
import br.com.agenda.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ContatoRepository contatoRepository;

    public void save(Usuario usuario) {
        jdbcTemplate.update("INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)", usuario.getNome(), usuario.getEmail(), usuario.getSenha());
    }

    public Usuario findByEmail(String email) {
        return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE email = ?", this::mapRowToUsuario, email);
    }

    public Usuario findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE id = ?", this::mapRowToUsuario, id);
    }

    public void update(Usuario usuario) {
        jdbcTemplate.update("UPDATE usuario SET nome = ?, email = ?, senha = ? WHERE id = ?", usuario.getNome(), usuario.getEmail(), usuario.getSenha(), usuario.getId());
    }

    public void delete(Usuario u) {
        if (u.getContatos() != null) {
            for (Contato c : u.getContatos()) {
                contatoRepository.delete(c);
            }
        }
        jdbcTemplate.update("DELETE FROM usuario WHERE id = ?", u.getId());
    }

    private Usuario mapRowToUsuario(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNome(rs.getString("nome"));
        usuario.setEmail(rs.getString("email"));
        usuario.setSenha(rs.getString("senha"));

        List<Contato> contatos = contatoRepository.findByUsuarioId(usuario.getId());
        usuario.setContatos(contatos);

        return usuario;
    }

    public Usuario findByEmailESenha(String email,String senha) {
        return jdbcTemplate.queryForObject("SELECT * FROM usuario WHERE email = ? AND senha = ?", this::mapRowToUsuario, email,senha);
    }
}
