package com.ssh.entity;


public class RestResponse {
    private String retCode;
    private String retInfo;

    public RestResponse() {
    }

    public RestResponse(String retCode) {
        super();
        this.retCode = retCode;
    }

    public RestResponse(String retCode, String retInfo) {
        super();
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    public RestResponse(int retCode) {
        super();
        this.retCode = String.valueOf(retCode);
    }

    public RestResponse(int retCode, String retInfo) {
        super();
        this.retCode = String.valueOf(retCode);
        this.retInfo = retInfo;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetInfo() {
        return retInfo;
    }

    public void setRetInfo(String retInfo) {
        this.retInfo = retInfo;
    }

//    public RestResponse save() {
//        this.retCode = "00000";
//        this.retInfo = "成功!";
//        return this;
//    }

    public void save(String retCode, String retInfo) {
        this.retCode = retCode;
        this.retInfo = retInfo;
    }

    public void save(int retCode, String retInfo) {
        this.retCode = String.valueOf(retCode);
        this.retInfo = retInfo;
    }

    public static void convert(String retCode, String retInfo, RestResponse resp) {
        switch (retCode) {
            case "20001":
                resp.setRetCode(UwsException.CODE_A00001);
                resp.setRetInfo(UwsException.INFO_A00001);
                break;
            case "20002":
                resp.setRetCode(UwsException.CODE_A00004);
                resp.setRetInfo(UwsException.INFO_A00004);
                break;
            case "20003":
                resp.setRetCode(UwsException.CODE_G20003);
                resp.setRetInfo(UwsException.INFO_G20003);
                break;
            case "20908":
                resp.setRetCode(UwsException.CODE_G20908);
                resp.setRetInfo(UwsException.INFO_G20908);
                break;
            case "20102":
                resp.setRetCode(UwsException.CODE_D00006);
                resp.setRetInfo(UwsException.INFO_D00006);
                break;
            case "20905":
                resp.setRetCode(UwsException.CODE_A00003);
                resp.setRetInfo(UwsException.INFO_A00003);
                break;
            case "20201":
                resp.setRetCode(UwsException.CODE_G20201);
                resp.setRetInfo(UwsException.INFO_G20201);
                break;
            case "20202":
                resp.setRetCode(UwsException.CODE_G20202);
                resp.setRetInfo(UwsException.INFO_G20202);
                break;
            case "20301":
                resp.setRetCode(UwsException.CODE_G20301);
                resp.setRetInfo(UwsException.INFO_G20301);
                break;
            case "23401":
                resp.setRetCode(UwsException.CODE_G23401);
                resp.setRetInfo(UwsException.INFO_G23401);
                break;
            case "23501":
                resp.setRetCode(UwsException.CODE_G23501);
                resp.setRetInfo(UwsException.INFO_G23501);
                break;
            case "20901":
                resp.setRetCode(UwsException.CODE_B00001);
                resp.setRetInfo(UwsException.INFO_B00001);
                break;
            case "20902":
                resp.setRetCode(UwsException.CODE_B00001);
                resp.setRetInfo(UwsException.INFO_B00001);
                break;
            case "10":
                resp.setRetCode(UwsException.CODE_A00002);
                resp.setRetInfo(UwsException.INFO_A00002);
                break;
            case "1001":
                resp.setRetCode(UwsException.CODE_D00006);
                resp.setRetInfo(UwsException.INFO_D00006);
                break;
            case "1":
                resp.setRetCode(UwsException.CODE_A00004);
                resp.setRetInfo(UwsException.INFO_A00004);
                break;
            case "03002":
                resp.setRetCode(UwsException.CODE_G03002);
                resp.setRetInfo(UwsException.INFO_G03002);
                break;
            case "20909":
                resp.setRetCode(UwsException.CODE_B00001);
                resp.setRetInfo(UwsException.INFO_B00001);
                break;
            case "01001":
                resp.setRetCode(UwsException.CODE_D00006);
                resp.setRetInfo(UwsException.INFO_D00006);
                break;
            case "20904":
                resp.setRetCode(UwsException.CODE_G20904);
                resp.setRetInfo(UwsException.INFO_G20904);
                break;
            default:
                resp.setRetCode(retCode);
                resp.setRetInfo(retInfo);
        }
    }

}
