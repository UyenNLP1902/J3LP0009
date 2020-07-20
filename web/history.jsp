<%-- 
    Document   : history
    Created on : Jul 10, 2020, 11:35:29 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
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
        <h1>View Request History</h1>
        <s:form action="history" method="GET">
            Request date: <input type="date" name="fromDate" value="${param.fromDate}" /> 
            to <input type="date" name="toDate" value="${param.toDate}" />

            <s:hidden name="currentPage" value="1"/>
            <s:textfield name="name" value="%{#parameters.name}" label="Resource name"/>
            <s:submit value="Search"/>
        </s:form>

        <br>
        <s:if test="%{list != null}">
            <s:set var="currentPage" value="#request.currentPage"/>
            <s:set var="noOfPages" value="#request.noOfPages"/>
            <table border="1">
                <thead>
                    <tr>
                        <th>Date</th>
                        <th>Info</th>
                        <th>Resource</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="list">
                    <form action="delete">
                        <tr>
                            <td>
                                <p style="width:250px">
                                    <input type="hidden" name="id" value="<s:property value="id"/>" />
                                    <s:property value="dateRequest"/>
                                </p>
                            </td>
                            <td>
                                <p style="width:250px">
                                    <b>Status: </b><font color="red">
                                    <s:property value="#session.STATUS_REQ_DAO.searchStatus(status).getStatus()"/></font>
                                </p>
                            </td>
                            <td>
                                <p style="width:250px">
                                    <s:set var="details" value="#session.DETAIL_DAO.search(id)"/>
                                    <s:iterator value="#details">
                                        •) <s:property value="#request.RESOURCE_DAO.searchResourceByPrimaryKey(resourceId).getName()"/>: <s:property value="quantity"/><br>
                                    </s:iterator>
                                </p>
                            </td>
                            <td>
                                <p style="width:250px">
                                    <s:if test="status == 1">
                                        <s:hidden name="currentPage" value="%{#currentPage}"/>
                                        <input type="hidden" name="fromDate" value="${param.fromDate}" />
                                        <input type="hidden" name="toDate" value="${param.toDate}" />
                                        <input type="hidden" name="name" value="${param.name}" />

                                        <input type="submit" value="Delete" name="btnAction" />
                                    </s:if>
                                    <s:else>
                                        -Answered-
                                    </s:else>
                                </p>
                            </td>
                        </tr>
                    </form>
                </s:iterator>
            </tbody>
        </table>

        <br>
        <s:if test="#currentPage != 1">
            <s:url var="urlRewritting" value="process.action">
                <s:param name="currentPage" value="#currentPage - 1"/>
                <s:param name="fromDate" value="fromDate"/>
                <s:param name="toDate" value="toDate"/>
                <s:param name="name" value="name"/>
            </s:url>
            <a href="${urlRewritting}">Previous</a>
        </s:if>

        Page ${currentPage}/${noOfPages}

        <s:if test="#currentPage < #noOfPages">
            <s:url var="urlRewritting" value="process.action">
                <s:param name="currentPage" value="#currentPage + 1"/>
                <s:param name="fromDate" value="fromDate"/>
                <s:param name="toDate" value="toDate"/>
                <s:param name="name" value="name"/>
            </s:url>
            <a href="${urlRewritting}">Next</a>
        </s:if>
    </s:if>
</body>
</html>
