package edu.eci.arsw.digitalqueue.controller;

import edu.eci.arsw.digitalqueue.assembler.AttentionPointRepresentationModelAssembler;
import edu.eci.arsw.digitalqueue.exception.AttentionPointNotFoundException;
import edu.eci.arsw.digitalqueue.exception.ServiceNotFoundException;
import edu.eci.arsw.digitalqueue.model.AttentionPoint;
import edu.eci.arsw.digitalqueue.model.Service;
import edu.eci.arsw.digitalqueue.repository.AttentionPointRepository;
import edu.eci.arsw.digitalqueue.repository.ServiceRepository;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

/**
 * AttentionPointController
 */
@RestController
@RequestMapping(value = "attentionPoints", produces = "application/json")
public class AttentionPointController {

    @Autowired
    private AttentionPointRepository attentionPointRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private AttentionPointRepresentationModelAssembler attentionPointRepresentationModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<AttentionPoint>> all(@RequestParam(required = false) String serviceName) {
        if (serviceName != null) {
            Service service = serviceRepository.findByName(serviceName).orElseThrow(() -> new ServiceNotFoundException(serviceName));
            List<EntityModel<AttentionPoint>> attentionPoints = attentionPointRepository.findByService(service).stream()
                    .map(attentionPointRepresentationModelAssembler::toModel).collect(Collectors.toList());

            return new CollectionModel<>(attentionPoints, linkTo(AttentionPointController.class).withSelfRel());
        }

        List<EntityModel<AttentionPoint>> attentionPoints = attentionPointRepository.findAll().stream()
                .map(attentionPointRepresentationModelAssembler::toModel).collect(Collectors.toList());

        return new CollectionModel<>(attentionPoints, linkTo(AttentionPointController.class).withSelfRel());
    }

    @PostMapping
    private ResponseEntity<?> add(@RequestBody AttentionPoint newAttentionPoint) throws URISyntaxException {
        EntityModel<AttentionPoint> entityModel = attentionPointRepresentationModelAssembler
                .toModel(attentionPointRepository.save(newAttentionPoint));

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref()))
                .body(entityModel);
    }

    @GetMapping("/{id}")
    public EntityModel<AttentionPoint> one(@PathVariable Long id) {
        AttentionPoint attentionPoint = attentionPointRepository.findById(id)
                .orElseThrow(() -> new AttentionPointNotFoundException(id));

        return attentionPointRepresentationModelAssembler.toModel(attentionPoint);
    }

    @PutMapping("/{id}")
    private ResponseEntity<EntityModel<AttentionPoint>> update(@PathVariable Long id,
            @RequestBody AttentionPoint newAttentionPoint) throws URISyntaxException {
        AttentionPoint updatedAttentionPoint = attentionPointRepository.findById(id).map(attentionPoint -> {
            attentionPoint.setCode(newAttentionPoint.getCode());
            attentionPoint.setUser(newAttentionPoint.getUser());
            attentionPoint.setEnable(newAttentionPoint.getEnable());
            return attentionPointRepository.save(attentionPoint);
        }).orElseGet(() -> {
            newAttentionPoint.setId(id);
            return attentionPointRepository.save(newAttentionPoint);
        });

        EntityModel<AttentionPoint> entityModel = attentionPointRepresentationModelAssembler
                .toModel(updatedAttentionPoint);

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref()))
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Object> delete(@PathVariable Long id) {
        attentionPointRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}