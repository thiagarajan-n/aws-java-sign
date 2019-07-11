package com.aws.sign;

import java.io.IOException;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;
import com.amazonaws.util.IOUtils;


public class HttpResponseHandlers implements
HttpResponseHandler<HttpResponse> {

/**
 * See {@link HttpResponseHandler}, method needsConnectionLeftOpen()
 */
private boolean needsConnectionLeftOpen;

/**
 * Ctor.
 * @param connectionLeftOpen Should the connection be closed immediately or not?
 */
public HttpResponseHandlers(boolean connectionLeftOpen) {
    this.needsConnectionLeftOpen = connectionLeftOpen;
}

public HttpResponse handle(HttpResponse response) {

    int status = response.getStatusCode();
    if(status < 200 || status >= 300) {
        String content;
        try {
            IOUtils.toString(response.getContent());
            content = IOUtils.toString(response.getContent());
        } catch (final IOException e) {
        	content = "Couldn't get response content!";
        }
        AmazonServiceException ase = new AmazonServiceException(content);
        ase.setStatusCode(status);
        throw ase;
    }

    return response;
    
}

public boolean needsConnectionLeftOpen() {
    return this.needsConnectionLeftOpen;
}

}