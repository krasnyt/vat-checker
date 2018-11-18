package cz.krasnyt.vatchecker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.krasnyt.vatchecker.data.HttpVatReader;
import cz.krasnyt.vatchecker.domain.Country;
import cz.krasnyt.vatchecker.service.VatManager;
import cz.krasnyt.vatchecker.util.Constants;

public class Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        VatManager vatManager = new VatManager(new HttpVatReader());

        int nrCountries = Constants.NR_COUNTRIES;

        LOGGER.info("{} countries with highest vat:", nrCountries);
        for (Country country : vatManager.getNCountriesWithHighestVat(nrCountries)) {
            LOGGER.info(country.toShortString());
        }

        LOGGER.info("{} countries with lowest vat:", nrCountries);
        for (Country country : vatManager.getNCountriesWithLowestVat(nrCountries)) {
            LOGGER.info(country.toShortString());
        }
    }

}