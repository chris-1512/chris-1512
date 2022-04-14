package edu.eci.arsw.digitalqueue.repository;

import edu.eci.arsw.digitalqueue.model.AttentionPoint;
import edu.eci.arsw.digitalqueue.model.Service;
import edu.eci.arsw.digitalqueue.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttentionPointRepository extends JpaRepository<AttentionPoint, Long>{

    List<AttentionPoint> findByEnable(Boolean enable);

    List<AttentionPoint> findByService(Service service);

    AttentionPoint findByUser(User user);

}