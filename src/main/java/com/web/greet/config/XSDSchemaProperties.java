package com.web.greet.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "xsd.greet")
public class XSDSchemaProperties {
	private String namespace;
	private String locationUri;
	private String serviceName;
	private String schemaFileName;
	private String jaxbGeneratedSourcePackageName;

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getLocationUri() {
		return locationUri;
	}

	public void setLocationUri(String locationUri) {
		this.locationUri = locationUri;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getSchemaFileName() {
		return schemaFileName;
	}

	public void setSchemaFileName(String schemaFileName) {
		this.schemaFileName = schemaFileName;
	}

	public String getJaxbGeneratedSourcePackageName() {
		return jaxbGeneratedSourcePackageName;
	}

	public void setJaxbGeneratedSourcePackageName(String jaxbGeneratedSourcePackageName) {
		this.jaxbGeneratedSourcePackageName = jaxbGeneratedSourcePackageName;
	}

}
