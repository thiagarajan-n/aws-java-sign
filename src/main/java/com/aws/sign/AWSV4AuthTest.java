package com.aws.sign;

import java.util.Map;
import java.util.TreeMap;


public class AWSV4AuthTest {
    public static void main(String[] args) {

        String url = "search-my-domain.us-east-1.es.amazonaws.com/accounts/account/xxxxx";
        String host="search-my-domain.us-east-1.es.amazonaws.com";
 

        /**
         * Add host without http or https protocol.
         * You can also add other parameters based on your amazon service requirement.
         */
        TreeMap<String, String> awsHeaders = new TreeMap<String, String>();
        awsHeaders.put("host", host);
        awsHeaders.put("content-type", "application/json");

        
        AWSV4Signature aWSV4Auth = new AWSV4Signature.Builder("ACCESS_KEY", "SECRET_KEY")
                                           .regionName("us-east-1")
                                           .serviceName("es") // es - elastic search. use your service name
                                           .httpMethodName("GET") //GET, PUT, POST, DELETE, etc...
                                           .canonicalURI("/accounts/account/xxxxx") //end point
                                           .queryParametes(null) //query parameters if any
                                           .awsHeaders(awsHeaders) //aws header parameters
                                           .payload(null) // payload if any
                                           .debug() // turn on the debug mode
                                           .build();
        
        /* Get header calculated for request */
        Map<String, String> header = aWSV4Auth.getHeaders();
        for (Map.Entry<String, String> entrySet : header.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            
            /* Attach header in your request */
            /* Simple get request */
//            \HttpGet httpGet = new HttpGet(url);
//            httpGet.addHeader(key, value);
        }
        // execute httpGet
    }
}

