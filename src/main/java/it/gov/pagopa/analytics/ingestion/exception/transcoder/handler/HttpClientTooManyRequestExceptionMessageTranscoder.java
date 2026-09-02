package it.gov.pagopa.analytics.ingestion.exception.transcoder.handler;

import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorDTO;
import it.gov.pagopa.analytics.ingestion.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.analytics.ingestion.exception.transcoder.ExceptionMessageTranscoder;
import org.springframework.web.client.HttpClientErrorException;

public class HttpClientTooManyRequestExceptionMessageTranscoder implements ExceptionMessageTranscoder<HttpClientErrorException.TooManyRequests> {
  @Override
  public ExceptionMessageTranscoded transcode(HttpClientErrorException.TooManyRequests tooManyRequestsException) {
    return new ExceptionMessageTranscoded(
      ErrorDTO.CategoryEnum.TOO_MANY_REQUESTS.name(),
      tooManyRequestsException.getMessage(),
      null);
  }
}
