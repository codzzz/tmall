<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<div class="buyPageDiv">
	<form action="forecreateOrder" method="post">
		<div class="buyFlow">
			<img src="img/site/simpleLogo.png" class="pull-left">
			<img src="img/site/buyflow.png" class="pull-right">
			<div style="clear:both"></div>
		</div>
		<div class="address">
			<div class="addressTip">输入收货地址</div>
			<div>
				<table class="addressTable">
					<tr>
						<td class="firstColumn">详细地址<soan class="redStar">*</soan></td>
						<td><textarea name="address" placeholder="建议您如实填写详细收货地址，例如接到名称，门牌好吗，楼层和房间号等信息"></textarea></td>
					</tr>
					<tr>
						<td>邮政编码</td>
						<td><input type="text" name="post" placeholder="如果您不清楚邮递区号，请填写000000"></td>
					</tr>
					<tr>
						<td>收货人姓名<soan class="redStar">*</soan></td>
						<td><input type="text" name="receiver" placeholder="长度不超过25个字符"></td>
					</tr>
					<tr>
						<td>手机号码<soan class="redStar">*</soan></td>
						<td><input type="text" name="mobile" placeholder="请输入11位手机号码"></td>
					</tr>
				</table>
			</div>
		</div>
		<div class="productList">
			<div class="productListTip'">确认订单信息</div>
			<table class="productListTable">
				<thead>
					<tr>
						<th colspan="2" class="productListTableFirstColumn">
							<img class="tmallbuy" src="img/site/tmallbuy.png">
							<a href="#nowhere" class="marketLink">店铺：天猫店铺</a>
							<a class="wangwangLink" href="#nowhere"><span class="wangwangGif"></span></a>
						</th>
					</tr>
				</thead>
			</table>
		</div>
	</form>

</div>
