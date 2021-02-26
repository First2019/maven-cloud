
package design.first.user.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean(value = "userApi")
    @Order(value = 1)
    public Docket groupRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(groupApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("design.first.user.controller"))
                .paths(PathSelectors.any())
                .build().globalOperationParameters(this.getParameterList())//token配置
                ;
    }

    private ApiInfo groupApiInfo(){
        return new ApiInfoBuilder()
                .title("用户信息接口！！！")
                .description("<div style='font-size:14px;color:red;'>user-ui-server RESTful APIs</div>")
                .termsOfServiceUrl("http://localhost:9011/")
                .contact("790867216@qq.com")
                .version("1.0")
                .build();
    }

    /**
     * header配置
     * @return
     */
    private List<Parameter> getParameterList(){
        ParameterBuilder tokenPar=new ParameterBuilder();
        List<Parameter> pars=new ArrayList<>();
        tokenPar.name("user-token").description("令牌").modelRef(new ModelRef("String"))
                .parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }

}
