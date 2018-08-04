<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<div class="confirmPayPageDiv">
	<div class="confirmPayImageDiv">
		<img alt="" src="img/site/confirmPayFlow.png">
		<div class="confirmPayTime1">
			<fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
		</div>
		<div class="confirmPayTime2">
			<fmt:formatDate value="${o.payDate}" pattern="yyyy-MM-dd HH:dd:ss"/>
		</div>
		<div class="confirmPayTime3">
			yyyy-MM-dd HH:mm:ss
		</div>
	</div>
	<div class="confirmPayOrderInfoDiv">
		<div class="confirmPayOrderInfoText">我已收货，同意支付宝付款</div>
	</div>
	<div class="confirmPayOrderItemDiv">
		<div class="confirmPayOrderItemText">订单信息</div>
		<table class="confirmPayOrderItemTable">
			<thead>
				<th colspan="2">宝贝</th>
				<th width="120px">单价</th>
				<th width="120px">数量</th>
				<th width="120px">商品总价</th>
				<th width="120px">运费</th>
			</thead>
			<c:forEach items="${o.orderItems}" var="oi">
				<tr>
					<td><img src="img/productSingle_middle/${oi.product.pImage.id}.jpg" width="50px"></td>
					<td class="confirmPayOrderItemProductLink">
						<a href="#nowhere">${oi.product.name}</a>
					</td>
					<td>￥<fmt:formatNumber type="number" value="${oi.product.originalPrice}" minFractionDigits="2"/></td>
					<td>${oi.number}</td>
					<td><span class="confirmPayProductPrice">￥<fmt:formatNumber type="number" value="${oi.product.promotePrice*oi.number}" minFractionDigits="2"/></span></td>
					<td><span>快递:0.00</span></td>
				</tr>
			</c:forEach>
		</table>
		<div class="confirmPayOrderItemText pull-right">
			实付款:<span class="confirmPayOrderItemSumPrice">￥<fmt:formatNumber type="number" value="${o.total}" minFractionDigits="2"/></span>
		</div>
	</div>
	<div class="confirmPayOrderDetailDiv">
		<table class="confirmPayOrderDetailTable">
			<tr>
				<td>订单编号:</td>
				<td>${o.orderCode} <img width="23px" src="img/site/confirmOrderTmall.png"></td>
			</tr>
			<tr>
				<td>卖家昵称:</td>
				<td>天猫商铺<span class="confirmPayOrderDetailWangWangGif"></span></td>
			</tr>
			<tr>
				<td>成交时间:</td>
				<td><fmt:formatDate value="${o.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</table>
	</div>
	<div class="confirmPayButtonDiv">
		<div class="confirmPayWarning">请收到货后，再确认收货！否则您可能钱货两空！</div>
		<a href="foreorderConfirmed?oid=${o.id}"><button class="confirmPayButton">确认支付</button></a>
	</div>
</div>