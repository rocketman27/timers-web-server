package com.bnpparibas.tr.timers.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GeoCatalogService {

    public static class Region {
        private String name;
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCode() { return name; } // For backward compatibility
    }



    public static class Country {
        private String countryCode; // COUNTRY_CODE from CSV
        private String region; // REGION from CSV
        public String getCountryCode() { return countryCode; }
        public void setCountryCode(String countryCode) { this.countryCode = countryCode; }
        public String getRegion() { return region; }
        public void setRegion(String region) { this.region = region; }
        // Backward compatibility methods
        public String getAlpha2() { return countryCode; }
        public String getRegionCode() { return region; }
        public String getName() { return countryCode; } // For now, just return code
    }



    private List<Region> regions = new ArrayList<>();
    private List<Country> countries = new ArrayList<>();

    @PostConstruct
    public void loadCatalogs() throws IOException {
        this.countries = readCountriesFromCsv("geo/countries.csv");
        this.regions = extractRegionsFromCountries();

    }

    private List<Country> readCountriesFromCsv(String path) throws IOException {
        List<Country> countries = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource(path);
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            boolean firstLine = true;
            
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false; // Skip header
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    Country country = new Country();
                    country.setCountryCode(parts[0].trim());
                    country.setRegion(parts[1].trim());
                    countries.add(country);
                }
            }
        }
        
        return countries;
    }

    private List<Region> extractRegionsFromCountries() {
        return countries.stream()
                .map(Country::getRegion)
                .distinct()
                .map(regionName -> {
                    Region region = new Region();
                    region.setName(regionName);
                    return region;
                })
                .collect(Collectors.toList());
    }



    public List<Region> getRegions() {
        return Collections.unmodifiableList(regions);
    }



    public List<Country> getCountries(Optional<String> regionName) {
        return countries.stream()
                .filter(c -> regionName.map(rn -> Objects.equals(rn, c.getRegion())).orElse(true))
                .collect(Collectors.toList());
    }

    public boolean isValidRegionCode(String code) {
        return regions.stream().anyMatch(r -> Objects.equals(r.getName(), code));
    }

    public boolean isValidCountryCode(String countryCode) {
        return countries.stream().anyMatch(c -> Objects.equals(c.getCountryCode(), countryCode));
    }

    public Set<String> allRegionCodes() {
        return regions.stream().map(Region::getName).collect(Collectors.toSet());
    }

    public Set<String> allCountryCodes() {
        return countries.stream().map(Country::getCountryCode).collect(Collectors.toSet());
    }
}


