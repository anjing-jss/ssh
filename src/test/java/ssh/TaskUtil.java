package ssh;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class TaskUtil {

	private static final Logger  logger = LoggerFactory.getLogger(TaskUtil.class);
	
	
	public boolean isUserHaveRight(HttpServletRequest  request) {
		return false;
	}
	
	
	public String getSign(String appId, String appKey, String timestamp, String body,String url) throws MalformedURLException{
	    URL urlObj = new URL(url);
	    url=urlObj.getPath();
		appKey = appKey.trim();
		appKey = appKey.replaceAll("\"", "");
		if (body != null) {
			body = body.trim();
		}
		if (!body.equals("")) {
			body = body.replaceAll("", "");
			body = body.replaceAll("\t", "");
			body = body.replaceAll("\r", "");
			body = body.replaceAll("\n", "");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(url).append(body).append(appId).append(appKey).append(timestamp);

		MessageDigest md = null;
		byte[] bytes = null;
		try {
			md = MessageDigest.getInstance("SHA-256");
			bytes = md.digest(sb.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return BinaryToHexString(bytes);
	}

	private String BinaryToHexString(byte[] bytes) {
		StringBuilder hex = new StringBuilder();
		String hexStr = "0123456789abcdef";
		for (int i = 0; i < bytes.length; i++) {		
			hex.append(String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4)));		
			hex.append(String.valueOf(hexStr.charAt(bytes[i] & 0x0F)));
		}
		return hex.toString();
	}

}
