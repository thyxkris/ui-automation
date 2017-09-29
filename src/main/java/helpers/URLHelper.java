package helpers;

import java.util.Calendar;

/**
 * Created by macked on 19/04/2017.
 */
public class URLHelper {
    private String url;
    // TODO: un-hardcode these
    private String brand = "flightcentre";      //hardcoded for now
    private String countrySuffix = ".com.au";   //hardcoded for now
    private String origin;
    private String destination;
    private String adults = "0";
    private String children = "0";
    private String infants = "0";
    private String cabinClass;

    private Calendar cal;
    private String departDate;
    private String returnDate;

    /**
    Environment setters to be used in project.properties: int-secure, int2-secure, int3-secure, stg-secure,
    pre-secure, secure
    **/

    public URLHelper() {
    }

    // Overload constructor
    public URLHelper(String[] cityPair, String[] paxBreakdown,
                     String cabinClass, String[] dates) {
        this.origin = cityPair[0];
        this.destination = cityPair[1];
        this.adults = paxBreakdown[0];
        this.cabinClass = cabinClass;
        this.departDate = dates[0];
        try {
            this.children = paxBreakdown[1];
            this.infants = paxBreakdown[2];
            this.returnDate = dates[1];
        } catch (ArrayIndexOutOfBoundsException e) {
        }

    }

    public String getUrl() {
        return url;
    }

    public String buildSearchURL() {

        return url;
    }

    //https://int-secure.flightcentre.com.au/search/SYD/BNE/20160216/20160220/1/0/0/ECONOMY
    public String buildDeeplinkURL() {
        // Build TLD (https://int-secure.flightcentre.com.au/search)
        url = "https://" + ConfigHelper.getString("execution.env") + "." + brand + countrySuffix + "/search/";

        //Add search parameters
        // TODO: handle return dates conditional
        //url = url + origin + "/" + destination + "/" + departDate + "/" + returnDate + "/";
        url = url + origin + "/" + destination + "/" + departDate + "/";
        url = url + adults + "/" + children + "/" + infants + "/" + cabinClass;

        return url;
    }


}
