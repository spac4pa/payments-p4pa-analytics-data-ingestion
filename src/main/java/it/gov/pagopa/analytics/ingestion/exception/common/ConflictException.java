package it.gov.pagopa.analytics.ingestion.exception.common;

import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorFieldDTO;
import it.gov.pagopa.analytics.ingestion.exception.NotRetryableActivityException;

import java.util.List;

@SuppressWarnings("java:S110") // Suppress "Inheritance tree of classes should not be too deep": allowed for exception hierarchy
public class ConflictException extends NotRetryableActivityException {

  public ConflictException(String code, String message) {
    this(code, message, null);
  }

  public ConflictException(String code, String message, List<ErrorFieldDTO> fieldErrors) {
    super(code, message, fieldErrors, null);
  }

}
