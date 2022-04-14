package edu.eci.arsw.digitalqueue.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.digitalqueue.assembler.UserRepresentationModelAssembler;
import edu.eci.arsw.digitalqueue.exception.UserNotFoundException;
import edu.eci.arsw.digitalqueue.model.User;
import edu.eci.arsw.digitalqueue.repository.UserRepository;

@RestController
@RequestMapping(value = "users", produces = "application/json")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRepresentationModelAssembler userRepresentationModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<User>> all() {
        List<EntityModel<User>> employees = userRepository.findAll().stream().map(userRepresentationModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(employees, linkTo(UserController.class).withSelfRel());
    }

    @PostMapping
    private ResponseEntity<?> add(@RequestBody User newEmployee) throws URISyntaxException {
        EntityModel<User> entityModel = userRepresentationModelAssembler.toModel(userRepository.save(newEmployee));

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref())).body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));

        return userRepresentationModelAssembler.toModel(user);
    }

    @PutMapping("/{id}")
    private ResponseEntity<EntityModel<User>> update(@PathVariable Long id, @RequestBody User newUser)
            throws URISyntaxException {
        User updatedEmployee = userRepository.findById(id).map(employee -> {
            employee.setName(newUser.getName());
            employee.setEmail(newUser.getEmail());
            return userRepository.save(employee);
        }).orElseGet(() -> {
            newUser.setId(id);
            return userRepository.save(newUser);
        });

        EntityModel<User> entityModel = userRepresentationModelAssembler.toModel(updatedEmployee);

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref())).body(entityModel);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> delete(@PathVariable Long id) {
        userRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
