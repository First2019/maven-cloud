package design.first.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("授权请求对象")
public class ImPowerRequest {
    @ApiModelProperty(value = "用户编号")
    private Long userNo;
    @ApiModelProperty(value = "权限编号")
    private Long permNo;
}
