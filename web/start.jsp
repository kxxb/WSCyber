<%-- 
    Document   : start
    Created on : 24.06.2008, 15:10:27
    Author     : Peter.s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
            <%-- start web service invocation --%><hr/>
    <%
    try {
	cyber.client.CyberWSService service = new cyber.client.CyberWSService();
	cyber.client.CyberWSPortType port = service.getCyberWSPort();
	 // TODO initialize WS operation arguments here
	java.lang.String go = "";
	port.cyberStart(go);
        out.println("Thread started ");
    } catch (Exception ex) {
	out.println(ex.toString());
    }
    %>
    <%-- end web service invocation --%><hr/>

        <a href="/CyberWS/stop.jsp">Stop WS</a>  
    </body>
</html>
