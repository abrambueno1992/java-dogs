package com.abrahambueno.javadogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/dogs")
public class DogsController {
    private final DogRepository dogrepos;
    private final DogResourceAssembler assembler;
    public DogsController(DogRepository dogsrepos, DogResourceAssembler assembler) {
        this.dogrepos = dogsrepos;
        this.assembler = assembler;
    }

    // Post -> Create
    // Get -> Read
    // Put -> Update / Replace
    // Patch -> Update / Modify (collections)
    // Delete -> Delete

    // RequestMapping(method = RequestMethod.GET)
    @GetMapping("/breeds")
    public Resources<Resource<Dogs>> all() {
        List<Resource<Dogs>> dogs = dogrepos.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).all()).withSelfRel());
    }

//    @GetMapping("/weight")

//    @GetMapping("/breeds")

//    @GetMapping("/apartment")
}
