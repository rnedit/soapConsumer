package com.example.soapconsumer;

import com.example.consumingwebservice.wsdl.StudentDetailsRequest;
import com.example.consumingwebservice.wsdl.StudentDetailsResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class Test extends WebServiceGatewaySupport {

    public StudentDetailsResponse getStudent(String name) {

        StudentDetailsRequest request = new StudentDetailsRequest();
        request.setName(name);

        StudentDetailsResponse response = (StudentDetailsResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/service/student-details", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/StudentDetailsRequest"));

        return response;
    }
}
