package com.demo.demo.hotels.db;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.demo.demo.hotels.domain.HotelAmenities;
import com.demo.demo.hotels.dto.HotelDto;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Table(name="hotels")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name="name")
    String name;
    @Column(name="address")
    String address;
    @Column(name="country")
    String country;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hotel_id")
    List<HotelRoomEntity> hotelRoomEntity;
    @Column(name = "amenities")
    List<HotelAmenities> amenities;
    @Column(name = "updated_at")
    Date updated_at;


    public HotelEntity() {
    }

    public HotelEntity(Long id,
                       String name,
                       String address,
                       String country,
                       List<HotelRoomEntity> hotelRoomEntity,
                       List<HotelAmenities> amenities) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.country = country;
        this.hotelRoomEntity = hotelRoomEntity;
        this.amenities = amenities;
    }

    public HotelDto toDto() {
        return new HotelDto(
                id, name, address, country, hotelRoomEntity, amenities
        );
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<HotelRoomEntity> getHotelRoomEntity() {
        return hotelRoomEntity;
    }

    public void setHotelRoomEntity(List<HotelRoomEntity> hotelRoomEntity) {
        this.hotelRoomEntity = hotelRoomEntity;
    }

    public List<HotelAmenities> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<HotelAmenities> amenities) {
        this.amenities = amenities;
    }
}
