package design.first.user.base;


import lombok.Data;

import java.io.Serializable;

/**
 * 响应对象
 */
@Data
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int RESULT_FAIL = 0;
    public static final int RESULT_SUCCESS = 20000;

    //状态码
    private Integer  code;
    //返回消息
    private String message;
    //返回对象
    private  Object data;

    public BaseResult(){}
    public BaseResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public BaseResult(Integer code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.data = object;
    }
    public BaseResult(Integer code, Object object) {
        this.code = code;
        this.data = object;
    }

    //成功返回
    public static BaseResult success(){
        return new BaseResult(RESULT_SUCCESS,"成功",null);
    }
    public static BaseResult success(String msg){
        return new BaseResult(RESULT_SUCCESS,msg,null);
    }
    public static BaseResult success(Object o){
        return new BaseResult(RESULT_SUCCESS,"成功",o);
    }
    public static BaseResult success(String msg,Object o){
        return new BaseResult(RESULT_SUCCESS,msg,o);
    }
    //失败返回
    public static BaseResult error(){
        return new BaseResult(RESULT_FAIL,"失败",null);
    }
    public static BaseResult error(String msg){
        return new BaseResult(RESULT_FAIL,msg,null);
    }
    public static BaseResult error(Object o){
        return new BaseResult(RESULT_FAIL,"失败",o);
    }

}
