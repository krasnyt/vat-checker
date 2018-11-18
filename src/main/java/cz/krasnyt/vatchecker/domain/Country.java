package cz.krasnyt.vatchecker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Country {

    private String name;
    private String countryCode;
    private List<VatPeriod> periods = new ArrayList<>();

    @JsonProperty("code")
    private String nativeCountryCode;

    public Country() {
    }

    public Country(String name, String countryCode, String nativeCode, List<VatPeriod> periods) {
        this.name = name;
        this.countryCode = countryCode;
        this.periods = periods;
        this.nativeCountryCode = nativeCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNativeCountryCode() {
        return nativeCountryCode;
    }

    public void setNativeCountryCode(String nativeCountryCode) {
        this.nativeCountryCode = nativeCountryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<VatPeriod> getPeriods() {
        return periods;
    }

    public void setPeriods(List<VatPeriod> periods) {
        this.periods = periods;
    }

    public VatPeriod getMostRecentVat() {
        Optional<VatPeriod> mostRecentVatOpt = getPeriods().stream()
                .max(Comparator.comparing(VatPeriod::getEffectiveFrom));

        return mostRecentVatOpt.orElseThrow(() -> new MissingVatException(this));
    }

    public BigDecimal getMostRecentStandardVat() {
        return getMostRecentVat().getRates().getStandardRate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Country country = (Country) o;
        return Objects.equals(name, country.name) &&
                Objects.equals(nativeCountryCode, country.nativeCountryCode) &&
                Objects.equals(countryCode, country.countryCode) &&
                Objects.equals(periods, country.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, nativeCountryCode, countryCode, periods);
    }

    public String toShortString() {
        return "Country='" + name + "', standardVat=" + getMostRecentStandardVat();
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", nativeCountryCode='" + nativeCountryCode + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", periods=" + periods +
                '}';
    }

}