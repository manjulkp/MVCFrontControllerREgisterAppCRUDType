<jsp:useBean id="con" class="com.uttara.mvc.ContactBean" scope="request">
	<jsp:setProperty name="con" property="*"/>
</jsp:useBean>
<jsp:forward page="addContact.do"/>