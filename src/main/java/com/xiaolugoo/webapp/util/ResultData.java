package com.xiaolugoo.webapp.util;

import java.util.List;

/**
 * @Auther: ALEX
 * @Date: 2018/5/5 22:11
 * @Description:
 */
public class ResultData {

    //返回请求标识符
    private String flag;

    //返回请求信息
    private String msg;

    //返回请求内容
    private List result;

    public ResultData() {

    }

    public ResultData(String flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public ResultData(String flag, String msg, List result) {
        this.flag = flag;
        this.msg = msg;
        this.result = result;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}
