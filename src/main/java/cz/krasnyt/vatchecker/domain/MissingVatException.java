package cz.krasnyt.vatchecker.domain;

/**
 * Exception that is thrown when a {@link Country} doesn't have any VAT.
 */
public class MissingVatException extends RuntimeException {

    public MissingVatException(Country country) {
        super("Country=" + country.getName() + " has no most recent VAT.");
    }

}