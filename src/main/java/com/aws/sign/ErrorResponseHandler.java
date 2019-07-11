package com.aws.sign;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.http.HttpResponseHandler;

public class ErrorResponseHandler implements HttpResponseHandler<AmazonServiceException> {

    /**
     * See {@link HttpResponseHandler}, method needsConnectionLeftOpen()
     */
    private boolean needsConnectionLeftOpen;

    /**
     * Ctor.
     * @param connectionLeftOpen Should the connection be closed immediately or not?
     */
    public ErrorResponseHandler(boolean connectionLeftOpen) {
        this.needsConnectionLeftOpen = connectionLeftOpen;
    }

    public AmazonServiceException handle(HttpResponse response) {
        AmazonServiceException ase = new AmazonServiceException(response.getStatusText());
        ase.setStatusCode(response.getStatusCode());
        ase.setServiceName("es");
        return ase;
    }

    public boolean needsConnectionLeftOpen() {
        return this.needsConnectionLeftOpen;
    }

}