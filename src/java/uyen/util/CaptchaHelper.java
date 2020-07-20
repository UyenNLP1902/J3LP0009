/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uyen.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

/**
 *
 * @author giang
 */
public class CaptchaHelper {

    public static boolean verify(String gRecaptchaResponse)
            throws IOException {

        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        try {
            URL verifyUrl = new URL(MyConstrain.SITE_VERIFY_URL);
            HttpURLConnection con = (HttpURLConnection) verifyUrl.openConnection();

            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", MyConstrain.USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String params = "secret=" + MyConstrain.SECRET_KEY
                    + "&response=" + gRecaptchaResponse;
            con.setDoOutput(true);
            OutputStream outputStream = con.getOutputStream();
            outputStream.write(params.getBytes());
            outputStream.flush();
            outputStream.close();

            //int responseCode = con.getResponseCode();
            //System.out.println("responseCode=" + responseCode);

            // Lấy Input Stream (Luồng đầu vào) của Connection
            // để đọc dữ liệu gửi đến từ Server.
            InputStream is = con.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            boolean success = jsonObject.getBoolean("success");
            return success;
        } catch (IOException ex) {
            return false;
        }
    }

}
