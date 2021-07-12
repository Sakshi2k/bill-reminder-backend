package billreminder.service;

import billreminder.exception.BillNotFoundException;
import billreminder.model.Bill;
import billreminder.repo.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class BillService {
    @Autowired
    private BillRepository billRepository;

    public boolean updatePastDue(Bill bill) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date today = new Date();
        bill.pastDue = !bill.paid && sdf.parse(bill.dueDate).compareTo(today) < 0;
        return bill.pastDue;
    }

    public Bill addBill(Bill bill) throws ParseException {
        bill.setBillCode(UUID.randomUUID().toString());
        bill.pastDue = this.updatePastDue(bill);
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
    public Bill updateBillById(Bill bill) throws ParseException {
        this.updatePastDue(bill);
        return billRepository.save(bill);
    }

    public void deleteBill(Long id) {
        billRepository.deleteBillById(id);
    }

    public void deleteBillByCode(String code) {
        billRepository.deleteBillByBillCode(code);
    }


}
