package cz.krasnyt.vatchecker.util;

import java.math.BigDecimal;
import java.time.LocalDate;

import cz.krasnyt.vatchecker.domain.VatPeriod;
import cz.krasnyt.vatchecker.domain.VatPeriodRates;

public class VatUtil {

    public static VatPeriod vatPeriod(LocalDate date, VatPeriodRates rates) {
        VatPeriod vatPeriod = new VatPeriod();

        vatPeriod.setEffectiveFrom(date);
        vatPeriod.setRates(rates);

        return vatPeriod;
    }

    public static VatPeriodRates rates(String standardRate) {
        VatPeriodRates rates = new VatPeriodRates();

        rates.setStandardRate(new BigDecimal(standardRate));

        return rates;
    }

}
