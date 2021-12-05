package pl.kulbat.monitorowaniejednostekmorskich.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.DestinationPoint;
import pl.kulbat.monitorowaniejednostekmorskich.service.mapper.PositionMapper;
import pl.kulbat.monitorowaniejednostekmorskich.service.repository.DestinationEntityRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.*;

@Service
@RequiredArgsConstructor
public class DestinationService {

    private static final String GEOCODING_URL = "http://api.positionstack.com/v1/forward?access_key=1c28dd66fdeca61da2b6a574ebfa8919&query=";
    private final RestTemplate restTemplate = new RestTemplate();
    private final DestinationEntityRepository repository;
    private final PositionMapper positionMapper;


    public List<DestinationPoint> findAllDestinations() {
        return repository.findAll().stream().map(positionMapper::map).collect(toList());
    }

    public DestinationPoint getDestinationPointByName(final String name) {
        if (StringUtils.hasText(name) && name.length() > 3) {
            final String finalUrl = GEOCODING_URL + name + "&limit=1";
            JsonNode data = restTemplate.getForObject(finalUrl, JsonNode.class).get("data");
            if (!data.isEmpty()) {
                data = data.get(0);
                return new DestinationPoint(
                        data.get("latitude").isNull() ? 0.0: data.get("latitude").asDouble(),
                        data.get("longitude").isNull() ? 0.0: data.get("longitude").asDouble(),
                        data.get("name").isNull() ? EMPTY : data.get("name").asText(),
                        data.get("country").isNull() ? EMPTY: data.get("country").asText(),
                        data.get("country_code").isNull() ? EMPTY: data.get("country_code").asText(),
                        data.get("continent").isNull() ? EMPTY: data.get("continent").asText()
                );
            }
        }
        return new DestinationPoint(0.0, 0.0, name);
    }
}
