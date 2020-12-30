package design.parent.overall.entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class HistoryConsume {
    String consumeId;
    String mId;
    String cId;
    Timestamp time;
    Double money;
}
