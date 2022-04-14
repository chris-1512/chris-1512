package edu.eci.arsw.digitalqueue.assembler;

import edu.eci.arsw.digitalqueue.model.User;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.eci.arsw.digitalqueue.controller.UserController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * EmployeeAssembler
 */
@Component
public class UserRepresentationModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    @Override
    public EntityModel<User> toModel(User user) {
        return new EntityModel<User>(user,
                linkTo(UserController.class).slash(user.getId()).withSelfRel(),
                linkTo(UserController.class).withRel("Employees"));
    }

}