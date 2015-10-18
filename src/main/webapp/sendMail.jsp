<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<s:iterator value="errorList">
<p><s:property/></p>
</s:iterator>

<s:if test="%{isSend}">
メールを送信しました
</s:if>

<h2>sendMail</h2>

<s:form action="sendMail">
	<s:textfield name="title" label="タイトル"></s:textfield>
	<s:textfield name="message" label="本文"></s:textfield>
	<s:submit value="送信"></s:submit>
</s:form>

</body>
</html>
