package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;
    
    @Test
    void deleteById() {
        Product expected = new Product("아이스아메리카노", 1500L, "asd.dsa");
        Product actual = productRepository.save(expected);

        productRepository.deleteById(actual.getId());
        assertAll(
                () -> assertThat(productRepository.findById(actual.getId())).isEmpty()
        );
    }

    @Test
    void findById() {
        Product expected = new Product("아이스아메리카노", 1500L, "asd.dsa");
        productRepository.save(expected);
        Long id = expected.getId();
        Product actual = productRepository.findById(id).get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }

    @Test
    void save() {
        Product expected = new Product("아이스아메리카노", 1500L, "asd.dsa");
        productRepository.save(expected);
        Long id = expected.getId();
        Product actual = productRepository.findById(id).get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
                () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
                () -> assertThat(actual.getImageUrl()).isEqualTo(expected.getImageUrl())
        );
    }
}