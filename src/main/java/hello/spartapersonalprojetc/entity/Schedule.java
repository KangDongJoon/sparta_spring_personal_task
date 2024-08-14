package hello.spartapersonalprojetc.entity;

import hello.spartapersonalprojetc.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedule {
    private Long id;
    private String task;
    private String name;
    private int pw;
    private int date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.name = requestDto.getName();
        this.date = requestDto.getDate();
    }
}
