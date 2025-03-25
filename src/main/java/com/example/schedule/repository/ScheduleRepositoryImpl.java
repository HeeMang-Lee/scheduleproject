package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<Schedule> findAll(String modifiedAt, String writer) {

        return List.of();
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Long id, Schedule schedule) {

    }

    @Override
    public void delete(Long id) {

    }
}
