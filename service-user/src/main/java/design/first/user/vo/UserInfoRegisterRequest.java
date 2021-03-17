package design.first.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户注册实体")
public class UserInfoRegisterRequest {

    @ApiModelProperty("用户名")
    private String userName;//用户名
    @ApiModelProperty("密码")
    private String userPwd;//密码
}
