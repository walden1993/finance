<%@page import="com.sun.org.apache.xml.internal.serialize.Printer"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
   <%@page import="com.sp2p.constants.IConstants"%>
    <title><%=IConstants.SEO_TITLE %></title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta name="keywords" content="<%=IConstants.SEO_KEYWORDS%>">
	<meta name="description" content="<%=IConstants.SEO_DESCRIPTION %>">
	<%=IConstants.SEO_OTHERTAGS %>
	<script type="text/javascript" src="script/jquery-2.0.js"></script>
    <style type="text/css">
      ul,li{margin:0;padding:0}
      #scrollDiv{height:182px;overflow:hidden}
      .load{width:18px;height:18px;margin:10px;}
    </style>