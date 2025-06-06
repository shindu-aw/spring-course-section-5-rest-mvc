package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BeerOrderShipment {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator // implementation of the @IdGeneratorType
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR) // needed for MySQL - changes storing from binary to regular characters
    private UUID id;

    @Version
    private Integer version;

    @OneToOne
    private BeerOrder beerOrder;

    private String trackingNumber;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

}
