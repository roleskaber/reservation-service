package com.demo.demo.hotels.api;

import com.demo.demo.hotels.db.HotelEntity;
import com.demo.demo.hotels.dto.HotelDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/create")
    public ResponseEntity<HotelEntity> createHotel(@RequestBody HotelDto hotelDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelService.addHotel(hotelDto));

    }

    @GetMapping("/all")
    public ResponseEntity<List<HotelEntity>> getAllHotelsByParams() {
        return ResponseEntity.ok(hotelService.getHotelsByParams());
    }

    @GetMapping("/search")
    public ResponseEntity<List<HotelEntity>> searchHotels(
            @RequestParam String hint,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return ResponseEntity.ok(hotelService.doElasticSearch(hint, size, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelEntity> findHotelByID(@PathVariable Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return ResponseEntity.ok("Deleted");
    }


}
