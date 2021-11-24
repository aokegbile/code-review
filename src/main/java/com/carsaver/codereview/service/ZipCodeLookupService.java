package com.carsaver.codereview.service;

import org.springframework.stereotype.Service;

@Service
public class ZipCodeLookupService {

    /**
     * @param zipCode Supplied ZipCode
     * @return - returns city for the given zipCode.
     */
    public String lookupCityByZip(String zipCode) {
        System.out.print("looking up city by zipCode (this might take a while)");

        try {
            //simulating a high latency call
            Thread.sleep(5000);
        } catch(Exception ignore) {

        }
        //CODEREVIEW Should be returning city not zipcode. https://www.zipcodeapi.com/
        return "37067";
    }

}
