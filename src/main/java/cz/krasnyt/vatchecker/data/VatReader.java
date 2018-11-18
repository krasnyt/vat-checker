package cz.krasnyt.vatchecker.data;

import java.util.List;

import cz.krasnyt.vatchecker.domain.Country;
import cz.krasnyt.vatchecker.domain.VatReadingException;

/**
 * Base interface for all vat readers
 */
public interface VatReader {

    /**
     * Reads VAT values from individual sources.
     *
     * @return List of countries and their VATs
     * @throws VatReadingException if the reading failed (for any reason). The original cause
     * is wrapped by this exception.
     */
    List<Country> readVatRates() throws VatReadingException;

}