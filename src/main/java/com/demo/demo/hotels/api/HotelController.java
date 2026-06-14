package com.demo.demo.hotels.api;

import com.demo.demo.hotels.db.HotelEntity;
import com.demo.demo.hotels.dto.HotelDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping("/create")
    public CompletableFuture<ResponseEntity<HotelEntity>> createHotel(@RequestBody HotelDto hotelDto) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity
                .status(HttpStatus.CREATED)
                .body(hotelService.addHotel(hotelDto))
        );

    }

    @GetMapping("/all")
    public CompletableFuture<ResponseEntity<List<HotelEntity>>> getAllHotelsByParams() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity
                .ok(hotelService.getHotelsByParams())
        );
    }

    @GetMapping("/search")
    public CompletableFuture<ResponseEntity<List<HotelEntity>>> searchHotels(
            @RequestParam String hint,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false, defaultValue = "1") int page
    ) {
        return CompletableFuture.supplyAsync(
                () -> ResponseEntity.ok(hotelService.doElasticSearch(hint, size, page))
        );
    }

    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<HotelEntity>> findHotelByID(@PathVariable Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(hotelService.getHotelById(id)));
    }


    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok("Deleted"));
    }


}
