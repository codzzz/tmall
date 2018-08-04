<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<div class="payedDiv">
	<div class="payedTextDiv">
		<img alt="" src="img/site/paySuccess.png">
		<span>你已付款成功</span>
	</div>
	<div class="payedAddressInfo">
		<ul>
			<li>	收货地址：${o.address} ${o.receiver} ${o.mobile}</li>
			<li>实付款:<span class="payedInfoPrice">￥<fmt:formatNumber type="number" value="${param.total}" minFractionDigits="2"/></span></li>
			<li>预计明天到达</li>
		</ul>
		<div class="payedCheckLinkDiv">
			您可以
			<a class="payedChaeckLink" href="forebought">查看已买到的宝贝</a>
			<a class="payedCheckLink" href="forebought">查看交易详情</a>
		</div>
	</div>
	<div class="payedSeperateLine"></div>
	<div class="warningDiv">
		<img alt="" src="img/site/warning.png">
		<b>安全提醒:</b>下单后，<span class="redColor boldWord">用QQ给您发送链接办理退款的都是骗子！</span>天猫不存在系统升级，订单异常等问题谨防假冒客服电话诈骗！
	</div>
</div>