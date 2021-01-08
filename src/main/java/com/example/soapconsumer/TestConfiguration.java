package com.example.soapconsumer;

import org.apache.http.Header;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.RequestDefaultHeaders;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Configuration
public class TestConfiguration {

    @Value("${client.user.name}")
    String user;

    @Value("${client.user.pw}")
    String pw;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath("com.example.consumingwebservice.wsdl");
        return marshaller;
    }

    @Bean
    public Test test(Jaxb2Marshaller marshaller) {
        Test client = new Test();
        client.setDefaultUri("http://localhost:8080/service");
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender(createHttpClient());
        client.setMessageSender(messageSender);
        return client;
    }

    @Bean
    HttpClient createHttpClient() {
        List<Header> headers = new ArrayList<>();
        BasicHeader authHeader = new BasicHeader("Authorization", "Basic " + base64authUserPassword(user,pw));
        headers.add(authHeader);
        // add more header as more as needed

        RequestDefaultHeaders reqHeader = new RequestDefaultHeaders(headers);

        CloseableHttpClient httpClient =
                HttpClients.custom()
                        .addInterceptorFirst(new HttpComponentsMessageSender.RemoveSoapHeadersInterceptor())
                        .addInterceptorLast(reqHeader)
                        .build();
        return httpClient;
    }

    private String base64authUserPassword(String username, String password) {
        String userpassword = username + ":" + password;
        String encodedAuthorization = new String(Base64.getEncoder().encode(userpassword.getBytes()));
        return encodedAuthorization;
    }
}
