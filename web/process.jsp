<%-- 
    Document   : process
    Created on : Jul 9, 2020, 11:38:20 AM
    Author     : SE140355
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Process Page</title>
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

        <h1>Process requests</h1>

        <s:set var="statusReqDao" value="#session.STATUS_REQ_DAO"/>

        <s:form action="process" method="GET">
            From <input type="date" name="fromDate" value="${param.fromDate}" /> 
            to <input type="date" name="toDate" value="${param.toDate}" />

            <s:select label="Status"
                      headerKey="0" headerValue="--Select status--"
                      list="#statusReqDao.getAllStatus()"
                      listKey="id" listValue="status"
                      name="status"/>
            <br>
            <s:hidden name="currentPage" value="1"/>
            <s:textfield name="resource" value="%{#parameters.resource}" label="Resource name"/>
            <s:textfield name="user" value="%{#parameters.user}" label="User"/>
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
                    <form action="answer">
                        <tr>
                            <td>
                                <p style="width:250px">
                                    <input type="hidden" name="id" value="<s:property value="id"/>" />
                                    <s:property value="dateRequest"/>
                                </p>
                            </td>
                            <td>
                                <p style="width:250px">
                                    <b>User request: </b>
                                    <s:property value="email"/>
                                    <%--
                                        <br>
                                        <b>Resource name: </b>
                                        <s:property value="#request.RESOURCE_DAO.searchResourceByPrimaryKey(resource).getName()"/>
                                        <input type="hidden" name="resId" value="<s:property value="resource"/>" />
                                    --%>
                                    <br>
                                    <b>Status: </b><font color="red">
                                    <s:property value="#statusReqDao.searchStatus(status).getStatus()"/></font>
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
                                        <input type="hidden" name="status" value="${param.status}" />
                                        <input type="hidden" name="resource" value="${param.resource}" />
                                        <input type="hidden" name="user" value="${param.user}" />

                                        <input type="submit" value="Accept" name="btnAction" /><br>
                                        <input type="submit" value="Delete" name="btnAction" />
                                        <s:if test="%{#request.ERROR != null && id == reqId}">
                                            <br><font color="red">
                                            ${requestScope.ERROR}
                                            </font>
                                        </s:if>
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
                <s:param name="status" value="status"/>
                <s:param name="resource" value="resource"/>
                <s:param name="user" value="user"/>
            </s:url>
            <a href="${urlRewritting}">Previous</a>
        </s:if>

        Page ${currentPage}/${noOfPages}

        <s:if test="#currentPage < #noOfPages">
            <s:url var="urlRewritting" value="process.action">
                <s:param name="currentPage" value="#currentPage + 1"/>
                <s:param name="fromDate" value="fromDate"/>
                <s:param name="toDate" value="toDate"/>
                <s:param name="status" value="status"/>
                <s:param name="resource" value="resource"/>
                <s:param name="user" value="user"/>
            </s:url>
            <a href="${urlRewritting}">Next</a>
        </s:if>
    </s:if>
</body>
</html>
