package com.web.greet.camel;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.web.greet.config.XSDSchemaProperties;

/**
 * 
 * @author 91884
 *
 */
@Component
public class SoapServerCamelRoute extends RouteBuilder {

	@Autowired
	SoapServerCamelProcessor processor;

	@Autowired
	XSDSchemaProperties schemaProperties;


	/**
	 * receives a soap request through its root root qualified name XML request
	 * converted to a java object through unmarshal method Processor processes the
	 * response from request and returns back to the caller
	 */
	@Override
	public void configure() throws Exception {
		JaxbDataFormat jaxb = new JaxbDataFormat(false);
		jaxb.setContextPath(schemaProperties.getJaxbGeneratedSourcePackageName());
		from(String.format("spring-ws:rootqname:{%s}HelloSoapRequest?endpointMapping=endpointMapping",
				schemaProperties.getNamespace()))
		.unmarshal(jaxb)
		.process(processor);

	}

}
