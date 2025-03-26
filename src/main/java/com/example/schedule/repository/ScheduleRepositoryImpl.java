package com.example.schedule.repository;

import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
/**
 * {@link ScheduleRepository}의 구현체로, JDBC를 사용하여 운동 일정을 저장, 조회, 수정, 삭제합니다.
 */
@Repository
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    /**
     * 운동 일정을 저장하고, 생성된 ID를 포함한 DTO를 반환합니다.
     *
     * @param schedule 저장할 일정 엔티티
     * @return 저장된 일정의 응답 DTO
     */
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
    /**
     * 조건에 맞는 전체 일정을 조회합니다. (수정일, 작성자 ID)
     *
     * @param modifiedAt 수정일 필터
     * @param authorId   작성자 ID 필터
     * @return 조건에 해당하는 일정 리스트
     */
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
    /**
     * 일정 ID로 조회하여 응답 DTO를 반환합니다.
     *
     * @param id 일정 ID
     * @return 조회된 일정 응답 DTO (Optional)
     */
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
    /**
     * 일정 엔티티를 수정합니다.
     *
     * @param schedule 수정할 일정 엔티티
     */
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
    /**
     * ID로 일정 엔티티를 조회합니다.
     *
     * @param id 일정 ID
     * @return 조회된 일정 (Optional)
     */
    @Override
    public Optional<Schedule> findEntityById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapper(), id);
        return result.stream().findFirst();
    }
    /**
     * 삭제를 위한 일정 조회 (비밀번호 포함 검증용)
     *
     * @param id 일정 ID
     * @return 조회된 일정 (Optional)
     */
    @Override
    public Optional<Schedule> findByIdForDelete(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        List<Schedule> result = jdbcTemplate.query(sql,scheduleRowMapper(),id);
        return result.stream().findAny();
    }
    /**
     * 일정 ID로 삭제합니다.
     *
     * @param id 삭제할 일정 ID
     */
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
    /**
     * Schedule 엔티티로 매핑하는 RowMapper
     *
     * @return Schedule 객체로 매핑되는 RowMapper
     */
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
    /**
     * Schedule + Author 정보를 ScheduleResponseDto로 매핑하는 RowMapper
     *
     * @return ScheduleResponseDto로 매핑되는 RowMapper
     */
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
