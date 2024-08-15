package hello.spartapersonalprojetc.dto;

import hello.spartapersonalprojetc.entity.Schedule;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String task;
    private String name;
    private int pw;
    private String writeDay;
    private String updateDay;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.task = schedule.getTask();
        this.name = schedule.getName();
        this.pw = schedule.getPw();
        this.writeDay = schedule.getWriteDay();
        this.updateDay = schedule.getUpdateDay();
    }

    public ScheduleResponseDto(Long id, String task, String name, int pw, String writeDay, String updateDay) {
        this.id = id;
        this.task = task;
        this.name = name;
        this.pw = pw;
        this.writeDay = writeDay;
        this.updateDay = updateDay;
    }
}
