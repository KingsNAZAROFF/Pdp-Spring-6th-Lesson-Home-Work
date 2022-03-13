package uz.pdp.pdpspring6thlessonhomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pdpspring6thlessonhomework.entity.Card;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<Card, UUID>{
    Optional<Card> findByCardNumber(Long cardNumber);
}
