<%-- 
    Document   : stop
    Created on : 24.06.2008, 15:09:03
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
	java.lang.String stopin = "";
	// TODO process result here
	int result = port.cyberStop(stopin);
	out.println("Result = "+result);
    } catch (Exception ex) {
	out.println(ex.toString());
    }
    %>
    <%-- end web service invocation --%><hr/>

       
       <a href="/CyberWS/start.jsp"> Start WS </a>  
    </body>
</html>
