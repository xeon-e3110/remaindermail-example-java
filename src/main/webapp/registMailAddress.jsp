<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<s:iterator value="errorList">
<p><s:property/></p>
</s:iterator>

<s:if test="%{isRegist}">
メールアドレスを登録しました
</s:if>

<h2>registMailAddress</h2>
<s:form action="registMailAddress">
	<s:textfield name='mailAddress' label="メールアドレス"></s:textfield>
	<s:textfield name='confirmMailAddress' label="確認用メールアドレス"></s:textfield>
	<s:submit value="送信"></s:submit>
</s:form>

</body>
</html>
