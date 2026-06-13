package com.demo.demo.hotels.api;

import com.demo.demo.hotels.db.HotelEntity;
import com.demo.demo.hotels.db.HotelRepository;
import com.demo.demo.hotels.db.HotelRoomEntity;
import com.demo.demo.hotels.dto.HotelDto;
import com.demo.demo.hotels.elastic.ElasticSearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService implements IHotelService {
    private final Logger logger = LoggerFactory.getLogger(HotelService.class);
    private final HotelRepository hotelRepository;
    private final ElasticSearchService elasticSearchService;

    public HotelService(HotelRepository hotelRepository, ElasticSearchService elasticSearchService) {
        this.hotelRepository = hotelRepository;
        this.elasticSearchService = elasticSearchService;
    }

    @Override
    @Transactional
    public HotelEntity addHotel(HotelDto hotelDto) {
        try {
            elasticSearchService.addEntity(hotelDto.toEntity());
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return hotelRepository.save(hotelDto.toEntity());

    }

    @Override
    public List<HotelEntity> getHotelsByParams() {
        return List.of();
    }

    @Override
    public List<HotelEntity> doElasticSearch(String hint, int size, int page) {
        try {
            return elasticSearchService.processElasticRequest(hint, size, page);
        } catch (IOException | InterruptedException e) {
            logger.error(e.toString());
        }
        return null;
    }

    @Override
    @Transactional
    public HotelEntity getHotelById(Long id) {
        return hotelRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with id {} not found"));
    }

    @Override
    @Transactional
    public void deleteHotel(Long id) {
        this.hotelRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<HotelRoomEntity> getRoomsByHotel(Long id) {
        return this.getHotelById(id).toDto().rooms();
    }
}
