package top.yxlgx.wink.util;

import lombok.Data;

/**
 * @author yanxin
 * @Description:
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T result;

    public Result<T> success(T result){
        this.code=0;
        this.message="success";
        this.result=result;
        return this;
    }

    public Result<T> success(){
        this.code=0;
        this.message="success";
        return this;
    }
}
