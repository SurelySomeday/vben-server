package top.yxlgx.wink.core.util;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author yanxin
 * @Description: 通用返回
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String message;

    @Getter
    @Setter
    private T result;


    public static <T> Result<T> success(T result){
        return restResult(result, ResultCode.SUCCESS.getCode(), null);
    }

    public static <T> Result<T> success(){
        return restResult(null, ResultCode.SUCCESS.getCode(), null);
    }

    public static <T>  Result<T> failed(){
        return restResult(null, ResultCode.FAIL.getCode(), null);
    }

    public static <T>  Result<T> failed(String message){
        return restResult(null, ResultCode.FAIL.getCode(), message);
    }

    public static <T> Result<T> restResult(T data, int code, String msg) {
        Result<T> apiResult = new Result<>();
        apiResult.setCode(code);
        apiResult.setResult(data);
        apiResult.setMessage(msg);
        return apiResult;
    }
}
