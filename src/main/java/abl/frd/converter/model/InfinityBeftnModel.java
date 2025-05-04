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
    @Column(name = "orgCustomerNo")
    private String orgCustomerNo;
    @Column(name = "currency")
    private String currency;
    @Column(name = "amount")
    private double amount;
    @Column(name = "enteredDate")
    private String enteredDate;
    @Column(name = "beneficiaryName")
    private String beneficiaryName;
    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;
    @Column(name = "orgAccountType")
    private String orgAccountType;
    @Column(name = "beneficiaryAccountType")
    private String beneficiaryAccountType;
    @Column(name = "routingNumber")
    private String routingNumber;

    @Column(name = "orgCompanyId")
    private String orgCompanyId;

    @Column(name = "orgCompanyName")
    private String orgCompanyName;

    @Column(name = "orgName")
    private String orgName;

    @Column(name = "orgAccountNo")
    private String orgAccountNo;



    public InfinityBeftnModel(String orgCompanyId, String orgCompanyName, String orgName, String orgAccountNo, String tranNo, String orgCustomerNo, String currency, double amount, String beneficiaryName, String beneficiaryAccount, String orgAccountType, String beneficiaryAccountType, String routingNumber) {
        this.tranNo = tranNo;
        this.orgCustomerNo = orgCustomerNo;
        this.currency = currency;
        this.amount = amount;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccount = beneficiaryAccount;
        this.orgAccountType = orgAccountType;
        this.beneficiaryAccountType = beneficiaryAccountType;
        this.routingNumber = routingNumber;
        this.orgCompanyId = orgCompanyId;
        this.orgCompanyName = orgCompanyName;
        this.orgName = orgName;
        this.orgAccountNo = orgAccountNo;
    }

    public String getOrgCompanyId() {
        return orgCompanyId;
    }

    public void setOrgCompanyId(String orgCompanyId) {
        this.orgCompanyId = orgCompanyId;
    }

    public String getOrgCompanyName() {
        return orgCompanyName;
    }

    public void setOrgCompanyName(String orgCompanyName) {
        this.orgCompanyName = orgCompanyName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgAccountNo() {
        return orgAccountNo;
    }

    public void setOrgAccountNo(String orgAccountNo) {
        this.orgAccountNo = orgAccountNo;
    }

    public InfinityBeftnModel() {

    }

    @Override
    public String toString() {
        return "InfinityBeftnModel{" +
                "id=" + id +
                ", exCode='" + exCode + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", customerNo='" + orgCustomerNo + '\'' +
                ", currency='" + currency + '\'' +
                ", amount=" + amount +
                ", enteredDate='" + enteredDate + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", remitterAccountType='" + orgAccountType + '\'' +
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

    public String getOrgCustomerNo() {
        return orgCustomerNo;
    }

    public void setOrgCustomerNo(String customerNo) {
        this.orgCustomerNo = customerNo;
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

    public String getOrgAccountType() {
        return orgAccountType;
    }

    public void setOrgAccountType(String remitterAccountType) {
        this.orgAccountType = remitterAccountType;
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
