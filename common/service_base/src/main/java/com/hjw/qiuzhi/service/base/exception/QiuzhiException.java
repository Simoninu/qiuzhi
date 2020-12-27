package com.hjw.qiuzhi.service.base.exception;

import com.hjw.qiuzhi.common.base.result.ResultCodeEnum;
import lombok.Data;

/**
 * 自定义异常类
 */
@Data
public class QiuzhiException extends RuntimeException {

    // 状态码
    private Integer code;

    /**
     * 接受状态码和消息
     * @param message 错误信息
     * @param code 状态码
     */
    public QiuzhiException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    /**
     * 接收枚举类型
     * @param resultCodeEnum 枚举类型
     */
    public QiuzhiException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "QiuzhiException{" +
                "code=" + code +
                ", message" + this.getMessage() +
                '}';
    }
}
