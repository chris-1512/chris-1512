package edu.eci.arsw.digitalqueue.repository;

import edu.eci.arsw.digitalqueue.model.Service;
import edu.eci.arsw.digitalqueue.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TurnRepository extends JpaRepository<Turn, String> {

    Optional<Turn> findFirstByServiceAndAttendedFalseOrderByRequestedDateTimeDesc(Service service);

    Optional<Turn> findFirstByServiceAndAttendedFalseOrderByRequestedDateTimeAsc(Service service);

    List<Turn> findByServiceAndAttendedFalseOrderByRequestedDateTimeDesc(Service service);

	List<Turn> findByService(Service service);
}
