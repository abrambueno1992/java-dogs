package com.abrahambueno.javadogs;

//import jdk.jfr.internal.Repository;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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
    // post /dogs -> adds the dog
    @PostMapping("")
    public ResponseEntity<?> createDog(@RequestBody Dogs newDog) throws URISyntaxException {
        Dogs newEntry = dogrepos.save(new Dogs (newDog.getName(), newDog.getWeight(), newDog.isApartment()));
        Resource<Dogs> resource = assembler.toResource(newEntry);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


    // put /dogs/{id} -> adds or update if already present, with id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDog(@RequestBody Dogs newDog, @PathVariable Long id) throws URISyntaxException {
        Dogs updatedDog = dogrepos.findById(id)
                .map(dog -> {
                    dog.setName(newDog.getName());
                    dog.setWeight(newDog.getWeight());
                    dog.setApartment(newDog.isApartment());
                    return dogrepos.save(dog);
                })
                .orElseGet(() -> {
                    newDog.setId(id);
                    return dogrepos.save(newDog);
                });
        Resource<Dogs> resource = assembler.toResource(updatedDog);
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }
    // delete /dogs/{id} -> deletes the dogs with that id

}
