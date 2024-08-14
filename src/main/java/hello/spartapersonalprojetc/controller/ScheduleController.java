package hello.spartapersonalprojetc.controller;

import hello.spartapersonalprojetc.dto.ScheduleRequestDto;
import hello.spartapersonalprojetc.dto.ScheduleResponseDto;
import hello.spartapersonalprojetc.entity.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
        Long maxId = scheduleList.size() > 0 ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedule.setId(maxId);

        // DB 저장
        scheduleList.put(schedule.getId(), schedule);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedule);

        return scheduleResponseDto;
    }

//    @GetMapping("/schedules")
//    public List<ScheduleResponseDto> getSchedule() {
//        // Map to List
//        List<ScheduleResponseDto> responseList = scheduleList.values().stream()
//                .map(ScheduleResponseDto::new).toList();
//
//        return responseList;
//    }
}