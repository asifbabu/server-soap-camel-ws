package com.web.greet.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@CamelSpringBootTest
@ContextConfiguration
class SoapServerCamelRouteTest {

	@Autowired
	private ProducerTemplate template;

	@Autowired
	private CamelContext camelContext;

	@EndpointInject("mock:result")
	private MockEndpoint mockResult;

	@Test
	void whenSendBody_thenGreetingReceivedSuccessfully() throws Exception {
		String requestBody = "<hel:HelloSoapRequest xmlns:hel=\"http://flydubai.com/HelloSoap\">\r\n"
				+ "         <hel:clientName>Client Name</hel:clientName>\r\n" + "      </hel:HelloSoapRequest>\r\n"
				+ "";
		AdviceWith.adviceWith(camelContext, "soapServerRoute", false, a -> {
			a.replaceFromWith("direct:start");
			a.weaveByToString(".*log.*").before().to("mock:result");
			});
		MockEndpoint.expectsMessageCount(1, mockResult);
		template.sendBody("direct:start", requestBody);
		mockResult.assertIsSatisfied();

	}

}
