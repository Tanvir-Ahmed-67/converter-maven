package abl.frd.converter.model;

import javax.persistence.*;

@Entity
@Table(name = "infinityBeftnDataTable")
public class InfinityBeftnModel {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "exCode")
    private String exCode;
    @Column(name = "tranNo")
    private String tranNo;
    @Column(name = "customerNo")
    private String customerNo;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private double amount;
    @Column(name = "enteredDate")
    private String enteredDate;
    @Column(name = "remitterName")
    private String remitterName;
    @Column(name = "remitterAccount")
    private String remitterAccount;
    @Column(name = "beneficiaryName")
    private String beneficiaryName;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "remitterAccountType")
    private String remitterAccountType;
    @Column(name = "beneficiaryAccountType")
    private String beneficiaryAccountType;
    @Column(name = "routingNumber")
    private String routingNumber;

    public InfinityBeftnModel(String tranNo, String customerNo, String currency, double amount, String remitterName, String remitterAccount, String beneficiaryName, String beneficiaryAccount, String remitterAccountType, String beneficiaryAccountType, String routingNumber) {
        this.tranNo = tranNo;
        this.customerNo = customerNo;
        this.currency = currency;
        this.amount = amount;
        this.remitterName = remitterName;
        this.remitterAccount = remitterAccount;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccount = beneficiaryAccount;
        this.remitterAccountType = remitterAccountType;
        this.beneficiaryAccountType = beneficiaryAccountType;
        this.routingNumber = routingNumber;
    }

    public InfinityBeftnModel() {

    }

    @Override
    public String toString() {
        return "InfinityBeftnModel{" +
                "id=" + id +
                ", exCode='" + exCode + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", customerNo='" + customerNo + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", enteredDate='" + enteredDate + '\'' +
                ", remitterName='" + remitterName + '\'' +
                ", remitterAccount='" + remitterAccount + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", remitterAccountType='" + remitterAccountType + '\'' +
                ", beneficiaryAccountType='" + beneficiaryAccountType + '\'' +
                ", routingNumber='" + routingNumber + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExCode() {
        return exCode;
    }

    public void setExCode(String exCode) {
        this.exCode = exCode;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getRemitterName() {
        return remitterName;
    }

    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    public String getRemitterAccount() {
        return remitterAccount;
    }

    public void setRemitterAccount(String remitterAccount) {
        this.remitterAccount = remitterAccount;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getRemitterAccountType() {
        return remitterAccountType;
    }

    public void setRemitterAccountType(String remitterAccountType) {
        this.remitterAccountType = remitterAccountType;
    }

    public String getBeneficiaryAccountType() {
        return beneficiaryAccountType;
    }

    public void setBeneficiaryAccountType(String beneficiaryAccountType) {
        this.beneficiaryAccountType = beneficiaryAccountType;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }
}
