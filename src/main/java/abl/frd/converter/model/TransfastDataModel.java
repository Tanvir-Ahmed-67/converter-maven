package abl.frd.converter.model;

import javax.persistence.*;



@Entity
@Table(name = "transfast_data_table")
public class TransfastDataModel {

/*
    TfPin
    Reference
    Invoice
    ReceiveAmount
    ReceiveCurrency
    TransactionDate
    SenderFullName
    SenderID
    SenderCountryName
    SenderCityName
    SenderAddress
    PaymentModeName
    ReceiverFullName
    Receiver Address
    ReceiverCityName
    ReceiverCountryName
    ReceiverPhoneMobile
    BranchId
    BranchName
    BankName
    AccountNumber
 */
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(name = "tfPin")       // excode
    private String tfPin;
    @Column(name = "referenceNo")  // tranno
    private String referenceNo;
    @Column(name = "invoiceNo")
    private String invoiceNo;
    @Column(name = "amount")
    private double amount;
    @Column(name = "currency")
    private String currency;
    @Column(name = "enteredDate")
    private String enteredDate;
    @Column(name = "remitter")
    private String remitter;
    @Column(name = "remitterId")
    private String remitterId;
    @Column(name = "remitterCountryName")
    private String remitterCountryName;
    @Column(name = "remitterCityName")
    private String remitterCityName;
    @Column(name = "remitterAdress")
    private String remitterAdress;
    @Column(name = "beneficiary")
    private String beneficiary;
    @Column(name = "beneficiaryCountryName")
    private String beneficiaryCountryName;
    @Column(name = "beneficiaryCityName")
    private String beneficiaryCityName;
    @Column(name = "beneficiaryAdress")
    private String beneficiaryAdress;
    @Column(name = "beneficiaryPhone")
    private String beneficiaryPhone;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "bankName")
    private String bankName;
    @Column(name = "branchName")
    private String branchName;
    @Column(name = "branchCode")
    private String branchCode;

    public TransfastDataModel(String tfPin, String referenceNo, String invoiceNo, double amount, String currency, String enteredDate, String remitter, String remitterId, String remitterCountryName, String remitterCityName, String remitterAdress, String beneficiary, String beneficiaryCountryName, String beneficiaryCityName, String beneficiaryAdress, String beneficiaryPhone, String beneficiaryAccount, String bankName, String branchName, String branchCode) {
        this.tfPin = tfPin;
        this.referenceNo = referenceNo;
        this.invoiceNo = invoiceNo;
        this.amount = amount;
        this.currency = currency;
        this.enteredDate = enteredDate;
        this.remitter = remitter;
        this.remitterId = remitterId;
        this.remitterCountryName = remitterCountryName;
        this.remitterCityName = remitterCityName;
        this.remitterAdress = remitterAdress;
        this.beneficiary = beneficiary;
        this.beneficiaryCountryName = beneficiaryCountryName;
        this.beneficiaryCityName = beneficiaryCityName;
        this.beneficiaryAdress = beneficiaryAdress;
        this.beneficiaryPhone = beneficiaryPhone;
        this.beneficiaryAccount = beneficiaryAccount;
        this.bankName = bankName;
        this.branchName = branchName;
        this.branchCode = branchCode;
    }
    public TransfastDataModel(){

    }

    @Override
    public String toString() {
        return "TransfastDataModel{" +
                "id=" + id +
                ", tfPin='" + tfPin + '\'' +
                ", referenceNo='" + referenceNo + '\'' +
                ", invoiceNo='" + invoiceNo + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", enteredDate='" + enteredDate + '\'' +
                ", remitter='" + remitter + '\'' +
                ", remitterId='" + remitterId + '\'' +
                ", remitterCountryName='" + remitterCountryName + '\'' +
                ", remitterCityName='" + remitterCityName + '\'' +
                ", remitterAdress='" + remitterAdress + '\'' +
                ", beneficiary='" + beneficiary + '\'' +
                ", beneficiaryCountryName='" + beneficiaryCountryName + '\'' +
                ", beneficiaryCityName='" + beneficiaryCityName + '\'' +
                ", beneficiaryAdress='" + beneficiaryAdress + '\'' +
                ", beneficiaryPhone='" + beneficiaryPhone + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", branchCode='" + branchCode + '\'' +
                '}';
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getRemitterId() {
        return remitterId;
    }

    public void setRemitterId(String remitterId) {
        this.remitterId = remitterId;
    }

    public String getRemitterCountryName() {
        return remitterCountryName;
    }

    public void setRemitterCountryName(String remitterCountryName) {
        this.remitterCountryName = remitterCountryName;
    }

    public String getRemitterCityName() {
        return remitterCityName;
    }

    public void setRemitterCityName(String remitterCityName) {
        this.remitterCityName = remitterCityName;
    }

    public String getRemitterAdress() {
        return remitterAdress;
    }

    public void setRemitterAdress(String remitterAdress) {
        this.remitterAdress = remitterAdress;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getBeneficiaryCountryName() {
        return beneficiaryCountryName;
    }

    public void setBeneficiaryCountryName(String beneficiaryCountryName) {
        this.beneficiaryCountryName = beneficiaryCountryName;
    }

    public String getBeneficiaryCityName() {
        return beneficiaryCityName;
    }

    public void setBeneficiaryCityName(String beneficiaryCityName) {
        this.beneficiaryCityName = beneficiaryCityName;
    }

    public String getBeneficiaryAdress() {
        return beneficiaryAdress;
    }

    public void setBeneficiaryAdress(String beneficiaryAdress) {
        this.beneficiaryAdress = beneficiaryAdress;
    }

    public String getBeneficiaryPhone() {
        return beneficiaryPhone;
    }

    public void setBeneficiaryPhone(String beneficiaryPhone) {
        this.beneficiaryPhone = beneficiaryPhone;
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
}
