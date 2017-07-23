<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<html>
<head>
<title></title>
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.popupWindow.plugin.js"/>"></script>	
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script type="text/javascript">
function getParameter() {
	var param = "labDcMst.dcId=" + $("#tblGrid").getSelectedRow().find('td').eq(2).text();
	return param;
}
$(document).ready(function() {
	$("#tblGrid").initGrid({lines:3});
	$('#tb').initPopupWindow({dailogWidth:'960', dailogHeight:'640'});

});
</script>
</head>
<body> 
<s:form id="frmExan01001K" theme="simple" action="%{progAction}" >
	<div class="progTitle">
  		<s:include value="/WEB-INF/pages/include/include.Title.jsp" />
	</div>
	<div id="tb">
		<fieldset id="listFieldset">
		<table width="100%" border="0" cellpadding="2" cellspacing="0">
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcId"/>：</td>
				<td width="30%">
					<s:textfield name="labDcMst.dcId" cssClass="enKey" maxlength="32" size="16" />
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcName"/>：</td>
				<td width="30%">
					<s:textfield name="labDcMst.dcName" maxlength="10" size="32"/>
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="suppId"/>：</td>
				<td width="30%">
					<s:textfield name="suppId" maxlength="10" size="32"/>
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcDistArea"/>：</td>
				<td width="30%">
					<span>
						<s:iterator value="dcDistAreaList" status="status" var="obj" >
							<input type="checkbox" name="distAreaList[<s:property value="#status.index" />]" value="<s:property value="optCde" />" 
							<s:if test="distAreaList[#status.index] == optCde">checked</s:if>  />
							<s:property value="#obj.optCdeNam" />						
						</s:iterator>
					</span>
				</td>
			</tr>
		</table>
		<!-- 按鍵組合 --><s:include value="/WEB-INF/pages/include/include.ListButton.jsp" /><!-- 按鍵組合 --> 
		</fieldset>
		<table id="tblGrid" class ="labDcMstListMap" width="100%" border="0" cellpadding="2" cellspacing="1">
			<thead>
				<tr align="center" bgcolor="#e3e3e3">
					<th width="3%"><s:text name="fix.00164" /></th>
					<th width="10%"><s:text name="fix.00090" /></th>
					<th width="20%"><s:text name="dcId" /></th>   
					<th width="22%"><s:text name="dcName" /></th> 
					<th width="18%"><s:text name="dcTimePeriod" /></th>
					<th width="18%"><s:text name="dcDistArea" /></th>
					<th><s:text name="suppId" /></th>
				</tr>
			 </thead>
			 <tbody>
				 <s:iterator value="labDcMstListMap" status="status">
				 	<tr>
						<td width="3%" id="sn" align="center"><s:property value="#status.index+1" /></td>
						<!-- 表單按鍵 --> 
						<td width="10%"><s:include value="/WEB-INF/pages/include/include.actionButton.jsp" /></td>
						<!-- 表單按鍵 -->
						<td width="20%" align="left"><label><s:property value="DC_ID" /></label></td>
						<td width="22%" align="left"><label><s:property value="DC_NAME" /></label></td>
						<td width="18%" align="center"><label><s:property value="DC_TIME_PERIOD" /></label></td>	
						<td width="18%" align="center"><label><s:property value="DC_DIST_AREA" /></label></td>
						<td align="center"><label><s:property value="SUPP_COUNT" /></label></td>
					</tr>
				 </s:iterator>
			 </tbody>
		</table>
	</div>
	<!-- 分頁按鍵列 --><s:include value="/WEB-INF/pages/include/include.PaginationBar.jsp" /><!-- 分頁按鍵列 -->
</s:form>
</body>
</html>