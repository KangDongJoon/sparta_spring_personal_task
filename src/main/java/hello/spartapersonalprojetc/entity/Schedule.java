package hello.spartapersonalprojetc.entity;

import hello.spartapersonalprojetc.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor

public class Schedule {
    private Long id;
    private String task;
    private String name;
    private int pw;
    private String date;

    public Schedule(ScheduleRequestDto requestDto) {
        this.task = requestDto.getTask();
        this.name = requestDto.getName();
        this.pw = requestDto.getPw();

        // 현재 시간 저장
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.date = now.format(formatter);
    }
}
