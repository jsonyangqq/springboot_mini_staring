package com.example.springbootredisclientexample.utils;

import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author jason.yang
 * @Description
 * @Date 2022-07-04
 */
public class ResultsBean<T> implements Serializable {
    private static final long serialVersionUID = 9074812109921199634L;

    public ResultsBean() {}

    /** 成功状态码 */
    public final static int RESULT_CODE_SUCCESS = 200;
    /** 业务异常状态码 */
    public final static int RESULT_CODE_SERVICE_ERR = 98;
    /** 系统异常状态码 */
    public final static int RESULT_CODE_SYSTEM_ERR = 99;

    /**
     * 图形验证码异常
     */
    public final static String FAIL_CODE_CAPTCHA_ERR="1107";

    /** 成功但查无数据 */
    public final static String FAIL_CODE_SUCCESS_NODATA = "1108";

    /** 修改次数超限 */
    public final static String FAIL_CODE_UPDATE_OVER_LIMIT = "1109";

    /** 更新代理商信息完善度--已配置 */
    public final static String FAIL_CODE_UPDATE_AGENT_INFOPERFECTION = "1110";


    private int code;


    private String message;


    private String failCode;

    private T object;

    public boolean success() {
        return RESULT_CODE_SUCCESS == code;
    }

    public boolean Nsuccess() {
        return !success();
    }

    public static <T> ResultsBean<T> SUCCESSNODATA(){
        return new ResultsBean<T>(RESULT_CODE_SUCCESS, FAIL_CODE_SUCCESS_NODATA,"暂无数据");
    }

    public static <T> ResultsBean<T> SUCCESSNODATA(String message){
        if(StringUtils.isEmpty(message)) {
            return SUCCESSNODATA();
        }
        return new ResultsBean<T>(RESULT_CODE_SUCCESS, FAIL_CODE_SUCCESS_NODATA,message);
    }

    public static <T> ResultsBean<T> SUCCESS(String failCode, String message) {
        return new ResultsBean<T>(RESULT_CODE_SUCCESS, failCode, message);
    }

    public static <T> ResultsBean<T> SUCCESS() {
        return SUCCESS(null);
    }

    public static <T> ResultsBean<T> SUCCESS(T t) {
        return new ResultsBean<T>(RESULT_CODE_SUCCESS, t);
    }

    public static <T> ResultsBean<T> EXCEPTION(String message) {
        return new ResultsBean<T>(RESULT_CODE_SYSTEM_ERR, message);
    }

    public static <T> ResultsBean<T> FAIL(String message) {
        return new ResultsBean<T>(RESULT_CODE_SERVICE_ERR, message);
    }

    public static <T> ResultsBean<T> FAIL(String failCode, String message) {
        return new ResultsBean<T>(RESULT_CODE_SERVICE_ERR, failCode, message);
    }

    private ResultsBean(int code, T object) {
        super();
        this.code = code;
        this.object = object;
    }

    public ResultsBean(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResultsBean(int code, String failCode, String message) {
        super();
        this.code = code;
        this.message = message;
        this.failCode = failCode;
    }

    public ResultsBean(int code, String failCode, String message, T object) {
        super();
        this.code = code;
        this.message = message;
        this.failCode = failCode;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFailCode() {
        return failCode;
    }

    public void setFailCode(String failCode) {
        this.failCode = failCode;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "ResultsBean [code=" + code + ", message=" + message + ", failCode=" + failCode + ", object=" + object + "]";
    }

}