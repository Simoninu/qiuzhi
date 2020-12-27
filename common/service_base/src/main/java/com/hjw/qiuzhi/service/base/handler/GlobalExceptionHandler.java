package com.hjw.qiuzhi.service.base.handler;

import com.hjw.qiuzhi.common.base.result.R;
import com.hjw.qiuzhi.common.base.result.ResultCodeEnum;
import com.hjw.qiuzhi.common.base.util.ExceptionUtils;
import com.hjw.qiuzhi.service.base.exception.QiuzhiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

/**
 * 统一异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 通用异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        // 打印异常信息
        //e.printStackTrace();
        // 将错误信息输出到日志文件与控制台
        log.error(ExceptionUtils.getMessage(e));
        // 返回错误信息
        return R.error();
    }

    /**
     * 特殊异常处理，先走特殊，再走通用
     * SQL语法错误
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public R error(BadSqlGrammarException e) {
        // 打印异常信息
        //e.printStackTrace();
        // 将错误信息输出到日志文件与控制台
        log.error(ExceptionUtils.getMessage(e));
        // 返回错误信息
        return R.setResult(ResultCodeEnum.BAD_SQL_GRAMMAR);
    }

    /**
     * 特殊异常处理
     * JSON格式异常
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public R error(HttpMessageNotReadableException e) {
        // 打印异常信息
        //e.printStackTrace();
        // 将错误信息输出到日志文件与控制台
        log.error(ExceptionUtils.getMessage(e));
        // 返回错误信息
        return R.setResult(ResultCodeEnum.JSON_PARSE_ERROR);
    }

    /**
     * 处理自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler(QiuzhiException.class)
    @ResponseBody
    public R error(QiuzhiException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
