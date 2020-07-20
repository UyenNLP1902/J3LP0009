<%-- 
    Document   : cart
    Created on : Jul 16, 2020, 9:11:09 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
    </head>
    <body>
        <s:a action="logout">
            Logout
        </s:a>
        <hr>
        <font color="red">
        Welcome, <s:property value="#session.ACCOUNT.name"/>
        </font><br>
        <s:a action="display_page">
            Return to main page
        </s:a>
        <h1>Cart Page</h1>
        <s:set var="cart" value="#session.CART"/>
        <s:if test="#cart == null">
            <h3>No items</h3>
        </s:if>
        <s:else>
            <s:if test="#cart.items.isEmpty() == false">
                <table border="1">
                    <thead>
                        <tr>
                            <th>Resource</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="%{#cart.items}">
                        <form action="manage">
                            <tr>
                                <td>
                                    <s:property value="key.name"/>
                                    <s:hidden name="id" value="%{key.id}"/>
                                </td>
                                <td>
                                    <input type="number" name="quantity"
                                           value="<s:property value="value"/>"
                                           min="1" max="<s:property value="key.remaining"/>"/>
                                </td>
                                <td>
                                    <input type="submit" value="Update" name="btnAction" /><br>
                                    <input type="submit" value="Remove" name="btnAction" />
                                </td>
                            </tr>
                        </form>
                    </s:iterator>
                    <br>
                    <s:form action="checkout">
                        <s:submit value="Check Out"/>
                    </s:form>
                </tbody>
            </table>

        </s:if>
    </s:else>

</body>
</html>
