package billreminder.model;

import javax.persistence.*;
import java.io.Serializable;

/*@Document(indexName = "billreminder", shards = 2)
@Data
@Setting(settingPath = "static/es-settings.json")*/
@Entity
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable=false, updatable=false)
    private Long id;
    private String billName;
    private String billDesc;
    @Column(nullable = false)
    private Long amount;
    private boolean paid = false;
    private boolean pastDue = false;
    // @JsonbDateFormat("dd/MM/yyyy")
    // private Date dueDate;
    private String dueDate;
    @Column(nullable=false, updatable=false)
    private String billCode;
    private String billType;

    public Bill() {}

    // public Bill(Long id, String billName, String billDesc, boolean paid, boolean pastDue, String billCode, Date dueDate) {
    public Bill(Long id, String billName, String billDesc, boolean paid, boolean pastDue, String billCode, String dueDate, String billType, Long amount) {
        this.id = id;
        this.billName = billName;
        this.billDesc = billDesc;
        this.paid = paid;
        this.pastDue = pastDue;
        this.billCode = billCode;
        this.dueDate = dueDate;
        this.billType = billType;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bill{" +
            "id=" + id +
            ", billName='" + billName + '\'' +
            ", billDesc='" + billDesc + '\'' +
            ", billCode='" + billCode + '\'' +
            ", dueDate='" + dueDate + '\'' +
            ", pastDue='" + pastDue + '\'' +
            ", billType='" + billType + '\'' +
            '}';
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getBillName() { return billName; }

    public void setBillName(String billName) {
        this.billName = billName;
    }

    public String getBillDesc() {
        return billDesc;
    }

    public void setBillDesc(String billDesc) {
        this.billDesc = billDesc;
    }

    public String getBillCode() {
        return billCode;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean isPastDue() {
        return pastDue;
    }

    public void setPastDue(boolean pastDue) {
        this.pastDue = pastDue;
    }

    public String getBillType() { return billType; }

    public void setBillType(String billType) { this.billType = billType; }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
