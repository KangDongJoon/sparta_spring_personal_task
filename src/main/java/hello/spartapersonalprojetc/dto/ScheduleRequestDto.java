package hello.spartapersonalprojetc.dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String task;
    private String name;
    private int pw;
    private String writeDay;
    private String updateDay;
}
