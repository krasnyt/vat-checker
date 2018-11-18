package cz.krasnyt.vatchecker.domain;

/**
 * Exception that is thrown when a specific {@link cz.krasnyt.vatchecker.data.VatReader} fails
 * to fetch data. The original cause is then wrapped by this exception.
 */
public class VatReadingException extends Exception {

    /**
     * Creates new exception with the specific cause.
     *
     * @param cause specific cause
     */
    public VatReadingException(Throwable cause) {
        super(cause);
    }

}