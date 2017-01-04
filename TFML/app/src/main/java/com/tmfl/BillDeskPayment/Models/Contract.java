package com.tmfl.BillDeskPayment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 2/1/17.
 */
public class Contract {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("user_id")
	@Expose
	private String userId;
	@SerializedName("usr_con_comp_code")
	@Expose
	private String usrConCompCode;
	@SerializedName("usr_con_no")
	@Expose
	private String usrConNo;
	@SerializedName("usr_crt_dt")
	@Expose
	private String usrCrtDt;
	@SerializedName("contract_status")
	@Expose
	private String contractStatus;
	@SerializedName("customer_contact1")
	@Expose
	private String customerContact1;
	@SerializedName("customer_contact2")
	@Expose
	private String customerContact2;
	@SerializedName("customer_contact3")
	@Expose
	private String customerContact3;
	@SerializedName("customer_contact4")
	@Expose
	private String customerContact4;
	@SerializedName("customer_contact5")
	@Expose
	private String customerContact5;
	@SerializedName("last_rec_amt")
	@Expose
	private String lastRecAmt;
	@SerializedName("last_receipt_date")
	@Expose
	private String lastReceiptDate;
	@SerializedName("last_receipt_no")
	@Expose
	private String lastReceiptNo;
	@SerializedName("od_no")
	@Expose
	private String odNo;
	@SerializedName("od_amt")
	@Expose
	private String odAmt;
	@SerializedName("pdc_flag")
	@Expose
	private String pdcFlag;
	@SerializedName("rc_number")
	@Expose
	private String rcNumber;
	@SerializedName("chass_no")
	@Expose
	private String chassNo;
	@SerializedName("engine_no")
	@Expose
	private String engineNo;
	@SerializedName("contract_start_date")
	@Expose
	private String contractStartDate;
	@SerializedName("maturity_date")
	@Expose
	private String maturityDate;
	@SerializedName("tenure")
	@Expose
	private String tenure;
	@SerializedName("finance_amt")
	@Expose
	private String financeAmt;
	@SerializedName("fin_charges")
	@Expose
	private String finCharges;
	@SerializedName("ins_prov")
	@Expose
	private String insProv;
	@SerializedName("option_money")
	@Expose
	private String optionMoney;
	@SerializedName("total_billed")
	@Expose
	private String totalBilled;
	@SerializedName("total_collected")
	@Expose
	private String totalCollected;
	@SerializedName("total_current_due")
	@Expose
	private String totalCurrentDue;
	@SerializedName("con_outst_amt")
	@Expose
	private String conOutstAmt;
	@SerializedName("product")
	@Expose
	private String product;
	@SerializedName("contract_status_date")
	@Expose
	private String contractStatusDate;
	@SerializedName("due_date")
	@Expose
	private String dueDate;
	@SerializedName("due_amount")
	@Expose
	private String dueAmount;
	@SerializedName("odc_collectio_amount")
	@Expose
	private String odcCollectioAmount;
	@SerializedName("total_expenses")
	@Expose
	private String totalExpenses;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsrConCompCode() {
		return usrConCompCode;
	}

	public void setUsrConCompCode(String usrConCompCode) {
		this.usrConCompCode = usrConCompCode;
	}

	public String getUsrConNo() {
		return usrConNo;
	}

	public void setUsrConNo(String usrConNo) {
		this.usrConNo = usrConNo;
	}

	public String getUsrCrtDt() {
		return usrCrtDt;
	}

	public void setUsrCrtDt(String usrCrtDt) {
		this.usrCrtDt = usrCrtDt;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getCustomerContact1() {
		return customerContact1;
	}

	public void setCustomerContact1(String customerContact1) {
		this.customerContact1 = customerContact1;
	}

	public String getCustomerContact2() {
		return customerContact2;
	}

	public void setCustomerContact2(String customerContact2) {
		this.customerContact2 = customerContact2;
	}

	public String getCustomerContact3() {
		return customerContact3;
	}

	public void setCustomerContact3(String customerContact3) {
		this.customerContact3 = customerContact3;
	}

	public String getCustomerContact4() {
		return customerContact4;
	}

	public void setCustomerContact4(String customerContact4) {
		this.customerContact4 = customerContact4;
	}

	public String getCustomerContact5() {
		return customerContact5;
	}

	public void setCustomerContact5(String customerContact5) {
		this.customerContact5 = customerContact5;
	}

	public String getLastRecAmt() {
		return lastRecAmt;
	}

	public void setLastRecAmt(String lastRecAmt) {
		this.lastRecAmt = lastRecAmt;
	}

	public String getLastReceiptDate() {
		return lastReceiptDate;
	}

	public void setLastReceiptDate(String lastReceiptDate) {
		this.lastReceiptDate = lastReceiptDate;
	}

	public String getLastReceiptNo() {
		return lastReceiptNo;
	}

	public void setLastReceiptNo(String lastReceiptNo) {
		this.lastReceiptNo = lastReceiptNo;
	}

	public String getOdNo() {
		return odNo;
	}

	public void setOdNo(String odNo) {
		this.odNo = odNo;
	}

	public String getOdAmt() {
		return odAmt;
	}

	public void setOdAmt(String odAmt) {
		this.odAmt = odAmt;
	}

	public String getPdcFlag() {
		return pdcFlag;
	}

	public void setPdcFlag(String pdcFlag) {
		this.pdcFlag = pdcFlag;
	}

	public String getRcNumber() {
		return rcNumber;
	}

	public void setRcNumber(String rcNumber) {
		this.rcNumber = rcNumber;
	}

	public String getChassNo() {
		return chassNo;
	}

	public void setChassNo(String chassNo) {
		this.chassNo = chassNo;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}

	public String getFinanceAmt() {
		return financeAmt;
	}

	public void setFinanceAmt(String financeAmt) {
		this.financeAmt = financeAmt;
	}

	public String getFinCharges() {
		return finCharges;
	}

	public void setFinCharges(String finCharges) {
		this.finCharges = finCharges;
	}

	public String getInsProv() {
		return insProv;
	}

	public void setInsProv(String insProv) {
		this.insProv = insProv;
	}

	public String getOptionMoney() {
		return optionMoney;
	}

	public void setOptionMoney(String optionMoney) {
		this.optionMoney = optionMoney;
	}

	public String getTotalBilled() {
		return totalBilled;
	}

	public void setTotalBilled(String totalBilled) {
		this.totalBilled = totalBilled;
	}

	public String getTotalCollected() {
		return totalCollected;
	}

	public void setTotalCollected(String totalCollected) {
		this.totalCollected = totalCollected;
	}

	public String getTotalCurrentDue() {
		return totalCurrentDue;
	}

	public void setTotalCurrentDue(String totalCurrentDue) {
		this.totalCurrentDue = totalCurrentDue;
	}

	public String getConOutstAmt() {
		return conOutstAmt;
	}

	public void setConOutstAmt(String conOutstAmt) {
		this.conOutstAmt = conOutstAmt;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getContractStatusDate() {
		return contractStatusDate;
	}

	public void setContractStatusDate(String contractStatusDate) {
		this.contractStatusDate = contractStatusDate;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getOdcCollectioAmount() {
		return odcCollectioAmount;
	}

	public void setOdcCollectioAmount(String odcCollectioAmount) {
		this.odcCollectioAmount = odcCollectioAmount;
	}

	public String getTotalExpenses() {
		return totalExpenses;
	}

	public void setTotalExpenses(String totalExpenses) {
		this.totalExpenses = totalExpenses;
	}

	public boolean isSelected;

	public boolean getIsSelected() {
		return isSelected;
	}

	public void setSelected( boolean selected ) {
		isSelected = selected;
	}
}
