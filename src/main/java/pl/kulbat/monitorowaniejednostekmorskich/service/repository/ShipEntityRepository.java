package pl.kulbat.monitorowaniejednostekmorskich.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipEntity;

import java.util.List;
import java.util.UUID;

@Repository
public interface ShipEntityRepository extends JpaRepository<ShipEntity, UUID> {

    @Query("SELECT s FROM ShipEntity s JOIN DestinationEntity d ON s.destination = d.uuid WHERE d.country = ?1")
    List<ShipEntity> findAllByDestinationCountry(String country);

}