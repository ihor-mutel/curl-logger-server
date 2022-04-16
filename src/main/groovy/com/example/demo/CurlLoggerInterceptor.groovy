package com.example.demo

import okhttp3.Headers
import okhttp3.HttpUrl
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.RequestBody
import okio.Buffer
import org.springframework.stereotype.Service

import javax.servlet.http.HttpServletRequest
import java.nio.charset.Charset
import java.util.stream.Collectors

@Service
public class CurlLoggerInterceptor {
  private StringBuilder curlCommandBuilder;
  private final Charset UTF8 = Charset.forName("UTF-8");
  private String tag = null;

  public CurlLoggerInterceptor() {
  }

  /**
   * Set logcat tag for curl lib to make it ease to filter curl logs only.
   * @param tag
   */


  public void intercept(Request request) throws IOException {

    curlCommandBuilder = new StringBuilder("");
    // add cURL command
    curlCommandBuilder.append("curl ");
    curlCommandBuilder.append("-X ");
    // add method
    curlCommandBuilder.append(request.method().toUpperCase() + " ");
    // adding headers
    for (String headerName : request.headers().names()) {
      addHeader(headerName, request.headers().get(headerName));
    }

    // adding request body
    RequestBody requestBody = request.body();
    if (request.body() != null) {
      Buffer buffer = new Buffer();
      requestBody.writeTo(buffer);
      Charset charset = UTF8;
        MediaType contentType = requestBody.contentType();
      if (contentType != null) {
        addHeader("Content-Type", request.body().contentType().toString());
        charset = contentType.charset(UTF8);
        curlCommandBuilder.append(" -d '" + buffer.readString(charset) + "'");
      }
    }

    // add request URL
    curlCommandBuilder.append(" \"" + request.url().toString() + "\"");
    curlCommandBuilder.append(" -L");

    CurlPrinter.print(tag, request.url().toString(), curlCommandBuilder.toString());
  }

  private void addHeader(String headerName, String headerValue) {
    curlCommandBuilder.append("-H " + "\"" + headerName + ": " + headerValue + "\" ");
  }

  Request create(HttpServletRequest request, String url)
  {

    final HttpUrl targetUrl = HttpUrl.get("http://localhost");

    final String originalBody = request.getReader().lines().collect (Collectors.joining (System.lineSeparator()));

    final RequestBody requestBody = RequestBody.create(MediaType.parse(request.getContentType()), originalBody);

    Map<String, String> headers = Collections.list(request.getHeaderNames())
      .stream()
      .collect(Collectors.toMap(h -> h, request::getHeader));

    Headers headerbuild = Headers.of(headers);

    final Request httpRequest = new Request.Builder()
      .post(requestBody)
      .url(targetUrl)
      .headers(headerbuild)
      .build();
    return httpRequest;
  }

  public String getURL(HttpServletRequest req) {

    String scheme = req.getScheme();             // http
    String serverName = req.getServerName();     // hostname.com
    int serverPort = req.getServerPort();        // 80
    String contextPath = req.getContextPath();   // /mywebapp
    String servletPath = req.getServletPath();   // /servlet/MyServlet
    String pathInfo = req.getPathInfo();         // /a/b;c=123
    String queryString = req.getQueryString();          // d=789

    // Reconstruct original requesting URL
    StringBuilder url = new StringBuilder();
    url.append(scheme).append("://").append(serverName);

    if (serverPort != 80 && serverPort != 443) {
      url.append(":").append(serverPort);
    }

    url.append(contextPath).append(servletPath);

    if (pathInfo != null) {
      url.append(pathInfo);
    }
    if (queryString != null) {
      url.append("?").append(queryString);
    }
    return url.toString();
  }

}
