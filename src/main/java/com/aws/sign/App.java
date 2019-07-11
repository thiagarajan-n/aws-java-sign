package com.aws.sign;

import java.io.IOException;
import java.net.URI;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.DefaultRequest;
import com.amazonaws.Request;
import com.amazonaws.Response;
import com.amazonaws.auth.AWS4Signer;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.http.AmazonHttpClient;
import com.amazonaws.http.ExecutionContext;
import com.amazonaws.http.HttpMethodName;
import com.amazonaws.http.HttpResponse;
import com.amazonaws.util.IOUtils;


/**
 * Make get call to AWS using SDK
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	String url = "https://search-MY_ES_DOMAIN.<REGION>.es.amazonaws.com/accounts/account/xxxxx";
    	//Instantiate the request
    	Request<Void> request = new DefaultRequest<Void>("es"); //Request to ElasticSearch
    	request.setHttpMethod(HttpMethodName.GET);
    	request.setEndpoint(URI.create(url));

    	//Sign it...
    	AWS4Signer signer = new AWS4Signer();
    	signer.setRegionName("us-east-1");
    	signer.setServiceName(request.getServiceName());
    	AWSCredentials awsCredentials=new BasicAWSCredentials("ACCESS_KEY", "SECRET_KEY");
    	signer.sign(request, awsCredentials);
    	
    	
    	HttpResponseHandlers responseHandler = new HttpResponseHandlers(true);
    	//Execute it and get the response...
    	Response<HttpResponse> rsp = new AmazonHttpClient(new ClientConfiguration())
    	    .requestExecutionBuilder()
    	    .executionContext(new ExecutionContext(true))
    	    .request(request)
    	    .errorResponseHandler(new ErrorResponseHandler(false))
    	    .execute(responseHandler);
    	    
    	
    	;
    	System.out.println( "Response from http call" + IOUtils.toString(rsp.getHttpResponse().getContent()));
    }
}

