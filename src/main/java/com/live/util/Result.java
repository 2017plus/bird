package com.live.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

public class Result {

    private Integer code;

    private String note;

    private JSONArray result;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public JSONArray getResult() {
        return result;
    }

    public void setResult(JSONArray result) {
        this.result = result;
    }

    public Result logout (){
        Result result = new Result();
        result.setCode(-100);
        result.setNote("token失效，请重新登录");
        return result;
    }

    public Result login (){
        Result result = new Result();
        result.setCode(100);
        result.setNote("登录成功");
        result.setResult(new JSONArray());
        return result;
    }

    public Result success (String note,JSONArray jsonArray){
        Result result = new Result();
        result.setCode(1);
        result.setNote(note);
        result.setResult(jsonArray);
        return result;
    }

    public Result fail (String note){
        Result result = new Result();
        result.setCode(-1);
        result.setNote(note);
        return result;
    }
}
