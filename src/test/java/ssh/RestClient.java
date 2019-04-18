package ssh;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang3.tuple.Pair;

public interface RestClient {
    void init();

    void init(Properties arg0);

    void setConfig(Properties arg0);

    <T> RestClientResponse<T> post(String arg0, Map<String, String> arg1,
            Object arg2, Class<T> arg3) throws RestClientException;

    <T> RestClientResponse<T> post(String arg0, Map<String, String> arg1,
            Map<String, String> arg2, Object arg3, Class<T> arg4)
            throws RestClientException;

    <T> RestClientResponse<T> get(String arg0, Map<String, String> arg1,
            Map<String, String> arg2, Map<String, String> arg3, Class<T> arg4)
            throws RestClientException;

    <T> RestClientResponse<T> put(String arg0, Map<String, String> arg1,
            Map<String, String> arg2, Object arg3, Class<T> arg4)
            throws RestClientException;

    <T> RestClientResponse<T> delete(String arg0, Map<String, String> arg1,
            Map<String, String> arg2, Map<String, String> arg3, Class<T> arg4)
            throws RestClientException;

    <T> RestClientResponse<T> delete(String arg0, Map<String, String> arg1,
            Map<String, String> arg2, Object arg3, Class<T> arg4)
            throws RestClientException;

    <T> RestClientResponse<T> delete(String arg0, Map<String, String> arg1,
            Map<String, String> arg2, List<Pair<String, String>> arg3,
            Class<T> arg4) throws RestClientException;

    void shutdown();
}