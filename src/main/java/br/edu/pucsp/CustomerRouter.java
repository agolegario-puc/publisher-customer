package br.edu.pucsp;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class CustomerRouter extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from("file:src/data/input?noop=true")
                .split().jsonpath("[*]")
                .choice()
                .when().jsonpath("[?(@.region=='São Paulo')]")
                .to("activemq:queue:customers-integration-sp")
                .when()
                .jsonpath("[?(@.region=='Rio de Janeiro')]")
                .to("activemq:queue:customers-integration-rj")
                .otherwise()
                .to("activemq:queue:customers-integration-all"
                ).log("${body}");
                //.to("file:src/data/output")
    }
}
