package billreminder.resource;

import billreminder.model.Bill;
/*
import billreminder.repo.ElasticsearchCrudRepository;
*/
import billreminder.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillResource {
    @Autowired
    private BillService billService;

/*
    @Autowired
    private ElasticsearchCrudRepository searchRepo;
*/

    @GetMapping("/all")
    public ResponseEntity<List<Bill>> getAllBills() {
        List<Bill> bills = billService.findAllBills();
        return new ResponseEntity<>(bills, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable("id") Long id) {
        Bill bill = billService.findBillById(id);
        return new ResponseEntity<>(bill, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) {
        Bill newBill = billService.addBill(bill);
        return new ResponseEntity<>(newBill, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Bill> updateBill(@RequestBody Bill bill) {
        Bill updateBill = billService.updateBillById(bill);
        return new ResponseEntity<>(updateBill, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable("id") Long id) {
        billService.deleteBill(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Elastic search Service

   /* @GetMapping("/")
    public ResponseEntity<List<Bill>> findByBillName(@PathVariable String billName) {
      //  return (ResponseEntity<List<Bill>>) searchRepo.findAll();
        return searchRepo.findByBillName(billName);
    }*/

    /* @DeleteMapping("/delete/{code}")
    public ResponseEntity<?> deleteBill(@PathVariable("code") String code) {
        billService.deleteBillByCode(code);
        return new ResponseEntity<>(HttpStatus.OK);
    */

}
