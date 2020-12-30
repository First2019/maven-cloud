package design.parent.overall.entity;
import lombok.Data;

@Data
public class Merchant {
    String mId;
    String typeId;
    Double mDiscount;
    Double mCoin;
    String noteVerify;
    String cardNum;
    String receipt;
    String recharge;
    String phone;
    String linkman;
    String address;
}
