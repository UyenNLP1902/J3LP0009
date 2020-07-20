<%-- 
    Document   : login
    Created on : Jun 26, 2020, 9:37:21 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login page</h1>
        <s:form action="login">
            <s:textfield name="email" value="" label="Email"/>
            <s:password name="password" value="" label="Password"/>
            <s:submit value="Login"/>
            <div class="g-recaptcha" 
                 data-sitekey="6LdVA64ZAAAAAGiYGylK_ydL4EdzhenkeNPCkAT1"/>
        </s:form>

        <a href="create_page">Create new account</a><br>
        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/J3LP0009/login_google&response_type=code&client_id=737862210716-n3p1k734evfp0ri9i1g99j3n4l4n2jb7.apps.googleusercontent.com&approval_prompt=force">Login by Google</a>

    </body>
</html>
