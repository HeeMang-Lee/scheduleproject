package com.example.schedule.repository;

import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public ScheduleRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ScheduleResponseDto save(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        String exercisesStr = String.join(",", schedule.getExercises());
        LocalDateTime createdAt = schedule.getCreatedAt();
        LocalDateTime modifiedAt = schedule.getModifiedAt();

        Map<String, Object> params = new HashMap<>();
        params.put("exercise_date",schedule.getExerciseDate());
        params.put("exercises",exercisesStr);
        params.put("author_id",schedule.getAuthorId());
        params.put("password",schedule.getPassword());
        params.put("created_at", Timestamp.valueOf(createdAt));
        params.put("modified_at",Timestamp.valueOf(modifiedAt));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getExerciseDate(),
                schedule.getExercises(),
                null,
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
                );
    }

    @Override
    public List<ScheduleResponseDto> findAll(String modifiedAt, Long authorId) {
        StringBuilder sql = new StringBuilder("SELECT s.id, s.exercise_date, s.exercises, s.created_at, s.modified_at, " +
                "a.id as author_id, a.name as author_name, a.email as author_email " +
                "FROM schedule s JOIN author a ON s.author_id = a.id WHERE 1=1");

        List<Object> params = new ArrayList<>();

        if (modifiedAt != null && !modifiedAt.isBlank()) {
            sql.append(" AND DATE(modified_at) = ?");
            params.add(modifiedAt);
        }
        if (authorId != null) {
            sql.append(" AND author_id = ?");
            params.add(authorId);
        }

        sql.append(" ORDER BY modified_at DESC");

        return jdbcTemplate.query(sql.toString(),scheduleWithAuthorRowMapper(),params.toArray());
    }

    @Override
    public Optional<ScheduleResponseDto> findById(Long id) {
        String sql = "SELECT s.id, s.exercise_date, s.exercises, s.created_at, s.modified_at, " +
                "a.id as author_id, a.name as author_name, a.email as author_email " +
                "FROM schedule s " +
                "JOIN author a ON s.author_id = a.id " +
                "WHERE s.id = ?";
        List<ScheduleResponseDto> result = jdbcTemplate.query(sql, scheduleWithAuthorRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public void update(Schedule schedule) {
        String sql = "UPDATE schedule SET exercise_date = ?, exercises = ? , author_id = ?, modified_at = ? WHERE id = ?";
        String exerciseStr = String.join(",",schedule.getExercises());

        jdbcTemplate.update(sql,
                schedule.getExerciseDate(),
                exerciseStr,
                schedule.getAuthorId(),
                Timestamp.valueOf(schedule.getModifiedAt()),
                schedule.getId()
        );

    }

    @Override
    public Optional<Schedule> findEntityById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findFirst();
    }

    @Override
    public Optional<Schedule> findByIdForDelete(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql,scheduleRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

//    private RowMapper<ScheduleResponseDto> scheduleResponseDtoRowMapper() {
//        return (rs, rowNum) -> new ScheduleResponseDto(
//                rs.getLong("id"),
//                rs.getString("exercise_date"),
//                Arrays.asList(rs.getString("exercises").split(",")),
//                rs.getString("writer"),
//                rs.getTimestamp("created_at").toLocalDateTime(),
//                rs.getTimestamp("modified_at").toLocalDateTime()
//        );
//    }

    private final RowMapper<Schedule> scheduleRowMapper() {
        return (rs, rowNum) -> new Schedule(
                rs.getLong("id"),
                rs.getLong("author_id"),
                rs.getString("exercise_date"),
                Arrays.asList(rs.getString("exercises").split(",")),
                rs.getString("password"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()

        );
    }

    private RowMapper<ScheduleResponseDto> scheduleWithAuthorRowMapper() {
        return (rs, rowNum) -> {
            AuthorResponseDto author = new AuthorResponseDto(
                    rs.getLong("author_id"),
                    rs.getString("author_name"),
                    rs.getString("author_email")
            );

            return new ScheduleResponseDto(
                    rs.getLong("id"),
                    rs.getString("exercise_date"),
                    Arrays.asList(rs.getString("exercises").split(",")),
                    author,
                    rs.getTimestamp("created_at").toLocalDateTime(),
                    rs.getTimestamp("modified_at").toLocalDateTime()
            );
        };
    }

}
