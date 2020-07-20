<%-- 
    Document   : activate
    Created on : Jul 4, 2020, 1:14:45 PM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Activation Page</title>
    </head>
    <body>
        <s:a action="logout">Logout</s:a>
            <hr>
            <font color="red">
            Welcome, <s:property value="%{#session.ACCOUNT.name}"/>
        </font>
        <br>
        <h2>Check your email for activation code</h2>
        <s:form action="activate">
            <s:textfield name="code" value="" label="Input your activation code here"/>
            <s:submit value="Activate"/>
        </s:form>
        <s:if test="#request.ERROR != null">
            <font color="red">
            ${requestScope.ERROR}
            </font>
        </s:if>
        <br>
        <s:a action="resend">Resend activation code</s:a>
        <s:if test="#request.RESEND != null">
            <br>
            <font color="red">
            ${requestScope.RESEND}
            </font>
        </s:if>
    </body>
</html>
