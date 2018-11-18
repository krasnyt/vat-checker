package cz.krasnyt.vatchecker.domain;

import java.time.LocalDate;
import java.util.Objects;

public class VatPeriod {

    private LocalDate effectiveFrom;
    private VatPeriodRates rates;

    public VatPeriod() {
    }

    public VatPeriod(LocalDate effectiveFrom, VatPeriodRates rates) {
        this.effectiveFrom = effectiveFrom;
        this.rates = rates;
    }

    public LocalDate getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDate effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public VatPeriodRates getRates() {
        return rates;
    }

    public void setRates(VatPeriodRates rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VatPeriod vatPeriod = (VatPeriod) o;
        return Objects.equals(effectiveFrom, vatPeriod.effectiveFrom) &&
                Objects.equals(rates, vatPeriod.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectiveFrom, rates);
    }

    @Override
    public String toString() {
        return "VatPeriod{" +
                "effectiveFrom=" + effectiveFrom +
                ", rates=" + rates +
                '}';
    }

}