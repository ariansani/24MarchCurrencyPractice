package VTTP2022.NUSISS.March24Currency.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Country {
    private String currencyId;
    private String currencyName;
    private String country;
    private String alpha3;
    private String id;
    private String currencySymbol;




    
    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAlpha3() {
        return alpha3;
    }

    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    private static final Logger logger = LoggerFactory.getLogger(Country.class);

    public static List<Country> create(String json) throws IOException{

        List<Country> countryList = new LinkedList<>();
        try (InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            JsonObject allCountries = o.getJsonObject("results");

            for(String keyStr : allCountries.keySet()){
                Country country = new Country();
                JsonObject keyvalue = allCountries.getJsonObject(keyStr);
                country.alpha3 = keyvalue.getString("alpha3");
                country.country = keyvalue.getString("name");
                country.currencyId = keyvalue.getString("currencyId");
                country.currencyName = keyvalue.getString("currencyName");
                country.currencySymbol = keyvalue.getString("currencySymbol");
                country.id = keyvalue.getString("id");
                countryList.add(country);
            }

        } 
        return countryList;
    }

    public static Double convert(String json) {
        
        double convertedAmt = 0;
        try (InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            Collection<JsonValue> values = o.values();
            for(JsonValue value : values)
                convertedAmt = Double.parseDouble(value.toString());
            
        } catch (Exception e) {
            //TODO: handle exception
        }
        return convertedAmt;


    }


}
