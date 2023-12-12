package top.yxlgx.wink.core.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author yanxin
 * @description
 */
@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(0),
    FAIL(-1);
    private final int code;
}
