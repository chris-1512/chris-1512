package edu.eci.arsw.digitalqueue.assembler;

import edu.eci.arsw.digitalqueue.controller.TurnController;
import edu.eci.arsw.digitalqueue.model.Turn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TurnRepresentationModelAssembler  implements RepresentationModelAssembler<Turn, EntityModel<Turn>> {

    @Override
    public EntityModel<Turn> toModel(Turn turn) {
        return new EntityModel<Turn>(turn,
                linkTo(TurnController.class).slash(turn.getCode()).withSelfRel(),
                linkTo(TurnController.class).withRel("turns"));
    }
}

