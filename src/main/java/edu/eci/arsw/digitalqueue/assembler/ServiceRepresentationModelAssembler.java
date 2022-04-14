package edu.eci.arsw.digitalqueue.assembler;

import edu.eci.arsw.digitalqueue.controller.ServiceController;
import edu.eci.arsw.digitalqueue.model.Service;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class ServiceRepresentationModelAssembler implements RepresentationModelAssembler<Service, EntityModel<Service>> {

    @Override
    public EntityModel<Service> toModel(Service service) {
        return new EntityModel<>(service,
                linkTo(ServiceController.class).slash(service.getId()).withSelfRel(),
                linkTo(ServiceController.class).withRel("queues"));
    }
}
