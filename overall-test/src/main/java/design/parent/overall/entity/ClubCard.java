package design.parent.overall.entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class ClubCard {

    String cId;
    String status;
    String issuer;
    String outer;
    Timestamp creTime;
    Timestamp outTime;
    Double integral;
    Double money;
}
