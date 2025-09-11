package com.example.timers.web;

import com.example.timers.service.GeoCatalogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/geo")
public class GeoController {

    private final GeoCatalogService geoCatalogService;

    public GeoController(GeoCatalogService geoCatalogService) {
        this.geoCatalogService = geoCatalogService;
    }

    @GetMapping("/regions")
    public ResponseEntity<List<GeoCatalogService.Region>> listRegions() {
        return ResponseEntity.ok(geoCatalogService.getRegions());
    }

    @GetMapping("/countries")
    public ResponseEntity<List<GeoCatalogService.Country>> listCountries(
            @RequestParam(name = "region", required = false) String regionCode
    ) {
        return ResponseEntity.ok(geoCatalogService.getCountries(Optional.ofNullable(regionCode)));
    }
}


