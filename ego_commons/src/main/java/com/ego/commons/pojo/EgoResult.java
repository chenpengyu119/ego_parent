package com.ego.commons.pojo;

/**
 * @author pengyu
 * @date 2019/9/20 16:01.
 */
public class EgoResult {

    private EgoResult(){};

    private int status;
    private String msg;
    private Object data;

    /**
     * 成功
     * @return
     */
    public static EgoResult ok(){
        EgoResult result = new EgoResult();
        result.setStatus(200);
        return result;
    }

    /**
     * 成功携带数据
     * @return
     */
    public static EgoResult ok(Object data){
        EgoResult result = new EgoResult();
        result.setStatus(200);
        result.setData(data);
        return result;
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public static EgoResult error(String msg){
        EgoResult result = new EgoResult();
        result.setStatus(500);
        result.setMsg(msg);
        return result;
    }



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
