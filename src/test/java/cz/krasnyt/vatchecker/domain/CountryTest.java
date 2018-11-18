package cz.krasnyt.vatchecker.domain;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static cz.krasnyt.vatchecker.util.VatUtil.rates;
import static cz.krasnyt.vatchecker.util.VatUtil.vatPeriod;
import static java.time.LocalDate.parse;
import static java.util.Arrays.asList;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

public class CountryTest {

    private Country country;

    @Before
    public void setUp() {
        country = new Country();
        country.setName("Bangladesh");
    }

    @Test(expected = MissingVatException.class)
    public void testCountry_getMostRecentStandardVat_NoVatPresent() {
        // should throw
        country.getMostRecentStandardVat();
    }

    @Test
    public void testCountry_getMostRecentStandardVat_NoVatPresent_CorrectExceptionMessage() {
        try {
            country.getMostRecentStandardVat();
        } catch (MissingVatException e) {
            assertThat(e.getMessage(), containsString("Bangladesh"));
            return;
        }
        fail("It should but it did not throw MissingVatException exception");
    }

    @Test
    public void testCountry_getMostRecentStandardVat() {
        country.setPeriods(asList(
                vatPeriod(parse("2017-01-01"), rates("13.0")),
                vatPeriod(parse("2015-01-01"), rates("10.3")),
                vatPeriod(parse("2016-01-01"), rates("11.0"))));

        assertThat(country.getMostRecentStandardVat(), is(equalTo(new BigDecimal("13.0"))));
    }

}