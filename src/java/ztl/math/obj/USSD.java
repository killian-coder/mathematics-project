/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ztl.math.obj;

/**
 *
 * @author user
 */
public class USSD {
    private String transactionID;
    private RequestType requestType;
    private String ShortCode;
    private String USSDString;
    private String MSISDN;
    private int ApplicationID;

    /**
     *
     */
    public USSD() {}
    
    /**
     *
     * @param transactionID
     * @param requestType
     * @param ShortCode
     * @param USSDString
     * @param MSISDN
     * @param ApplicationID
     */
    public USSD(String transactionID, RequestType requestType, String ShortCode, String USSDString, String MSISDN, int ApplicationID) {
        this.transactionID = transactionID;
        this.requestType = requestType;
        this.ShortCode = ShortCode;
        this.USSDString = USSDString;
        this.MSISDN = MSISDN;
        this.ApplicationID = ApplicationID;
    }

    /**
     *
     * @return
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     *
     * @param transactionID
     */
    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    /**
     *
     * @return
     */
    public String getShortCode() {
        return ShortCode;
    }

    /**
     *
     * @param ShortCode
     */
    public void setShortCode(String ShortCode) {
        this.ShortCode = ShortCode;
    }

    /**
     *
     * @return
     */
    public String getUSSDString() {
        return USSDString;
    }

    /**
     *
     * @param USSDString
     */
    public void setUSSDString(String USSDString) {
        this.USSDString = USSDString;
    }

    /**
     *
     * @return
     */
    public String getMSISDN() {
        return MSISDN;
    }

    /**
     *
     * @param MSISDN
     */
    public void setMSISDN(String MSISDN) {
        this.MSISDN = MSISDN;
    }

    /**
     *
     * @return
     */
    public int getApplicationID() {
        return ApplicationID;
    }

    /**
     *
     * @param ApplicationID
     */
    public void setApplicationID(int ApplicationID) {
        this.ApplicationID = ApplicationID;
    }

    @Override
    public String toString(){
        return String.format("&TransId=%s&RequestType=%d&MSISDN=%s&AppId=%d&USSDString=%s", this.transactionID,this.requestType.getID(),this.MSISDN,this.ApplicationID,this.USSDString);
    
    }
    
    
}
