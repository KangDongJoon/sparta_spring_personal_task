package hello.spartapersonalprojetc.controller;

import hello.spartapersonalprojetc.dto.ScheduleRequestDto;
import hello.spartapersonalprojetc.dto.ScheduleResponseDto;
import hello.spartapersonalprojetc.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final JdbcTemplate jdbcTemplate;
    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/schedules")
    public ScheduleResponseDto creatSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (task, name, pw, date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTask());
                    preparedStatement.setString(2, schedule.getName());
                    preparedStatement.setInt(3, schedule.getPw());
                    preparedStatement.setString(4, schedule.getDate());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedule.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

    @GetMapping("/schedules")
    public List<ScheduleResponseDto> getSchedule() {
        // DB 조회
        String sql = "SELECT * FROM SCHEDULE";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String task = rs.getString("task");
                int pw = rs.getInt("pw");
                String date = rs.getString("date");
                return new ScheduleResponseDto(id, task, name, pw, date);
            }
        });
    }

    @GetMapping("/schedules/{id}")
    public ScheduleResponseDto getScheduleById(@PathVariable Long id) {
        // DB 조회
        String sql = "SELECT * FROM SCHEDULE WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String task = rs.getString("task");
                int pw = rs.getInt("pw");
                String date = rs.getString("date");
                return new ScheduleResponseDto(id, task, name, pw, date);
            }
        });
    }
}