package cz.krasnyt.vatchecker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VatRates {

    private String version;

    @JsonProperty("rates")
    private List<Country> countries = new ArrayList<>();

    public VatRates() {
    }

    public VatRates(String version, List<Country> countries) {
        this.version = version;
        this.countries = countries;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VatRates vatRates = (VatRates) o;
        return Objects.equals(version, vatRates.version) &&
                Objects.equals(countries, vatRates.countries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, countries);
    }

    @Override
    public String toString() {
        return "VatRates{" +
                "countries=" + countries +
                ", version='" + version + '\'' +
                '}';
    }
}
