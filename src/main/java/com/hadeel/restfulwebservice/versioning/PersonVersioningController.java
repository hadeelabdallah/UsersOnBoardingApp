package com.hadeel.restfulwebservice.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

    @GetMapping("/v1/person")
    public PersonV1 personV1(){
        return new PersonV1("Hadeel");
    }

    @GetMapping("/v2/person")
    public PersonV2 personV2(){
        Name name = new Name("Hadeel", "Abdallah");
        return new PersonV2(name);
    }

    @GetMapping(value = "/person/param", params = "version=1")
    public PersonV1 personParamV1(){
        return new PersonV1("Hadeel");
    }

    @GetMapping(value = "/person/param", params = "version=2")
    public PersonV2 personParamV2(){
        Name name = new Name("Hadeel", "Abdallah");
        return new PersonV2(name);
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 personHeaderV1(){
        return new PersonV1("Hadeel");
    }

    @GetMapping(value = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 personHeaderV2(){
        Name name = new Name("Hadeel", "Abdallah");
        return new PersonV2(name);
    }

    @GetMapping(value = "/person/produces", produces = "application/app-v1+json")
    public PersonV1 personProduceV1(){
        return new PersonV1("Hadeel");
    }

    @GetMapping(value = "/person/produces", produces = "application/app-v2+json")
    public PersonV2 personProduceV2(){
        Name name = new Name("Hadeel", "Abdallah");
        return new PersonV2(name);
    }
}
