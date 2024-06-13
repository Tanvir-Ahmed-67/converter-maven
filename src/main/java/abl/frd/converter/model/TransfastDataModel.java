package abl.frd.converter.model;

import javax.persistence.*;



@Entity
@Table(name = "transfast_data_table")
public class TransfastDataModel {

    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "tfPin")
    private String tfPin;
    @Column(name = "referenceNo")
    private String referenceNo;
    @Column(name = "invoiceNo")
    private String invoiceNo;
    @Column(name = "invoiceDate")
    private String invoiceDate;
    @Column(name = "paidDate")
    private String paidDate;
    @Column(name = "status")
    private String status;

    @Column(name = "remitter")
    private String remitter;
    @Column(name = "beneficiary")
    private String beneficiary;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "branchCode")
    private String branchCode;
    @Column(name = "payingBranchRoutingNo")
    private String payingBranchRoutingNo;
    @Column(name = "payingBankBranchName")
    private String payingBankBranchName;
    @Column(name = "beneficiaryState")
    private String beneficiaryState;
    @Column(name = "beneficiaryCityName")
    private String beneficiaryCityName;
    @Column(name = "cashierName")
    private String cashierName;
    @Column(name = "amountDoller")
    private Double amountDoller;
    @Column(name = "amountLocal")
    private Double amountLocal;
    @Column(name = "beneficiaryPhone")
    private String beneficiaryPhone;
    @Column(name = "remitterCountry")
    private String remitterCountry;

    public TransfastDataModel(){

    }





    public String getPayingBankBranchName() {
        return payingBankBranchName;
    }

    public void setPayingBankBranchName(String payingBankBranchName) {
        this.payingBankBranchName = payingBankBranchName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTfPin() {
        return tfPin;
    }

    public void setTfPin(String tfPin) {
        this.tfPin = tfPin;
    }

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getPayingBranchRoutingNo() {
        return payingBranchRoutingNo;
    }

    public void setPayingBranchRoutingNo(String payingBranchRoutingNo) {
        this.payingBranchRoutingNo = payingBranchRoutingNo;
    }

    public String getBeneficiaryState() {
        return beneficiaryState;
    }

    public void setBeneficiaryState(String beneficiaryState) {
        this.beneficiaryState = beneficiaryState;
    }

    public String getBeneficiaryCityName() {
        return beneficiaryCityName;
    }

    public void setBeneficiaryCityName(String beneficiaryCityName) {
        this.beneficiaryCityName = beneficiaryCityName;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }
    public String getBeneficiaryPhone() {
        return beneficiaryPhone;
    }

    public void setBeneficiaryPhone(String beneficiaryPhone) {
        this.beneficiaryPhone = beneficiaryPhone;
    }

    public String getRemitterCountry() {
        return remitterCountry;
    }

    public void setRemitterCountry(String remitterCountry) {
        this.remitterCountry = remitterCountry;
    }

    public Double getAmountDoller() {
        return amountDoller;
    }

    public void setAmountDoller(Double amountDoller) {
        this.amountDoller = amountDoller;
    }

    public Double getAmountLocal() {
        return amountLocal;
    }

    public void setAmountLocal(Double amountLocal) {
        this.amountLocal = amountLocal;
    }

    @Override
    public String toString() {
        return "TransfastDataModel{" +
                "id=" + id +
                ", tfPin='" + tfPin + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", paidDate='" + paidDate + '\'' +
                ", status='" + status + '\'' +
                ", remitter='" + remitter + '\'' +
                ", beneficiary='" + beneficiary + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                ", payingBranchRoutingNo='" + payingBranchRoutingNo + '\'' +
                ", payingBankBranchName='" + payingBankBranchName + '\'' +
                ", beneficiaryState='" + beneficiaryState + '\'' +
                ", beneficiaryCityName='" + beneficiaryCityName + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", amountDoller=" + amountDoller +
                ", amountLocal=" + amountLocal +
                ", beneficiaryPhone='" + beneficiaryPhone + '\'' +
                ", remitterCountry='" + remitterCountry + '\'' +
                '}';
    }

    public TransfastDataModel(String tfPin, String referenceNo, String invoiceNo, String invoiceDate, String paidDate, String status, String remitter, String beneficiary, String beneficiaryAccount, String bankName, String branchName, String branchCode, String payingBranchRoutingNo, String payingBankBranchName, String beneficiaryState, String beneficiaryCityName, String cashierName, Double amountDoller, Double amountLocal, String beneficiaryPhone, String remitterCountry) {
        this.tfPin = tfPin;
        this.referenceNo = referenceNo;
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.paidDate = paidDate;
        this.status = status;
        this.remitter = remitter;
        this.beneficiary = beneficiary;
        this.beneficiaryAccount = beneficiaryAccount;
        this.bankName = bankName;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.payingBranchRoutingNo = payingBranchRoutingNo;
        this.payingBankBranchName = payingBankBranchName;
        this.beneficiaryState = beneficiaryState;
        this.beneficiaryCityName = beneficiaryCityName;
        this.cashierName = cashierName;
        this.amountDoller = amountDoller;
        this.amountLocal = amountLocal;
        this.beneficiaryPhone = beneficiaryPhone;
        this.remitterCountry = remitterCountry;
    }
}
