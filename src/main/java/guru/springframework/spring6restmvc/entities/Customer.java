package guru.springframework.spring6restmvc.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator // implementation of the @IdGeneratorType
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    @JdbcTypeCode(SqlTypes.CHAR) // needed for MySQL - changes storing from binary to regular characters
    private UUID id;

    @Version
    private Integer version;

    private String name;

    @Column(length = 255)
    private String email;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // when Project Lombok generates code, it will go ahead and initialize that to the default of 'new HashSet<>()'
    @Builder.Default
    @OneToMany(mappedBy = "customer")
    private Set<BeerOrder> beerOrders = new HashSet<>();

}
