package pl.kulbat.monitorowaniejednostekmorskich.model;

@MappedSuperclass
@EqualsAndHashCode(of = "uuid")
public abstract class BaseEntity {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private UUID uuid = UUID.randomUUID();

    @Version
    private Integer version;

    private LocalDateTime creationDate;
    // getter, setter
}