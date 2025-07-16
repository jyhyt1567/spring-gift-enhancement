package gift.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import gift.entity.Member;
import gift.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void deleteById() {
        Member expected = new Member("asd@asd.asd", "dasdada", "user");
        Member actual = memberRepository.save(expected);

        memberRepository.deleteById(actual.getId());
        assertAll(
                () -> assertThat(memberRepository.findById(actual.getId())).isEmpty()
        );
    }

    @Test
    void findByEmail() {
        Member expected = new Member("asd@asd.asd", "dasdada", "user");
        memberRepository.save(expected);
        Long id = expected.getId();
        Member actual = memberRepository.findByEmail(expected.getEmail()).get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }

    @Test
    void save() {
        Member expected = new Member("asd@asd.asd", "dasdada", "user");
        memberRepository.save(expected);
        Long id = expected.getId();
        Member actual = memberRepository.findById(id).get();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getEmail()).isEqualTo(expected.getEmail()),
                () -> assertThat(actual.getPassword()).isEqualTo(expected.getPassword()),
                () -> assertThat(actual.getRole()).isEqualTo(expected.getRole())
        );
    }
}