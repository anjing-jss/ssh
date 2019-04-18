package ssh;

import java.util.Map;

public class RestClientResponse<T> {

  private Map<String, String> headers;
  private int statusCode;
  private T body;

  public RestClientResponse() {
  }

  public RestClientResponse(Map<String, String> headers, int statusCode,
          T body) {
      this.headers = headers;
      this.statusCode = statusCode;
      this.body = body;
  }

  public Map<String, String> getHeaders() {
      return this.headers;
  }

  public void setHeaders(Map<String, String> headers) {
      this.headers = headers;
  }

  public int getStatusCode() {
      return this.statusCode;
  }

  public void setStatusCode(int statusCode) {
      this.statusCode = statusCode;
  }

  public T getBody() {
      return this.body;
  }

  public void setBody(T body) {
      this.body = body;
  }

  public String toString() {
      return "RestResponse [headers=" + this.headers + ", statusCode="
              + this.statusCode + ", body=" + this.body + "]";
  }
  
}
