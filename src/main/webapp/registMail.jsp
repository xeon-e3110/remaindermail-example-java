<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<s:iterator value="registMail.errorList">
<p><s:property/></p>
</s:iterator>

<s:if test="%{registMail.isRegist}">
メールを登録しました
</s:if>

<h2>リマインダーメール登録</h2>

<s:form action="registMail">
	<s:textfield name="registMail.title" label="タイトル"></s:textfield>
	<s:textfield name="registMail.message" label="本文"></s:textfield>
	<s:submit value="登録"></s:submit>
</s:form>

</body>
</html>
