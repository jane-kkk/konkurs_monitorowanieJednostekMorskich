package pl.kulbat.monitorowaniejednostekmorskich.model.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kulbat.monitorowaniejednostekmorskich.model.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Destinations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class DestinationEntity extends BaseEntity {

    private String name;
    private String country;
    private String countryCode;
    private String continent;
    private Double latitude;
    private Double longitude;
    @OneToMany(mappedBy = "destination", fetch = FetchType.EAGER)
    private List<ShipEntity> ship;

}