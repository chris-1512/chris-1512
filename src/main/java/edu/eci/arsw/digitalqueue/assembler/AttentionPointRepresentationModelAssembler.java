package edu.eci.arsw.digitalqueue.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import edu.eci.arsw.digitalqueue.controller.AttentionPointController;
import edu.eci.arsw.digitalqueue.model.AttentionPoint;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * AttentionPointAssembler
 */
@Component
public class AttentionPointRepresentationModelAssembler implements RepresentationModelAssembler<AttentionPoint, EntityModel<AttentionPoint>> {

    @Override
    public EntityModel<AttentionPoint> toModel(AttentionPoint attentionPoint) {
        return new EntityModel<AttentionPoint>(attentionPoint,
                linkTo(AttentionPointController.class).slash(attentionPoint.getId()).withSelfRel(),
                linkTo(AttentionPointController.class).withRel("attentionPoints"));
    }

}