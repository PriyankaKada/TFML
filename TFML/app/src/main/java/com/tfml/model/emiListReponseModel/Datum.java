package com.tfml.model.emiListReponseModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by webwerks on 6/10/16.
 */

public class Datum {
    @SerializedName("COMP_CD")
    @Expose
    private String cOMPCD;
    @SerializedName("OBJECT_ID")
    @Expose
    private String oBJECTID;
    @SerializedName("A_DATE")
    @Expose
    private String aDATE;
    @SerializedName("AMOUNT")
    @Expose
    private String aMOUNT;
    @SerializedName("FIN_CHRG")
    @Expose
    private String fINCHRG;
    @SerializedName("PRINCP")
    @Expose
    private String pRINCP;
    @SerializedName("INS_AMT")
    @Expose
    private String iNSAMT;
    @SerializedName("PRI_AMT")
    @Expose
    private String pRIAMT;

    /**
     *
     * @return
     * The cOMPCD
     */
    public String getCOMPCD() {
        return cOMPCD;
    }

    /**
     *
     * @param cOMPCD
     * The COMP_CD
     */
    public void setCOMPCD(String cOMPCD) {
        this.cOMPCD = cOMPCD;
    }

    /**
     *
     * @return
     * The oBJECTID
     */
    public String getOBJECTID() {
        return oBJECTID;
    }

    /**
     *
     * @param oBJECTID
     * The OBJECT_ID
     */
    public void setOBJECTID(String oBJECTID) {
        this.oBJECTID = oBJECTID;
    }

    /**
     *
     * @return
     * The aDATE
     */
    public String getADATE() {
        return aDATE;
    }

    /**
     *
     * @param aDATE
     * The A_DATE
     */
    public void setADATE(String aDATE) {
        this.aDATE = aDATE;
    }

    /**
     *
     * @return
     * The aMOUNT
     */
    public String getAMOUNT() {
        return aMOUNT;
    }

    /**
     *
     * @param aMOUNT
     * The AMOUNT
     */
    public void setAMOUNT(String aMOUNT) {
        this.aMOUNT = aMOUNT;
    }

    /**
     *
     * @return
     * The fINCHRG
     */
    public String getFINCHRG() {
        return fINCHRG;
    }

    /**
     *
     * @param fINCHRG
     * The FIN_CHRG
     */
    public void setFINCHRG(String fINCHRG) {
        this.fINCHRG = fINCHRG;
    }

    /**
     *
     * @return
     * The pRINCP
     */
    public String getPRINCP() {
        return pRINCP;
    }

    /**
     *
     * @param pRINCP
     * The PRINCP
     */
    public void setPRINCP(String pRINCP) {
        this.pRINCP = pRINCP;
    }

    /**
     *
     * @return
     * The iNSAMT
     */
    public String getINSAMT() {
        return iNSAMT;
    }

    /**
     *
     * @param iNSAMT
     * The INS_AMT
     */
    public void setINSAMT(String iNSAMT) {
        this.iNSAMT = iNSAMT;
    }

    /**
     *
     * @return
     * The pRIAMT
     */
    public String getPRIAMT() {
        return pRIAMT;
    }

    /**
     *
     * @param pRIAMT
     * The PRI_AMT
     */
    public void setPRIAMT(String pRIAMT) {
        this.pRIAMT = pRIAMT;
    }
}
