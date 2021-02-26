package design.first.commons.test;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@ToString
public class Bean2 {
    String str;
    LocalDateTime time;
    Boolean lean;
}
