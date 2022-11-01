package abl.frd.converter.model;

import javax.persistence.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "beftn_data_table")
public class BeftnDataModel {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy = SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "beftn_seq", initialValue = 1)
    private int id;
    @Column(name = "exCode")
    private String exCode;
    @Column(name = "tranNo")
    private String tranNo;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private double amount;
    @Column(name = "enteredDate")
    private String enteredDate;
    @Column(name = "remitter")
    private String remitter;
    @Column(name = "beneficiary")
    private String beneficiary;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "bankCode")
    private String bankCode;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "branchCode")
    private String branchCode;

    public BeftnDataModel() {

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

    public String getRemitter() {
        return remitter;
    }

    public void setRemitter(String remitter) {
        this.remitter = remitter;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public BeftnDataModel(String exCode, String tranNo, String currency, double amount, String enteredDate, String remitter, String beneficiary, String beneficiaryAccount, String bankCode, String bankName, String branchName, String branchCode) {
        this.exCode = exCode;
        this.tranNo = tranNo;
        this.currency = currency;
        this.amount = amount;
        this.enteredDate = enteredDate;
        this.remitter = remitter;
        this.beneficiary = beneficiary;
        this.beneficiaryAccount = beneficiaryAccount;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.branchName = branchName;
        this.branchCode = branchCode;
    }

    @Override
    public String toString() {
        return "BeftnDataModel{" +
                "id=" + id +
                ", exCode='" + exCode + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", enteredDate='" + enteredDate + '\'' +
                ", remitter='" + remitter + '\'' +
                ", beneficiary='" + beneficiary + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                '}';
    }
}
