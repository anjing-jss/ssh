package ssh;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
public class SimpleJsonRestClient implements RestClient{
  public static final String HTTP_HEADER_ACCEPT_VAL = "application/json";
  public static final String HTTP_HEADER_CONTENT_TYPE_VAL = "application/json";
  public static final String PK_MAX_CONN_TOTAL = "client.json.maxConnTotal";
  public static final String PK_MAX_CONN_PER_ROUTE = "client.json.maxConnPerRoute";
  private final int DEF_CONNECT_TIMEOUT = 30;
  private final int DEF_SOCKET_TIMEOUT = 30;
  private HttpClient client;
  private static HttpClient defClient;
  private ObjectMapper mapper = new ObjectMapper();
  private Properties config;

  public SimpleJsonRestClient() {
      this.mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
  }

  public void setConfig(Properties config) {
      this.config = config;
  }

  public void setClient(HttpClient client) {
      this.client = client;
  }

  public void init() {
      this.init(this.config == null ? new Properties() : this.config);
  }

  public void init(Properties config) {
      this.config = config;
      synchronized (this) {
          if (defClient == null) {
              int maxConnTotal = Integer.parseInt(config.getProperty(
                      "client.json.maxConnTotal", "5000"));
              int maxConnPerRoute = Integer.parseInt(config.getProperty(
                      "client.json.maxConnTotal", "1000"));
              defClient = HttpClients.custom().setMaxConnTotal(maxConnTotal)
                      .setMaxConnPerRoute(maxConnPerRoute).build();
          }

          if (this.client == null) {
              this.client = defClient;
          }

      }
  }

  public <T> RestClientResponse<T> post(String url,
      Map<String, String> headers, Object request, Class<T> t)
      throws RestClientException {
  return this.post(url, (Map) null, headers, request, t);
}

public <T> RestClientResponse<T> post(String url,
      Map<String, String> pathVariables, Map<String, String> headers,
      Object request, Class<T> t) throws RestClientException {
  HttpPost post = new HttpPost(this.genRealPath(url, pathVariables));
  post.setConfig(RequestConfig.custom().setConnectTimeout(30000)
          .setSocketTimeout(30000).build());
  CloseableHttpResponse resp = null;

  try {
      resp = this.call(post, headers, request);
      RestClientResponse e = new RestClientResponse();
      e.setStatusCode(resp.getStatusLine().getStatusCode());
      HashMap resHeaders = new HashMap();
      Header[] arg12;
      int arg11 = (arg12 = resp.getAllHeaders()).length;

      for (int arg10 = 0; arg10 < arg11; ++arg10) {
          Header rt = arg12[arg10];
          resHeaders.put(rt.getName(), rt.getValue());
      }

      if (t != null && e.getStatusCode() == 200) {
          if (t.equals(String.class)) {
              e.setBody(IOUtils.toString(resp.getEntity().getContent()));
          } else {
              System.out.println("hihihihihihihihih:"+resp.getEntity()
                  .getContent());
              System.out.println("22222:"+resp.getEntity().getContentLength()+",2222222");
              if(resp.getEntity().getContent()!=null){
                Object arg26 = this.mapper.readValue(resp.getEntity()
                    .getContent(), t);
                e.setBody(arg26);
              }
              
          }
      } else {
          String arg25 = IOUtils.toString(resp.getEntity().getContent());
          System.out.println(arg25);
      }

      e.setHeaders(resHeaders);
      RestClientResponse arg14 = e;
      return arg14;
  } catch (ClientProtocolException arg22) {
      arg22.printStackTrace();
      throw new RestClientException(arg22);
  } catch (IOException arg23) {
      arg23.printStackTrace();
      throw new RestClientException(arg23);
  } finally {
      if (resp != null) {
          try {
              resp.close();
          } catch (IOException arg21) {
              arg21.printStackTrace();
          }
      }

  }
}

public <T> RestClientResponse<T> get(String url,
      Map<String, String> pathVariables, Map<String, String> headers,
      Map<String, String> request, Class<T> t) throws RestClientException {
  ArrayList params = new ArrayList();
  if (request != null) {
      Iterator get = request.entrySet().iterator();

      while (get.hasNext()) {
          Entry realPath = (Entry) get.next();
          params.add(new BasicNameValuePair((String) realPath.getKey(),
                  (String) realPath.getValue()));
      }
  }

  String arg33 = null;

  try {
      if (params.isEmpty()) {
          arg33 = this.genRealPath(url, pathVariables);
      } else {
          arg33 = this.genRealPath(url, pathVariables)
                  + "?"
                  + EntityUtils
                          .toString(new UrlEncodedFormEntity(params));
      }
  } catch (UnsupportedEncodingException arg28) {
      arg28.printStackTrace();
  } catch (IOException arg29) {
      arg29.printStackTrace();
  }

  HttpGet arg34 = new HttpGet(arg33);
  arg34.setConfig(RequestConfig.custom().setConnectTimeout(30000)
          .setSocketTimeout(30000).build());
  CloseableHttpResponse resp = null;

  RestClientResponse arg16;
  try {
      resp = this.call(arg34, headers, (Object) null);
      RestClientResponse e = new RestClientResponse();
      e.setStatusCode(resp.getStatusLine().getStatusCode());
      HashMap resHeaders = new HashMap();
      Header[] arg14;
      int arg13 = (arg14 = resp.getAllHeaders()).length;

      for (int arg12 = 0; arg12 < arg13; ++arg12) {
          Header rt = arg14[arg12];
          resHeaders.put(rt.getName(), rt.getValue());
      }

      if (t != null && e.getStatusCode() == 200) {
          if (t.equals(String.class)) {
              e.setBody(IOUtils.toString(resp.getEntity().getContent()));
          } else {
              Object arg36 = this.mapper.readValue(resp.getEntity()
                      .getContent(), t);
              e.setBody(arg36);
          }
      } else {
          String arg35 = IOUtils.toString(resp.getEntity().getContent());
          System.out.println(arg35);
      }

      e.setHeaders(resHeaders);
      arg16 = e;
  } catch (ClientProtocolException arg30) {
      arg30.printStackTrace();
      throw new RestClientException(arg30);
  } catch (IOException arg31) {
      arg31.printStackTrace();
      throw new RestClientException(arg31);
  } finally {
      if (resp != null) {
          try {
              resp.close();
          } catch (IOException arg26) {
              arg26.printStackTrace();
          }
      }

  }

  return arg16;
}

public <T> RestClientResponse<T> put(String url,
      Map<String, String> pathVariables, Map<String, String> headers,
      Object request, Class<T> t) throws RestClientException {
  HttpPut put = new HttpPut(this.genRealPath(url, pathVariables));
  put.setConfig(RequestConfig.custom().setConnectTimeout(30000)
          .setSocketTimeout(30000).build());
  CloseableHttpResponse resp = null;

  RestClientResponse arg14;
  try {
      resp = this.call(put, headers, request);
      RestClientResponse e = new RestClientResponse();
      e.setStatusCode(resp.getStatusLine().getStatusCode());
      HashMap resHeaders = new HashMap();
      Header[] arg12;
      int arg11 = (arg12 = resp.getAllHeaders()).length;

      for (int arg10 = 0; arg10 < arg11; ++arg10) {
          Header body = arg12[arg10];
          resHeaders.put(body.getName(), body.getValue());
      }

      if (t != null && e.getStatusCode() == 200) {
          if (t.equals(String.class)) {
              e.setBody(IOUtils.toString(resp.getEntity().getContent()));
          } else {
              Object arg25 = this.mapper.readValue(resp.getEntity()
                      .getContent(), t);
              e.setBody(arg25);
          }
      }

      e.setHeaders(resHeaders);
      arg14 = e;
  } catch (ClientProtocolException arg22) {
      arg22.printStackTrace();
      throw new RestClientException(arg22);
  } catch (IOException arg23) {
      arg23.printStackTrace();
      throw new RestClientException(arg23);
  } finally {
      if (resp != null) {
          try {
              resp.close();
          } catch (IOException arg21) {
              arg21.printStackTrace();
          }
      }

  }

  return arg14;
}

public <T> RestClientResponse<T> delete(String url,
      Map<String, String> pathVariables, Map<String, String> headers,
      Object request, Class<T> t) throws RestClientException {
  return this.delete(url, pathVariables, headers, (List) null, t);
}

public <T> RestClientResponse<T> delete(String url,
      Map<String, String> pathVariables, Map<String, String> headers,
      Map<String, String> request, Class<T> t) throws RestClientException {
  ArrayList pairs = new ArrayList();
  Iterator arg7 = request.entrySet().iterator();

  while (arg7.hasNext()) {
      Entry entry = (Entry) arg7.next();
      pairs.add(Pair.of((String) entry.getKey(),
              (String) entry.getValue()));
  }

  return this.delete(url, pathVariables, headers, (List) pairs, t);
}

public <T> RestClientResponse<T> delete(String url,
      Map<String, String> pathVariables, Map<String, String> headers,
      List<Pair<String, String>> request, Class<T> t)
      throws RestClientException {
  ArrayList params = new ArrayList();
  if (request != null) {
      Iterator delete = request.iterator();

      while (delete.hasNext()) {
          Pair realPath = (Pair) delete.next();
          params.add(new BasicNameValuePair((String) realPath.getKey(),
                  (String) realPath.getValue()));
      }
  }

  String arg33 = null;

  try {
      if (params.isEmpty()) {
          arg33 = this.genRealPath(url, pathVariables);
      } else {
          arg33 = this.genRealPath(url, pathVariables)
                  + "?"
                  + EntityUtils
                          .toString(new UrlEncodedFormEntity(params));
      }
  } catch (UnsupportedEncodingException arg28) {
      arg28.printStackTrace();
  } catch (IOException arg29) {
      arg29.printStackTrace();
  }

  HttpDelete arg34 = new HttpDelete(arg33);
  arg34.setConfig(RequestConfig.custom().setConnectTimeout(30000)
          .setSocketTimeout(30000).build());
  CloseableHttpResponse resp = null;

  RestClientResponse arg16;
  try {
      resp = this.call(arg34, headers, request);
      RestClientResponse e = new RestClientResponse();
      e.setStatusCode(resp.getStatusLine().getStatusCode());
      HashMap resHeaders = new HashMap();
      Header[] arg14;
      int arg13 = (arg14 = resp.getAllHeaders()).length;

      for (int arg12 = 0; arg12 < arg13; ++arg12) {
          Header body = arg14[arg12];
          resHeaders.put(body.getName(), body.getValue());
      }

      if (t != null && e.getStatusCode() == 200) {
          if (t.equals(String.class)) {
              e.setBody(IOUtils.toString(resp.getEntity().getContent()));
          } else {
              Object arg35 = this.mapper.readValue(resp.getEntity()
                      .getContent(), t);
              e.setBody(arg35);
          }
      }

      e.setHeaders(resHeaders);
      arg16 = e;
  } catch (ClientProtocolException arg30) {
      arg30.printStackTrace();
      throw new RestClientException(arg30);
  } catch (IOException arg31) {
      arg31.printStackTrace();
      throw new RestClientException(arg31);
  } finally {
      if (resp != null) {
          try {
              resp.close();
          } catch (IOException arg26) {
              arg26.printStackTrace();
          }
      }

  }

  return arg16;
}

public CloseableHttpResponse call(HttpUriRequest request,
      Map<String, String> headers, Object obj)
      throws ClientProtocolException, IOException {
  if (headers != null) {
      Iterator arg4 = headers.entrySet().iterator();

      while (arg4.hasNext()) {
          Entry jsonBody = (Entry) arg4.next();
          request.addHeader((String) jsonBody.getKey(),
                  (String) jsonBody.getValue());
      }
  }

  if (obj != null && request instanceof HttpEntityEnclosingRequest) {
      String jsonBody1 = obj instanceof String
              ? (String) obj
              : this.mapper.writeValueAsString(obj);
      ((HttpEntityEnclosingRequest) request).setEntity(new StringEntity(
              jsonBody1, ContentType.create("application/json",
                      Consts.UTF_8)));
  }

  if (this.client == null) {
      this.init();
  }

  return (CloseableHttpResponse) this.client.execute(request);
}

private String genRealPath(String url, Map<String, String> pathVariables) {
  if (pathVariables != null && pathVariables.size() != 0) {
      String result = url;

      Entry entry;
      for (Iterator arg4 = pathVariables.entrySet().iterator(); arg4
              .hasNext(); result = StringUtils.replace(result, "{"
              + (String) entry.getKey() + "}", (String) entry.getValue())) {
          entry = (Entry) arg4.next();
      }

      return result;
  } else {
      return url;
  }
}

public void shutdown() {
  try {
      ((CloseableHttpClient) this.client).close();
  } catch (IOException arg1) {
      arg1.printStackTrace();
  }

}
}
