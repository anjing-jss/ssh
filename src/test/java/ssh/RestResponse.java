package ssh;


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


}
