package ssh;

public class RestClientException extends Exception {
  private static final long serialVersionUID = 1L;

  public RestClientException() {
  }

  public RestClientException(String msg) {
      super(msg);
  }

  public RestClientException(Throwable ex) {
      super(ex);
  }

  public RestClientException(String msg, Throwable ex) {
      super(msg, ex);
  }
}