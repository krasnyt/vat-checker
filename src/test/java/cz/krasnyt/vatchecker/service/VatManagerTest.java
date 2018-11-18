package cz.krasnyt.vatchecker.service;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cz.krasnyt.vatchecker.data.VatReader;
import cz.krasnyt.vatchecker.domain.Country;
import cz.krasnyt.vatchecker.domain.VatPeriod;
import cz.krasnyt.vatchecker.domain.VatReadingException;

import static cz.krasnyt.vatchecker.util.VatUtil.rates;
import static cz.krasnyt.vatchecker.util.VatUtil.vatPeriod;
import static java.time.LocalDate.parse;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class VatManagerTest {

    private VatManager manager;
    private List<Country> mockedCountries;

    @Before
    public void setUp() throws VatReadingException {
        mockedCountries = new ArrayList<>(asList(
                createBangladeshCountry(),
                createKongoCountry(),
                createTurkmenistanCountry()));

        manager = new VatManager(mockVatReader(mockedCountries));
    }

    @Test
    public void testRefresh() {
        List<Country> countries = manager.getCountriesSortedByStandardVat(SortOrder.DESC);

        assertThat(countries.size(), is(equalTo(3)));
        assertThat(countries.get(0), is(equalTo(createKongoCountry())));
        assertThat(countries.get(1), is(equalTo(createTurkmenistanCountry())));
        assertThat(countries.get(2), is(equalTo(createBangladeshCountry())));

        // add one country
        mockedCountries.add(createVietnamCountry());

        // refresh the manager
        manager.refresh();

        countries = manager.getCountriesSortedByStandardVat(SortOrder.DESC);

        assertThat(countries.size(), is(equalTo(4)));
        assertThat(countries.get(0), is(equalTo(createVietnamCountry())));
        assertThat(countries.get(1), is(equalTo(createKongoCountry())));
        assertThat(countries.get(2), is(equalTo(createTurkmenistanCountry())));
        assertThat(countries.get(3), is(equalTo(createBangladeshCountry())));
    }

    @Test
    public void testGetOrderedCountries() {
        List<Country> countries = manager.getCountriesSortedByStandardVat(SortOrder.DESC);

        assertThat(countries.size(), is(equalTo(3)));
        assertThat(countries.get(0), is(equalTo(createKongoCountry())));
        assertThat(countries.get(1), is(equalTo(createTurkmenistanCountry())));
        assertThat(countries.get(2), is(equalTo(createBangladeshCountry())));

        countries = manager.getCountriesSortedByStandardVat(SortOrder.ASC);
        assertThat(countries.size(), is(equalTo(3)));
        assertThat(countries.get(0), is(equalTo(createBangladeshCountry())));
        assertThat(countries.get(1), is(equalTo(createTurkmenistanCountry())));
        assertThat(countries.get(2), is(equalTo(createKongoCountry())));
    }

    @Test
    public void testNCountriesWithHighestVat_UpperBound() {
        List<Country> countries = manager.getNCountriesWithHighestVat(5);

        // return all it can
        assertThat(countries.size(), is(equalTo(3)));
        assertThat(countries.get(0), is(equalTo(createKongoCountry())));
        assertThat(countries.get(1), is(equalTo(createTurkmenistanCountry())));
        assertThat(countries.get(2), is(equalTo(createBangladeshCountry())));
    }

    @Test
    public void testNCountriesWithHighestVat_LowerBound() {
        List<Country> countries = manager.getNCountriesWithHighestVat(1);

        // return only required amount
        assertThat(countries.size(), is(equalTo(1)));
        assertThat(countries.get(0), is(equalTo(createKongoCountry())));
    }

    private VatReader mockVatReader(List<Country> countries) throws VatReadingException {
        VatReader mock = mock(VatReader.class);

        when(mock.readVatRates()).thenReturn(countries);

        return mock;
    }

    private Country createTurkmenistanCountry() {
        List<VatPeriod> periods = asList(
                vatPeriod(parse("2017-01-01"), rates("13.0")),
                vatPeriod(parse("2015-01-01"), rates("10.3")),
                vatPeriod(parse("2016-01-01"), rates("11.0")));

        return new Country("Turkmenistan", "TKMNS", "TKMNS", periods);
    }

    private Country createBangladeshCountry() {
        VatPeriod period = vatPeriod(parse("2010-01-01"), rates("11.0"));

        return new Country("Bangladesh", "BGDSH", "BGDSH", singletonList(period));
    }

    private Country createKongoCountry() {
        VatPeriod period = vatPeriod(parse("1990-01-01"), rates("48.0"));

        return new Country("Kongo", "KNG", "KNG", singletonList(period));
    }

    private Country createVietnamCountry() {
        VatPeriod period = vatPeriod(parse("1995-01-01"), rates("49.0"));

        return new Country("Vietnam", "VNM", "VNM", singletonList(period));
    }

}