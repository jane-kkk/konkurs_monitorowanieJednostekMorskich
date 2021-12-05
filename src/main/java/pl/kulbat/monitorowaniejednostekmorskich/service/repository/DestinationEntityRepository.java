package pl.kulbat.monitorowaniejednostekmorskich.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.DestinationEntity;

import java.util.UUID;

@Repository
public interface DestinationEntityRepository extends JpaRepository<DestinationEntity, UUID> {
}