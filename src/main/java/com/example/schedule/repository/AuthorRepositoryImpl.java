package com.example.schedule.repository;

import com.example.schedule.entity.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository{

    private final JdbcTemplate jdbcTemplate;

    public AuthorRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Author save(Author author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("author")
                .usingGeneratedKeyColumns("id");

        Map<String,Object> params = new HashMap<>();
        params.put("name",author.getName());
        params.put("email",author.getEmail());
        params.put("created_at", Timestamp.valueOf(author.getCreatedAt()));
        params.put("modified_at",Timestamp.valueOf(author.getModifiedAt()));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Author(
                key.longValue(),
                author.getName(),
                author.getEmail(),
                author.getCreatedAt(),
                author.getModifiedAt()
        );
    }

    @Override
    public Optional<Author> findById(Long id) {
        String sql = "SELECT * FROM author WHERE id = ?";
        List<Author> result = jdbcTemplate.query(sql, authorRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Author> findByEmail(String email) {
        String sql = "SELECT * FROM author WHERE email = ?";
        List<Author> result = jdbcTemplate.query(sql,authorRowMapper(),email);
        return result.stream().findAny();
    }

    private RowMapper<Author> authorRowMapper() {
        return (rs,rowNum) -> new Author(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }
}
