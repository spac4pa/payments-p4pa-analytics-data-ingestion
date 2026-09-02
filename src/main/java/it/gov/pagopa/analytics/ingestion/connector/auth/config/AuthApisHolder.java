package it.gov.pagopa.analytics.ingestion.connector.auth.config;

import it.gov.pagopa.analytics.ingestion.config.rest.HttpClientErrorJsonBodyHandler;
import it.gov.pagopa.analytics.ingestion.config.rest.RestTemplateConfig;
import it.gov.pagopa.analytics.ingestion.connector.auth.mapper.AuthErrorDTOMapper;
import it.gov.pagopa.pu.auth.client.generated.AuthnApi;
import it.gov.pagopa.pu.auth.dto.generated.AuthErrorDTO;
import it.gov.pagopa.pu.auth.generated.ApiClient;
import it.gov.pagopa.pu.auth.generated.BaseApi;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.json.JsonMapper;

@Lazy
@Service
public class AuthApisHolder {

    private final AuthnApi authnApi;

    private final ThreadLocal<String> bearerTokenHolder = new ThreadLocal<>();

    public AuthApisHolder(
            AuthApiClientConfig clientConfig,
            RestTemplateBuilder restTemplateBuilder,
            JsonMapper jsonMapper
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(clientConfig.getBaseUrl());
        apiClient.setBearerToken(bearerTokenHolder::get);
        apiClient.setMaxAttemptsForRetry(Math.max(1, clientConfig.getMaxAttempts()));
        apiClient.setWaitTimeMillis(clientConfig.getWaitTimeMillis());
        if (clientConfig.isPrintBodyWhenError()) {
            restTemplate.setErrorHandler(RestTemplateConfig.bodyPrinterWhenError("AUTH"));
        }
      restTemplate.setErrorHandler(new HttpClientErrorJsonBodyHandler<>(jsonMapper, "AUTH", clientConfig.isPrintBodyWhenError(),
        AuthErrorDTO.class, AuthErrorDTOMapper::map)
      );

        this.authnApi = new AuthnApi(apiClient);
    }

    @PreDestroy
    public void unload(){
        bearerTokenHolder.remove();
    }

    /** It will return a {@link AuthnApi} instrumented with the provided accessToken. Use null if auth is not required */
    public AuthnApi getAuthnApi(String accessToken){
        return getApi(accessToken, authnApi);
    }

    private <T extends BaseApi> T getApi(String accessToken, T api) {
        bearerTokenHolder.set(accessToken);
        return api;
    }
}
