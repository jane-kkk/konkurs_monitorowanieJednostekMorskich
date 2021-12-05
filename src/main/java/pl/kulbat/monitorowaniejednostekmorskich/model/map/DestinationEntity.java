package pl.kulbat.monitorowaniejednostekmorskich.model.map;

@Entity
@Table(name = "Destinations")
@AllArgsConstructor
public class DestinationEntity extends BaseEntity{

        private String name;
        private String country;
        private String countryCode;
        private String continent;

        @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER)
        private ShipEntity ship;

}