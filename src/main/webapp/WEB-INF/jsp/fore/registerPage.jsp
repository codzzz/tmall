<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<script>
$(function(){
	<c:if test="${!empty msg}">
		$("span.errorMessage").html("${msg}")
		$("div.registerErrorMessageDiv").css("visibility","visible")
	</c:if>
	$(".registerForm").submit(function(){
		if(0==$("#name").val().length){
			$("span.errorMessage").html("请输入用户名")
			$("div.registerErrorMessageDiv").css("visibility","visible")
			return false;
		}
		if(0==$("#password").val().length){
			$("span.errorMessage").html("请输入密码")
			$("div.registerErrorMessageDiv").css("visibility","visible")
			return false;
		}
		if(0==$("#repeatpassword").val().length){
			$("span.errorMessage").html("请输入重复密码")
			$("div.registerErrorMessageDiv").css("visibility","visible")
			return false;
		}
		if($("#password").val() != $("#repeatpassword").val()){
			$("span.errorMessage").html("重复密码不一致")
			$("div.registerErrorMessageDiv").css("visibility","visible")
			return false;
		}
		return true;
	})
})
</script>
<form action="foreregister" method="post" class="registerForm">
	<div class="registerDiv">
		<div class="registerErrorMessageDiv">
			<div class="alert alert-danger" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
				<span class="errorMessage"></span>
			</div>
		</div>
		
		<table class="registerTable" align="center">
			<tr>
				<td class="registerTip registerTableLeftTD">设置会员名</td>
				<td></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">登陆名</td>
				<td class="registerTableRightTD"><input id="name" name="name" placeholder="会员名一旦设置成功，无法修改"></td>
			</tr>
			<tr>
				<td class="registerTip registerTableLeftTD">设置登录密码</td>
				<td class="registerTableRightTD">登陆时验证，保护账号信息</td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">登陆密码</td>
				<td class="registerTableRightTD"><input type="password" id="password" name="password" placeholder="设置登录密码"></td>
			</tr>
			<tr>
				<td class="registerTableLeftTD">密码确认</td>
				<td class="registerTableRightTD"><input type="password" id="repeatpassword" placeholder="请再次输入你的密码"></td>
			</tr>
			<tr>
				<td colspan="2" class="registerButtonTD">
					<button type="submit">提交</button>
				</td>
			</tr>
		</table>
	</div>
</form>