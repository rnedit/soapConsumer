package com.example.soapconsumer;

import com.example.consumingwebservice.wsdl.StudentDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    Test test;

    @RequestMapping(value = "/soap", method = RequestMethod.GET)
    public void testeSoap() {
        StudentDetailsResponse response =  test.getStudent("asds");
        System.out.println(response.getStudent().getName());
    }
}
