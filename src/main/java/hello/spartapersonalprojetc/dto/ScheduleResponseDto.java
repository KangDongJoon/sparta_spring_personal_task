package hello.spartapersonalprojetc.dto;

import hello.spartapersonalprojetc.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String name;
    private int pw;
    private String date;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.name = schedule.getName();
        this.pw = schedule.getPw();
        this.date = schedule.getDate();
    }
}
