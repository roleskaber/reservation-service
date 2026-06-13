package com.demo.demo.hotels.elastic;

import com.demo.demo.hotels.db.HotelEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticSearchService {

    private final Map<String, String> var = System.getenv();
    private final HttpClient client = HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            var.get("ELASTIC_USERNAME"),
                            var.get("ELASTIC_PASSWORD").toCharArray()
                    );
                }
            })
            .connectTimeout(Duration.ofSeconds(15))
            .build();

    private List<HotelEntity> serializeResult(String body) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(body);
        JsonNode hits = root.path("hits").path("hits");

        List<HotelEntity> hotels = new ArrayList<>();
        for (JsonNode hit : hits) {
            JsonNode source = hit.path("_source");
            HotelEntity hotel = mapper.treeToValue(source, HotelEntity.class);
            hotels.add(hotel);
        }
        return hotels;
    }

    public List<HotelEntity> processElasticRequest(String hint, int size, int page)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(var.get("ELASTIC_URL") + "/hotels/_search"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        BodyBuilderKt.getQueryObj(hint, page, size)
                ))
                .build();
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        return serializeResult(response.body());
    }

    public void addEntity(HotelEntity hotelEntity) throws IOException, InterruptedException {
        var entity = new ObjectMapper().writeValueAsString(hotelEntity);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create((var.get("ELASTIC_URL") + "/hotels/_doc/" + hotelEntity.getId())))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(entity))
                .build();
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
    }
}
