<jsp:useBean id="log" class="com.uttara.mvc.LoginBean" scope="request">
	<jsp:setProperty name="log" property="*"/>
</jsp:useBean>
<jsp:forward page="login.do"/>