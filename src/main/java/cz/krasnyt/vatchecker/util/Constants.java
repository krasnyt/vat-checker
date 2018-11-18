package cz.krasnyt.vatchecker.util;

/**
 * Class with constants.
 *
 * Normally these constants/properties would go to a typical property file used by Spring
 * but for simplicity and proportion reasons it's done like this.
 */
public class Constants {

    /**
     * Number of countries that the application will print for lowest and highest standard VAT rate.
     */
    public static final int NR_COUNTRIES = 3;

    /**
     * URL of remote source with VAT rates in JSON format.
     */
    public static final String VAT_URL = "http://jsonvat.com";

}