package ssh;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 查询用户信息应答
 * 
 * @author Administrator
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnlineResponse extends RestResponse {

	private Boolean isOnline;

	public Boolean getIsOnline() {
    return isOnline;
  }

  public void setIsOnline(Boolean isOnline) {
    this.isOnline = isOnline;
  }

  public OnlineResponse() {

	}

	public OnlineResponse(String retCode) {
		super(retCode);
	}

	public OnlineResponse(String retCode, String retInfo) {
		super(retCode, retInfo);
	}

	public OnlineResponse(int retCode) {
		super(retCode);
	}

	public OnlineResponse(int retCode, String retInfo) {
		super(retCode, retInfo);
	}


}
