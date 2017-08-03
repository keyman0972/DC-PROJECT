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
<script type="text/javascript" src="<s:url value="/ddscPlugin/ddsc.gridList.plugin.js"/>"></script>
<script language="javascript">
$(document).ready(function() {
    $("#tblGrid").initGrid({height:'480'});   
});
</script>
</head>
<body>
<s:form method="post" theme="simple" action="%{progAction}">
	<s:hidden name="labDcMst.ver" />
	<div class="progTitle"> 
       <!-- 程式標題 --> <s:include value="/WEB-INF/pages/include/include.ConfirmTitle.jsp" /> <!-- 程式標題 -->
    </div>
    <div id="tb">
    <table width="100%" border="0" cellpadding="4" cellspacing="0" >
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="dcId" />：</td>
				<td width="30%">
					<s:property  value="labDcMst.dcId" />
					<s:hidden name="labDcMst.dcId"/></td>
				<td width="20%" class="colNameAlign required">*<s:text name="dcName" />：</td>
				<td width="30%">
					<s:property value="labDcMst.dcName" />
					<s:hidden name="labDcMst.dcName"/></td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcAddr" />：</td>
				<td colspan="3">
					<s:property  value="labDcMst.dcAddr" />
					<s:hidden name="labDcMst.dcAddr"/>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="dcTel" />：</td>
				<td width="30%">
					<s:property value="labDcMst.dcTel" />
					<s:hidden name="labDcMst.dcTel"/>
				</td>
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcTimePeriod" />：</td>
				<td width="30%">
					<s:property value="labDcMst.dcTimePerIod.optCde"/>-<s:property value="labDcMst.dcTimePerIod.optCdeNam"/>
					<s:hidden name="labDcMst.dcTimePerIod.optCde" />
				</td>
			</tr>
			<tr class="trBgEven">
				<td width="20%" class="colNameAlign">&nbsp;<s:text name="dcDistArea" />：</td>
				<td colspan="3">
					<span>
						<s:iterator value="dcDistAreaList" status="status" >
							<input type="radio" id="dcDistArea_<s:property value="#status.index" />" 
								    name="labDcMst.dcDistArea" value="<s:property value="optCde" />"
									disabled <s:if test="labDcMst.dcDistArea == optCde">checked</s:if> />						
							<s:property value="optCdeNam" />
						</s:iterator>
						<s:hidden name="labDcMst.dcDistArea" />
					</span>
				</td>
			</tr>
			<tr class="trBgOdd">
				<td width="20%" class="colNameAlign required">*<s:text name="suppId" />：</td>
				<td colspan="3">
					<span>
						<s:iterator value="labDcMst.labDcSuppRelList" status="status" var="obj">
							<s:hidden name="%{'labDcMst.labDcSuppRelList['+#status.index+'].labSuppMst.suppId'}" value="%{labSuppMst.suppId}"/>
						</s:iterator>
						<s:iterator value="labSuppMstListMap" status="status" var="obj" >
							<input type="checkbox" id="suppId_<s:property value="#status.index" />"
								name="labDcMst.labDcSuppRelList[<s:property value="#status.index" />].labSuppMst.suppId"
								value="<s:property value="#obj.SUPP_ID" />" disabled
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
        <s:include value="/WEB-INF/pages/include/include.ConfirmButton.jsp" /> 
    <!-- 按鍵組合 -->
</s:form>
</body>
</html>