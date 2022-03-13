package uz.pdp.pdpspring6thlessonhomework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.pdpspring6thlessonhomework.entity.BankomatHistory;
@Repository
public interface BankomatHistoryRepository extends JpaRepository<BankomatHistory,Long> {
}
