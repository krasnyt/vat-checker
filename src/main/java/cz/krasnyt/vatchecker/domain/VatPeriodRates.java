package cz.krasnyt.vatchecker.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class VatPeriodRates {

    @JsonProperty("standard")
    private BigDecimal standardRate;

    public VatPeriodRates() {
    }

    public VatPeriodRates(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    public BigDecimal getStandardRate() {
        return standardRate;
    }

    public void setStandardRate(BigDecimal standardRate) {
        this.standardRate = standardRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VatPeriodRates that = (VatPeriodRates) o;
        return Objects.equals(standardRate, that.standardRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(standardRate);
    }

    @Override
    public String toString() {
        return "VatPeriodRates{" +
                "standardRate=" + standardRate +
                '}';
    }

}