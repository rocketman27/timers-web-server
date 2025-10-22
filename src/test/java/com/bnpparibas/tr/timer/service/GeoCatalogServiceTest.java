package com.bnpparibas.tr.timer.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class GeoCatalogServiceTest {

    @Test
    void loadCatalogs_and_basicQueries() throws IOException {
        GeoCatalogService svc = new GeoCatalogService();
        svc.loadCatalogs();

        assertThat(svc.getRegions()).isNotEmpty();
        assertThat(svc.getCountries(Optional.empty())).isNotEmpty();

        String anyRegion = svc.getRegions().get(0).getName();
        assertThat(svc.isValidRegionCode(anyRegion)).isTrue();
        assertThat(svc.getCountries(Optional.of(anyRegion))).allMatch(c -> anyRegion.equals(c.getRegion()));

        String anyCountry = svc.getCountries(Optional.empty()).get(0).getCountryCode();
        assertThat(svc.isValidCountryCode(anyCountry)).isTrue();
        assertThat(svc.allRegionCodes()).contains(anyRegion);
        assertThat(svc.allCountryCodes()).contains(anyCountry);
    }
}


