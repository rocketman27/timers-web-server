package com.bnpparibas.tr.timer.domain;

import com.bnpparibas.tr.timer.domain.event.FilterCondition;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FilterConditionTest {

    @Test
    void getters_and_setters() {
        FilterCondition fc = new FilterCondition();
        fc.setRegions(java.util.List.of("EMEA"));
        fc.setCountries(java.util.List.of("GB"));
        fc.setProductTypes(java.util.List.of("CASH"));
        fc.setBookingModels(java.util.List.of("B1"));
        fc.setFlowTypes(java.util.List.of("FX"));

        assertThat(fc.getRegions()).containsExactly("EMEA");
        assertThat(fc.getCountries()).containsExactly("GB");
        assertThat(fc.getProductTypes()).containsExactly("CASH");
        assertThat(fc.getBookingModels()).containsExactly("B1");
        assertThat(fc.getFlowTypes()).containsExactly("FX");
    }
}


