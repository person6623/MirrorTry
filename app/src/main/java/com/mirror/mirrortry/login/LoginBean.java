package com.mirror.mirrortry.login;

/**
 * Created by dllo on 16/6/25.
 */
public class LoginBean {
    /**
     * result :
     * msg : 密码错误
     * data :
     */

    private String result;
    private String msg;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

//    /**
//     * result : 1
//     * msg :
//     * data : {"token":"2c25afa66151fcc199ae2962d96ec11d","uid":"549"}
//     */
//
//    private String result;
//    private String msg;
//    /**
//     * token : 2c25afa66151fcc199ae2962d96ec11d
//     * uid : 549
//     */
//
//    private DataBean data;
//
//    public String getResult() {
//        return result;
//    }
//
//    public void setResult(String result) {
//        this.result = result;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public static class DataBean {
//        private String token;
//        private String uid;
//
//        public String getToken() {
//            return token;
//        }
//
//        public void setToken(String token) {
//            this.token = token;
//        }
//
//        public String getUid() {
//            return uid;
//        }
//
//        public void setUid(String uid) {
//            this.uid = uid;
//        }
//    }


}
