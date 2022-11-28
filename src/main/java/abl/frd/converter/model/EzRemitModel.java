package abl.frd.converter.model;

import javax.persistence.*;


@Entity
@Table(name = "EzRemitDataTable")
public class EzRemitModel {
    @Id
    @Column(name = "row_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name = "exCode")
    private String exCode;

    @Column(name = "tranNo")
    private String tranNo;

    @Column(name = "amount")
    private double amount;

    @Column(name = "enteredDate")
    private String enteredDate;

    @Column(name = "remitterName")
    private String remitterName;

    @Column(name = "beneficiaryName")
    private String beneficiaryName;

    @Column(name = "beneficiaryAccount")
    private String beneficiaryAccount;

    @Column(name = "remitterAddress")
    private String remitterAddress;

    @Column(name = "remitterCountry")
    private String remitterCountry;

    public EzRemitModel() {

    }


    @Override
    public String toString() {
        return "EzRemitModel{" +
                "id=" + id +
                ", exCode='" + exCode + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", amount=" + amount +
                ", enteredDate='" + enteredDate + '\'' +
                ", remitterName='" + remitterName + '\'' +
                ", beneficiaryName='" + beneficiaryName + '\'' +
                ", beneficiaryAccount='" + beneficiaryAccount + '\'' +
                ", remitterAddress='" + remitterAddress + '\'' +
                ", remitterCountry='" + remitterCountry + '\'' +
                '}';
    }

    public EzRemitModel(String tranNo, double amount, String enteredDate, String remitterName, String beneficiaryName, String beneficiaryAccount, String remitterAddress, String remitterCountry) {
        this.tranNo = tranNo;
        this.amount = amount;
        this.enteredDate = enteredDate;
        this.remitterName = remitterName;
        this.beneficiaryName = beneficiaryName;
        this.beneficiaryAccount = beneficiaryAccount;
        this.remitterAddress = remitterAddress;
        this.remitterCountry = remitterCountry;
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

    public String getRemitterAddress() {
        return remitterAddress;
    }

    public void setRemitterAddress(String remitterAddress) {
        this.remitterAddress = remitterAddress;
    }

    public String getRemitterCountry() {
        return remitterCountry;
    }

    public void setRemitterCountry(String remitterCountry) {
        this.remitterCountry = remitterCountry;
    }
}
