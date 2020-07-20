/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uyen.account;

/**
 *
 * @author HP
 */
public class AccountCreateErrors {

    private String emailFormatErr;
    private String phoneFormatErr;
    private String nameLengthErr;
    private String passwordLengthErr;
    private String addressLengthErr;
    private String confirmNotMatched;
    private String emailIsExisted;

    public AccountCreateErrors() {
    }

    public String getEmailFormatErr() {
        return emailFormatErr;
    }

    public void setEmailFormatErr(String emailFormatErr) {
        this.emailFormatErr = emailFormatErr;
    }

    public String getPhoneFormatErr() {
        return phoneFormatErr;
    }

    public void setPhoneFormatErr(String phoneFormatErr) {
        this.phoneFormatErr = phoneFormatErr;
    }

    public String getNameLengthErr() {
        return nameLengthErr;
    }

    public void setNameLengthErr(String nameLengthErr) {
        this.nameLengthErr = nameLengthErr;
    }

    public String getPasswordLengthErr() {
        return passwordLengthErr;
    }

    public void setPasswordLengthErr(String passwordLengthErr) {
        this.passwordLengthErr = passwordLengthErr;
    }

    public String getAddressLengthErr() {
        return addressLengthErr;
    }

    public void setAddressLengthErr(String addressLengthErr) {
        this.addressLengthErr = addressLengthErr;
    }

    public String getConfirmNotMatched() {
        return confirmNotMatched;
    }

    public void setConfirmNotMatched(String confirmNotMatched) {
        this.confirmNotMatched = confirmNotMatched;
    }

    public String getEmailIsExisted() {
        return emailIsExisted;
    }

    public void setEmailIsExisted(String emailIsExisted) {
        this.emailIsExisted = emailIsExisted;
    }
    
    
}
