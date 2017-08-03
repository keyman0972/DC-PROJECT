<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/include.Taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<base target="_self" />
<s:include value="/WEB-INF/pages/include/include.Scripts.jsp" />
<script type="text/javascript" src="<s:url value="/jquery/ui/jquery.ui.datepicker.min.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridEditList.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.validation.plugin.js"/>"></script>
<script type="text/javascript" src="<s:url value="/jquery/jquery.alphanumeric.js"/>"></script>
<script type="text/javascript" src="<s:url value="/js/ddsc.input.js"/>"></script>
<script language="javascript">
var oTable;
//畫面欄位檢核
function validate() {
	$("#frmSys01001K").validate("clearPrompt"); 
	
 	$("#dcId").validateRequired({fieldText:'<s:text name="dcId" />'});
 	$("#dcName").validateRequired({fieldText:'<s:text name="dcName" />'});
 	$("#dcTel").validateRequired({fieldText:'<s:text name="dcTel" />'});
 	$("#dcDistArea_0").validateRequired({fieldText:'<s:text name="dcDistArea" />'});
 	$("#suppId_0").validateRequired({fieldText:'<s:text name="suppId" />'});
 	<%-- --%>
    return $("#frmSys01001K").validate("showPromptWithErrors");
}
$(document).ready(function(){
	oTable = $('#tblGrid').initEditGrid({height:'250'});
	
});

</script>
</head>
<body>
<s:form id="frmSys01001K" method="post" theme="simple" action="%{progAction}" target="ifrConfirm">
<s:hidden name="labDcMst.ver" />
 	<div class="progTitle"> 
		<s:include value="/WEB-INF/pages/include/include.EditTitle.jsp" />
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="dcId" />：</td>
				<td width="30%">
					<s:textfield id="dcId" name="labDcMst.dcId" cssClass="enKey" readonly="progAction == 'updateSubmit'" maxlength="10" size="16" cssClass="enKey" />
				</td>
				<td width="20%" class="colNameAlign required">*<s:text name="dcName" />：</td>
				<td width="30%">
					<s:textfield id="dcName" name="labDcMst.dcName" maxlength="32" size="32" />
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcAddr" />：</td>
				<td colspan="3">
					<s:textfield id="dcAddr" name="labDcMst.dcAddr" maxlength="128" size="100" />
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="dcTel" />：</td>
				<td width="30%">
					<s:textfield id="dcTel" name="labDcMst.dcTel"  maxlength="32" size="16" cssClass="numKey" />
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcTimePeriod" />：</td>
				<td width="30%">
					<s:select id="dcTimePeriod" name="labDcMst.dcTimePerIod.optCde" headerValue="%{getText('fix.00162')}" headerKey=""
					list="dcTimePerIodList" listKey="optCde" listValue="optCde + '-' + optCdeNam" value="labDcMst.dcTimePerIod.optCde" />
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign required">*<s:text name="dcDistArea" />：</td>
				<td colspan="3">
					<span>
						<s:iterator value="dcDistAreaList" status="status" >
							<input type="radio" id="dcDistArea_<s:property value="#status.index" />" 
								    name="labDcMst.dcDistArea" value="<s:property value="optCde" />"
									<s:if test="labDcMst.dcDistArea == optCde">checked</s:if> />						
							<s:property value="optCdeNam" />
						</s:iterator>
					</span>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="suppId" />：</td>
				<td colspan="3">
					<span>
						<s:iterator value="labSuppMstListMap" status="status" var="obj" >
							<input type="checkbox" 
								id="suppId_<s:property value="#status.index" />" 
								name="labDcMst.labDcSuppRelList[<s:property value="#status.index" />].labSuppMst.suppId"
								value="<s:property value="#obj.SUPP_ID" />" 
								<s:if test="checkedSuppIdMap.get(#obj.SUPP_ID) != null">checked</s:if>
								 />					
							<label><s:property value="#obj.SUPP_NAME" /></label>					
						</s:iterator>
					</span>
				</td>
			</tr>
	</table>
    </div>
	<!-- 按鍵組合 --> 
	<s:include value="/WEB-INF/pages/include/include.EditButton.jsp" />
	<!-- 按鍵組合 -->
</s:form>
<iframe id="ifrConfirm" name="ifrConfirm" width="100%" height="768" frameborder="0" marginwidth="0" marginheight="0" scrolling="no" style="display:none; border: 0px none"></iframe>
</body>
</html>