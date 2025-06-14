package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerListByName() {
        Page<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%", null); // % signs are wildcards

        assertThat(list.getContent().size()).isEqualTo(336);
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(
                    Beer.builder()
                            .beerName("My Beer 0123456789 0123456789 0123456789 0123456789 0123456789")
                            .beerStyle(BeerStyle.PALE_ALE)
                            .upc("23423424234")
                            .price(new BigDecimal("11.99"))
                            .build()
            );

            // * sometimes, Hibernate will flush and batch, but the session is ending too quickly, so to actually
            // * see the error from the constraint validation, we need to explicitly flush the repository
            // ? .flush() tells Hibernate to immediately write to the database
            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(
                Beer.builder()
                        .beerName("My Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("23423424234")
                        .price(new BigDecimal("11.99"))
                        .build()
        );

        // * sometimes, Hibernate will flush and batch, but the session is ending too quickly, so to actually
        // * see the error from the constraint validation, we need to explicitly flush the repository
        // ? .flush() tells Hibernate to immediately write to the database
        beerRepository.flush();

        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}