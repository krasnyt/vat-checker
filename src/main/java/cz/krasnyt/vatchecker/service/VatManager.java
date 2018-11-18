package cz.krasnyt.vatchecker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cz.krasnyt.vatchecker.data.VatReader;
import cz.krasnyt.vatchecker.domain.Country;
import cz.krasnyt.vatchecker.domain.VatReadingException;

/**
 * Class that operates on a list of countries and their VATs.
 */
public class VatManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(VatManager.class);

    /**
     * VAT Reader that is used to fetch VAT rates from 'some' source.
     */
    private final VatReader reader;

    /**
     * Locally cached a list of countries with their VATs
     */
    private final List<Country> countries = new ArrayList<>();

    /**
     * Creates a new VAT manager
     *
     * @param reader reader that is used to fetch new (refresh) countries and the VAT rates
     */
    public VatManager(VatReader reader) {
        this.reader = reader;
        refresh();
    }

    /**
     * Refreshes the local cached memory of countries. It uses the {@link VatReader} as a source.
     */
    public void refresh() {
        countries.clear();
        try {
            countries.addAll(reader.readVatRates());
            LOGGER.info("VatManager refreshed. Countries={}", countries.size());
        } catch (VatReadingException e) {
            LOGGER.error("Unable to fetch VAT data.", e);
        } catch (Exception e) {
            LOGGER.error("Unhandled exception when refreshing VAT rates", e);
        }
    }

    /**
     * Returns a list of top N countries with highest VAT (ordered).
     * It respects the total number of countries. It is safe to request more than cached number
     * of countries. In that case only those countries will be returned.
     *
     * @param nrCountries number of countries to return
     * @return list of countries
     */
    public List<Country> getNCountriesWithHighestVat(int nrCountries) {
        return getNCountriesByStandardVat(nrCountries, SortOrder.DESC);
    }

    /**
     * Returns a list of top N countries with highest VAT (ordered).
     * It respects the total number of countries. It is safe to request more than cached number
     * of countries. In that case only those countries will be returned.
     *
     * @param nrCountries number of countries to return
     * @return list of countries
     */
    public List<Country> getNCountriesWithLowestVat(int nrCountries) {
        return getNCountriesByStandardVat(nrCountries, SortOrder.ASC);
    }

    /**
     * Returns top N countries from the memory after sorting them by {@link SortOrder}
     *
     * @param nrCountries number of countries to return
     * @param sortOrder   sort order before getting the sublist
     * @return list of countries
     */
    private List<Country> getNCountriesByStandardVat(int nrCountries, SortOrder sortOrder) {
        if (nrCountries == 0) {
            return Collections.emptyList();
        }

        List<Country> sortedCountries = getCountriesSortedByStandardVat(sortOrder);

        int indexFrom = 0;
        int indexTo = Math.min(sortedCountries.size(), nrCountries);

        return sortedCountries.subList(indexFrom, indexTo);
    }

    /**
     * Sorts countries by the standard VAT in the specified order and returns them.
     * It operates on the copy of the cache so any further changes on the returned list
     * won't affect the cache.
     *
     * @param sortOrder sort order
     * @return list of sorted countries
     */
    public List<Country> getCountriesSortedByStandardVat(SortOrder sortOrder) {
        List<Country> copyCollection = new ArrayList<>(countries);

        if (sortOrder != null) {
            Comparator<Country> comparator = Comparator.comparing(Country::getMostRecentStandardVat);
            if (sortOrder == SortOrder.DESC) {
                comparator = comparator.reversed();
            }
            copyCollection.sort(comparator);
        }

        return copyCollection;
    }

}