package com.zhangwei.example.springbootdemo80.elasticsearch.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * @program: spring-boot-demo-80
 * @description
 * @author: 张伟
 * @create: 2020-09-20 21:58
 **/
@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> {
    private int code;
    private String message;
    private T data;


    public BaseResult(int code,String message){
        this(code,message,null);
    }

    public BaseResult(T data){
        this.code=200;
        this.message="操作成功";
        this.data = data;
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> BaseResult<T> success(T data){
        return new BaseResult(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> BaseResult<T> success(){
        return (BaseResult<T>) success(null);
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> BaseResult<T> error(int code,String message){
        return new BaseResult<T>(code,message);
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> BaseResult<T> error(String message){
        return new BaseResult<T>(201,message);
    }


}
