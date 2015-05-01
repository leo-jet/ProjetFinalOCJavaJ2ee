<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<style type="text/css"><%@include file="css/styles.css" %></style>
<style type="text/css"><%@include file="css/menu.css" %></style>
<script type="text/javascript" src="javascript/jquery.js"></script>
<script type="text/javascript" src="javascript/menu.js"></script>
<script type="text/javascript" src="javascript/javascript.js"></script>
<base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />