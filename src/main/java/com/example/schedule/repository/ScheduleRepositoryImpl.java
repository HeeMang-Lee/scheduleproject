package com.example.schedule.repository;

import com.example.schedule.entity.Schedule;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Repository
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;


    @Override
    public Schedule save(Schedule schedule) {
        return null;
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
