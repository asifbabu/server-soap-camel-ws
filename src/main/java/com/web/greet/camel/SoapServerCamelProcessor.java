package com.web.greet.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.flydubai.hellosoap.HelloSoapRequest;
import com.flydubai.hellosoap.HelloSoapResponse;
import com.web.greet.config.GreetingProperties;
/**
 * 
 * @author 91884
 *
 */
@Component
public class SoapServerCamelProcessor implements Processor{
	
	@Autowired
	GreetingProperties greetingProperties;
	/**
	 * Receives the incoming request from client and transforms the request and returns
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		HelloSoapRequest bodyIn = (HelloSoapRequest) exchange.getIn().getBody();
		HelloSoapResponse response = new HelloSoapResponse();
		response.setResponse(greetingProperties.getMessage() + bodyIn.getClientName());
		exchange.getMessage().setBody(response);
	}

}
