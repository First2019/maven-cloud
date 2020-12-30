package design.parent.overall.entity;

import lombok.Data;

@Data
public class Commodity {
    String cId;
    String mId;
    String pictureAddr;
    String name;
    String intro;
    Double price;
    Double vipPrice;
}
