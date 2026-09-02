package it.gov.pagopa.analytics.ingestion.exception;

import io.temporal.client.WorkflowExecutionAlreadyStarted;
import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorDTO;
import it.gov.pagopa.analytics.ingestion.exception.common.CommonExceptionHandler;
import it.gov.pagopa.analytics.ingestion.exception.custom.WorkflowInternalErrorException;
import it.gov.pagopa.analytics.ingestion.exception.custom.WorkflowNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler extends CommonExceptionHandler {

  @ExceptionHandler(WorkflowExecutionAlreadyStarted.class)
  public ResponseEntity<ErrorDTO> handleWorkflowExecutionAlreadyStarted(WorkflowExecutionAlreadyStarted ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.CONFLICT, ErrorDTO.CategoryEnum.CONFLICT);
  }

  @ExceptionHandler({WorkflowNotFoundException.class, io.temporal.client.WorkflowNotFoundException.class})
  public ResponseEntity<ErrorDTO> handleNotFoundException(RuntimeException ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.NOT_FOUND, ErrorDTO.CategoryEnum.NOT_FOUND);
  }

  @ExceptionHandler({WorkflowInternalErrorException.class})
  public ResponseEntity<ErrorDTO> handleInternalError(RuntimeException ex, HttpServletRequest request) {
    return handleException(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, ErrorDTO.CategoryEnum.GENERIC_ERROR);
  }

}
