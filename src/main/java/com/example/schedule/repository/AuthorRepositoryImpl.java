package com.example.schedule.repository;

import com.example.schedule.entity.Author;
import lombok.RequiredArgsConstructor;
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
/**
 * {@link AuthorRepository}의 구현체로, JDBC를 사용하여 작성자 정보를 저장 및 조회합니다.
 */
@Repository
@RequiredArgsConstructor
public class AuthorRepositoryImpl implements AuthorRepository{

    private final JdbcTemplate jdbcTemplate;

    /**
     * 작성자 정보를 DB에 저장합니다.
     * SimpleJdbcInsert를 사용하여 자동 생성되는 ID 값을 반환받습니다.
     *
     * @param author 저장할 작성자 엔티티
     * @return 저장된 작성자 엔티티 (ID 포함)
     */
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
    /**
     * ID를 기준으로 작성자 정보를 조회합니다.
     *
     * @param id 작성자 ID
     * @return 조회된 작성자 정보 (Optional)
     */
    @Override
    public Optional<Author> findById(Long id) {
        String sql = "SELECT * FROM author WHERE id = ?";
        List<Author> result = jdbcTemplate.query(sql, authorRowMapper(), id);
        return result.stream().findAny();
    }
    /**
     * 이메일을 기준으로 작성자 정보를 조회합니다.
     *
     * @param email 작성자 이메일
     * @return 조회된 작성자 정보 (Optional)
     */
    @Override
    public Optional<Author> findByEmail(String email) {
        String sql = "SELECT * FROM author WHERE email = ?";
        List<Author> result = jdbcTemplate.query(sql,authorRowMapper(),email);
        return result.stream().findAny();
    }
    /**
     * 작성자 정보를 Author 객체로 매핑하는 RowMapper
     *
     * @return Author 객체로 변환하는 RowMapper
     */
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
