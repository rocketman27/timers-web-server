package com.bnpparibas.tr.timer.web;

import com.bnpparibas.tr.timer.service.GeoCatalogService;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class GeoControllerTest {

    @Test
    void listRegions_and_listCountries_returnData() throws Exception {
        GeoCatalogService svc = new GeoCatalogService();
        svc.loadCatalogs();
        GeoController controller = new GeoController(svc);

        var regionsResp = controller.listRegions();
        assertThat(regionsResp.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(regionsResp.getBody()).isNotEmpty();

        String region = regionsResp.getBody().get(0).getName();
        var countriesResp = controller.listCountries(region);
        assertThat(countriesResp.getStatusCode().is2xxSuccessful()).isTrue();
        List<GeoCatalogService.Country> countries = countriesResp.getBody();
        assertThat(countries).isNotEmpty();
        assertThat(countries).allMatch(c -> region.equals(c.getRegion()));
    }
}


