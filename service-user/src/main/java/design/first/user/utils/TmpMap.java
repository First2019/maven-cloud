package design.first.user.utils;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TmpMap {
    private byte[] publicKey;//公钥
    private byte[] privateKey;//私钥
}
