package cz.krasnyt.vatchecker.data;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import cz.krasnyt.vatchecker.domain.Country;
import cz.krasnyt.vatchecker.domain.VatReadingException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class HttpVatReaderTest {

    private VatReader vatReader;

    @Before
    public void setUp() {
        vatReader = new HttpVatReader();
    }

    @Test
    @Ignore("Requires internet")
    public void testReader_readVatRates() throws VatReadingException {
        List<Country> countries = vatReader.readVatRates();

        assertThat(countries.size(), not(is(equalTo(0))));
    }

}