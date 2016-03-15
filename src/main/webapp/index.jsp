<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" import="edu.jiangxin.searchengine.search.MyEngine"%>
<%@ page language="java" import="edu.jiangxin.searchengine.search.ResultModel"%>
<%
	request.setCharacterEncoding("UTF-8");
	MyEngine myEngine = null;
	ArrayList<ResultModel> list=null;
	boolean isResult = false;
	String keyword = request.getParameter("keyWord");
	if(keyword!=null)
	{
		keyword = keyword.trim();

		if(keyword.equals(""))
			isResult = false;
		else
		{
			isResult = true;
			ServletContext app = (ServletContext) pageContext.getServletContext();
			String strPath = app.getRealPath("/");

			myEngine = new MyEngine(strPath+"/index.idx");
			list = myEngine.getResultSet(keyword);

			if(list==null)isResult = false;
		}
	}
	else
	{
		keyword="";
	}
 %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>三金搜索，贼好用搜索</title>
<meta name="keywords" content="三金搜索,搜索引擎,搜索">
<meta name="description" content="一个简单的搜索引擎，快速检索，精确匹配">
<meta http-equiv="pragma" content="no-cache" />
<!-- 禁用浏览器缓存 -->
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0">
<link href="CSS/index.css" rel="stylesheet" type="text/css" />
<link href="Images/favicon.ico" rel="bookmark" />
<!-- 设置书签图标 -->
<link href="Images/favicon.ico" rel="shortcut icon" />
<!-- 设置标题 -->
</head>

<body>

	<div class="header" id="header">
		<a href="index.jsp"><img src="Images/Search.jpg" alt="找找看"
			width="900" height="120" align="middle" /></a> <br /> <br />
		<hr align="center" size="100px" />
		<form id="fmSearch" method="post" action="index.jsp">
			<input type="text" name="keyWord" class="inputText"
				value="<%=keyword %>" />&nbsp; <input type="submit" value="搜索"
				class="inputsubmit" />
		</form>
	</div>
	<div class="headerBaseInfo">
		<%
		 if(isResult)
		 {%>
		<a href="#">三金搜索</a> 找到相关内容<%=list.size() %>篇，用时<%=myEngine.getTime() %>毫秒
		<%
		 }
		 %>
	</div>
	<div class="resultBoby">
		<%
			 if(isResult)
			 {
				for(Object o:list)
				{
					ResultModel mod = (ResultModel)o;
			%>
		<div class="bobyTitle">
			<a href="<%=mod.getUrl() %>" target="_blank"> <%= myEngine.highLightKey(mod.getTitle()) %>
			</a>
		</div>
		<div class="bobyContent">
			<%= myEngine.highLightKey(mod.getPartContent()) %>
		</div>
		<div class="bobyUrl">
			<span style="color: Gray;"><%=mod.getUrl() %></span>&nbsp;&nbsp;
		</div>
		<%
				}
			 }else
			 {
			 %>
		<div class="bobyTitle">
			<div class="bobyContent">
				没有查到任何东西，有可能是您的关键词有误，也有可能是我们的搜索引擎还不完善，我们会继续努力地。</div>
		</div>
		<%} %>
	</div>
	<div id="copyright">
		版权所有 JiangXin Copyright © 2014 All Rights Reserved <br /> <a
			href="mailto:jiangxinnju@163.com">联系我们</a>
	</div>
</body>
</html>
