package pl.kulbat.monitorowaniejednostekmorskich.model.map;

@Entity
@AllArgsConstructor
public class ShipEntity extends BaseEntity{

    private Long mmsi;
    private LocalDate timeStamp;
    private String name;
    private String imo;
    private String country;

    private DestinationEntity destination;
    private ShipType shipType;

}