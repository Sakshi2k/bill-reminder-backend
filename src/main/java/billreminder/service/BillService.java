package billreminder.service;

import billreminder.exception.BillNotFoundException;
import billreminder.model.Bill;
import billreminder.repo.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public Bill addBill(Bill bill) {
        bill.setBillCode(UUID.randomUUID().toString());
        System.out.println("*** Bill :     " + bill);
        System.out.println("New Bill Added : "+bill.toString());
        return billRepository.save(bill);
    }

    public List<Bill> findAllBills() {
        return billRepository.findAll();
    }

    public Bill findBillById(Long id) {
        return billRepository
                .findBillById(id)
                .orElseThrow(() -> new BillNotFoundException("Bill by id " + id + " was not found !"));
    }
    public Bill updateBillById(Bill bill)  {
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteBillById(id);
    }

    public void deleteBillByCode(String code) {
        billRepository.deleteBillByBillCode(code);
    }

}
