package pl.kulbat.monitorowaniejednostekmorskich.model.map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.kulbat.monitorowaniejednostekmorskich.model.BaseEntity;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "Ships")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ShipEntity extends BaseEntity {

    private Integer mmsi;
    private ZonedDateTime timeStamp;
    private String name;
    private String imo;
    private String country;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "destination_id")
    private DestinationEntity destination;
    private ShipType shipType;

}