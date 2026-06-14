package com.demo.demo.hotels.dto;

import com.demo.demo.hotels.db.HotelEntity;
import com.demo.demo.hotels.domain.HotelAmenities;
import com.demo.demo.hotels.db.HotelRoomEntity;

import java.util.Date;
import java.util.List;

public record HotelDto(
        Long id,
        String name,
        String address,
        String country,
        List<HotelRoomEntity> rooms,
        List<HotelAmenities> amenities,
        Date updatedAt
) {
    public HotelEntity toEntity() {
        return new HotelEntity(
                null,
                name,
                address,
                country,
                rooms,
                amenities,
                updatedAt
        );
    }
}
