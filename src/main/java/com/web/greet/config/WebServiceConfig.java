package com.web.greet.config;

import org.apache.camel.component.spring.ws.bean.CamelEndpointMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.MessageEndpointAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	@Autowired
	XSDSchemaProperties xsdProperties;
	
	/**
	 * 
	 * @param applicationContext
	 * @return ServletRegistrationBean
	 */
	@Bean
	ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean<>(servlet, String.format("/%s/*", xsdProperties.getLocationUri()));
	}
	/**
	 * 
	 * @param XsdSchema greetSchema
	 * @returns DefaultWsdl11Definition defaultWsdl11Definition bean with configuration details like 
	 * location URI, XSD schema, target namespace and port type name 
	 */
	@Bean(name = "greet")
	DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema greetSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName(xsdProperties.getServiceName());
		wsdl11Definition.setLocationUri(String.format("/%s", xsdProperties.getLocationUri()));
		wsdl11Definition.setTargetNamespace(xsdProperties.getNamespace());
		wsdl11Definition.setSchema(greetSchema);
		return wsdl11Definition;
	}
	/**
	 * creates an XSD Schema from the XSD classpath resource file
	 * @return XsdSchema
	 */
	@Bean
	XsdSchema greetSchema() {
		return new SimpleXsdSchema(new ClassPathResource(xsdProperties.getSchemaFileName()));
	}
	/**
	 * 
	 * @return camelEndpointMapping this is required for the camel route to receive SOAP request
	 */
	@Bean(name = "endpointMapping")
	CamelEndpointMapping endpointMapping() {
		return new CamelEndpointMapping();
	}
	/**
	 * 
	 * @return messageEndpointAdapter
	 */
	@Bean()
	EndpointAdapter messageEndpointAdapter() {
		return new MessageEndpointAdapter();
	}

}