package br.com.agenda.repositories;

import br.com.agenda.entities.AuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AuthTokenRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AuthToken save(AuthToken authToken) {
        String sql = "INSERT INTO authToken (token, usuario_id, expiracao, ativo) VALUES (?, ?, ?, ?) RETURNING id";

        Long generatedId = jdbcTemplate.queryForObject(sql, new Object[]{
                authToken.getToken(),
                authToken.getUsuarioId(),
                authToken.getExpiracao(),
                authToken.isAtivo()
        }, Long.class);

        authToken.setId(generatedId);
        return authToken;
    }

    public List<AuthToken> findAllByAtivoIsFalse() {
        String sql = "SELECT * FROM authToken WHERE ativo = FALSE";
        return jdbcTemplate.query(sql, new AuthTokenRowMapper());
    }
    public AuthToken findById(Long id) {
        String sql = "SELECT * FROM authToken WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new AuthTokenRowMapper(), id);
    }
    public void deleteById(Long id) {
        String sql = "DELETE FROM authToken WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public AuthToken findByToken(String token) {
        try{
            String sql = "SELECT * FROM authToken WHERE token = ?";
            return jdbcTemplate.queryForObject(sql, new AuthTokenRowMapper(), token);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public void deactivateToken(String token) {
        String sql = "UPDATE authToken SET ativo = FALSE WHERE token = ?";
        jdbcTemplate.update(sql, token);
    }

    public List<AuthToken> findAll() {
        String sql = "SELECT * FROM authToken";
        return jdbcTemplate.query(sql, new AuthTokenRowMapper());
    }

    public AuthToken findByTokenAndUsuarioId(String token, Long usuarioId) {
        try{
            String sql = "SELECT * FROM authToken WHERE token = ? AND usuario_id = ?";
            return jdbcTemplate.queryForObject(sql, new AuthTokenRowMapper(), token, usuarioId);
        }
        catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    private static class AuthTokenRowMapper implements RowMapper<AuthToken> {
        @Override
        public AuthToken mapRow(ResultSet rs, int rowNum) throws SQLException {
            AuthToken authToken = new AuthToken();
            authToken.setId(rs.getLong("id"));
            authToken.setToken(rs.getString("token"));
            authToken.setUsuarioId(rs.getLong("usuario_id"));
            authToken.setExpiracao(rs.getTimestamp("expiracao").toLocalDateTime());
            authToken.setAtivo(rs.getBoolean("ativo"));
            return authToken;
        }
    }
}
