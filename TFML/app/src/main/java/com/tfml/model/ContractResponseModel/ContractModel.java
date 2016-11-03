package com.tfml.model.ContractResponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by webwerks on 1/10/16.
 */

public class ContractModel implements Serializable {

	@SerializedName( "id" )
	@Expose
	private Integer id;
	@SerializedName( "user_id" )
	@Expose
	private String  userId;
	@SerializedName( "usr_con_comp_code" )
	@Expose
	private String  usrConCompCode;
	@SerializedName( "usr_con_no" )
	@Expose
	private String  usrConNo;
	@SerializedName( "usr_crt_dt" )
	@Expose
	private String  usrCrtDt;
	@SerializedName( "contract_status" )
	@Expose
	private String  contractStatus;
	@SerializedName( "customer_contact1" )
	@Expose
	private String  customerContact1;
	@SerializedName( "customer_contact2" )
	@Expose
	private String  customerContact2;
	@SerializedName( "customer_contact3" )
	@Expose
	private Object  customerContact3;
	@SerializedName( "customer_contact4" )
	@Expose
	private String  customerContact4;
	@SerializedName( "customer_contact5" )
	@Expose
	private Object  customerContact5;
	@SerializedName( "last_rec_amt" )
	@Expose
	private String  lastRecAmt;
	@SerializedName( "last_receipt_date" )
	@Expose
	private String  lastReceiptDate;
	@SerializedName( "last_receipt_no" )
	@Expose
	private String  lastReceiptNo;
	@SerializedName( "od_no" )
	@Expose
	private String  odNo;
	@SerializedName( "od_amt" )
	@Expose
	private String  odAmt;
	@SerializedName( "pdc_flag" )
	@Expose
	private String  pdcFlag;
	@SerializedName( "rc_number" )
	@Expose
	private String  rcNumber;
	@SerializedName( "chass_no" )
	@Expose
	private String  chassNo;
	@SerializedName( "engine_no" )
	@Expose
	private String  engineNo;
	@SerializedName( "contract_start_date" )
	@Expose
	private String  contractStartDate;
	@SerializedName( "maturity_date" )
	@Expose
	private String  maturityDate;
	@SerializedName( "tenure" )
	@Expose
	private String  tenure;
	@SerializedName( "finance_amt" )
	@Expose
	private String  financeAmt;
	@SerializedName( "fin_charges" )
	@Expose
	private String  finCharges;
	@SerializedName( "ins_prov" )
	@Expose
	private String  insProv;
	@SerializedName( "option_money" )
	@Expose
	private String  optionMoney;
	@SerializedName( "total_billed" )
	@Expose
	private String  totalBilled;
	@SerializedName( "total_collected" )
	@Expose
	private String  totalCollected;
	@SerializedName( "total_current_due" )
	@Expose
	private String  totalCurrentDue;
	@SerializedName( "con_outst_amt" )
	@Expose
	private String  conOutstAmt;
	@SerializedName( "product" )
	@Expose
	private Object  product;
	@SerializedName( "contract_status_date" )
	@Expose
	private Object  contractStatusDate;
	@SerializedName( "due_date" )
	@Expose
	private Object  dueDate;
	@SerializedName( "due_amount" )
	@Expose
	private Object dueAmount;

	public Object getProduct() {
		return product;
	}

	public void setProduct( Object product ) {
		this.product = product;
	}

	public Object getContractStatusDate() {
		return contractStatusDate;
	}

	public void setContractStatusDate( Object contractStatusDate ) {
		this.contractStatusDate = contractStatusDate;
	}

	public Object getDueDate() {
		return dueDate;
	}

	public void setDueDate( Object dueDate ) {
		this.dueDate = dueDate;
	}

	public Object getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount( Object dueAmount ) {
		this.dueAmount = dueAmount;
	}

	/**
	 * @return The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id The id
	 */
	public void setId( Integer id ) {
		this.id = id;
	}

	/**
	 * @return The userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId The user_id
	 */
	public void setUserId( String userId ) {
		this.userId = userId;
	}

	/**
	 * @return The usrConCompCode
	 */
	public String getUsrConCompCode() {
		return usrConCompCode;
	}

	/**
	 * @param usrConCompCode The usr_con_comp_code
	 */
	public void setUsrConCompCode( String usrConCompCode ) {
		this.usrConCompCode = usrConCompCode;
	}

	/**
	 * @return The usrConNo
	 */
	public String getUsrConNo() {
		return usrConNo;
	}

	/**
	 * @param usrConNo The usr_con_no
	 */
	public void setUsrConNo( String usrConNo ) {
		this.usrConNo = usrConNo;
	}

	/**
	 * @return The usrCrtDt
	 */
	public String getUsrCrtDt() {
		return usrCrtDt;
	}

	/**
	 * @param usrCrtDt The usr_crt_dt
	 */
	public void setUsrCrtDt( String usrCrtDt ) {
		this.usrCrtDt = usrCrtDt;
	}

	/**
	 * @return The contractStatus
	 */
	public String getContractStatus() {
		return contractStatus;
	}

	/**
	 * @param contractStatus The contract_status
	 */
	public void setContractStatus( String contractStatus ) {
		this.contractStatus = contractStatus;
	}

	/**
	 * @return The customerContact1
	 */
	public String getCustomerContact1() {
		return customerContact1;
	}

	/**
	 * @param customerContact1 The customer_contact1
	 */
	public void setCustomerContact1( String customerContact1 ) {
		this.customerContact1 = customerContact1;
	}

	/**
	 * @return The customerContact2
	 */
	public String getCustomerContact2() {
		return customerContact2;
	}

	/**
	 * @param customerContact2 The customer_contact2
	 */
	public void setCustomerContact2( String customerContact2 ) {
		this.customerContact2 = customerContact2;
	}

	/**
	 * @return The customerContact3
	 */
	public Object getCustomerContact3() {
		return customerContact3;
	}

	/**
	 * @param customerContact3 The customer_contact3
	 */
	public void setCustomerContact3( Object customerContact3 ) {
		this.customerContact3 = customerContact3;
	}

	/**
	 * @return The customerContact4
	 */
	public String getCustomerContact4() {
		return customerContact4;
	}

	/**
	 * @param customerContact4 The customer_contact4
	 */
	public void setCustomerContact4( String customerContact4 ) {
		this.customerContact4 = customerContact4;
	}

	/**
	 * @return The customerContact5
	 */
	public Object getCustomerContact5() {
		return customerContact5;
	}

	/**
	 * @param customerContact5 The customer_contact5
	 */
	public void setCustomerContact5( Object customerContact5 ) {
		this.customerContact5 = customerContact5;
	}

	/**
	 * @return The lastRecAmt
	 */
	public String getLastRecAmt() {
		return lastRecAmt;
	}

	/**
	 * @param lastRecAmt The last_rec_amt
	 */
	public void setLastRecAmt( String lastRecAmt ) {
		this.lastRecAmt = lastRecAmt;
	}

	/**
	 * @return The lastReceiptDate
	 */
	public String getLastReceiptDate() {
		return lastReceiptDate;
	}

	/**
	 * @param lastReceiptDate The last_receipt_date
	 */
	public void setLastReceiptDate( String lastReceiptDate ) {
		this.lastReceiptDate = lastReceiptDate;
	}

	/**
	 * @return The lastReceiptNo
	 */
	public String getLastReceiptNo() {
		return lastReceiptNo;
	}

	/**
	 * @param lastReceiptNo The last_receipt_no
	 */
	public void setLastReceiptNo( String lastReceiptNo ) {
		this.lastReceiptNo = lastReceiptNo;
	}

	/**
	 * @return The odNo
	 */
	public String getOdNo() {
		return odNo;
	}

	/**
	 * @param odNo The od_no
	 */
	public void setOdNo( String odNo ) {
		this.odNo = odNo;
	}

	/**
	 * @return The odAmt
	 */
	public String getOdAmt() {
		return odAmt;
	}

	/**
	 * @param odAmt The od_amt
	 */
	public void setOdAmt( String odAmt ) {
		this.odAmt = odAmt;
	}

	/**
	 * @return The pdcFlag
	 */
	public String getPdcFlag() {
		return pdcFlag;
	}

	/**
	 * @param pdcFlag The pdc_flag
	 */
	public void setPdcFlag( String pdcFlag ) {
		this.pdcFlag = pdcFlag;
	}

	/**
	 * @return The rcNumber
	 */
	public String getRcNumber() {
		return rcNumber;
	}

	/**
	 * @param rcNumber The rc_number
	 */
	public void setRcNumber( String rcNumber ) {
		this.rcNumber = rcNumber;
	}

	/**
	 * @return The chassNo
	 */
	public String getChassNo() {
		return chassNo;
	}

	/**
	 * @param chassNo The chass_no
	 */
	public void setChassNo( String chassNo ) {
		this.chassNo = chassNo;
	}

	/**
	 * @return The engineNo
	 */
	public String getEngineNo() {
		return engineNo;
	}

	/**
	 * @param engineNo The engine_no
	 */
	public void setEngineNo( String engineNo ) {
		this.engineNo = engineNo;
	}

	/**
	 * @return The contractStartDate
	 */
	public String getContractStartDate() {
		return contractStartDate;
	}

	/**
	 * @param contractStartDate The contract_start_date
	 */
	public void setContractStartDate( String contractStartDate ) {
		this.contractStartDate = contractStartDate;
	}

	/**
	 * @return The maturityDate
	 */
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * @param maturityDate The maturity_date
	 */
	public void setMaturityDate( String maturityDate ) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @return The tenure
	 */
	public String getTenure() {
		return tenure;
	}

	/**
	 * @param tenure The tenure
	 */
	public void setTenure( String tenure ) {
		this.tenure = tenure;
	}

	/**
	 * @return The financeAmt
	 */
	public String getFinanceAmt() {
		return financeAmt;
	}

	/**
	 * @param financeAmt The finance_amt
	 */
	public void setFinanceAmt( String financeAmt ) {
		this.financeAmt = financeAmt;
	}

	/**
	 * @return The finCharges
	 */
	public String getFinCharges() {
		return finCharges;
	}

	/**
	 * @param finCharges The fin_charges
	 */
	public void setFinCharges( String finCharges ) {
		this.finCharges = finCharges;
	}

	/**
	 * @return The insProv
	 */
	public String getInsProv() {
		return insProv;
	}

	/**
	 * @param insProv The ins_prov
	 */
	public void setInsProv( String insProv ) {
		this.insProv = insProv;
	}

	/**
	 * @return The optionMoney
	 */
	public String getOptionMoney() {
		return optionMoney;
	}

	/**
	 * @param optionMoney The option_money
	 */
	public void setOptionMoney( String optionMoney ) {
		this.optionMoney = optionMoney;
	}

	/**
	 * @return The totalBilled
	 */
	public String getTotalBilled() {
		return totalBilled;
	}

	/**
	 * @param totalBilled The total_billed
	 */
	public void setTotalBilled( String totalBilled ) {
		this.totalBilled = totalBilled;
	}

	/**
	 * @return The totalCollected
	 */
	public String getTotalCollected() {
		return totalCollected;
	}

	/**
	 * @param totalCollected The total_collected
	 */
	public void setTotalCollected( String totalCollected ) {
		this.totalCollected = totalCollected;
	}

	/**
	 * @return The totalCurrentDue
	 */
	public String getTotalCurrentDue() {
		return totalCurrentDue;
	}

	/**
	 * @param totalCurrentDue The total_current_due
	 */
	public void setTotalCurrentDue( String totalCurrentDue ) {
		this.totalCurrentDue = totalCurrentDue;
	}

	/**
	 * @return The conOutstAmt
	 */
	public String getConOutstAmt() {
		return conOutstAmt;
	}

	/**
	 * @param conOutstAmt The con_outst_amt
	 */
	public void setConOutstAmt( String conOutstAmt ) {
		this.conOutstAmt = conOutstAmt;
	}


}
