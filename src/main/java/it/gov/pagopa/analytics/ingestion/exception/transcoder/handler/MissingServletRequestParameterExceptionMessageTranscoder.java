package it.gov.pagopa.analytics.ingestion.exception.transcoder.handler;

import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorDTO;
import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorFieldDTO;
import it.gov.pagopa.analytics.ingestion.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.analytics.ingestion.exception.transcoder.ExceptionMessageTranscoder;
import org.springframework.web.bind.MissingServletRequestParameterException;

import java.util.List;

public class MissingServletRequestParameterExceptionMessageTranscoder implements ExceptionMessageTranscoder<MissingServletRequestParameterException> {

  @Override
  public ExceptionMessageTranscoded transcode(MissingServletRequestParameterException missingServletRequestParameterException) {
    return new ExceptionMessageTranscoded(
      ErrorDTO.CategoryEnum.BAD_REQUEST.name(),
      missingServletRequestParameterException.getMessage(),
      List.of(new ErrorFieldDTO(missingServletRequestParameterException.getParameterName(), "NotNull", missingServletRequestParameterException.getMessage())));
  }
}
