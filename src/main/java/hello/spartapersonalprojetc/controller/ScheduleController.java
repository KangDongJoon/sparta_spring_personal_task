package hello.spartapersonalprojetc.controller;

import hello.spartapersonalprojetc.dto.ScheduleRequestDto;
import hello.spartapersonalprojetc.dto.ScheduleResponseDto;
import hello.spartapersonalprojetc.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final Map<Long, Schedule> scheduleList = new HashMap<>();

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        // RequestDto -> Entity
        Schedule schedule = new Schedule(requestDto);

        // Schedule ID의 최대값 찾기
        Long maxId = !scheduleList.isEmpty() ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setId(maxId);

        // DB 저장
        scheduleList.put(schedule.getId(), schedule);

        // Entity -> ResponseDto

        return new ScheduleResponseDto(schedule);
    }

    @GetMapping("/schedule/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        Schedule schedule = scheduleList.get(id);
        return new ScheduleResponseDto(schedule);
    }
}