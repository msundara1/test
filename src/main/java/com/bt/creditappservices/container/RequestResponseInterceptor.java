package com.bt.creditappservices.container;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

/**
 * @author msundara
 */
@Component
public class RequestResponseInterceptor extends HandlerInterceptorAdapter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseInterceptor.class);

  private static final String X_CORRELATION_ID = "X-Correlation-ID";

  private static final String STARTTIME_HEADER = "startTime";

  private static final String ETAG = "ETag";

  private static final String APPBASEURL = "/creditapp/";

  /**
   * Executed before actual handler is executed
   */
  @Override
  public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
    if (request.getRequestURI().indexOf(APPBASEURL) == -1) {
      return true;
    }
    long startTime = System.currentTimeMillis();
    String correlationId = UUID.randomUUID().toString();
    request.setAttribute(STARTTIME_HEADER, startTime);
    request.setAttribute(X_CORRELATION_ID, correlationId);
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("[" + correlationId + "] request received at [" + (new Date(startTime)));
    }
    return true;
  }

  /**
   * Executed after complete request is finished
   */
  @Override
  public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
      final Exception ex) throws Exception {
    if (request.getRequestURI().indexOf(APPBASEURL) == -1) {
      return;
    }
    String requestURI = request.getRequestURI();
    String correlationId = (String) request.getAttribute(X_CORRELATION_ID);
    long startTime = (Long) request.getAttribute(STARTTIME_HEADER);
    response.setHeader(ETAG, correlationId);
    super.afterCompletion(request, response, handler, ex);
    long endTime = System.currentTimeMillis();
    int responseStatus = getResponseStatusCode(response);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.info("[" + correlationId + "] executeTime [" + (endTime - startTime) + "] ms. Response status[" + responseStatus + "]");
    }
  }

  private String getRemoteAddress(final HttpServletRequest request) {
    final String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
    if (ipFromHeader != null && ipFromHeader.length() > 0) {
      return ipFromHeader;
    }
    return request.getRemoteAddr();
  }

  private String getRequestData(final HttpServletRequest request) throws UnsupportedEncodingException {
    String payload = null;
    ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
    if (wrapper != null) {
      payload = getPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
    }
    return payload;
  }

  private String getResponseData(final HttpServletResponse response) throws IOException {
    String payload = null;
    ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper != null) {
      payload = getPayload(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
      wrapper.copyBodyToResponse();
    }
    return payload;
  }

  private String getPayload(byte[] buf, String characterEncoding) throws UnsupportedEncodingException {
    String payload = null;
    if (buf.length > 0) {
      payload = new String(buf, 0, buf.length, characterEncoding);
    }
    return payload;
  }

  private int getResponseStatusCode(final HttpServletResponse response) {
    int responseStatusCode = -1;
    ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper != null) {
      responseStatusCode = wrapper.getStatusCode();
    }
    return responseStatusCode;
  }
}