package hello.spartapersonalprojetc.controller;

import hello.spartapersonalprojetc.dto.ScheduleRequestDto;
import hello.spartapersonalprojetc.dto.ScheduleResponseDto;
import hello.spartapersonalprojetc.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        String sql = "INSERT INTO schedule (task, name, pw, writeDay, updateDay) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedule.getTask());
                    preparedStatement.setString(2, schedule.getName());
                    preparedStatement.setInt(3, schedule.getPw());
                    preparedStatement.setString(4, schedule.getWriteDay());
                    preparedStatement.setString(5, schedule.getUpdateDay());
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
                String writeDay = rs.getString("writeday");
                String updateDay = rs.getString("updateday");
                return new ScheduleResponseDto(id, task, name, pw, writeDay, updateDay);
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
                String writeDay = rs.getString("writeday");
                String updateDay = rs.getString("updateday");
                return new ScheduleResponseDto(id, task, name, pw, writeDay, updateDay);
            }
        });
    }

    //@GetMapping("/schedules/filter")

    @PutMapping("schedules/{id}")
    public ResponseEntity<String> setSchedule(
            @PathVariable Long id,
            @RequestParam int pw,
            @RequestParam(required = false) String task,
            @RequestParam(required = false) String name) {

        StringBuilder sqlBuilder = new StringBuilder("UPDATE SCHEDULE SET ");
        String sql = "SELECT pw FROM SCHEDULE WHERE id = ?";
        List<Object> params = new ArrayList<>();
        int idsPw = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);

        if (idsPw != pw) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 틀렸습니다.");
        }
        if (task != null) {
            sqlBuilder.append("task = ?, ");
            params.add(task);
        }
        if (name != null) {
            sqlBuilder.append("name = ?, ");
            params.add(name);
        }

        sqlBuilder.append("updateDay = ?, ");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        params.add(now.format(formatter));


        sqlBuilder.deleteCharAt(sqlBuilder.length() - 2);
        sqlBuilder.append(" WHERE id = ?");
        params.add(id);

        jdbcTemplate.update(sqlBuilder.toString(), params.toArray());

        return ResponseEntity.ok("일정 수정 완료");
    }

    @DeleteMapping("schedules/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable Long id, @RequestParam int pw) {
        String sql = "SELECT pw FROM SCHEDULE WHERE id = ?";

        int idsPw = jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class);

        if (idsPw != pw) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("비밀번호가 틀렸습니다.");
        } else {
            String deleteSql = "DELETE FROM SCHEDULE WHERE id = ?";
            jdbcTemplate.update(deleteSql, id);
            return ResponseEntity.ok("Schedule deleted successfully");
        }
    }
}