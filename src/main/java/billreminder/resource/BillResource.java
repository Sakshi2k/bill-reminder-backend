package billreminder.resource;

import billreminder.model.Bill;
import billreminder.service.BillService;
import billreminder.service.ElasticSearchBillService;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/bill")
public class BillResource {
    @Autowired
    private BillService billService;

    @Autowired
    private ElasticSearchBillService elasticSearchBillService;

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
    public ResponseEntity<Bill> addBill(@RequestBody Bill bill) throws IOException, ParseException {
        Bill newBill = billService.addBill(bill);
        elasticSearchBillService.addBill(bill);
        return new ResponseEntity<>(newBill, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Bill> updateBill(@RequestBody Bill bill) throws IOException, ParseException {
        System.out.println("--- Updating bill ---");
        Bill updateBill = billService.updateBillById(bill);
        System.out.println(elasticSearchBillService.updateBillByBillCode(bill));
        return new ResponseEntity<>(updateBill, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBill(@PathVariable("id") Long id) throws IOException, InterruptedException {
        Bill thisBill =  billService.findBillById(id);
        // thisBill.wait();
        billService.deleteBill(id);
        elasticSearchBillService.deleteBillByBillCode(thisBill.getBillCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Elastic-search Service only
    @GetMapping("/search/{searchName}")
    public SearchHit[] getBillsByName(@PathVariable("searchName") String searchValue) throws IOException {
        return elasticSearchBillService.getBillByBillName(searchValue);
    }


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
