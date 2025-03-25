package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.awt.*;
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
        params.put("writer",schedule.getWriter());
        params.put("password",schedule.getPassword());
        params.put("created_at", Timestamp.valueOf(createdAt));
        params.put("modified_at",Timestamp.valueOf(modifiedAt));

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getExerciseDate(),
                schedule.getExercises(),
                schedule.getWriter(),
                createdAt,
                modifiedAt
                );
    }

    @Override
    public List<ScheduleResponseDto> findAll(String modifiedAt, String writer) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (modifiedAt != null && !modifiedAt.isBlank()) {
            sql.append(" AND DATE(modified_at) = ?");
            params.add(modifiedAt);
        }
        if (writer != null && !writer.isBlank()) {
            sql.append(" AND writer = ?");
            params.add(writer);
        }

        sql.append(" ORDER BY modified_at DESC");

        return jdbcTemplate.query(sql.toString(),scheduleResponseDtoRowMapper(),params.toArray());
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql,scheduleRowMapper,id);
        return result.stream().findAny();
    }

    @Override
    public void update(Long id, Schedule schedule) {
        String sql = "UPDATE schedule SET exercise_date = ?, exercises = ? , writer = ?, modified_at = ? WHERE id = ?";
        String exerciseStr = String.join(",",schedule.getExercises());

        jdbcTemplate.update(sql,
                schedule.getExerciseDate(),
                exerciseStr,
                schedule.getWriter(),
                Timestamp.valueOf(schedule.getModifiedAt()),
                id
        );

    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM schedule WHERE id = ?";
        jdbcTemplate.update(sql,id);
    }

    private RowMapper<ScheduleResponseDto> scheduleResponseDtoRowMapper() {
        return (rs, rowNum) -> new ScheduleResponseDto(
                rs.getLong("id"),
                rs.getString("exercise_date"),
                Arrays.asList(rs.getString("exercises").split(",")),
                rs.getString("writer"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }

    private final RowMapper<Schedule> scheduleRowMapper = (rs,rowNum) -> {
        Long id = rs.getLong("id");
        String exerciseDate = rs.getString("exercise_date");
        String exercisesStr = rs.getString("exercises");
        List<String> exercises = Arrays.asList(exercisesStr.split(","));
        String writer = rs.getString("writer");
        String password = rs.getString("password");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime modifiedAt = rs.getTimestamp("modified_at").toLocalDateTime();

        return  new Schedule(id,exerciseDate,exercises,writer,password,createdAt,modifiedAt);
    };
}
