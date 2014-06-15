<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	Engine.IndexPro indexPro = null;
	ArrayList<Engine.ResultModel> list=null;
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
			indexPro = new Engine.IndexPro(strPath+"index.txt",strPath+"wordtable.txt");
			list = indexPro.getResultSet(keyword);
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
    <title>鑫胜搜索，不要钱的搜索</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="CSS/index.css" rel="stylesheet" type="text/css" />
    <link href="Images/favicon.ico" rel="bookmark" />
    <link href="Images/favicon.ico" rel="shortcut icon" />
  </head>
  
  <body>
   
    <div class="header">
        <a href="index.jsp">
            <img alt="找找看" src="Images/Search.jpg" height="40" width="120" /></a>
            <form id="fmSearch" method="post" action="index.jsp">
            <input type="text" name="keyWord" class="inputText" value="<%=keyword %>"/>
        &nbsp;
        	<input type="submit" value="Seach" class="inputsubmit"/>
        	</form>
    </div>
    <div class="headerBaseInfo">
    	<%
    	 if(isResult)
    	 {%>
    	 	<a href="#">鑫胜搜索</a> 找到相关内容<%=list.size() %>篇，用时<%=indexPro.getTime() %>毫秒
    	 <%
    	 }
    	 %>
        
    </div>
    <div class="resultBoby">
        <div class="leftBoby">
            <%
	    	 if(isResult)
	    	 {
	    	 	for(Object o:list)
	    	 	{
	    	 		Engine.ResultModel mod = (Engine.ResultModel)o;
	    	 %>
	    	 
	    	 	<div class="bobyTitle">
                        <a href="<%=mod.getUrl() %>" target="_blank">
                            <%= indexPro.HighLightKey(mod.getTitle()) %></a></div>
                    <div class="bobyContent">
                        <%= indexPro.HighLightKey(mod.getPartContent()) %>
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
                       	 没有查到任何东西，要不咱左拐用百度？
                    </div>
               </div>    
             <%} %>      
               
            <div class="leftBobyPager">
                
            </div>
        </div>
        <div class="rightBoby">
            <table border="0">
            	<tr>
            		<td>
            			<form action="http://www.baidu.com/baidu">
							<input type=hidden value='<%=keyword %>' name=word>
							<input type="submit" value="Baidu 搜索">
							<input name=tn type=hidden value="bds">
							<input name=cl type=hidden value="3">
							<input name=ct type=hidden value="2097152">
							<input name=si type=hidden value="www.williamlong.info">
						</form>
            		</td>
            		<td>
	            		<form method=get action="http://www.google.com/search">
							<input type=hidden value='<%=keyword %>' name=q>
							<input type=submit name=btnG value="Google 搜索">
							<input type=hidden name=ie value=GB2312>
							<input type=hidden name=oe value=GB2312>
							<input type=hidden name=hl value=zh-CN>
							<input type=hidden name=domains value="www.williamlong.info">
							<input type=hidden name=sitesearch value="www.williamlong.info">
						</form>
            		</td>
            	</tr>
            </table>
                
        </div>
    </div>
    <div id="relativeSearch">
       
    </div>
    <div id="copyright">
        版权所有 JiangXin/ZhangYinSheng Copyright &copy; 2014 All Rights
        Reserved&nbsp;&nbsp;
    </div> 
    
</body>
</html>
