package com.tfml.model.soapModel.response;

/**
 * Created by Satyawan on 26/9/16.
 */

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "SOAP:Envelope")
public class ResponseEnvelope implements Serializable
{

    @Element(name="Header", required = false)
    String header;


    @Element(name="Body", required = false)
    Body body;


    @Attribute(name="SOAP", required = false)
    String sOAP;



    public String getHeader() { return this.header; }
    public void setHeader(String _value) { this.header = _value; }


    public Body getBody() { return this.body; }
    public void setBody(Body _value) { this.body = _value; }


    public String getSOAP() { return this.sOAP; }
    public void setSOAP(String _value) { this.sOAP = _value; }



    public static class Body implements Serializable {

        @Element(name="ZCIS.Response", required = false)
        ZCISResponse zCISResponse;



        public ZCISResponse getZCISResponse() { return this.zCISResponse; }
        public void setZCISResponse(ZCISResponse _value) { this.zCISResponse = _value; }


    }

    public static class ZCISResponse implements Serializable{

        @Element(name="IT_CARDEX1", required = false)
        IT_CARDEX1 iT_CARDEX1;


        @Element(name="IT_CARDEX2", required = false)
        IT_CARDEX2 iT_CARDEX2;


        @Element(name="IT_DUES1", required = false)
        IT_DUES1 iT_DUES1;


        @Element(name="IT_DUES2", required = false)
        IT_DUES2 iT_DUES2;


        @Element(name="IT_DUES3", required = false)
        IT_DUES3 iT_DUES3;


        @ElementList(name = "IT_ODC", required = false)
        List<Item> iT_ODC;


        @Element(name="I_ADDTIONAL", required = false)
        I_ADDTIONAL i_ADDTIONAL;


        @ElementList(name = "I_EXP", required = false)
        List<Item> i_EXP;


        @ElementList(name = "I_INSTALLMENT", required = false)
        List<Item> i_INSTALLMENT;


        @ElementList(name = "I_INSURANCE", required = false)
        List<Item> i_INSURANCE;


        @ElementList(name = "I_REC", required = false)
        List<Item> i_REC;


        @ElementList(name = "I_REJ", required = false)
        List<Item> i_REJ;


        @ElementList(name = "I_STK", required = false)
        List<Item> i_STK;


        @Element(name="I_ZCARDEX_NOTE", required = false)
        I_ZCARDEX_NOTE i_ZCARDEX_NOTE;


        @Element(name="RETURN", required = false)
        RETURN rETURN;


        @Attribute(name="ns0", required = false)
        String ns0;



        public IT_CARDEX1 getIT_CARDEX1() { return this.iT_CARDEX1; }
        public void setIT_CARDEX1(IT_CARDEX1 _value) { this.iT_CARDEX1 = _value; }


        public IT_CARDEX2 getIT_CARDEX2() { return this.iT_CARDEX2; }
        public void setIT_CARDEX2(IT_CARDEX2 _value) { this.iT_CARDEX2 = _value; }


        public IT_DUES1 getIT_DUES1() { return this.iT_DUES1; }
        public void setIT_DUES1(IT_DUES1 _value) { this.iT_DUES1 = _value; }


        public IT_DUES2 getIT_DUES2() { return this.iT_DUES2; }
        public void setIT_DUES2(IT_DUES2 _value) { this.iT_DUES2 = _value; }


        public IT_DUES3 getIT_DUES3() { return this.iT_DUES3; }
        public void setIT_DUES3(IT_DUES3 _value) { this.iT_DUES3 = _value; }


        public List<Item> getIT_ODC() { return this.iT_ODC; }
        public void setIT_ODC(List<Item> _value) { this.iT_ODC = _value; }


        public I_ADDTIONAL getI_ADDTIONAL() { return this.i_ADDTIONAL; }
        public void setI_ADDTIONAL(I_ADDTIONAL _value) { this.i_ADDTIONAL = _value; }


        public List<Item> getI_EXP() { return this.i_EXP; }
        public void setI_EXP(List<Item> _value) { this.i_EXP = _value; }


        public List<Item> getI_INSTALLMENT() { return this.i_INSTALLMENT; }
        public void setI_INSTALLMENT(List<Item> _value) { this.i_INSTALLMENT = _value; }


        public List<Item> getI_INSURANCE() { return this.i_INSURANCE; }
        public void setI_INSURANCE(List<Item> _value) { this.i_INSURANCE = _value; }


        public List<Item> getI_REC() { return this.i_REC; }
        public void setI_REC(List<Item> _value) { this.i_REC = _value; }


        public List<Item> getI_REJ() { return this.i_REJ; }
        public void setI_REJ(List<Item> _value) { this.i_REJ = _value; }


        public List<Item> getI_STK() { return this.i_STK; }
        public void setI_STK(List<Item> _value) { this.i_STK = _value; }


        public I_ZCARDEX_NOTE getI_ZCARDEX_NOTE() { return this.i_ZCARDEX_NOTE; }
        public void setI_ZCARDEX_NOTE(I_ZCARDEX_NOTE _value) { this.i_ZCARDEX_NOTE = _value; }


        public RETURN getRETURN() { return this.rETURN; }
        public void setRETURN(RETURN _value) { this.rETURN = _value; }


        public String getNs0() { return this.ns0; }
        public void setNs0(String _value) { this.ns0 = _value; }


    }

    public static class IT_CARDEX1 implements Serializable {

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class Item implements Serializable{

        @Element(name="CONTRACTNO", required = false)
        String cONTRACTNO;


        @Element(name="REQDATE", required = false)
        String rEQDATE;


        @Element(name="VZSKZ", required = false)
        String vZSKZ;


        @Element(name="OBJECTID", required = false)
        String oBJECTID;


        @Element(name="CONTSTARTDT", required = false)
        String cONTSTARTDT;


        @Element(name="CONTENDDT", required = false)
        String cONTENDDT;


        @Element(name="ASSET", required = false)
        String aSSET;


        @Element(name="ASSET_TXT", required = false)
        String aSSET_TXT;


        @Element(name="STATUS", required = false)
        String sTATUS;


        @Element(name="STAT_DATE", required = false)
        String sTAT_DATE;


        @Element(name="TYPE", required = false)
        String tYPE;


        @Element(name="DCH_TXT", required = false)
        String dCH_TXT;


        @Element(name="CLIENT", required = false)
        String cLIENT;


        @Element(name="GUARANTOR", required = false)
        String gUARANTOR;


        @Element(name="DEALER", required = false)
        String dEALER;


        @Element(name="SUPPLIER", required = false)
        String sUPPLIER;


        @Element(name="COMPANY_NAME", required = false)
        String cOMPANY_NAME;


        @Element(name="CLIENT_NAME", required = false)
        String cLIENT_NAME;


        @Element(name="DEALER_NAME", required = false)
        String dEALER_NAME;


        @Element(name="SUPPLIER_NAME", required = false)
        String sUPPLIER_NAME;


        @Element(name="DSA", required = false)
        String dSA;


        @Element(name="DSA_NAME", required = false)
        String dSA_NAME;


        @Element(name="DMA", required = false)
        String dMA;


        @Element(name="DMA_NAME", required = false)
        String dMA_NAME;


        @Element(name="ACT_MGR_NAME", required = false)
        String aCT_MGR_NAME;


        @Element(name="REGION", required = false)
        String rEGION;


        @Element(name="BRANCH", required = false)
        String bRANCH;


        @Element(name="SALES_OFFICE", required = false)
        String sALES_OFFICE;


        @Element(name="IBC", required = false)
        String iBC;


        @Element(name="AREA", required = false)
        String aREA;


        @Element(name="INV_DATE", required = false)
        String iNV_DATE;


        @Element(name="TA_ADJ_DATE", required = false)
        String tA_ADJ_DATE;


        @Element(name="DISB_DATE", required = false)
        String dISB_DATE;


        @Element(name="FIRST_INST_DATE", required = false)
        String fIRST_INST_DATE;


        @Element(name="MATURITY_DATE", required = false)
        String mATURITY_DATE;


        @Element(name="FIA_I", required = false)
        String fIA_I;


        @Element(name="FIA_II", required = false)
        String fIA_II;


        @Element(name="COLLECTION_AG", required = false)
        String cOLLECTION_AG;


        @Element(name="LODGING", required = false)
        String lODGING;


        @Element(name="MSP", required = false)
        String mSP;


        @Element(name="SCHEME_CODE", required = false)
        String sCHEME_CODE;


        @Element(name="FIN_CHRG_RATE", required = false)
        String fIN_CHRG_RATE;


        @Element(name="PPR", required = false)
        String pPR;


        @Element(name="TENURE", required = false)
        String tENURE;


        @Element(name="TENURE_UNIT", required = false)
        String tENURE_UNIT;


        @Element(name="TERM", required = false)
        String tERM;


        @Element(name="TERM_REC", required = false)
        String tERM_REC;


        @Element(name="LEAD_PRD", required = false)
        String lEAD_PRD;


        @Element(name="LEAD_PRD_UNIT", required = false)
        String lEAD_PRD_UNIT;


        @Element(name="ADV_EMI", required = false)
        String aDV_EMI;


        @Element(name="INST_FREQ", required = false)
        String iNST_FREQ;


        @Element(name="SD_AMT", required = false)
        String sD_AMT;


        @Element(name="SD_INT_RATE", required = false)
        String sD_INT_RATE;


        @Element(name="SD_INT_TYPE", required = false)
        String sD_INT_TYPE;


        @Element(name="SD_INT_TYPE_TXT", required = false)
        String sD_INT_TYPE_TXT;


        @Element(name="SERV_CHRG", required = false)
        String sERV_CHRG;


        @Element(name="INVOICE_NO", required = false)
        String iNVOICE_NO;


        @Element(name="MODEL", required = false)
        String mODEL;


        @Element(name="ENGINE_NO", required = false)
        String eNGINE_NO;


        @Element(name="CHASIS_NO", required = false)
        String cHASIS_NO;


        @Element(name="REG_NO", required = false)
        String rEG_NO;


        @Element(name="INS_POLICY_NO", required = false)
        String iNS_POLICY_NO;


        @Element(name="POLICY_START_DT", required = false)
        String pOLICY_START_DT;


        @Element(name="POLICY_EXPR_DT", required = false)
        String pOLICY_EXPR_DT;


        @Element(name="ADV_INS", required = false)
        String aDV_INS;


        @Element(name="INV_AMT", required = false)
        String iNV_AMT;


        @Element(name="INITIAL_HIRE", required = false)
        String iNITIAL_HIRE;


        @Element(name="FIN_AMT", required = false)
        String fIN_AMT;


        @Element(name="FIN_CHRG", required = false)
        String fIN_CHRG;


        @Element(name="INS_PROV", required = false)
        String iNS_PROV;


        @Element(name="OPTION_MONEY", required = false)
        String oPTION_MONEY;


        @Element(name="PDC", required = false)
        String pDC;


        @Element(name="CONTR_VAL", required = false)
        String cONTR_VAL;


        @Element(name="DUE_TILL_DATE", required = false)
        String dUE_TILL_DATE;


        @Element(name="REC_TILL_DATE", required = false)
        String rEC_TILL_DATE;


        @Element(name="ODC_RATE", required = false)
        String oDC_RATE;


        @Element(name="OVERDUE", required = false)
        String oVERDUE;


        @Element(name="ACCRUED_ODC", required = false)
        String aCCRUED_ODC;


        @Element(name="SECURITIZATION", required = false)
        String sECURITIZATION;


        @Element(name="ZZPOOLNUMBER", required = false)
        String zZPOOLNUMBER;


        @Element(name="ZZRATE", required = false)
        String zZRATE;


        @Element(name="ZZDATE", required = false)
        String zZDATE;


        @Element(name="LEGAL_STATUS", required = false)
        String lEGAL_STATUS;


        @Element(name="REPO_FLAG", required = false)
        String rEPO_FLAG;


        @Element(name="WRITE_OFF", required = false)
        String wRITE_OFF;


        @Element(name="REMEDIAL_STATUS", required = false)
        String rEMEDIAL_STATUS;


        @Element(name="NORM", required = false)
        String nORM;


        @Element(name="VEH_USAGE", required = false)
        String vEH_USAGE;


        @Element(name="SOA_DATE_FROM", required = false)
        String sOA_DATE_FROM;


        @Element(name="SOA_DATE_TO", required = false)
        String sOA_DATE_TO;


        @Element(name="SOA_PRV_AMT", required = false)
        String sOA_PRV_AMT;


        @Element(name="SOA_PRD_AMT", required = false)
        String sOA_PRD_AMT;


        @Element(name="SOA_PRINCIPAL", required = false)
        String sOA_PRINCIPAL;


        @Element(name="SOA_FIN_CHRG", required = false)
        String sOA_FIN_CHRG;


        @Element(name="SOA_ADV_EMI", required = false)
        String sOA_ADV_EMI;


        @Element(name="BODY_CHASIS", required = false)
        String bODY_CHASIS;


        @Element(name="EXCESS_RECD", required = false)
        String eXCESS_RECD;


        @Element(name="EXPENSES_DUE", required = false)
        String eXPENSES_DUE;


        @Element(name="COVER_NOTENO", required = false)
        String cOVER_NOTENO;


        @Element(name="COVER_NOTEDT", required = false)
        String cOVER_NOTEDT;


        @Element(name="TOTAL_LOSS", required = false)
        String tOTAL_LOSS;


        @Element(name="BUKRS", required = false)
        String bUKRS;


        @Element(name="PDC_TYPE", required = false)
        String pDC_TYPE;


        @Element(name="LPO_STATUS", required = false)
        String lPO_STATUS;


        @Element(name="MANDT", required = false)
        String mANDT;


        @Element(name="CATEGORY", required = false)
        String cATEGORY;


        @Element(name="CHQ_CLEARED", required = false)
        String cHQ_CLEARED;


        @Element(name="VZSKZ_DF", required = false)
        String vZSKZ_DF;


        @Element(name="ODC_IND_REQ", required = false)
        String oDC_IND_REQ;


        @Element(name="ODC_IND_AP", required = false)
        String oDC_IND_AP;


        @Element(name="WORKING_IND", required = false)
        String wORKING_IND;


        @Element(name="ODC_RATE_D_DF", required = false)
        String oDC_RATE_D_DF;


        @Element(name="ODC_RATE_C_DF", required = false)
        String oDC_RATE_C_DF;


        @Element(name="ODC_RATE_REQ", required = false)
        String oDC_RATE_REQ;


        @Element(name="ODC_RATE_AP", required = false)
        String oDC_RATE_AP;


        @Element(name="WORKING_RATE_D", required = false)
        String wORKING_RATE_D;


        @Element(name="WORKING_RATE_C", required = false)
        String wORKING_RATE_C;


        @Element(name="ODC_WAIVER_REQ", required = false)
        String oDC_WAIVER_REQ;


        @Element(name="EXP_WAIVER_REQ", required = false)
        String eXP_WAIVER_REQ;


        @Element(name="ODC_WAIVER", required = false)
        String oDC_WAIVER;


        @Element(name="EXP_WAIVER", required = false)
        String eXP_WAIVER;


        @Element(name="VAL_WAIVER", required = false)
        String vAL_WAIVER;


        @Element(name="WAIVER_ENTRY", required = false)
        String wAIVER_ENTRY;


        @Element(name="VKBUR", required = false)
        String vKBUR;


        @Element(name="SPART", required = false)
        String sPART;


        @Element(name="AUTH_IND", required = false)
        String aUTH_IND;


        @Element(name="STATUS_WF_DESC", required = false)
        String sTATUS_WF_DESC;


        @Element(name="STATUS_WF", required = false)
        String sTATUS_WF;


        @Element(name="ACTION", required = false)
        String aCTION;


        @Element(name="DECISION_MADE", required = false)
        String dECISION_MADE;


        @Element(name="OBJID", required = false)
        String oBJID;


        @Element(name="ADJUST_SD", required = false)
        String aDJUST_SD;


        @Element(name="ADJUST_PPR", required = false)
        String aDJUST_PPR;


        @Element(name="PPR_SCH_RATE", required = false)
        String pPR_SCH_RATE;


        @Element(name="PPR_PRO_RATE", required = false)
        String pPR_PRO_RATE;


        @Element(name="PPR_PRORATA_NOS", required = false)
        String pPR_PRORATA_NOS;


        @Element(name="TOTAL_DUE", required = false)
        String tOTAL_DUE;


        @Element(name="TOTAL_REC", required = false)
        String tOTAL_REC;


        @Element(name="TOTAL_NET", required = false)
        String tOTAL_NET;


        @Element(name="BILLED_DUE", required = false)
        String bILLED_DUE;


        @Element(name="BILLED_REC", required = false)
        String bILLED_REC;


        @Element(name="BILLED2_DUE", required = false)
        String bILLED2_DUE;


        @Element(name="BILLED2_REC", required = false)
        String bILLED2_REC;


        @Element(name="COLL2_DUE", required = false)
        String cOLL2_DUE;


        @Element(name="COLL2_REC", required = false)
        String cOLL2_REC;


        @Element(name="NONBILLED_DUE", required = false)
        String nONBILLED_DUE;


        @Element(name="PRINCIPAL_DUE", required = false)
        String pRINCIPAL_DUE;


        @Element(name="CONTR_FILA_DUE", required = false)
        String cONTR_FILA_DUE;


        @Element(name="CONTR_VAL_DUE", required = false)
        String cONTR_VAL_DUE;


        @Element(name="CONTR_VAL_REC", required = false)
        String cONTR_VAL_REC;


        @Element(name="FIN_AMT_DUE", required = false)
        String fIN_AMT_DUE;


        @Element(name="FIN_CHRG_DUE", required = false)
        String fIN_CHRG_DUE;


        @Element(name="INS_FILA_DUE", required = false)
        String iNS_FILA_DUE;


        @Element(name="INSURANCE_DUE", required = false)
        String iNSURANCE_DUE;


        @Element(name="INSURANCE_REC", required = false)
        String iNSURANCE_REC;


        @Element(name="INS_ACCT_DUE", required = false)
        String iNS_ACCT_DUE;


        @Element(name="INS_ACCT_REC", required = false)
        String iNS_ACCT_REC;


        @Element(name="INS_CLAIM_DUE", required = false)
        String iNS_CLAIM_DUE;


        @Element(name="INS_CLAIM_REC", required = false)
        String iNS_CLAIM_REC;


        @Element(name="INS_CHRG_DUE", required = false)
        String iNS_CHRG_DUE;


        @Element(name="INS_CHRG_REC", required = false)
        String iNS_CHRG_REC;


        @Element(name="INS_PROV_DUE", required = false)
        String iNS_PROV_DUE;


        @Element(name="INS_PROV_REC", required = false)
        String iNS_PROV_REC;


        @Element(name="COLL_CHRG_DUE", required = false)
        String cOLL_CHRG_DUE;


        @Element(name="COLL_CHRG_REC", required = false)
        String cOLL_CHRG_REC;


        @Element(name="COLL_GROS_DUE", required = false)
        String cOLL_GROS_DUE;


        @Element(name="COLL_GROS_REC", required = false)
        String cOLL_GROS_REC;


        @Element(name="BANK_CHRG_DUE", required = false)
        String bANK_CHRG_DUE;


        @Element(name="BANK_CHRG_REC", required = false)
        String bANK_CHRG_REC;


        @Element(name="REPO_CHRG_DUE", required = false)
        String rEPO_CHRG_DUE;


        @Element(name="REPO_CHRG_REC", required = false)
        String rEPO_CHRG_REC;


        @Element(name="LEGL_EXP_DUE", required = false)
        String lEGL_EXP_DUE;


        @Element(name="LEGL_EXP_REC", required = false)
        String lEGL_EXP_REC;


        @Element(name="PDD_CHRG_DUE", required = false)
        String pDD_CHRG_DUE;


        @Element(name="PDD_CHRG_REC", required = false)
        String pDD_CHRG_REC;


        @Element(name="STAMP_DUTY_DUE", required = false)
        String sTAMP_DUTY_DUE;


        @Element(name="STAMP_DUTY_REC", required = false)
        String sTAMP_DUTY_REC;


        @Element(name="STAMP_DUTY_M_DUE", required = false)
        String sTAMP_DUTY_M_DUE;


        @Element(name="STAMP_DUTY_M_REC", required = false)
        String sTAMP_DUTY_M_REC;


        @Element(name="DOCU_CHRG_M_DUE", required = false)
        String dOCU_CHRG_M_DUE;


        @Element(name="DOCU_CHRG_M_REC", required = false)
        String dOCU_CHRG_M_REC;


        @Element(name="SWAP_CHRG_DUE", required = false)
        String sWAP_CHRG_DUE;


        @Element(name="SWAP_CHRG_REC", required = false)
        String sWAP_CHRG_REC;


        @Element(name="ODC_CHRG_DUE", required = false)
        String oDC_CHRG_DUE;


        @Element(name="ODC_CHRG_REC", required = false)
        String oDC_CHRG_REC;


        @Element(name="ODC_POSTED_DUE", required = false)
        String oDC_POSTED_DUE;


        @Element(name="ODC_POSTED_REC", required = false)
        String oDC_POSTED_REC;


        @Element(name="OPT_MONEY_DUE", required = false)
        String oPT_MONEY_DUE;


        @Element(name="OPT_MONEY_REC", required = false)
        String oPT_MONEY_REC;


        @Element(name="PREPAY_REB_DUE", required = false)
        String pREPAY_REB_DUE;


        @Element(name="PREPAY_REB_REC", required = false)
        String pREPAY_REB_REC;


        @Element(name="OS_PRI_DUE", required = false)
        String oS_PRI_DUE;


        @Element(name="OS_PRI_REC", required = false)
        String oS_PRI_REC;


        @Element(name="LAST_INT_DUE", required = false)
        String lAST_INT_DUE;


        @Element(name="LAST_INT_REC", required = false)
        String lAST_INT_REC;


        @Element(name="PMT_AMT_DUE", required = false)
        String pMT_AMT_DUE;


        @Element(name="PMT_AMT_REC", required = false)
        String pMT_AMT_REC;


        @Element(name="SD_AMT_DUE", required = false)
        String sD_AMT_DUE;


        @Element(name="SD_AMT_REC", required = false)
        String sD_AMT_REC;


        @Element(name="SD_INT_DUE", required = false)
        String sD_INT_DUE;


        @Element(name="SD_INT_REC", required = false)
        String sD_INT_REC;


        @Element(name="PPR_DUE", required = false)
        String pPR_DUE;


        @Element(name="PPR_REC", required = false)
        String pPR_REC;


        @Element(name="RES_VAL_DUE", required = false)
        String rES_VAL_DUE;


        @Element(name="RES_VAL_REC", required = false)
        String rES_VAL_REC;


        @Element(name="LM_FEE_DUE", required = false)
        String lM_FEE_DUE;


        @Element(name="LM_FEE_REC", required = false)
        String lM_FEE_REC;


        @Element(name="DOWNPAY_DUE", required = false)
        String dOWNPAY_DUE;


        @Element(name="DOWNPAY_REC", required = false)
        String dOWNPAY_REC;


        @Element(name="OTH_EXP_DUE", required = false)
        String oTH_EXP_DUE;


        @Element(name="OTH_EXP_REC", required = false)
        String oTH_EXP_REC;


        @Element(name="WRITE_OFF_DUE", required = false)
        String wRITE_OFF_DUE;


        @Element(name="WRITE_OFF_REC", required = false)
        String wRITE_OFF_REC;


        @Element(name="DEALER_LIAB_DUE", required = false)
        String dEALER_LIAB_DUE;


        @Element(name="DEALER_LIAB_REC", required = false)
        String dEALER_LIAB_REC;


        @Element(name="ADV_EMI_DUE", required = false)
        String aDV_EMI_DUE;


        @Element(name="ADV_EMI_REC", required = false)
        String aDV_EMI_REC;


        @Element(name="AD_SERV_CHRG_DUE", required = false)
        String aD_SERV_CHRG_DUE;


        @Element(name="AD_SERV_CHRG_REC", required = false)
        String aD_SERV_CHRG_REC;


        @Element(name="CANC_CHRG_DUE", required = false)
        String cANC_CHRG_DUE;


        @Element(name="CANC_CHRG_REC", required = false)
        String cANC_CHRG_REC;


        @Element(name="FI3_AG_CHRG_DUE", required = false)
        String fI3_AG_CHRG_DUE;


        @Element(name="FI3_AG_CHRG_REC", required = false)
        String fI3_AG_CHRG_REC;


        @Element(name="HOLD_CHRG_DUE", required = false)
        String hOLD_CHRG_DUE;


        @Element(name="HOLD_CHRG_REC", required = false)
        String hOLD_CHRG_REC;


        @Element(name="LEASE_TAX_DUE", required = false)
        String lEASE_TAX_DUE;


        @Element(name="LEASE_TAX_REC", required = false)
        String lEASE_TAX_REC;


        @Element(name="MISC_DUE", required = false)
        String mISC_DUE;


        @Element(name="MISC_REC", required = false)
        String mISC_REC;


        @Element(name="RET_CHRG_DUE", required = false)
        String rET_CHRG_DUE;


        @Element(name="RET_CHRG_REC", required = false)
        String rET_CHRG_REC;


        @Element(name="RTO_CHRG_DUE", required = false)
        String rTO_CHRG_DUE;


        @Element(name="RTO_CHRG_REC", required = false)
        String rTO_CHRG_REC;


        @Element(name="SERV_CHRG_DUE", required = false)
        String sERV_CHRG_DUE;


        @Element(name="SERV_CHRG_REC", required = false)
        String sERV_CHRG_REC;


        @Element(name="SERV_TAX_EX_DUE", required = false)
        String sERV_TAX_EX_DUE;


        @Element(name="SERV_TAX_EX_REC", required = false)
        String sERV_TAX_EX_REC;


        @Element(name="SALES_TAX_DUE", required = false)
        String sALES_TAX_DUE;


        @Element(name="SALES_TAX_REC", required = false)
        String sALES_TAX_REC;


        @Element(name="VAL_ADD_TAX_DUE", required = false)
        String vAL_ADD_TAX_DUE;


        @Element(name="VAL_ADD_TAX_REC", required = false)
        String vAL_ADD_TAX_REC;


        @Element(name="WAIVER_DUE", required = false)
        String wAIVER_DUE;


        @Element(name="WAIVER_REC", required = false)
        String wAIVER_REC;


        @Element(name="INCOME_DUE", required = false)
        String iNCOME_DUE;


        @Element(name="INCOME_REC", required = false)
        String iNCOME_REC;


        @Element(name="TRANS_DUE", required = false)
        String tRANS_DUE;


        @Element(name="TRANS_REC", required = false)
        String tRANS_REC;


        @Element(name="COLL_AG_CHRG_DUE", required = false)
        String cOLL_AG_CHRG_DUE;


        @Element(name="COLL_AG_CHRG_REC", required = false)
        String cOLL_AG_CHRG_REC;


        @Element(name="SALE_AMT_REC", required = false)
        String sALE_AMT_REC;


        @Element(name="ST_BRI_DUE", required = false)
        String sT_BRI_DUE;


        @Element(name="ST_BRI_REC", required = false)
        String sT_BRI_REC;


        @Element(name="ST_INT_DUE", required = false)
        String sT_INT_DUE;


        @Element(name="ST_INT_REC", required = false)
        String sT_INT_REC;


        @Element(name="EC_INT_DUE", required = false)
        String eC_INT_DUE;


        @Element(name="EC_INT_REC", required = false)
        String eC_INT_REC;


        @Element(name="ST_LAST_INT_DUE", required = false)
        String sT_LAST_INT_DUE;


        @Element(name="ST_LAST_INT_REC", required = false)
        String sT_LAST_INT_REC;


        @Element(name="EC_LAST_INT_DUE", required = false)
        String eC_LAST_INT_DUE;


        @Element(name="EC_LAST_INT_REC", required = false)
        String eC_LAST_INT_REC;


        @Element(name="ST_ODC_DUE", required = false)
        String sT_ODC_DUE;


        @Element(name="ST_ODC_REC", required = false)
        String sT_ODC_REC;


        @Element(name="EC_ODC_DUE", required = false)
        String eC_ODC_DUE;


        @Element(name="EC_ODC_REC", required = false)
        String eC_ODC_REC;


        @Element(name="ST_PMT_DUE", required = false)
        String sT_PMT_DUE;


        @Element(name="ST_PMT_REC", required = false)
        String sT_PMT_REC;


        @Element(name="EC_PMT_DUE", required = false)
        String eC_PMT_DUE;


        @Element(name="EC_PMT_REC", required = false)
        String eC_PMT_REC;


        @Element(name="TOT_EXP_DUE", required = false)
        String tOT_EXP_DUE;


        @Element(name="TOT_EXP_REC", required = false)
        String tOT_EXP_REC;


        @Element(name="DEALER_LIAB_RATE", required = false)
        String dEALER_LIAB_RATE;


        @Element(name="DEALER_LIAB", required = false)
        String dEALER_LIAB;


        @Element(name="PER_DAY_INT", required = false)
        String pER_DAY_INT;


        @Element(name="REASON_REQ", required = false)
        String rEASON_REQ;


        @Element(name="REASON_RECOM", required = false)
        String rEASON_RECOM;


        @Element(name="REMARK", required = false)
        String rEMARK;


        @Element(name="REMARK_HO", required = false)
        String rEMARK_HO;


        @Element(name="BILLING", required = false)
        String bILLING;


        @Element(name="UNAME", required = false)
        String uNAME;


        @Element(name="DATE_R", required = false)
        String dATE_R;


        @Element(name="TIMESTAMP", required = false)
        String tIMESTAMP;


        @Element(name="UNAME_WF_CR", required = false)
        String uNAME_WF_CR;


        @Element(name="TS_WF_CR", required = false)
        String tS_WF_CR;


        @Element(name="UNAME_WF_DM", required = false)
        String uNAME_WF_DM;


        @Element(name="TS_WF_DM", required = false)
        String tS_WF_DM;


        @Element(name="UNAME_APPL", required = false)
        String uNAME_APPL;


        @Element(name="DATE_A", required = false)
        String dATE_A;


        @Element(name="TS_APPL", required = false)
        String tS_APPL;


        @Element(name="UNAME_TERM", required = false)
        String uNAME_TERM;


        @Element(name="DATE_T", required = false)
        String dATE_T;


        @Element(name="TS_TERM", required = false)
        String tS_TERM;


        @Element(name="PRINT_BLOCK", required = false)
        String pRINT_BLOCK;


        @Element(name="PRINT_STATUS", required = false)
        String pRINT_STATUS;


        @Element(name="PRINT_UNAME", required = false)
        String pRINT_UNAME;


        @Element(name="PRINT_DATUM", required = false)
        String pRINT_DATUM;


        @Element(name="PRINT_TIME", required = false)
        String pRINT_TIME;


        @Element(name="PRINT_COLL_BY", required = false)
        String pRINT_COLL_BY;


        @Element(name="PRINT_COLL_DT", required = false)
        String pRINT_COLL_DT;


        @Element(name="RTO_REF_NO", required = false)
        String rTO_REF_NO;


        @Element(name="RTO_REF_DATE", required = false)
        String rTO_REF_DATE;


        @Element(name="OBJECTID_CP", required = false)
        String oBJECTID_CP;


        @Element(name="BTR_INIT", required = false)
        String bTR_INIT;


        @Element(name="BTR_UNAME", required = false)
        String bTR_UNAME;


        @Element(name="BTR_INIT_DT", required = false)
        String bTR_INIT_DT;


        @Element(name="BTR_COMP", required = false)
        String bTR_COMP;


        @Element(name="BTR_SELE", required = false)
        String bTR_SELE;


        @Element(name="BTR_ERR", required = false)
        String bTR_ERR;


        @Element(name="ST_GL_DUE", required = false)
        String sT_GL_DUE;


        @Element(name="ST_GL_REC", required = false)
        String sT_GL_REC;


        @Element(name="CA_CHRG_DUE", required = false)
        String cA_CHRG_DUE;


        @Element(name="CA_CHRG_REC", required = false)
        String cA_CHRG_REC;


        @Element(name="PENDRC_REC", required = false)
        String pENDRC_REC;


        @Element(name="PENDRC_DUE", required = false)
        String pENDRC_DUE;


        @Element(name="COLL2_ZXY_REC", required = false)
        String cOLL2_ZXY_REC;


        @Element(name="COLL2_ZXY_DUE", required = false)
        String cOLL2_ZXY_DUE;


        @Element(name="ZDATE", required = false)
        String zDATE;


        @Element(name="SHKZG", required = false)
        String sHKZG;


        @Element(name="SRNO", required = false)
        String sRNO;


        @Element(name="SECCO", required = false)
        String sECCO;


        @Element(name="GJAHR", required = false)
        String gJAHR;


        @Element(name="BELNR", required = false)
        String bELNR;


        @Element(name="BUZEI", required = false)
        String bUZEI;


        @Element(name="BLART", required = false)
        String bLART;


        @Element(name="BUDAT", required = false)
        String bUDAT;


        @Element(name="BLDAT", required = false)
        String bLDAT;


        @Element(name="ZFBDT", required = false)
        String zFBDT;


        @Element(name="ANBWA", required = false)
        String aNBWA;


        @Element(name="DESCP", required = false)
        String dESCP;


        @Element(name="PAN_ID", required = false)
        String pAN_ID;


        @Element(name="PAN_NO", required = false)
        String pAN_NO;


        @Element(name="INST_TYPE", required = false)
        String iNST_TYPE;


        @Element(name="INST_NO", required = false)
        String iNST_NO;


        @Element(name="INST_DATE", required = false)
        String iNST_DATE;


        @Element(name="DRAW_BNK", required = false)
        String dRAW_BNK;


        @Element(name="DMBTR", required = false)
        String dMBTR;


        @Element(name="DUE", required = false)
        String dUE;


        @Element(name="REC", required = false)
        String rEC;


        @Element(name="BAL", required = false)
        String bAL;


        @Element(name="ZDAYS", required = false)
        String zDAYS;


        @Element(name="ODC", required = false)
        String oDC;


        @Element(name="ODC_POS", required = false)
        String oDC_POS;


        @Element(name="ODC_NEG", required = false)
        String oDC_NEG;


        @Element(name="C_DUE", required = false)
        String c_DUE;


        @Element(name="C_REC", required = false)
        String c_REC;


        @Element(name="SGTXT", required = false)
        String sGTXT;


        @Element(name="DRCR", required = false)
        String dRCR;


        @Element(name="CPUDT", required = false)
        String cPUDT;


        @Element(name="TDS_FLAG", required = false)
        String tDS_FLAG;


        @Element(name="TANNO", required = false)
        String tANNO;


        @Element(name="PDC_TEXT", required = false)
        String pDC_TEXT;


        @Element(name="AD", required = false)
        String aD;


        @Element(name="PD", required = false)
        String pD;


        @Element(name="INST_FR", required = false)
        String iNST_FR;


        @Element(name="INST_TO", required = false)
        String iNST_TO;


        @Element(name="AMOUNT", required = false)
        String aMOUNT;


        @Element(name="ZYEAR", required = false)
        String zYEAR;


        @Element(name="PROVISON_AMT", required = false)
        String pROVISON_AMT;


        @Element(name="STOCKID", required = false)
        String sTOCKID;


        @Element(name="TRANSID", required = false)
        String tRANSID;


        @Element(name="SEQNR", required = false)
        String sEQNR;


        @Element(name="RWBTR", required = false)
        String rWBTR;


        @Element(name="PDCTYPE", required = false)
        String pDCTYPE;


        @Element(name="PDCTYPE_TXT", required = false)
        String pDCTYPE_TXT;


        @Element(name="PDCBANK", required = false)
        String pDCBANK;


        @Element(name="BRNCH", required = false)
        String bRNCH;


        @Element(name="CHECTDATE", required = false)
        String cHECTDATE;


        @Element(name="BANKDATE", required = false)
        String bANKDATE;


        @Element(name="CHECT", required = false)
        String cHECT;


        @Element(name="STATUS_TXT", required = false)
        String sTATUS_TXT;


        @Element(name="NOTE", required = false)
        String nOTE;


        @Element(name="NOTE1", required = false)
        String nOTE1;


        @Element(name="NOTE2", required = false)
        String nOTE2;


        @Element(name="ID", required = false)
        String iD;


        @Element(name="NUMBER", required = false)
        String nUMBER;


        @Element(name="MESSAGE", required = false)
        String mESSAGE;


        @Element(name="LOG_NO", required = false)
        String lOG_NO;


        @Element(name="LOG_MSG_NO", required = false)
        String lOG_MSG_NO;


        @Element(name="MESSAGE_V1", required = false)
        String mESSAGE_V1;


        @Element(name="MESSAGE_V2", required = false)
        String mESSAGE_V2;


        @Element(name="MESSAGE_V3", required = false)
        String mESSAGE_V3;


        @Element(name="MESSAGE_V4", required = false)
        String mESSAGE_V4;


        @Element(name="PARAMETER", required = false)
        String pARAMETER;


        @Element(name="ROW", required = false)
        String rOW;


        @Element(name="FIELD", required = false)
        String fIELD;


        @Element(name="SYSTEM", required = false)
        String sYSTEM;



        public String getCONTRACTNO() { return this.cONTRACTNO; }
        public void setCONTRACTNO(String _value) { this.cONTRACTNO = _value; }


        public String getREQDATE() { return this.rEQDATE; }
        public void setREQDATE(String _value) { this.rEQDATE = _value; }


        public String getVZSKZ() { return this.vZSKZ; }
        public void setVZSKZ(String _value) { this.vZSKZ = _value; }


        public String getOBJECTID() { return this.oBJECTID; }
        public void setOBJECTID(String _value) { this.oBJECTID = _value; }


        public String getCONTSTARTDT() { return this.cONTSTARTDT; }
        public void setCONTSTARTDT(String _value) { this.cONTSTARTDT = _value; }


        public String getCONTENDDT() { return this.cONTENDDT; }
        public void setCONTENDDT(String _value) { this.cONTENDDT = _value; }


        public String getASSET() { return this.aSSET; }
        public void setASSET(String _value) { this.aSSET = _value; }


        public String getASSET_TXT() { return this.aSSET_TXT; }
        public void setASSET_TXT(String _value) { this.aSSET_TXT = _value; }


        public String getSTATUS() { return this.sTATUS; }
        public void setSTATUS(String _value) { this.sTATUS = _value; }


        public String getSTAT_DATE() { return this.sTAT_DATE; }
        public void setSTAT_DATE(String _value) { this.sTAT_DATE = _value; }


        public String getTYPE() { return this.tYPE; }
        public void setTYPE(String _value) { this.tYPE = _value; }


        public String getDCH_TXT() { return this.dCH_TXT; }
        public void setDCH_TXT(String _value) { this.dCH_TXT = _value; }


        public String getCLIENT() { return this.cLIENT; }
        public void setCLIENT(String _value) { this.cLIENT = _value; }


        public String getGUARANTOR() { return this.gUARANTOR; }
        public void setGUARANTOR(String _value) { this.gUARANTOR = _value; }


        public String getDEALER() { return this.dEALER; }
        public void setDEALER(String _value) { this.dEALER = _value; }


        public String getSUPPLIER() { return this.sUPPLIER; }
        public void setSUPPLIER(String _value) { this.sUPPLIER = _value; }


        public String getCOMPANY_NAME() { return this.cOMPANY_NAME; }
        public void setCOMPANY_NAME(String _value) { this.cOMPANY_NAME = _value; }


        public String getCLIENT_NAME() { return this.cLIENT_NAME; }
        public void setCLIENT_NAME(String _value) { this.cLIENT_NAME = _value; }


        public String getDEALER_NAME() { return this.dEALER_NAME; }
        public void setDEALER_NAME(String _value) { this.dEALER_NAME = _value; }


        public String getSUPPLIER_NAME() { return this.sUPPLIER_NAME; }
        public void setSUPPLIER_NAME(String _value) { this.sUPPLIER_NAME = _value; }


        public String getDSA() { return this.dSA; }
        public void setDSA(String _value) { this.dSA = _value; }


        public String getDSA_NAME() { return this.dSA_NAME; }
        public void setDSA_NAME(String _value) { this.dSA_NAME = _value; }


        public String getDMA() { return this.dMA; }
        public void setDMA(String _value) { this.dMA = _value; }


        public String getDMA_NAME() { return this.dMA_NAME; }
        public void setDMA_NAME(String _value) { this.dMA_NAME = _value; }


        public String getACT_MGR_NAME() { return this.aCT_MGR_NAME; }
        public void setACT_MGR_NAME(String _value) { this.aCT_MGR_NAME = _value; }


        public String getREGION() { return this.rEGION; }
        public void setREGION(String _value) { this.rEGION = _value; }


        public String getBRANCH() { return this.bRANCH; }
        public void setBRANCH(String _value) { this.bRANCH = _value; }


        public String getSALES_OFFICE() { return this.sALES_OFFICE; }
        public void setSALES_OFFICE(String _value) { this.sALES_OFFICE = _value; }


        public String getIBC() { return this.iBC; }
        public void setIBC(String _value) { this.iBC = _value; }


        public String getAREA() { return this.aREA; }
        public void setAREA(String _value) { this.aREA = _value; }


        public String getINV_DATE() { return this.iNV_DATE; }
        public void setINV_DATE(String _value) { this.iNV_DATE = _value; }


        public String getTA_ADJ_DATE() { return this.tA_ADJ_DATE; }
        public void setTA_ADJ_DATE(String _value) { this.tA_ADJ_DATE = _value; }


        public String getDISB_DATE() { return this.dISB_DATE; }
        public void setDISB_DATE(String _value) { this.dISB_DATE = _value; }


        public String getFIRST_INST_DATE() { return this.fIRST_INST_DATE; }
        public void setFIRST_INST_DATE(String _value) { this.fIRST_INST_DATE = _value; }


        public String getMATURITY_DATE() { return this.mATURITY_DATE; }
        public void setMATURITY_DATE(String _value) { this.mATURITY_DATE = _value; }


        public String getFIA_I() { return this.fIA_I; }
        public void setFIA_I(String _value) { this.fIA_I = _value; }


        public String getFIA_II() { return this.fIA_II; }
        public void setFIA_II(String _value) { this.fIA_II = _value; }


        public String getCOLLECTION_AG() { return this.cOLLECTION_AG; }
        public void setCOLLECTION_AG(String _value) { this.cOLLECTION_AG = _value; }


        public String getLODGING() { return this.lODGING; }
        public void setLODGING(String _value) { this.lODGING = _value; }


        public String getMSP() { return this.mSP; }
        public void setMSP(String _value) { this.mSP = _value; }


        public String getSCHEME_CODE() { return this.sCHEME_CODE; }
        public void setSCHEME_CODE(String _value) { this.sCHEME_CODE = _value; }


        public String getFIN_CHRG_RATE() { return this.fIN_CHRG_RATE; }
        public void setFIN_CHRG_RATE(String _value) { this.fIN_CHRG_RATE = _value; }


        public String getPPR() { return this.pPR; }
        public void setPPR(String _value) { this.pPR = _value; }


        public String getTENURE() { return this.tENURE; }
        public void setTENURE(String _value) { this.tENURE = _value; }


        public String getTENURE_UNIT() { return this.tENURE_UNIT; }
        public void setTENURE_UNIT(String _value) { this.tENURE_UNIT = _value; }


        public String getTERM() { return this.tERM; }
        public void setTERM(String _value) { this.tERM = _value; }


        public String getTERM_REC() { return this.tERM_REC; }
        public void setTERM_REC(String _value) { this.tERM_REC = _value; }


        public String getLEAD_PRD() { return this.lEAD_PRD; }
        public void setLEAD_PRD(String _value) { this.lEAD_PRD = _value; }


        public String getLEAD_PRD_UNIT() { return this.lEAD_PRD_UNIT; }
        public void setLEAD_PRD_UNIT(String _value) { this.lEAD_PRD_UNIT = _value; }


        public String getADV_EMI() { return this.aDV_EMI; }
        public void setADV_EMI(String _value) { this.aDV_EMI = _value; }


        public String getINST_FREQ() { return this.iNST_FREQ; }
        public void setINST_FREQ(String _value) { this.iNST_FREQ = _value; }


        public String getSD_AMT() { return this.sD_AMT; }
        public void setSD_AMT(String _value) { this.sD_AMT = _value; }


        public String getSD_INT_RATE() { return this.sD_INT_RATE; }
        public void setSD_INT_RATE(String _value) { this.sD_INT_RATE = _value; }


        public String getSD_INT_TYPE() { return this.sD_INT_TYPE; }
        public void setSD_INT_TYPE(String _value) { this.sD_INT_TYPE = _value; }


        public String getSD_INT_TYPE_TXT() { return this.sD_INT_TYPE_TXT; }
        public void setSD_INT_TYPE_TXT(String _value) { this.sD_INT_TYPE_TXT = _value; }


        public String getSERV_CHRG() { return this.sERV_CHRG; }
        public void setSERV_CHRG(String _value) { this.sERV_CHRG = _value; }


        public String getINVOICE_NO() { return this.iNVOICE_NO; }
        public void setINVOICE_NO(String _value) { this.iNVOICE_NO = _value; }


        public String getMODEL() { return this.mODEL; }
        public void setMODEL(String _value) { this.mODEL = _value; }


        public String getENGINE_NO() { return this.eNGINE_NO; }
        public void setENGINE_NO(String _value) { this.eNGINE_NO = _value; }


        public String getCHASIS_NO() { return this.cHASIS_NO; }
        public void setCHASIS_NO(String _value) { this.cHASIS_NO = _value; }


        public String getREG_NO() { return this.rEG_NO; }
        public void setREG_NO(String _value) { this.rEG_NO = _value; }


        public String getINS_POLICY_NO() { return this.iNS_POLICY_NO; }
        public void setINS_POLICY_NO(String _value) { this.iNS_POLICY_NO = _value; }


        public String getPOLICY_START_DT() { return this.pOLICY_START_DT; }
        public void setPOLICY_START_DT(String _value) { this.pOLICY_START_DT = _value; }


        public String getPOLICY_EXPR_DT() { return this.pOLICY_EXPR_DT; }
        public void setPOLICY_EXPR_DT(String _value) { this.pOLICY_EXPR_DT = _value; }


        public String getADV_INS() { return this.aDV_INS; }
        public void setADV_INS(String _value) { this.aDV_INS = _value; }


        public String getINV_AMT() { return this.iNV_AMT; }
        public void setINV_AMT(String _value) { this.iNV_AMT = _value; }


        public String getINITIAL_HIRE() { return this.iNITIAL_HIRE; }
        public void setINITIAL_HIRE(String _value) { this.iNITIAL_HIRE = _value; }


        public String getFIN_AMT() { return this.fIN_AMT; }
        public void setFIN_AMT(String _value) { this.fIN_AMT = _value; }


        public String getFIN_CHRG() { return this.fIN_CHRG; }
        public void setFIN_CHRG(String _value) { this.fIN_CHRG = _value; }


        public String getINS_PROV() { return this.iNS_PROV; }
        public void setINS_PROV(String _value) { this.iNS_PROV = _value; }


        public String getOPTION_MONEY() { return this.oPTION_MONEY; }
        public void setOPTION_MONEY(String _value) { this.oPTION_MONEY = _value; }


        public String getPDC() { return this.pDC; }
        public void setPDC(String _value) { this.pDC = _value; }


        public String getCONTR_VAL() { return this.cONTR_VAL; }
        public void setCONTR_VAL(String _value) { this.cONTR_VAL = _value; }


        public String getDUE_TILL_DATE() { return this.dUE_TILL_DATE; }
        public void setDUE_TILL_DATE(String _value) { this.dUE_TILL_DATE = _value; }


        public String getREC_TILL_DATE() { return this.rEC_TILL_DATE; }
        public void setREC_TILL_DATE(String _value) { this.rEC_TILL_DATE = _value; }


        public String getODC_RATE() { return this.oDC_RATE; }
        public void setODC_RATE(String _value) { this.oDC_RATE = _value; }


        public String getOVERDUE() { return this.oVERDUE; }
        public void setOVERDUE(String _value) { this.oVERDUE = _value; }


        public String getACCRUED_ODC() { return this.aCCRUED_ODC; }
        public void setACCRUED_ODC(String _value) { this.aCCRUED_ODC = _value; }


        public String getSECURITIZATION() { return this.sECURITIZATION; }
        public void setSECURITIZATION(String _value) { this.sECURITIZATION = _value; }


        public String getZZPOOLNUMBER() { return this.zZPOOLNUMBER; }
        public void setZZPOOLNUMBER(String _value) { this.zZPOOLNUMBER = _value; }


        public String getZZRATE() { return this.zZRATE; }
        public void setZZRATE(String _value) { this.zZRATE = _value; }


        public String getZZDATE() { return this.zZDATE; }
        public void setZZDATE(String _value) { this.zZDATE = _value; }


        public String getLEGAL_STATUS() { return this.lEGAL_STATUS; }
        public void setLEGAL_STATUS(String _value) { this.lEGAL_STATUS = _value; }


        public String getREPO_FLAG() { return this.rEPO_FLAG; }
        public void setREPO_FLAG(String _value) { this.rEPO_FLAG = _value; }


        public String getWRITE_OFF() { return this.wRITE_OFF; }
        public void setWRITE_OFF(String _value) { this.wRITE_OFF = _value; }


        public String getREMEDIAL_STATUS() { return this.rEMEDIAL_STATUS; }
        public void setREMEDIAL_STATUS(String _value) { this.rEMEDIAL_STATUS = _value; }


        public String getNORM() { return this.nORM; }
        public void setNORM(String _value) { this.nORM = _value; }


        public String getVEH_USAGE() { return this.vEH_USAGE; }
        public void setVEH_USAGE(String _value) { this.vEH_USAGE = _value; }


        public String getSOA_DATE_FROM() { return this.sOA_DATE_FROM; }
        public void setSOA_DATE_FROM(String _value) { this.sOA_DATE_FROM = _value; }


        public String getSOA_DATE_TO() { return this.sOA_DATE_TO; }
        public void setSOA_DATE_TO(String _value) { this.sOA_DATE_TO = _value; }


        public String getSOA_PRV_AMT() { return this.sOA_PRV_AMT; }
        public void setSOA_PRV_AMT(String _value) { this.sOA_PRV_AMT = _value; }


        public String getSOA_PRD_AMT() { return this.sOA_PRD_AMT; }
        public void setSOA_PRD_AMT(String _value) { this.sOA_PRD_AMT = _value; }


        public String getSOA_PRINCIPAL() { return this.sOA_PRINCIPAL; }
        public void setSOA_PRINCIPAL(String _value) { this.sOA_PRINCIPAL = _value; }


        public String getSOA_FIN_CHRG() { return this.sOA_FIN_CHRG; }
        public void setSOA_FIN_CHRG(String _value) { this.sOA_FIN_CHRG = _value; }


        public String getSOA_ADV_EMI() { return this.sOA_ADV_EMI; }
        public void setSOA_ADV_EMI(String _value) { this.sOA_ADV_EMI = _value; }


        public String getBODY_CHASIS() { return this.bODY_CHASIS; }
        public void setBODY_CHASIS(String _value) { this.bODY_CHASIS = _value; }


        public String getEXCESS_RECD() { return this.eXCESS_RECD; }
        public void setEXCESS_RECD(String _value) { this.eXCESS_RECD = _value; }


        public String getEXPENSES_DUE() { return this.eXPENSES_DUE; }
        public void setEXPENSES_DUE(String _value) { this.eXPENSES_DUE = _value; }


        public String getCOVER_NOTENO() { return this.cOVER_NOTENO; }
        public void setCOVER_NOTENO(String _value) { this.cOVER_NOTENO = _value; }


        public String getCOVER_NOTEDT() { return this.cOVER_NOTEDT; }
        public void setCOVER_NOTEDT(String _value) { this.cOVER_NOTEDT = _value; }


        public String getTOTAL_LOSS() { return this.tOTAL_LOSS; }
        public void setTOTAL_LOSS(String _value) { this.tOTAL_LOSS = _value; }


        public String getBUKRS() { return this.bUKRS; }
        public void setBUKRS(String _value) { this.bUKRS = _value; }


        public String getPDC_TYPE() { return this.pDC_TYPE; }
        public void setPDC_TYPE(String _value) { this.pDC_TYPE = _value; }


        public String getLPO_STATUS() { return this.lPO_STATUS; }
        public void setLPO_STATUS(String _value) { this.lPO_STATUS = _value; }


        public String getMANDT() { return this.mANDT; }
        public void setMANDT(String _value) { this.mANDT = _value; }


        public String getCATEGORY() { return this.cATEGORY; }
        public void setCATEGORY(String _value) { this.cATEGORY = _value; }


        public String getCHQ_CLEARED() { return this.cHQ_CLEARED; }
        public void setCHQ_CLEARED(String _value) { this.cHQ_CLEARED = _value; }


        public String getVZSKZ_DF() { return this.vZSKZ_DF; }
        public void setVZSKZ_DF(String _value) { this.vZSKZ_DF = _value; }


        public String getODC_IND_REQ() { return this.oDC_IND_REQ; }
        public void setODC_IND_REQ(String _value) { this.oDC_IND_REQ = _value; }


        public String getODC_IND_AP() { return this.oDC_IND_AP; }
        public void setODC_IND_AP(String _value) { this.oDC_IND_AP = _value; }


        public String getWORKING_IND() { return this.wORKING_IND; }
        public void setWORKING_IND(String _value) { this.wORKING_IND = _value; }


        public String getODC_RATE_D_DF() { return this.oDC_RATE_D_DF; }
        public void setODC_RATE_D_DF(String _value) { this.oDC_RATE_D_DF = _value; }


        public String getODC_RATE_C_DF() { return this.oDC_RATE_C_DF; }
        public void setODC_RATE_C_DF(String _value) { this.oDC_RATE_C_DF = _value; }


        public String getODC_RATE_REQ() { return this.oDC_RATE_REQ; }
        public void setODC_RATE_REQ(String _value) { this.oDC_RATE_REQ = _value; }


        public String getODC_RATE_AP() { return this.oDC_RATE_AP; }
        public void setODC_RATE_AP(String _value) { this.oDC_RATE_AP = _value; }


        public String getWORKING_RATE_D() { return this.wORKING_RATE_D; }
        public void setWORKING_RATE_D(String _value) { this.wORKING_RATE_D = _value; }


        public String getWORKING_RATE_C() { return this.wORKING_RATE_C; }
        public void setWORKING_RATE_C(String _value) { this.wORKING_RATE_C = _value; }


        public String getODC_WAIVER_REQ() { return this.oDC_WAIVER_REQ; }
        public void setODC_WAIVER_REQ(String _value) { this.oDC_WAIVER_REQ = _value; }


        public String getEXP_WAIVER_REQ() { return this.eXP_WAIVER_REQ; }
        public void setEXP_WAIVER_REQ(String _value) { this.eXP_WAIVER_REQ = _value; }


        public String getODC_WAIVER() { return this.oDC_WAIVER; }
        public void setODC_WAIVER(String _value) { this.oDC_WAIVER = _value; }


        public String getEXP_WAIVER() { return this.eXP_WAIVER; }
        public void setEXP_WAIVER(String _value) { this.eXP_WAIVER = _value; }


        public String getVAL_WAIVER() { return this.vAL_WAIVER; }
        public void setVAL_WAIVER(String _value) { this.vAL_WAIVER = _value; }


        public String getWAIVER_ENTRY() { return this.wAIVER_ENTRY; }
        public void setWAIVER_ENTRY(String _value) { this.wAIVER_ENTRY = _value; }


        public String getVKBUR() { return this.vKBUR; }
        public void setVKBUR(String _value) { this.vKBUR = _value; }


        public String getSPART() { return this.sPART; }
        public void setSPART(String _value) { this.sPART = _value; }


        public String getAUTH_IND() { return this.aUTH_IND; }
        public void setAUTH_IND(String _value) { this.aUTH_IND = _value; }


        public String getSTATUS_WF_DESC() { return this.sTATUS_WF_DESC; }
        public void setSTATUS_WF_DESC(String _value) { this.sTATUS_WF_DESC = _value; }


        public String getSTATUS_WF() { return this.sTATUS_WF; }
        public void setSTATUS_WF(String _value) { this.sTATUS_WF = _value; }


        public String getACTION() { return this.aCTION; }
        public void setACTION(String _value) { this.aCTION = _value; }


        public String getDECISION_MADE() { return this.dECISION_MADE; }
        public void setDECISION_MADE(String _value) { this.dECISION_MADE = _value; }


        public String getOBJID() { return this.oBJID; }
        public void setOBJID(String _value) { this.oBJID = _value; }


        public String getADJUST_SD() { return this.aDJUST_SD; }
        public void setADJUST_SD(String _value) { this.aDJUST_SD = _value; }


        public String getADJUST_PPR() { return this.aDJUST_PPR; }
        public void setADJUST_PPR(String _value) { this.aDJUST_PPR = _value; }


        public String getPPR_SCH_RATE() { return this.pPR_SCH_RATE; }
        public void setPPR_SCH_RATE(String _value) { this.pPR_SCH_RATE = _value; }


        public String getPPR_PRO_RATE() { return this.pPR_PRO_RATE; }
        public void setPPR_PRO_RATE(String _value) { this.pPR_PRO_RATE = _value; }


        public String getPPR_PRORATA_NOS() { return this.pPR_PRORATA_NOS; }
        public void setPPR_PRORATA_NOS(String _value) { this.pPR_PRORATA_NOS = _value; }


        public String getTOTAL_DUE() { return this.tOTAL_DUE; }
        public void setTOTAL_DUE(String _value) { this.tOTAL_DUE = _value; }


        public String getTOTAL_REC() { return this.tOTAL_REC; }
        public void setTOTAL_REC(String _value) { this.tOTAL_REC = _value; }


        public String getTOTAL_NET() { return this.tOTAL_NET; }
        public void setTOTAL_NET(String _value) { this.tOTAL_NET = _value; }


        public String getBILLED_DUE() { return this.bILLED_DUE; }
        public void setBILLED_DUE(String _value) { this.bILLED_DUE = _value; }


        public String getBILLED_REC() { return this.bILLED_REC; }
        public void setBILLED_REC(String _value) { this.bILLED_REC = _value; }


        public String getBILLED2_DUE() { return this.bILLED2_DUE; }
        public void setBILLED2_DUE(String _value) { this.bILLED2_DUE = _value; }


        public String getBILLED2_REC() { return this.bILLED2_REC; }
        public void setBILLED2_REC(String _value) { this.bILLED2_REC = _value; }


        public String getCOLL2_DUE() { return this.cOLL2_DUE; }
        public void setCOLL2_DUE(String _value) { this.cOLL2_DUE = _value; }


        public String getCOLL2_REC() { return this.cOLL2_REC; }
        public void setCOLL2_REC(String _value) { this.cOLL2_REC = _value; }


        public String getNONBILLED_DUE() { return this.nONBILLED_DUE; }
        public void setNONBILLED_DUE(String _value) { this.nONBILLED_DUE = _value; }


        public String getPRINCIPAL_DUE() { return this.pRINCIPAL_DUE; }
        public void setPRINCIPAL_DUE(String _value) { this.pRINCIPAL_DUE = _value; }


        public String getCONTR_FILA_DUE() { return this.cONTR_FILA_DUE; }
        public void setCONTR_FILA_DUE(String _value) { this.cONTR_FILA_DUE = _value; }


        public String getCONTR_VAL_DUE() { return this.cONTR_VAL_DUE; }
        public void setCONTR_VAL_DUE(String _value) { this.cONTR_VAL_DUE = _value; }


        public String getCONTR_VAL_REC() { return this.cONTR_VAL_REC; }
        public void setCONTR_VAL_REC(String _value) { this.cONTR_VAL_REC = _value; }


        public String getFIN_AMT_DUE() { return this.fIN_AMT_DUE; }
        public void setFIN_AMT_DUE(String _value) { this.fIN_AMT_DUE = _value; }


        public String getFIN_CHRG_DUE() { return this.fIN_CHRG_DUE; }
        public void setFIN_CHRG_DUE(String _value) { this.fIN_CHRG_DUE = _value; }


        public String getINS_FILA_DUE() { return this.iNS_FILA_DUE; }
        public void setINS_FILA_DUE(String _value) { this.iNS_FILA_DUE = _value; }


        public String getINSURANCE_DUE() { return this.iNSURANCE_DUE; }
        public void setINSURANCE_DUE(String _value) { this.iNSURANCE_DUE = _value; }


        public String getINSURANCE_REC() { return this.iNSURANCE_REC; }
        public void setINSURANCE_REC(String _value) { this.iNSURANCE_REC = _value; }


        public String getINS_ACCT_DUE() { return this.iNS_ACCT_DUE; }
        public void setINS_ACCT_DUE(String _value) { this.iNS_ACCT_DUE = _value; }


        public String getINS_ACCT_REC() { return this.iNS_ACCT_REC; }
        public void setINS_ACCT_REC(String _value) { this.iNS_ACCT_REC = _value; }


        public String getINS_CLAIM_DUE() { return this.iNS_CLAIM_DUE; }
        public void setINS_CLAIM_DUE(String _value) { this.iNS_CLAIM_DUE = _value; }


        public String getINS_CLAIM_REC() { return this.iNS_CLAIM_REC; }
        public void setINS_CLAIM_REC(String _value) { this.iNS_CLAIM_REC = _value; }


        public String getINS_CHRG_DUE() { return this.iNS_CHRG_DUE; }
        public void setINS_CHRG_DUE(String _value) { this.iNS_CHRG_DUE = _value; }


        public String getINS_CHRG_REC() { return this.iNS_CHRG_REC; }
        public void setINS_CHRG_REC(String _value) { this.iNS_CHRG_REC = _value; }


        public String getINS_PROV_DUE() { return this.iNS_PROV_DUE; }
        public void setINS_PROV_DUE(String _value) { this.iNS_PROV_DUE = _value; }


        public String getINS_PROV_REC() { return this.iNS_PROV_REC; }
        public void setINS_PROV_REC(String _value) { this.iNS_PROV_REC = _value; }


        public String getCOLL_CHRG_DUE() { return this.cOLL_CHRG_DUE; }
        public void setCOLL_CHRG_DUE(String _value) { this.cOLL_CHRG_DUE = _value; }


        public String getCOLL_CHRG_REC() { return this.cOLL_CHRG_REC; }
        public void setCOLL_CHRG_REC(String _value) { this.cOLL_CHRG_REC = _value; }


        public String getCOLL_GROS_DUE() { return this.cOLL_GROS_DUE; }
        public void setCOLL_GROS_DUE(String _value) { this.cOLL_GROS_DUE = _value; }


        public String getCOLL_GROS_REC() { return this.cOLL_GROS_REC; }
        public void setCOLL_GROS_REC(String _value) { this.cOLL_GROS_REC = _value; }


        public String getBANK_CHRG_DUE() { return this.bANK_CHRG_DUE; }
        public void setBANK_CHRG_DUE(String _value) { this.bANK_CHRG_DUE = _value; }


        public String getBANK_CHRG_REC() { return this.bANK_CHRG_REC; }
        public void setBANK_CHRG_REC(String _value) { this.bANK_CHRG_REC = _value; }


        public String getREPO_CHRG_DUE() { return this.rEPO_CHRG_DUE; }
        public void setREPO_CHRG_DUE(String _value) { this.rEPO_CHRG_DUE = _value; }


        public String getREPO_CHRG_REC() { return this.rEPO_CHRG_REC; }
        public void setREPO_CHRG_REC(String _value) { this.rEPO_CHRG_REC = _value; }


        public String getLEGL_EXP_DUE() { return this.lEGL_EXP_DUE; }
        public void setLEGL_EXP_DUE(String _value) { this.lEGL_EXP_DUE = _value; }


        public String getLEGL_EXP_REC() { return this.lEGL_EXP_REC; }
        public void setLEGL_EXP_REC(String _value) { this.lEGL_EXP_REC = _value; }


        public String getPDD_CHRG_DUE() { return this.pDD_CHRG_DUE; }
        public void setPDD_CHRG_DUE(String _value) { this.pDD_CHRG_DUE = _value; }


        public String getPDD_CHRG_REC() { return this.pDD_CHRG_REC; }
        public void setPDD_CHRG_REC(String _value) { this.pDD_CHRG_REC = _value; }


        public String getSTAMP_DUTY_DUE() { return this.sTAMP_DUTY_DUE; }
        public void setSTAMP_DUTY_DUE(String _value) { this.sTAMP_DUTY_DUE = _value; }


        public String getSTAMP_DUTY_REC() { return this.sTAMP_DUTY_REC; }
        public void setSTAMP_DUTY_REC(String _value) { this.sTAMP_DUTY_REC = _value; }


        public String getSTAMP_DUTY_M_DUE() { return this.sTAMP_DUTY_M_DUE; }
        public void setSTAMP_DUTY_M_DUE(String _value) { this.sTAMP_DUTY_M_DUE = _value; }


        public String getSTAMP_DUTY_M_REC() { return this.sTAMP_DUTY_M_REC; }
        public void setSTAMP_DUTY_M_REC(String _value) { this.sTAMP_DUTY_M_REC = _value; }


        public String getDOCU_CHRG_M_DUE() { return this.dOCU_CHRG_M_DUE; }
        public void setDOCU_CHRG_M_DUE(String _value) { this.dOCU_CHRG_M_DUE = _value; }


        public String getDOCU_CHRG_M_REC() { return this.dOCU_CHRG_M_REC; }
        public void setDOCU_CHRG_M_REC(String _value) { this.dOCU_CHRG_M_REC = _value; }


        public String getSWAP_CHRG_DUE() { return this.sWAP_CHRG_DUE; }
        public void setSWAP_CHRG_DUE(String _value) { this.sWAP_CHRG_DUE = _value; }


        public String getSWAP_CHRG_REC() { return this.sWAP_CHRG_REC; }
        public void setSWAP_CHRG_REC(String _value) { this.sWAP_CHRG_REC = _value; }


        public String getODC_CHRG_DUE() { return this.oDC_CHRG_DUE; }
        public void setODC_CHRG_DUE(String _value) { this.oDC_CHRG_DUE = _value; }


        public String getODC_CHRG_REC() { return this.oDC_CHRG_REC; }
        public void setODC_CHRG_REC(String _value) { this.oDC_CHRG_REC = _value; }


        public String getODC_POSTED_DUE() { return this.oDC_POSTED_DUE; }
        public void setODC_POSTED_DUE(String _value) { this.oDC_POSTED_DUE = _value; }


        public String getODC_POSTED_REC() { return this.oDC_POSTED_REC; }
        public void setODC_POSTED_REC(String _value) { this.oDC_POSTED_REC = _value; }


        public String getOPT_MONEY_DUE() { return this.oPT_MONEY_DUE; }
        public void setOPT_MONEY_DUE(String _value) { this.oPT_MONEY_DUE = _value; }


        public String getOPT_MONEY_REC() { return this.oPT_MONEY_REC; }
        public void setOPT_MONEY_REC(String _value) { this.oPT_MONEY_REC = _value; }


        public String getPREPAY_REB_DUE() { return this.pREPAY_REB_DUE; }
        public void setPREPAY_REB_DUE(String _value) { this.pREPAY_REB_DUE = _value; }


        public String getPREPAY_REB_REC() { return this.pREPAY_REB_REC; }
        public void setPREPAY_REB_REC(String _value) { this.pREPAY_REB_REC = _value; }


        public String getOS_PRI_DUE() { return this.oS_PRI_DUE; }
        public void setOS_PRI_DUE(String _value) { this.oS_PRI_DUE = _value; }


        public String getOS_PRI_REC() { return this.oS_PRI_REC; }
        public void setOS_PRI_REC(String _value) { this.oS_PRI_REC = _value; }


        public String getLAST_INT_DUE() { return this.lAST_INT_DUE; }
        public void setLAST_INT_DUE(String _value) { this.lAST_INT_DUE = _value; }


        public String getLAST_INT_REC() { return this.lAST_INT_REC; }
        public void setLAST_INT_REC(String _value) { this.lAST_INT_REC = _value; }


        public String getPMT_AMT_DUE() { return this.pMT_AMT_DUE; }
        public void setPMT_AMT_DUE(String _value) { this.pMT_AMT_DUE = _value; }


        public String getPMT_AMT_REC() { return this.pMT_AMT_REC; }
        public void setPMT_AMT_REC(String _value) { this.pMT_AMT_REC = _value; }


        public String getSD_AMT_DUE() { return this.sD_AMT_DUE; }
        public void setSD_AMT_DUE(String _value) { this.sD_AMT_DUE = _value; }


        public String getSD_AMT_REC() { return this.sD_AMT_REC; }
        public void setSD_AMT_REC(String _value) { this.sD_AMT_REC = _value; }


        public String getSD_INT_DUE() { return this.sD_INT_DUE; }
        public void setSD_INT_DUE(String _value) { this.sD_INT_DUE = _value; }


        public String getSD_INT_REC() { return this.sD_INT_REC; }
        public void setSD_INT_REC(String _value) { this.sD_INT_REC = _value; }


        public String getPPR_DUE() { return this.pPR_DUE; }
        public void setPPR_DUE(String _value) { this.pPR_DUE = _value; }


        public String getPPR_REC() { return this.pPR_REC; }
        public void setPPR_REC(String _value) { this.pPR_REC = _value; }


        public String getRES_VAL_DUE() { return this.rES_VAL_DUE; }
        public void setRES_VAL_DUE(String _value) { this.rES_VAL_DUE = _value; }


        public String getRES_VAL_REC() { return this.rES_VAL_REC; }
        public void setRES_VAL_REC(String _value) { this.rES_VAL_REC = _value; }


        public String getLM_FEE_DUE() { return this.lM_FEE_DUE; }
        public void setLM_FEE_DUE(String _value) { this.lM_FEE_DUE = _value; }


        public String getLM_FEE_REC() { return this.lM_FEE_REC; }
        public void setLM_FEE_REC(String _value) { this.lM_FEE_REC = _value; }


        public String getDOWNPAY_DUE() { return this.dOWNPAY_DUE; }
        public void setDOWNPAY_DUE(String _value) { this.dOWNPAY_DUE = _value; }


        public String getDOWNPAY_REC() { return this.dOWNPAY_REC; }
        public void setDOWNPAY_REC(String _value) { this.dOWNPAY_REC = _value; }


        public String getOTH_EXP_DUE() { return this.oTH_EXP_DUE; }
        public void setOTH_EXP_DUE(String _value) { this.oTH_EXP_DUE = _value; }


        public String getOTH_EXP_REC() { return this.oTH_EXP_REC; }
        public void setOTH_EXP_REC(String _value) { this.oTH_EXP_REC = _value; }


        public String getWRITE_OFF_DUE() { return this.wRITE_OFF_DUE; }
        public void setWRITE_OFF_DUE(String _value) { this.wRITE_OFF_DUE = _value; }


        public String getWRITE_OFF_REC() { return this.wRITE_OFF_REC; }
        public void setWRITE_OFF_REC(String _value) { this.wRITE_OFF_REC = _value; }


        public String getDEALER_LIAB_DUE() { return this.dEALER_LIAB_DUE; }
        public void setDEALER_LIAB_DUE(String _value) { this.dEALER_LIAB_DUE = _value; }


        public String getDEALER_LIAB_REC() { return this.dEALER_LIAB_REC; }
        public void setDEALER_LIAB_REC(String _value) { this.dEALER_LIAB_REC = _value; }


        public String getADV_EMI_DUE() { return this.aDV_EMI_DUE; }
        public void setADV_EMI_DUE(String _value) { this.aDV_EMI_DUE = _value; }


        public String getADV_EMI_REC() { return this.aDV_EMI_REC; }
        public void setADV_EMI_REC(String _value) { this.aDV_EMI_REC = _value; }


        public String getAD_SERV_CHRG_DUE() { return this.aD_SERV_CHRG_DUE; }
        public void setAD_SERV_CHRG_DUE(String _value) { this.aD_SERV_CHRG_DUE = _value; }


        public String getAD_SERV_CHRG_REC() { return this.aD_SERV_CHRG_REC; }
        public void setAD_SERV_CHRG_REC(String _value) { this.aD_SERV_CHRG_REC = _value; }


        public String getCANC_CHRG_DUE() { return this.cANC_CHRG_DUE; }
        public void setCANC_CHRG_DUE(String _value) { this.cANC_CHRG_DUE = _value; }


        public String getCANC_CHRG_REC() { return this.cANC_CHRG_REC; }
        public void setCANC_CHRG_REC(String _value) { this.cANC_CHRG_REC = _value; }


        public String getFI3_AG_CHRG_DUE() { return this.fI3_AG_CHRG_DUE; }
        public void setFI3_AG_CHRG_DUE(String _value) { this.fI3_AG_CHRG_DUE = _value; }


        public String getFI3_AG_CHRG_REC() { return this.fI3_AG_CHRG_REC; }
        public void setFI3_AG_CHRG_REC(String _value) { this.fI3_AG_CHRG_REC = _value; }


        public String getHOLD_CHRG_DUE() { return this.hOLD_CHRG_DUE; }
        public void setHOLD_CHRG_DUE(String _value) { this.hOLD_CHRG_DUE = _value; }


        public String getHOLD_CHRG_REC() { return this.hOLD_CHRG_REC; }
        public void setHOLD_CHRG_REC(String _value) { this.hOLD_CHRG_REC = _value; }


        public String getLEASE_TAX_DUE() { return this.lEASE_TAX_DUE; }
        public void setLEASE_TAX_DUE(String _value) { this.lEASE_TAX_DUE = _value; }


        public String getLEASE_TAX_REC() { return this.lEASE_TAX_REC; }
        public void setLEASE_TAX_REC(String _value) { this.lEASE_TAX_REC = _value; }


        public String getMISC_DUE() { return this.mISC_DUE; }
        public void setMISC_DUE(String _value) { this.mISC_DUE = _value; }


        public String getMISC_REC() { return this.mISC_REC; }
        public void setMISC_REC(String _value) { this.mISC_REC = _value; }


        public String getRET_CHRG_DUE() { return this.rET_CHRG_DUE; }
        public void setRET_CHRG_DUE(String _value) { this.rET_CHRG_DUE = _value; }


        public String getRET_CHRG_REC() { return this.rET_CHRG_REC; }
        public void setRET_CHRG_REC(String _value) { this.rET_CHRG_REC = _value; }


        public String getRTO_CHRG_DUE() { return this.rTO_CHRG_DUE; }
        public void setRTO_CHRG_DUE(String _value) { this.rTO_CHRG_DUE = _value; }


        public String getRTO_CHRG_REC() { return this.rTO_CHRG_REC; }
        public void setRTO_CHRG_REC(String _value) { this.rTO_CHRG_REC = _value; }


        public String getSERV_CHRG_DUE() { return this.sERV_CHRG_DUE; }
        public void setSERV_CHRG_DUE(String _value) { this.sERV_CHRG_DUE = _value; }


        public String getSERV_CHRG_REC() { return this.sERV_CHRG_REC; }
        public void setSERV_CHRG_REC(String _value) { this.sERV_CHRG_REC = _value; }


        public String getSERV_TAX_EX_DUE() { return this.sERV_TAX_EX_DUE; }
        public void setSERV_TAX_EX_DUE(String _value) { this.sERV_TAX_EX_DUE = _value; }


        public String getSERV_TAX_EX_REC() { return this.sERV_TAX_EX_REC; }
        public void setSERV_TAX_EX_REC(String _value) { this.sERV_TAX_EX_REC = _value; }


        public String getSALES_TAX_DUE() { return this.sALES_TAX_DUE; }
        public void setSALES_TAX_DUE(String _value) { this.sALES_TAX_DUE = _value; }


        public String getSALES_TAX_REC() { return this.sALES_TAX_REC; }
        public void setSALES_TAX_REC(String _value) { this.sALES_TAX_REC = _value; }


        public String getVAL_ADD_TAX_DUE() { return this.vAL_ADD_TAX_DUE; }
        public void setVAL_ADD_TAX_DUE(String _value) { this.vAL_ADD_TAX_DUE = _value; }


        public String getVAL_ADD_TAX_REC() { return this.vAL_ADD_TAX_REC; }
        public void setVAL_ADD_TAX_REC(String _value) { this.vAL_ADD_TAX_REC = _value; }


        public String getWAIVER_DUE() { return this.wAIVER_DUE; }
        public void setWAIVER_DUE(String _value) { this.wAIVER_DUE = _value; }


        public String getWAIVER_REC() { return this.wAIVER_REC; }
        public void setWAIVER_REC(String _value) { this.wAIVER_REC = _value; }


        public String getINCOME_DUE() { return this.iNCOME_DUE; }
        public void setINCOME_DUE(String _value) { this.iNCOME_DUE = _value; }


        public String getINCOME_REC() { return this.iNCOME_REC; }
        public void setINCOME_REC(String _value) { this.iNCOME_REC = _value; }


        public String getTRANS_DUE() { return this.tRANS_DUE; }
        public void setTRANS_DUE(String _value) { this.tRANS_DUE = _value; }


        public String getTRANS_REC() { return this.tRANS_REC; }
        public void setTRANS_REC(String _value) { this.tRANS_REC = _value; }


        public String getCOLL_AG_CHRG_DUE() { return this.cOLL_AG_CHRG_DUE; }
        public void setCOLL_AG_CHRG_DUE(String _value) { this.cOLL_AG_CHRG_DUE = _value; }


        public String getCOLL_AG_CHRG_REC() { return this.cOLL_AG_CHRG_REC; }
        public void setCOLL_AG_CHRG_REC(String _value) { this.cOLL_AG_CHRG_REC = _value; }


        public String getSALE_AMT_REC() { return this.sALE_AMT_REC; }
        public void setSALE_AMT_REC(String _value) { this.sALE_AMT_REC = _value; }


        public String getST_BRI_DUE() { return this.sT_BRI_DUE; }
        public void setST_BRI_DUE(String _value) { this.sT_BRI_DUE = _value; }


        public String getST_BRI_REC() { return this.sT_BRI_REC; }
        public void setST_BRI_REC(String _value) { this.sT_BRI_REC = _value; }


        public String getST_INT_DUE() { return this.sT_INT_DUE; }
        public void setST_INT_DUE(String _value) { this.sT_INT_DUE = _value; }


        public String getST_INT_REC() { return this.sT_INT_REC; }
        public void setST_INT_REC(String _value) { this.sT_INT_REC = _value; }


        public String getEC_INT_DUE() { return this.eC_INT_DUE; }
        public void setEC_INT_DUE(String _value) { this.eC_INT_DUE = _value; }


        public String getEC_INT_REC() { return this.eC_INT_REC; }
        public void setEC_INT_REC(String _value) { this.eC_INT_REC = _value; }


        public String getST_LAST_INT_DUE() { return this.sT_LAST_INT_DUE; }
        public void setST_LAST_INT_DUE(String _value) { this.sT_LAST_INT_DUE = _value; }


        public String getST_LAST_INT_REC() { return this.sT_LAST_INT_REC; }
        public void setST_LAST_INT_REC(String _value) { this.sT_LAST_INT_REC = _value; }


        public String getEC_LAST_INT_DUE() { return this.eC_LAST_INT_DUE; }
        public void setEC_LAST_INT_DUE(String _value) { this.eC_LAST_INT_DUE = _value; }


        public String getEC_LAST_INT_REC() { return this.eC_LAST_INT_REC; }
        public void setEC_LAST_INT_REC(String _value) { this.eC_LAST_INT_REC = _value; }


        public String getST_ODC_DUE() { return this.sT_ODC_DUE; }
        public void setST_ODC_DUE(String _value) { this.sT_ODC_DUE = _value; }


        public String getST_ODC_REC() { return this.sT_ODC_REC; }
        public void setST_ODC_REC(String _value) { this.sT_ODC_REC = _value; }


        public String getEC_ODC_DUE() { return this.eC_ODC_DUE; }
        public void setEC_ODC_DUE(String _value) { this.eC_ODC_DUE = _value; }


        public String getEC_ODC_REC() { return this.eC_ODC_REC; }
        public void setEC_ODC_REC(String _value) { this.eC_ODC_REC = _value; }


        public String getST_PMT_DUE() { return this.sT_PMT_DUE; }
        public void setST_PMT_DUE(String _value) { this.sT_PMT_DUE = _value; }


        public String getST_PMT_REC() { return this.sT_PMT_REC; }
        public void setST_PMT_REC(String _value) { this.sT_PMT_REC = _value; }


        public String getEC_PMT_DUE() { return this.eC_PMT_DUE; }
        public void setEC_PMT_DUE(String _value) { this.eC_PMT_DUE = _value; }


        public String getEC_PMT_REC() { return this.eC_PMT_REC; }
        public void setEC_PMT_REC(String _value) { this.eC_PMT_REC = _value; }


        public String getTOT_EXP_DUE() { return this.tOT_EXP_DUE; }
        public void setTOT_EXP_DUE(String _value) { this.tOT_EXP_DUE = _value; }


        public String getTOT_EXP_REC() { return this.tOT_EXP_REC; }
        public void setTOT_EXP_REC(String _value) { this.tOT_EXP_REC = _value; }


        public String getDEALER_LIAB_RATE() { return this.dEALER_LIAB_RATE; }
        public void setDEALER_LIAB_RATE(String _value) { this.dEALER_LIAB_RATE = _value; }


        public String getDEALER_LIAB() { return this.dEALER_LIAB; }
        public void setDEALER_LIAB(String _value) { this.dEALER_LIAB = _value; }


        public String getPER_DAY_INT() { return this.pER_DAY_INT; }
        public void setPER_DAY_INT(String _value) { this.pER_DAY_INT = _value; }


        public String getREASON_REQ() { return this.rEASON_REQ; }
        public void setREASON_REQ(String _value) { this.rEASON_REQ = _value; }


        public String getREASON_RECOM() { return this.rEASON_RECOM; }
        public void setREASON_RECOM(String _value) { this.rEASON_RECOM = _value; }


        public String getREMARK() { return this.rEMARK; }
        public void setREMARK(String _value) { this.rEMARK = _value; }


        public String getREMARK_HO() { return this.rEMARK_HO; }
        public void setREMARK_HO(String _value) { this.rEMARK_HO = _value; }


        public String getBILLING() { return this.bILLING; }
        public void setBILLING(String _value) { this.bILLING = _value; }


        public String getUNAME() { return this.uNAME; }
        public void setUNAME(String _value) { this.uNAME = _value; }


        public String getDATE_R() { return this.dATE_R; }
        public void setDATE_R(String _value) { this.dATE_R = _value; }


        public String getTIMESTAMP() { return this.tIMESTAMP; }
        public void setTIMESTAMP(String _value) { this.tIMESTAMP = _value; }


        public String getUNAME_WF_CR() { return this.uNAME_WF_CR; }
        public void setUNAME_WF_CR(String _value) { this.uNAME_WF_CR = _value; }


        public String getTS_WF_CR() { return this.tS_WF_CR; }
        public void setTS_WF_CR(String _value) { this.tS_WF_CR = _value; }


        public String getUNAME_WF_DM() { return this.uNAME_WF_DM; }
        public void setUNAME_WF_DM(String _value) { this.uNAME_WF_DM = _value; }


        public String getTS_WF_DM() { return this.tS_WF_DM; }
        public void setTS_WF_DM(String _value) { this.tS_WF_DM = _value; }


        public String getUNAME_APPL() { return this.uNAME_APPL; }
        public void setUNAME_APPL(String _value) { this.uNAME_APPL = _value; }


        public String getDATE_A() { return this.dATE_A; }
        public void setDATE_A(String _value) { this.dATE_A = _value; }


        public String getTS_APPL() { return this.tS_APPL; }
        public void setTS_APPL(String _value) { this.tS_APPL = _value; }


        public String getUNAME_TERM() { return this.uNAME_TERM; }
        public void setUNAME_TERM(String _value) { this.uNAME_TERM = _value; }


        public String getDATE_T() { return this.dATE_T; }
        public void setDATE_T(String _value) { this.dATE_T = _value; }


        public String getTS_TERM() { return this.tS_TERM; }
        public void setTS_TERM(String _value) { this.tS_TERM = _value; }


        public String getPRINT_BLOCK() { return this.pRINT_BLOCK; }
        public void setPRINT_BLOCK(String _value) { this.pRINT_BLOCK = _value; }


        public String getPRINT_STATUS() { return this.pRINT_STATUS; }
        public void setPRINT_STATUS(String _value) { this.pRINT_STATUS = _value; }


        public String getPRINT_UNAME() { return this.pRINT_UNAME; }
        public void setPRINT_UNAME(String _value) { this.pRINT_UNAME = _value; }


        public String getPRINT_DATUM() { return this.pRINT_DATUM; }
        public void setPRINT_DATUM(String _value) { this.pRINT_DATUM = _value; }


        public String getPRINT_TIME() { return this.pRINT_TIME; }
        public void setPRINT_TIME(String _value) { this.pRINT_TIME = _value; }


        public String getPRINT_COLL_BY() { return this.pRINT_COLL_BY; }
        public void setPRINT_COLL_BY(String _value) { this.pRINT_COLL_BY = _value; }


        public String getPRINT_COLL_DT() { return this.pRINT_COLL_DT; }
        public void setPRINT_COLL_DT(String _value) { this.pRINT_COLL_DT = _value; }


        public String getRTO_REF_NO() { return this.rTO_REF_NO; }
        public void setRTO_REF_NO(String _value) { this.rTO_REF_NO = _value; }


        public String getRTO_REF_DATE() { return this.rTO_REF_DATE; }
        public void setRTO_REF_DATE(String _value) { this.rTO_REF_DATE = _value; }


        public String getOBJECTID_CP() { return this.oBJECTID_CP; }
        public void setOBJECTID_CP(String _value) { this.oBJECTID_CP = _value; }


        public String getBTR_INIT() { return this.bTR_INIT; }
        public void setBTR_INIT(String _value) { this.bTR_INIT = _value; }


        public String getBTR_UNAME() { return this.bTR_UNAME; }
        public void setBTR_UNAME(String _value) { this.bTR_UNAME = _value; }


        public String getBTR_INIT_DT() { return this.bTR_INIT_DT; }
        public void setBTR_INIT_DT(String _value) { this.bTR_INIT_DT = _value; }


        public String getBTR_COMP() { return this.bTR_COMP; }
        public void setBTR_COMP(String _value) { this.bTR_COMP = _value; }


        public String getBTR_SELE() { return this.bTR_SELE; }
        public void setBTR_SELE(String _value) { this.bTR_SELE = _value; }


        public String getBTR_ERR() { return this.bTR_ERR; }
        public void setBTR_ERR(String _value) { this.bTR_ERR = _value; }


        public String getST_GL_DUE() { return this.sT_GL_DUE; }
        public void setST_GL_DUE(String _value) { this.sT_GL_DUE = _value; }


        public String getST_GL_REC() { return this.sT_GL_REC; }
        public void setST_GL_REC(String _value) { this.sT_GL_REC = _value; }


        public String getCA_CHRG_DUE() { return this.cA_CHRG_DUE; }
        public void setCA_CHRG_DUE(String _value) { this.cA_CHRG_DUE = _value; }


        public String getCA_CHRG_REC() { return this.cA_CHRG_REC; }
        public void setCA_CHRG_REC(String _value) { this.cA_CHRG_REC = _value; }


        public String getPENDRC_REC() { return this.pENDRC_REC; }
        public void setPENDRC_REC(String _value) { this.pENDRC_REC = _value; }


        public String getPENDRC_DUE() { return this.pENDRC_DUE; }
        public void setPENDRC_DUE(String _value) { this.pENDRC_DUE = _value; }


        public String getCOLL2_ZXY_REC() { return this.cOLL2_ZXY_REC; }
        public void setCOLL2_ZXY_REC(String _value) { this.cOLL2_ZXY_REC = _value; }


        public String getCOLL2_ZXY_DUE() { return this.cOLL2_ZXY_DUE; }
        public void setCOLL2_ZXY_DUE(String _value) { this.cOLL2_ZXY_DUE = _value; }


        public String getZDATE() { return this.zDATE; }
        public void setZDATE(String _value) { this.zDATE = _value; }


        public String getSHKZG() { return this.sHKZG; }
        public void setSHKZG(String _value) { this.sHKZG = _value; }


        public String getSRNO() { return this.sRNO; }
        public void setSRNO(String _value) { this.sRNO = _value; }


        public String getSECCO() { return this.sECCO; }
        public void setSECCO(String _value) { this.sECCO = _value; }


        public String getGJAHR() { return this.gJAHR; }
        public void setGJAHR(String _value) { this.gJAHR = _value; }


        public String getBELNR() { return this.bELNR; }
        public void setBELNR(String _value) { this.bELNR = _value; }


        public String getBUZEI() { return this.bUZEI; }
        public void setBUZEI(String _value) { this.bUZEI = _value; }


        public String getBLART() { return this.bLART; }
        public void setBLART(String _value) { this.bLART = _value; }


        public String getBUDAT() { return this.bUDAT; }
        public void setBUDAT(String _value) { this.bUDAT = _value; }


        public String getBLDAT() { return this.bLDAT; }
        public void setBLDAT(String _value) { this.bLDAT = _value; }


        public String getZFBDT() { return this.zFBDT; }
        public void setZFBDT(String _value) { this.zFBDT = _value; }


        public String getANBWA() { return this.aNBWA; }
        public void setANBWA(String _value) { this.aNBWA = _value; }


        public String getDESCP() { return this.dESCP; }
        public void setDESCP(String _value) { this.dESCP = _value; }


        public String getPAN_ID() { return this.pAN_ID; }
        public void setPAN_ID(String _value) { this.pAN_ID = _value; }


        public String getPAN_NO() { return this.pAN_NO; }
        public void setPAN_NO(String _value) { this.pAN_NO = _value; }


        public String getINST_TYPE() { return this.iNST_TYPE; }
        public void setINST_TYPE(String _value) { this.iNST_TYPE = _value; }


        public String getINST_NO() { return this.iNST_NO; }
        public void setINST_NO(String _value) { this.iNST_NO = _value; }


        public String getINST_DATE() { return this.iNST_DATE; }
        public void setINST_DATE(String _value) { this.iNST_DATE = _value; }


        public String getDRAW_BNK() { return this.dRAW_BNK; }
        public void setDRAW_BNK(String _value) { this.dRAW_BNK = _value; }


        public String getDMBTR() { return this.dMBTR; }
        public void setDMBTR(String _value) { this.dMBTR = _value; }


        public String getDUE() { return this.dUE; }
        public void setDUE(String _value) { this.dUE = _value; }


        public String getREC() { return this.rEC; }
        public void setREC(String _value) { this.rEC = _value; }


        public String getBAL() { return this.bAL; }
        public void setBAL(String _value) { this.bAL = _value; }


        public String getZDAYS() { return this.zDAYS; }
        public void setZDAYS(String _value) { this.zDAYS = _value; }


        public String getODC() { return this.oDC; }
        public void setODC(String _value) { this.oDC = _value; }


        public String getODC_POS() { return this.oDC_POS; }
        public void setODC_POS(String _value) { this.oDC_POS = _value; }


        public String getODC_NEG() { return this.oDC_NEG; }
        public void setODC_NEG(String _value) { this.oDC_NEG = _value; }


        public String getC_DUE() { return this.c_DUE; }
        public void setC_DUE(String _value) { this.c_DUE = _value; }


        public String getC_REC() { return this.c_REC; }
        public void setC_REC(String _value) { this.c_REC = _value; }


        public String getSGTXT() { return this.sGTXT; }
        public void setSGTXT(String _value) { this.sGTXT = _value; }


        public String getDRCR() { return this.dRCR; }
        public void setDRCR(String _value) { this.dRCR = _value; }


        public String getCPUDT() { return this.cPUDT; }
        public void setCPUDT(String _value) { this.cPUDT = _value; }


        public String getTDS_FLAG() { return this.tDS_FLAG; }
        public void setTDS_FLAG(String _value) { this.tDS_FLAG = _value; }


        public String getTANNO() { return this.tANNO; }
        public void setTANNO(String _value) { this.tANNO = _value; }


        public String getPDC_TEXT() { return this.pDC_TEXT; }
        public void setPDC_TEXT(String _value) { this.pDC_TEXT = _value; }


        public String getAD() { return this.aD; }
        public void setAD(String _value) { this.aD = _value; }


        public String getPD() { return this.pD; }
        public void setPD(String _value) { this.pD = _value; }


        public String getINST_FR() { return this.iNST_FR; }
        public void setINST_FR(String _value) { this.iNST_FR = _value; }


        public String getINST_TO() { return this.iNST_TO; }
        public void setINST_TO(String _value) { this.iNST_TO = _value; }


        public String getAMOUNT() { return this.aMOUNT; }
        public void setAMOUNT(String _value) { this.aMOUNT = _value; }


        public String getZYEAR() { return this.zYEAR; }
        public void setZYEAR(String _value) { this.zYEAR = _value; }


        public String getPROVISON_AMT() { return this.pROVISON_AMT; }
        public void setPROVISON_AMT(String _value) { this.pROVISON_AMT = _value; }


        public String getSTOCKID() { return this.sTOCKID; }
        public void setSTOCKID(String _value) { this.sTOCKID = _value; }


        public String getTRANSID() { return this.tRANSID; }
        public void setTRANSID(String _value) { this.tRANSID = _value; }


        public String getSEQNR() { return this.sEQNR; }
        public void setSEQNR(String _value) { this.sEQNR = _value; }


        public String getRWBTR() { return this.rWBTR; }
        public void setRWBTR(String _value) { this.rWBTR = _value; }


        public String getPDCTYPE() { return this.pDCTYPE; }
        public void setPDCTYPE(String _value) { this.pDCTYPE = _value; }


        public String getPDCTYPE_TXT() { return this.pDCTYPE_TXT; }
        public void setPDCTYPE_TXT(String _value) { this.pDCTYPE_TXT = _value; }


        public String getPDCBANK() { return this.pDCBANK; }
        public void setPDCBANK(String _value) { this.pDCBANK = _value; }


        public String getBRNCH() { return this.bRNCH; }
        public void setBRNCH(String _value) { this.bRNCH = _value; }


        public String getCHECTDATE() { return this.cHECTDATE; }
        public void setCHECTDATE(String _value) { this.cHECTDATE = _value; }


        public String getBANKDATE() { return this.bANKDATE; }
        public void setBANKDATE(String _value) { this.bANKDATE = _value; }


        public String getCHECT() { return this.cHECT; }
        public void setCHECT(String _value) { this.cHECT = _value; }


        public String getSTATUS_TXT() { return this.sTATUS_TXT; }
        public void setSTATUS_TXT(String _value) { this.sTATUS_TXT = _value; }


        public String getNOTE() { return this.nOTE; }
        public void setNOTE(String _value) { this.nOTE = _value; }


        public String getNOTE1() { return this.nOTE1; }
        public void setNOTE1(String _value) { this.nOTE1 = _value; }


        public String getNOTE2() { return this.nOTE2; }
        public void setNOTE2(String _value) { this.nOTE2 = _value; }


        public String getID() { return this.iD; }
        public void setID(String _value) { this.iD = _value; }


        public String getNUMBER() { return this.nUMBER; }
        public void setNUMBER(String _value) { this.nUMBER = _value; }


        public String getMESSAGE() { return this.mESSAGE; }
        public void setMESSAGE(String _value) { this.mESSAGE = _value; }


        public String getLOG_NO() { return this.lOG_NO; }
        public void setLOG_NO(String _value) { this.lOG_NO = _value; }


        public String getLOG_MSG_NO() { return this.lOG_MSG_NO; }
        public void setLOG_MSG_NO(String _value) { this.lOG_MSG_NO = _value; }


        public String getMESSAGE_V1() { return this.mESSAGE_V1; }
        public void setMESSAGE_V1(String _value) { this.mESSAGE_V1 = _value; }


        public String getMESSAGE_V2() { return this.mESSAGE_V2; }
        public void setMESSAGE_V2(String _value) { this.mESSAGE_V2 = _value; }


        public String getMESSAGE_V3() { return this.mESSAGE_V3; }
        public void setMESSAGE_V3(String _value) { this.mESSAGE_V3 = _value; }


        public String getMESSAGE_V4() { return this.mESSAGE_V4; }
        public void setMESSAGE_V4(String _value) { this.mESSAGE_V4 = _value; }


        public String getPARAMETER() { return this.pARAMETER; }
        public void setPARAMETER(String _value) { this.pARAMETER = _value; }


        public String getROW() { return this.rOW; }
        public void setROW(String _value) { this.rOW = _value; }


        public String getFIELD() { return this.fIELD; }
        public void setFIELD(String _value) { this.fIELD = _value; }


        public String getSYSTEM() { return this.sYSTEM; }
        public void setSYSTEM(String _value) { this.sYSTEM = _value; }


    }

    public static class IT_CARDEX2 implements Serializable{

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class IT_DUES1 implements Serializable {

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class IT_DUES2 implements Serializable {

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class IT_DUES3 implements Serializable {

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class I_ADDTIONAL implements Serializable {

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class I_ZCARDEX_NOTE implements Serializable{

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }

    public static class RETURN implements Serializable{

        @Element(name="item", required = false)
        Item item;



        public Item getItem() { return this.item; }
        public void setItem(Item _value) { this.item = _value; }


    }
}
