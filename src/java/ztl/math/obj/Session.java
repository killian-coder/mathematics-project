/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ztl.math.obj;

import com.google.gson.Gson;

/**
 *
 * @author user
 */
public class Session {
    
    private int firstNumber;
    private int secondNumber;
    
    private String id;
    private String MSISDN;
    private Menu menu;
    private String USSDString;
    private int state;
    private NetworkPlan plan;
    private int offer;
    
    private int currentPage;
    
    

    /**
     *
     */
    public Session() {
    }

    public int getFirstNumber() {
        return firstNumber;
    }

    public void setFirstNumber(int firstNumber) {
        this.firstNumber = firstNumber;
    }

    public int getSecondNumber() {
        return secondNumber;
    }

    public void setSecondNumber(int secondNumber) {
        this.secondNumber = secondNumber;
    }
    
    

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
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
    
    public Menu getMenu() {
        return menu;
    }
    
    public void setMenu(Menu menu) {
        this.menu = menu;
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
    public int getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(int state) {
        this.state = state;
    }
    
    public NetworkPlan getPlan() {
        return plan;
    }
    
    public void setPlan(NetworkPlan plan) {
        this.plan = plan;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    
    
    
    @Override
    public String toString() {
        Session s = new Session();
        
        s.setId(id);
        s.setMSISDN(MSISDN);
        s.setFirstNumber(firstNumber);
        s.setSecondNumber(secondNumber);
        s.setMenu(menu);
        s.setPlan(plan);
        s.setOffer(offer);
        s.setState(state);
        s.setUSSDString(USSDString);
        return new Gson().toJson(s);
    }
    
}
