package VTTP2022.NUSISS.March24Currency.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP2022.NUSISS.March24Currency.models.Country;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyService {
    
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private static final String URL = "https://free.currconv.com/api/v7/%s";

    //SET FREE_CURR_CONV = your api key
    @Value("${free.curr.conv}")
    private String apiKey;

    private boolean hasKey;
    private List<Country> currencyAll;
    

    @PostConstruct
    private void init(){
        hasKey = null != apiKey;
        logger.info(">>>>>>API KEY SET: ".formatted(hasKey,apiKey));
        currencyAll = getAllCurrencies();
    }

    public List<Country> getAllCurrencies(){
        String currencyUrl = UriComponentsBuilder
        .fromUriString(URL.formatted("countries/"))
        .queryParam("apiKey", apiKey)
        .toUriString();


        RequestEntity<Void> req = RequestEntity
        .get(currencyUrl)
        .accept(MediaType.APPLICATION_JSON)
        .build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = null;

        List<Country> currencyList = new LinkedList<>();
        try {
            resp = template.exchange(req,String.class);
            currencyList = Country.create(resp.getBody());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return currencyList;



    }

    public Double convertCurrency(Integer amount, String fromCurrency, String toCurrency) {
        
        String convertUrl = UriComponentsBuilder
        .fromUriString(URL.formatted("convert/"))
        .queryParam("q",fromCurrency+"_"+toCurrency)
        .queryParam("compact", "ultra")
        .queryParam("apiKey",apiKey)
        .toUriString();


        RequestEntity<Void> req = RequestEntity
        .get(convertUrl)
        .accept(MediaType.APPLICATION_JSON)
        .build();
        
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;
        resp = template.exchange(req, String.class);

        double convertedAmt =0;

        try {
            convertedAmt = Country.convert(resp.getBody());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return convertedAmt;

    }

}
