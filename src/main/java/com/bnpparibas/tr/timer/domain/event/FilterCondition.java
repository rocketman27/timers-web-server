package com.bnpparibas.tr.timer.domain.event;

import java.util.ArrayList;
import java.util.List;

public class FilterCondition {
    private List<String> regions = new ArrayList<>();
    private List<String> countries = new ArrayList<>();
    private List<String> productTypes = new ArrayList<>();
    private List<String> bookingModels = new ArrayList<>();
    private List<String> flowTypes = new ArrayList<>();

    public List<String> getRegions() { return regions; }
    public void setRegions(List<String> regions) { this.regions = regions; }
    public List<String> getCountries() { return countries; }
    public void setCountries(List<String> countries) { this.countries = countries; }
    public List<String> getProductTypes() { return productTypes; }
    public void setProductTypes(List<String> productTypes) { this.productTypes = productTypes; }
    public List<String> getBookingModels() { return bookingModels; }
    public void setBookingModels(List<String> bookingModels) { this.bookingModels = bookingModels; }
    public List<String> getFlowTypes() { return flowTypes; }
    public void setFlowTypes(List<String> flowTypes) { this.flowTypes = flowTypes; }
}


