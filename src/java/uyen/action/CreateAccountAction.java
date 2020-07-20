/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.action;

import com.opensymphony.xwork2.ActionContext;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import uyen.account.AccountCreateErrors;
import uyen.account.AccountDAO;
import uyen.account.AccountDTO;
import uyen.activation.ActivationDAO;
import uyen.util.DataTypeConverter;
import uyen.util.EmailHelper;

/**
 *
 * @author HP
 */
public class CreateAccountAction {

    //private final static Logger log = Logger.getLogger(CreateAccountAction.class);
    private String email;
    private String password;
    private String confirm;
    private String name;
    private String phone;
    private String address;
    
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    
    private final String EMAIL_VALID = "[A-Za-z0-9.+-_]{1,}"
            + "[@]{1}"
            + "[A-Za-z]{1,6}"
            + "[.]{1}"
            + "[A-Za-z]{2,4}";
    private AccountCreateErrors errors;
    
    public CreateAccountAction() {
    }
    
    public String create() throws Exception {
        String url = FAIL;
        
        boolean foundErr = false;
        try {
            if (email.trim().isEmpty() || !email.matches(EMAIL_VALID)) {
                foundErr = true;
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setEmailFormatErr("Wrong email format!");
            }
            
            if (password.isEmpty() || password.trim().length() < 6 || password.trim().length() > 20) {
                foundErr = true;
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setPasswordLengthErr("Password is required inputted from 6 to 20 characters");
            } else if (!confirm.equals(password)) {
                foundErr = true;
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setConfirmNotMatched("Confirm must match password");
            }
            
            if (name.trim().length() < 2 || name.trim().length() > 30) {
                foundErr = true;
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setNameLengthErr("Name is required inputted from 2 to 30 characters");
            }
            
            int phoneNum = DataTypeConverter.convertStringToInteger(phone);
            if (phone.trim().isEmpty()
                    || phoneNum == -1) {
                foundErr = true;
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setPhoneFormatErr("Wrong phone format");
            }
            
            if (address.trim().length() < 6 || address.trim().length() > 50) {
                foundErr = true;
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setAddressLengthErr("Address is required inputted from 6 to 50 characters");
            }
            
            if (!foundErr) {
                try {
                    AccountDTO dto = new AccountDTO(email, password, name, phoneNum, address);
                    AccountDAO dao = new AccountDAO();
                    boolean created = dao.createAccount(dto);
                    
                    if (created) {
                        String code = EmailHelper.getRandomActivationCode();
                        ActivationDAO activationDAO = new ActivationDAO();
                        boolean check = activationDAO.insertActivationCode(dto, code);
                        
                        if (check) {
                            EmailHelper eh = new EmailHelper();
                            eh.setToEmail(dto.getEmail());
                            eh.sendEmail(code);
                            url = SUCCESS;
                        }
                    }
                } catch (NamingException ex) {
                    //log.error(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg.contains("duplicate")) {
                if (errors == null) {
                    errors = new AccountCreateErrors();
                }
                errors.setEmailIsExisted("This email is already exist.");
            }
        }
        
        if (foundErr) {
            Map request = (Map) ActionContext.getContext().get("request");
            request.put("ERRORS", errors);
        }
        return url;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirm() {
        return confirm;
    }
    
    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
}
