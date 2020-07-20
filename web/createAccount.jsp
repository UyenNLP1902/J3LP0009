<%-- 
    Document   : createAccount
    Created on : Jun 30, 2020, 6:52:31 PM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
    </head>
    <body>
        <h1>Create Account</h1>

        <s:set var="errors" value="%{#request.ERRORS}"/>
        <s:form action="create_account">
            Email: <input type="text" name="email" value="${param.email}" /><br>
            <s:if test="#errors.emailFormatErr != null">
                <font color="red">
                <s:property value="%{#errors.emailFormatErr}"/>
                </font>
            </s:if>
            <br>

            Password: <input type="password" name="password" value="${param.password}" /> (6 - 20 characters)<br>
            <s:if test="#errors.passwordLengthErr != null">
                <font color="red">
                <s:property value="%{#errors.passwordLengthErr}"/>
                </font>
            </s:if>
            <br>

            Confirm: <input type="password" name="confirm" value="" /><br>
            <s:if test="#errors.confirmNotMatched != null">
                <font color="red">
                <s:property value="%{#errors.confirmNotMatched}"/>
                </font>
            </s:if>
            <br>

            Name: <input type="text" name="name" value="${param.name}" /> (2 - 30 characters)<br>
            <s:if test="#errors.nameLengthErr != null">
                <font color="red">
                <s:property value="%{#errors.nameLengthErr}"/>
                </font>
            </s:if>
            <br>

            Phone: <input type="text" name="phone" value="${param.phone}" /><br>
            <s:if test="#errors.phoneFormatErr != null">
                <font color="red">
                <s:property value="%{#errors.phoneFormatErr}"/>
                </font>
            </s:if>
            <br>

            Address: <input type="text" name="address" value="${param.address}" /> (6 - 50 characters)<br>
            <s:if test="#errors.addressLengthErr != null">
                <font color="red">
                <s:property value="%{#errors.addressLengthErr}"/>
                </font>
            </s:if>
            <br>
            <s:submit value="Create"/>
        </s:form>
        <s:if test="#errors.emailIsExisted != null">
            <font color="red">
            <s:property value="%{#errors.emailIsExisted}"/>
            </font>
        </s:if>
    </body>
</html>
