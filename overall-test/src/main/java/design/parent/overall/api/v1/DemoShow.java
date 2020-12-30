package design.parent.overall.api.v1;

import com.github.xiaoymin.knife4j.annotations.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName DemoShow
 * @Description 侵入式接口文档精简教程说明
 * @Author lcz
 * @Date 2019/11/08 16:33
 */
@Api(value = "DemoShow", tags = "演示增强本使用接口方式")
@ApiSort(value = 1)// 大分类顺序
@RequestMapping("/authAfter")
@RestController
public class DemoShow {

    /**
     * 单层级文档说明，一般业务参考此处即可
     *
     * ①query参数
     * ②body参数说明
     * ③url路径参数（不建议使用）
     *
     * query 参数说明：实际开发中删减下面代码中的字段属性
     * body  参数说明：此方法对单层传输参数使用，一般业务情况足够使用
     * url   参数说明：url携带参数不建议使用[url协调不好会导致越权问题]
     */
    @ApiOperation(value = "jdk-Map-动态创建显示参数", produces = "application/json")
    @ApiImplicitParams(value = {
            /*query参数*/
            @ApiImplicitParam(name = "id", value = "注解id", required = true, dataTypeClass = String.class, paramType = "query", example = "000001"),
            @ApiImplicitParam(name = "name", value = "注解名称", required = true, dataTypeClass = Integer.class, paramType = "query", example = "mk567"),
            /*url参数*/
            @ApiImplicitParam(name = "mk", value = "注解名称", required = true, dataTypeClass = Integer.class, paramType = "path", example = "1234565")
    })
    /*body参数*/
    @ApiOperationSupport(
    params =  @DynamicParameters(name = "createModel01",properties = {
            @DynamicParameter(name = "id",value = "注解id",example = "X000111",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "name",value = "注解名称")
    }),
    responses = @DynamicResponseParameters(name = "CreateOrderMapModel",properties = {
            @DynamicParameter(name = "id",value = "注解id",example = "X000111",required = true,dataTypeClass = Integer.class),
            @DynamicParameter(name = "name",value = "注解名称")
    })
    )
    @PostMapping("/create_01/{mk}")
    public ResponseEntity<Map> create_01(@RequestBody Map map, @PathVariable(value = "mk") String mk){
        System.out.println(mk);
        return ResponseEntity.ok(map);
    }

}
