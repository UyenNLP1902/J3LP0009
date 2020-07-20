/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.IOException;
import uyen.account.AccountDTO;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;

/**
 *
 * @author HP
 */
public class APIWrapper {

    public static String getAccessToken(String code) throws IOException {
        String response = Request.Post(MyConstrain.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(Form.form().add("client_id", MyConstrain.GOOGLE_CLIENT_ID)
                        .add("client_secret", MyConstrain.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", MyConstrain.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", MyConstrain.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        String token = jobj.get("access_token").toString().replaceAll("\"", "");
        return token;
    }
    
    public static AccountDTO getAccountInfo(String token) throws IOException {
        String link = MyConstrain.GOOGLE_LINK_GET_USER_INFO + token;
        String response = Request.Get(link).execute().returnContent().asString();
        AccountDTO dto = new Gson().fromJson(response, AccountDTO.class);
        if (dto == null) {
            return null;
        }
        return dto;
    }
}
