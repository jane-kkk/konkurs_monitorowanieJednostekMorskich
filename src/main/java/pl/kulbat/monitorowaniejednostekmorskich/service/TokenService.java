package pl.kulbat.monitorowaniejednostekmorskich.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.kulbat.monitorowaniejednostekmorskich.model.TokenRequest;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class TokenService {
    private static final String TOKEN_URL = "https://id.barentswatch.no/connect/token";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    private void refreshAccessToken() {
        log.info("getAccessToken started");
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        MultiValueMap requestParameters = new LinkedMultiValueMap<String, String>();
        requestParameters.setAll(objectMapper.convertValue(new TokenRequest(), new TypeReference<>() {
        }));
        HttpEntity<MultiValueMap> httpEntity = new HttpEntity<>(requestParameters, headers);

        ResponseEntity<JsonNode> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, httpEntity, JsonNode.class);
        accessToken = response.getBody().get("access_token").asText();
        log.info("token retrieved");
    }
}
