package com.demo.demo.hotels.db;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.demo.demo.hotels.domain.HotelAmenities;
import com.demo.demo.hotels.dto.HotelDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Table(name="hotels")
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
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
    Date updatedAt;

    public HotelDto toDto() {
        return new HotelDto(
                id, name, address, country, hotelRoomEntity, amenities, updatedAt
        );
    }
}
