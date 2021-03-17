package design.first.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("新增权限请求对象")
public class InsertPermissionRequest {
    @ApiModelProperty("限制路径")
    private String permUrl;
    @ApiModelProperty("权限描述")
    private String permName;
}
