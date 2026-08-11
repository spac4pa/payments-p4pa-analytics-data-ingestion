package it.gov.pagopa.analytics.ingestion.config.rest;

import it.gov.pagopa.analytics.ingestion.dto.generated.ErrorFieldDTO;

import java.util.List;

public record PuErrorDTO(
  String category,
  String code,
  String message,
  List<ErrorFieldDTO> fields
) {
}
