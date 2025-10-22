package com.bnpparibas.tr.timer.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GeoCatalogServiceCountryTest {

    @Test
    void cover_all_country_methods() {
        GeoCatalogService.Country c = new GeoCatalogService.Country();
        c.setCountryCode("US");
        c.setRegion("Americas");

        assertThat(c.getCountryCode()).isEqualTo("US");
        assertThat(c.getRegion()).isEqualTo("Americas");
        // Backward-compat methods
        assertThat(c.getAlpha2()).isEqualTo("US");
        assertThat(c.getRegionCode()).isEqualTo("Americas");
        assertThat(c.getName()).isEqualTo("US");
    }
}


