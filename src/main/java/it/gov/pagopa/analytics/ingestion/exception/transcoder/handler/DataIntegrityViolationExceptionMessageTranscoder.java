package it.gov.pagopa.analytics.ingestion.exception.transcoder.handler;

import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorDTO;
import it.gov.pagopa.analytics.ingestion.exception.transcoder.ExceptionMessageTranscoded;
import it.gov.pagopa.analytics.ingestion.exception.transcoder.ExceptionMessageTranscoder;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

public class DataIntegrityViolationExceptionMessageTranscoder implements ExceptionMessageTranscoder<DataIntegrityViolationException> {

  @Override
  public ExceptionMessageTranscoded transcode(DataIntegrityViolationException dataIntegrityViolationException) {
    String errorMsg = "Conflict.";
    if(dataIntegrityViolationException.getCause() instanceof ConstraintViolationException hibernateConstraintViolationException) {
      errorMsg += " " + hibernateConstraintViolationException.getSQLException().getMessage();
    }
    return new ExceptionMessageTranscoded(
      ErrorDTO.CategoryEnum.CONFLICT.name(),
      errorMsg,
      null) ;
  }
}
