<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<c:forEach items="${codeSearchList.list}" var="code">
	                    	<tr>
	                    		<td style="text-align:center;"><c:out value="${code.userName}" default=""/></td>
	                    		<td style="text-align:center;"><c:out value="${code.phoneNum}" default=""/></td>
	                    		<td style="text-align:center;"><c:out value="${code.authCode}" default=""/></td>
	                    		<td style="text-align:center;"><c:out value="${code.createTime}" default=""/></td>
	                    		<td style="text-align:center;"><c:out value="${code.emailAddress}" default=""/></td>
	                    		<td style="text-align:center;"><%-- <c:out value="${code.expiresStatus}" default=""/> --%>
	                    		<c:choose>
	                    		<c:when test="${code.expiresStatus == 1}">正常</c:when>
	                    		<c:when test="${code.expiresStatus == 2}">过期</c:when>
	                    		<c:when test="${code.expiresStatus == 3}">使用</c:when>
	                    		<%-- <c:otherwise>其他</c:otherwise> --%>
	                    		</c:choose>
	                    		</td>
	                    	</tr>
	                    </c:forEach>
</body>
</html>