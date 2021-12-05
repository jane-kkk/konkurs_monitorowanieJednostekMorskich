package pl.kulbat.monitorowaniejednostekmorskich.service.repository;

@Repository
public interface DestinationEntityRepository extends CrudRepository<DestinationEntity, UUID> {

    findAllShipsByCountry(String country);

}