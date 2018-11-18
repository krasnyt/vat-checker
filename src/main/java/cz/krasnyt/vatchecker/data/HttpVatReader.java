package cz.krasnyt.vatchecker.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import cz.krasnyt.vatchecker.data.json.CustomJsonProvider;
import cz.krasnyt.vatchecker.domain.Country;
import cz.krasnyt.vatchecker.domain.VatRates;
import cz.krasnyt.vatchecker.domain.VatReadingException;
import cz.krasnyt.vatchecker.util.Constants;

/**
 * VAT Reader that fetches VAT rates from HTTP JSON source using Jaxrs Rest client.
 */
public class HttpVatReader implements VatReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpVatReader.class);

    /**
     * Reads VAT values from remote JSON source through HTTP rest call. JSON is then deserialized
     * into a list of countries and their VATs.
     *
     * @return list of countries and their VATs
     */
    @Override
    public List<Country> readVatRates() throws VatReadingException {
        long t1 = System.currentTimeMillis();

        List<Country> countries;
        try {
            Client client = ClientBuilder.newClient().register(new CustomJsonProvider());
            WebTarget path = client.target(Constants.VAT_URL);
            Invocation.Builder request = path.request(MediaType.APPLICATION_JSON_TYPE);

            countries = request.get(VatRates.class).getCountries();
        } catch (Exception e) {
            throw new VatReadingException(e);
        }

        long t2 = System.currentTimeMillis();

        LOGGER.info("Fetched countries={} in ms={}", countries.size(), (t2 - t1));

        return countries;
    }

}