package com.orderservice.domain.vo;

import lombok.Getter;

public class Country {
    @Getter
    public String countryCode;
    @Getter
    public String countryName;

    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
}
