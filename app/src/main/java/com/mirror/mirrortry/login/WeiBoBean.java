package com.mirror.mirrortry.login;

/**
 * Created by dllo on 16/6/29.
 */
public class WeiBoBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"20bbcb9839a2e2107fc3238ae4b04a5f"}
     */

    private String result;
    private String msg;
    /**
     * token : 20bbcb9839a2e2107fc3238ae4b04a5f
     */

    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
