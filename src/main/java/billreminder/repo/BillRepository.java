package billreminder.repo;

import billreminder.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long>{
    Optional<Bill> findBillById(Long id);

    void deleteBillById(Long id);

    void deleteBillByBillCode(String billCode);
}
