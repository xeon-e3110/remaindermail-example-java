<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>

<s:iterator value="sendMail.errorList">
<p><s:property/></p>
</s:iterator>

<s:if test="%{sendMail.isSend}">
メールを送信しました
</s:if>

<h2>sendMail</h2>

<s:form action="sendMail">
	<s:textfield name="sendMail.title" label="タイトル"></s:textfield>
	<s:textfield name="sendMail.message" label="本文"></s:textfield>
	<s:submit value="送信"></s:submit>
</s:form>


<s:if test="%{sendMail.isResend}">
一部のアドレスにメールが送れませんでした<br>
送れなかったメールアドレスに再送信する
<s:form action="resendMail">
	<s:hidden name="sendMail.isResend" value="true"></s:hidden>
	<s:submit value="再送信"></s:submit>
</s:form>
</s:if>

</body>
</html>
