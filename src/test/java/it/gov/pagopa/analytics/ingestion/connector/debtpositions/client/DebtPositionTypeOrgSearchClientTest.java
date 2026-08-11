package it.gov.pagopa.analytics.ingestion.connector.debtpositions.client;

import it.gov.pagopa.analytics.ingestion.connector.debtpositions.config.DebtPositionApisHolder;
import it.gov.pagopa.pu.debtpositions.client.generated.DebtPositionTypeOrgSearchControllerApi;
import it.gov.pagopa.pu.debtpositions.dto.generated.CollectionModelDebtPositionTypeOrg;
import it.gov.pagopa.pu.debtpositions.dto.generated.DebtPositionTypeOrg;
import it.gov.pagopa.pu.debtpositions.dto.generated.PagedModelDebtPositionTypeOrgEmbedded;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DebtPositionTypeOrgSearchClientTest {

  @Mock
  private DebtPositionApisHolder debtPositionApisHolderMock;
  @Mock
  private DebtPositionTypeOrgSearchControllerApi debtPositionTypeOrgSearchControllerApiMock;

  private DebtPositionTypeOrgSearchClient debtPositionTypeOrgSearchClient;

  @BeforeEach
  void setUp() {
    debtPositionTypeOrgSearchClient = new DebtPositionTypeOrgSearchClient(debtPositionApisHolderMock);
  }

  @AfterEach
  void verifyNoMoreInteractions() {
    Mockito.verifyNoMoreInteractions(
      debtPositionApisHolderMock,
      debtPositionTypeOrgSearchControllerApiMock
    );
  }

  @Test
  void givenNullResultWhenGetUpdatedDebtPositionTypeOrgsThenEmptyList() {
    String accessToken = "ACCESSTOKEN";
    OffsetDateTime lastUpdateDate = OffsetDateTime.now();

    CollectionModelDebtPositionTypeOrg collectionModel = new CollectionModelDebtPositionTypeOrg();

    when(debtPositionApisHolderMock.getDebtPositionTypeOrgSearchControllerApi(accessToken))
      .thenReturn(debtPositionTypeOrgSearchControllerApiMock);

    when(debtPositionTypeOrgSearchControllerApiMock
      .crudDebtPositionTypeOrgsFindByUpdateDateGreaterThanOrderByUpdateDateAsc(lastUpdateDate))
      .thenReturn(collectionModel);

    List<DebtPositionTypeOrg> result = debtPositionTypeOrgSearchClient.getUpdatedDebtPositionTypeOrgs(lastUpdateDate, accessToken);

    assertSame(List.of(), result);
  }

  @Test
  void whenGetUpdatedDebtPositionTypeOrgsThenEmptyList() {
    String accessToken = "ACCESSTOKEN";
    OffsetDateTime lastUpdateDate = OffsetDateTime.now();

    List<DebtPositionTypeOrg> expectedResult = List.of();
    CollectionModelDebtPositionTypeOrg collectionModel = new CollectionModelDebtPositionTypeOrg();
    collectionModel.setEmbedded(new PagedModelDebtPositionTypeOrgEmbedded(expectedResult));

    when(debtPositionApisHolderMock.getDebtPositionTypeOrgSearchControllerApi(accessToken))
      .thenReturn(debtPositionTypeOrgSearchControllerApiMock);

    when(debtPositionTypeOrgSearchControllerApiMock
      .crudDebtPositionTypeOrgsFindByUpdateDateGreaterThanOrderByUpdateDateAsc(lastUpdateDate))
      .thenReturn(collectionModel);

    List<DebtPositionTypeOrg> result = debtPositionTypeOrgSearchClient.getUpdatedDebtPositionTypeOrgs(lastUpdateDate, accessToken);

    assertSame(expectedResult, result);
  }

}
