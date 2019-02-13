package com.abrahambueno.javadogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public Resources<Resource<Dogs>> allBreeds() {
//        dogrepos = dogrepos.findAll().sort((e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
        List<Resource<Dogs>> dogs = dogrepos.findAll().stream().map(assembler::toResource).sorted((d11, d12) ->
                d11.getContent().getName().compareToIgnoreCase(d12.getContent().getName())).collect(Collectors.toList());
//        Collections.sort(dogs);

        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).allBreeds()).withSelfRel());
    }

    @GetMapping("/weight")
    public Resources<Resource<Dogs>> allWeight() {
        List<Resource<Dogs>> dogs = dogrepos.findAll().stream().map(assembler::toResource)
                .sorted((e1, e2) -> (int) e1.getContent().getWeight() - (int) e1.getContent().getWeight())
                .collect(Collectors.toList());
        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).allWeight()).withSelfRel());
    }
    @GetMapping("/apartments")
    public Resources<Resource<Dogs>> apartmentSuitable() {
        List<Resource<Dogs>> dogs = dogrepos.findAll().stream().filter(e -> (boolean) e.isApartment())
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).allBreeds()).withSelfRel());

    }

    @GetMapping("/breeds/{breed}")
    public Resources<Resource<Dogs>> byBreed(@PathVariable String breed){
        List<Resource<Dogs>> dogs = dogrepos.findAll().stream().filter(e -> e.getName().toLowerCase().equals(breed.toLowerCase()))
                .map(assembler::toResource).sorted((d11, d12) ->
                        d11.getContent().getName().compareToIgnoreCase(d12.getContent().getName())).collect(Collectors.toList());
        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).allBreeds()).withSelfRel());
    }

}
