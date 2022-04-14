package edu.eci.arsw.digitalqueue.controller;

import edu.eci.arsw.digitalqueue.assembler.TurnRepresentationModelAssembler;
import edu.eci.arsw.digitalqueue.exception.NoTurnsInServiceException;
import edu.eci.arsw.digitalqueue.exception.ServiceNotFoundException;
import edu.eci.arsw.digitalqueue.exception.TurnAlreadyCancelledException;
import edu.eci.arsw.digitalqueue.exception.TurnNotFoundException;
import edu.eci.arsw.digitalqueue.model.Service;
import edu.eci.arsw.digitalqueue.model.Turn;
import edu.eci.arsw.digitalqueue.repository.ServiceRepository;
import edu.eci.arsw.digitalqueue.repository.TurnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;



import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "turns", produces = "application/json")
public class TurnController {

    @Autowired
    private TurnRepository turnRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private TurnRepresentationModelAssembler turnRepresentationModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Turn>> all() {
        List<EntityModel<Turn>> turns = turnRepository.findAll().stream().map(turnRepresentationModelAssembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(turns, linkTo(TurnController.class).withSelfRel());
    }

    @PostMapping
    private ResponseEntity<?> add(@RequestBody Turn newTurn) throws URISyntaxException {
        EntityModel<Turn> entityModel = turnRepresentationModelAssembler.toModel(turnRepository.save(newTurn));

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref())).body(entityModel);
    }

    @GetMapping("/{code}")
    public EntityModel<Turn> one(@PathVariable String code) {
        Turn turn = turnRepository.findById(code).orElseThrow(() -> new TurnNotFoundException(code));

        return turnRepresentationModelAssembler.toModel(turn);
    }

    @GetMapping("/totalWaitingByQueue")
    public int totalWaitingByQueue(@RequestParam String service) {
        return turnRepository.findByServiceAndAttendedFalseOrderByRequestedDateTimeDesc(
                serviceRepository.findByName(service).orElseThrow(() -> new ServiceNotFoundException(service))
        ).size();
    }

    @GetMapping("/countTurns")
    public int countTurnsByQueue(@RequestParam String service) {
        List<Turn> turns = turnRepository.findByService(
            serviceRepository.findByName(
                service).orElseThrow(() -> new ServiceNotFoundException(service)));
        return turns.size();
    }


    @GetMapping("/totalWaiting")
    public long countTotalTurnsWaiting() {
        List<Service> services = serviceRepository.findAll();
        long totalTurns = 0;
        for(int i = 0; i < services.size(); i++){
            Service service = services.get(i);
            System.out.println(service.getName());
            totalTurns += totalWaitingByQueue(service.getName());
        }
        System.out.println(totalTurns);
        return totalTurns;
    }

    @GetMapping("/totalTickets")
    public long countTotalTurns() {
        List<Turn> turns = turnRepository.findAll();
        long totalTurns = turns.size();
        return totalTurns;
    }



    @GetMapping("/next")
    public EntityModel<Turn> nextInQueue(@RequestParam String service) {
        Turn turn = turnRepository.findFirstByServiceAndAttendedFalseOrderByRequestedDateTimeAsc(
                serviceRepository.findByName(service).orElseThrow(() -> new ServiceNotFoundException(service))
        ).orElseThrow(() -> new NoTurnsInServiceException(service));
        System.out.println(turn.getCode() + " " + turn.getAttended());
        turn.setAttended(true);
        turn.setAttendedDateTime(new Timestamp(System.currentTimeMillis()));
        turnRepository.save(turn);

        return turnRepresentationModelAssembler.toModel(turn);
    }


    @PutMapping("/{code}")
    private ResponseEntity<EntityModel<Turn>> update(@PathVariable String code, @RequestBody Turn newTurn)
            throws URISyntaxException {
        Turn updatedTurn = turnRepository.findById(code).map(turn -> {
            turn.setAttended(newTurn.getAttended());
            turn.setAttendedDateTime(newTurn.getAttendedDateTime());
            turn.setAttentionPoint(newTurn.getAttentionPoint());
            turn.setCancelled(newTurn.getCancelled());
            turn.setClientName(newTurn.getClientName());
            turn.setService(newTurn.getService());
            turn.setRequestedDateTime(newTurn.getRequestedDateTime());
            return turnRepository.save(turn);
        }).orElseGet(() -> {
            newTurn.setCode(code);
            return turnRepository.save(newTurn);
        });

        EntityModel<Turn> entityModel = turnRepresentationModelAssembler.toModel(updatedTurn);

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref())).body(entityModel);
    }

    @PutMapping("/complete/{code}")
    private ResponseEntity<EntityModel<Turn>> complete(@PathVariable String code) throws URISyntaxException {
        Turn updatedTurn = turnRepository.findById(code).orElseThrow(() -> new TurnNotFoundException(code));
        EntityModel<Turn> entityModel = turnRepresentationModelAssembler.toModel(updatedTurn);
        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref())).body(entityModel);

    }


    @PutMapping("/cancel/{code}")
    private ResponseEntity<EntityModel<Turn>> cancel(@PathVariable String code) throws URISyntaxException {
        Turn updatedTurn = turnRepository.findById(code).orElseThrow(() -> new TurnNotFoundException(code));
        System.out.println(code);
        if (updatedTurn.getCancelled()) {
            throw new TurnAlreadyCancelledException(code);
        }
        updatedTurn.setCancelled(true);
        turnRepository.save(updatedTurn);

        EntityModel<Turn> entityModel = turnRepresentationModelAssembler.toModel(updatedTurn);

        return ResponseEntity.created(new URI(entityModel.getRequiredLink("self").expand().getHref())).body(entityModel);
    }

}
