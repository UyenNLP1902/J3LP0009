<%-- 
    Document   : display
    Created on : Jun 30, 2020, 6:33:13 PM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Display Page</title>
    </head>
    <body>
            <s:a action="logout">
                Logout
            </s:a>
            <hr>
            <font color="red">
            Welcome, <s:property value="#session.ACCOUNT.name"/>
            </font><br>

            <s:if test="#session.ACCOUNT.role != 1">
                <s:a action="cart_page">View cart</s:a><br>
                <s:a action="history_page">View request history</s:a>
            </s:if> 
            <s:else>
                <s:a action="process_page">Process requests</s:a>
            </s:else>

            <h1>Display Page</h1>
            <s:set var="categoryDao" value="#session.CATEGORY_DAO"/>
            <s:set var="colorDao" value="#session.COLOR_DAO"/>

            <s:form action="search" method="GET">
                From <input type="date" name="fromDate" value="${param.fromDate}" /> 
                to <input type="date" name="toDate" value="${param.toDate}" />

                <s:select label="Category"
                          headerKey="0" headerValue="--Choose a category--"
                          list="#categoryDao.getAllCatgories()"
                          listKey="id" listValue="category"
                          name="category" />
                <br>
                <s:hidden name="currentPage" value="1"/>
                <s:textfield name="name" value="%{#parameters.name}" label="Name"/>
                <s:submit value="Search"/>
            </s:form>

            <br>
            <s:if test="%{list != null}">
                <s:set var="currentPage" value="#request.currentPage"/>
                <s:set var="noOfPages" value="#request.noOfPages"/>
                <table border="1">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Info</th>
                            <th>Booking</th>
                        </tr>
                    </thead>
                    <tbody>
                        <s:iterator value="list">
                        <form action="booking">
                            <tr>
                                <td>
                                    <s:property value="name"/>
                                    <s:hidden name="resourceId" value="%{id}"/>
                                </td>
                                <td>
                                    <b>Color: </b>
                                    <s:property value="#colorDao.searchColor(color).getColor()"/>

                                    <br>
                                    <b>Category: </b>
                                    <s:property value="#categoryDao.searchCategory(category).getCategory()"/>

                                    <br>
                                    <b>Quantity: </b><s:property value="remaining"/>/<s:property value="quantity"/>
                                </td>
                                <td>
                                    <s:if test="#session.ACCOUNT.role == 1">
                                        <font color="red">
                                        Not available
                                        </font>
                                    </s:if>
                                    <s:elseif test="remaining <= 0">
                                        <font color="red">
                                        Out of stock
                                        </font>
                                    </s:elseif>
                                    <s:else>
                                        Choose: <input type="number" name="quantity" value="1"
                                                       min="1" max="<s:property value="remaining"/>"/>
                                        <br>
                                        <s:hidden name="currentPage" value="%{#currentPage}"/>
                                        <input type="hidden" name="fromDate" value="${param.fromDate}" />
                                        <input type="hidden" name="toDate" value="${param.toDate}" />
                                        <input type="hidden" name="name" value="${param.name}" />
                                        <input type="hidden" name="category" value="${param.category}" />
                                        <input type="submit" value="Request" />
                                        <br>
                                        <s:if test="%{#request.REQ_RESULT != null && idBooking == id}">
                                            <font color="red">
                                            ${requestScope.REQ_RESULT}
                                            </font>
                                        </s:if>
                                    </s:else>
                                </td>
                            </tr>
                        </form>
                    </s:iterator>
                </tbody>
            </table>

            <br>
            <s:if test="#currentPage != 1">
                <s:url var="urlRewritting" value="search.action">
                    <s:param name="currentPage" value="#currentPage - 1"/>
                    <s:param name="fromDate" value="fromDate"/>
                    <s:param name="toDate" value="toDate"/>
                    <s:param name="category" value="category"/>
                    <s:param name="name" value="name"/>
                </s:url>
                <a href="${urlRewritting}">Previous</a>
            </s:if>

            Page ${currentPage}/${noOfPages}

            <s:if test="#currentPage < #noOfPages">
                <s:url var="urlRewritting" value="search.action">
                    <s:param name="currentPage" value="#currentPage + 1"/>
                    <s:param name="fromDate" value="fromDate"/>
                    <s:param name="toDate" value="toDate"/>
                    <s:param name="category" value="category"/>
                    <s:param name="name" value="name"/>
                </s:url>
                <a href="${urlRewritting}">Next</a>
            </s:if>
        </s:if>
</body>
</html>
