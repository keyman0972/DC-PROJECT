package com.ddsc.km.exam.action;

import java.util.*;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.common.comm.service.ICommOptCdeService;
import com.ddsc.core.action.AbstractAction;
import com.ddsc.core.action.IBaseAction;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.exception.DdscAuthException;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.entity.LabDcMst;
import com.ddsc.km.exam.entity.LabDcSuppRel;
import com.ddsc.km.exam.service.ILabDcMstService;
import com.ddsc.km.lab.service.ILabSuppMstService;
import com.ddsc.km.lab.entity.LabSuppMst;

/**
 * <table>
 * <tr>
 * <th>版本</th>
 * <th>日期</th>
 * <th>詳細說明</th>
 * <th>modifier</th>
 * </tr>
 * <tr>
 * <td>1.0</td>
 * <td>2017/7/18</td>
 * <td>新建檔案</td>
 * <td>"keyman"</td>
 * </tr>
 * </table>
 * @author "keyman"
 *
 * 類別說明 :
 *
 *
 * 版權所有 Copyright 2008 © 中菲電腦股份有限公司 本網站內容享有著作權，禁止侵害，違者必究。 <br>
 * (C) Copyright Dimerco Data System Corporation Inc., Ltd. 2009 All Rights
 */

public class exam01001KAction extends AbstractAction implements IBaseAction {

	private static final long serialVersionUID = 137276040725643119L;
	

	private ILabDcMstService labDcMstService;			//
	private ICommOptCdeService commOptCdeService;		//動態產生
	private ILabSuppMstService labSuppMstService;		//
	private List<Map<String,String>> labDcMstListMap;	//顯示資料list.jsp
	
	private LabDcMst labDcMst;							//搜尋條件
	private String suppId;								//搜尋條件
	private List<String> distAreaList; 					//list.jsp request使用
	private List<String> editDistAreaList;				//edit.jsp 配送區域以選取
	private Map<String,String> checkedSuppIdMap;		//response 供應商已選取
	
	private List<CommOptCde> dcDistAreaList;			//顯示配送區域 list.jsp、edit.jsp
	private List<CommOptCde> dcTimePerIodList;			//顯示配送時段 edit.jsp
	private List<Map<String,Object>> labSuppMstListMap;	//顯示供應商代碼 edit.jsp
	
	@Override
	public String init() throws Exception {
		try {

		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public String search() throws Exception {
		try {
			List<String> areaList = new ArrayList<String>();
			if(distAreaList!=null && !distAreaList.isEmpty()){
				for(int i=0;i<distAreaList.size();i++){
					if(distAreaList.get(i) != null){
						areaList.add(distAreaList.get(i));						
					}
				}
			}else{
				areaList = distAreaList;
			}
			
			Map<String, Object> conditions = new HashMap<String, Object>();
			conditions.put("dcId", labDcMst.getDcId());
			conditions.put("dcName", labDcMst.getDcName());
			conditions.put("suppId", this.getSuppId());
			conditions.put("dcdistArea", areaList);
			Pager resultPager = getLabDcMstService().searchByConditions(conditions, getPager(), this.getUserInfo());
			
			List<Map<String, String>> alist = new ArrayList<Map<String, String>>();
			alist = (List<Map<String, String>>) resultPager.getData();

			this.setLabDcMstListMap(alist);
			setPager(resultPager);
			
			if (alist == null || alist.size() <= 0) {
				this.addActionError(this.getText("w.0001"));
			}
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_SEARCH);
		return SUCCESS;
	}
	
	@Override
	public String query() throws Exception {
		try{
			labDcMst = getLabDcMstService().get(labDcMst.getDcId(), this.getUserInfo());

		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		
		setNextAction(ACTION_QUERY);
		return SUCCESS;
	}
	
	@Override
	public String create() throws Exception {
		try {
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
		}
		setNextAction(ACTION_CREATE_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String createSubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				setNextAction(ACTION_CREATE_CONFIRM);
				return RESULT_CONFIRM;
			} else {
				return this.createConfirm();
			}
		} catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
			return RESULT_EDIT;
		} catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()),e.getMsgFullMessage() }));
			return RESULT_EDIT;
		}
	}

	@Override
	public String createConfirm() throws Exception {
		try {
			labDcMst = getLabDcMstService().create(labDcMst, getUserInfo());
			
			setNextAction(ACTION_CREATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_CREATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		try{
			labDcMst = labDcMstService.get(labDcMst.getDcId(), this.getUserInfo());
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));

		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_UPDATE_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String updateSubmit() throws Exception {
		try {
			if (hasConfirm()) {
				setNextAction(ACTION_UPDATE_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				return this.updateConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String updateConfirm() throws Exception {
		try{
			getLabDcMstService().update(labDcMst, getUserInfo());
			setNextAction(ACTION_UPDATE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_UPDATE_SUBMIT);
			return RESULT_EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		try{
			labDcMst = labDcMstService.get(labDcMst.getDcId(), this.getUserInfo());
			
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_DELETE_CONFIRM);
		return SUCCESS;
	}

	@Override
	public String deleteConfirm() throws Exception {
		try{
			labDcMst = getLabDcMstService().delete(labDcMst, getUserInfo());
			setNextAction(ACTION_DELETE);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return SUCCESS;
		}
	}

	@Override
	public String copy() throws Exception {
		try{
			labDcMst = labDcMstService.get(labDcMst.getDcId(), this.getUserInfo());
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		setNextAction(ACTION_COPY_SUBMIT);
		return SUCCESS;
	}

	@Override
	public String copySubmit() throws Exception {
		try {
			if (this.hasConfirm() == true) {
				// 有確認頁
				setNextAction(ACTION_COPY_CONFIRM);
				return RESULT_CONFIRM;
			}
			else {
				return this.copyConfirm();
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return SUCCESS;
		}
	}

	@Override
	public String copyConfirm() throws Exception {
		try{
			labDcMst = getLabDcMstService().create(labDcMst, getUserInfo());
			
			setNextAction(ACTION_COPY);
			return RESULT_SHOW;
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
			setNextAction(ACTION_COPY_SUBMIT);
			return RESULT_EDIT;
		}
	}
	
	@Deprecated
	@Override
	public String approve() throws Exception {
		return null;
	}
	
	/**
	 * 檢核
	 */
	@Override
	public void validate() {
		try {
			setUpInfo();
		}
		catch (DdscAuthException e) {
			throw e;
		}
		catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
		catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(新增頁)
	 */
	public void validateCreateSubmit() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(新增頁)
	 */
	public void validateCreateConfirm() {
		try {
			this.checkPrimaryKey();
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(更新頁)
	 */
	public void validateUpdateSubmit() {
		try {
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(更新頁)
	 */
	public void validateUpdateConfirm() {
		try {
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(刪除頁)
	 */
	public void validateDeleteConfirm() {
		try {
			this.checkValidateRule();
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按送出鈕(複製頁)
	 */
	public void validateCopySubmit() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}

	/**
	 * 檢核 - 按確定鈕(複製頁)
	 */
	public void validateCopyConfirm() {
		try {
			this.checkValidateRule();
			this.checkPrimaryKey();
		}catch (DdscAuthException e) {
			throw e;
		}catch (DdscApplicationException e) {
			// 取得 SQL 錯誤碼，並依多國語系設定顯示於Message box
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] { e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage() }));
		}
	}
	
	private boolean checkPrimaryKey() throws Exception {
		boolean isValid = true;
		
		Map<String, Object> conditions = new HashMap<String, Object>();
		conditions.put("dcId", labDcMst.getDcId());
		if (labDcMstService.getDataRowCountByConditions(conditions, this.getUserInfo()) > 0) {
			this.addFieldError("dcId", this.getText("dcId")+ this.getText("eP.0004"));
			isValid = false;
		}
		
		return isValid;
	}

	private boolean checkValidateRule() throws Exception {
		boolean isValid = true;
		try {
			setUpInfo();
			remNullList(labDcMst.getLabDcSuppRelList());

			// 檢核供應商是否存在
			for(int i=0;i<labDcMst.getLabDcSuppRelList().size();i++){
				suppId = labDcMst.getLabDcSuppRelList().get(i).getLabSuppMst().getSuppId();
				LabSuppMst labSuppMst = this.getLabSuppMstService().get(suppId, this.getUserInfo());					
				if(labSuppMst == null){
					this.addFieldError("suppId", this.getText("suppId") + this.getText("eP.0003"));
					isValid = false;
					break;
				}
			}

			// 檢查參數代碼是否存在 (時段)
			CommOptCde dcTime = getCommOptCdeService().getByKey("DC01", labDcMst.getDcTimePerIod().getOptCde(), this.getUserInfo());
			if (dcTime == null) {
				this.addFieldError("dcTimePeriod", this.getText("dcTimePeriod")+ this.getText("eP.0003"));
				isValid = false;
			}else{
				labDcMst.setDcTimePerIod(dcTime);
			}
				
			// 檢查參數代碼是否存在 (區域)
			CommOptCde dcArea = getCommOptCdeService().getByKey("DC02", labDcMst.getDcDistArea(), this.getUserInfo());
			if (dcArea == null) {
				this.addFieldError("dcDistArea", this.getText("dcDistArea")+ this.getText("eP.0003"));
				isValid = false;
			}else{
				labDcMst.setDcDistArea(dcArea.getOptCde());
			}
		}catch (DdscApplicationException e) {
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}catch (Exception ex) {
			DdscApplicationException e = new DdscApplicationException(ex, this.getUserInfo());
			this.addActionError(this.getText("eP.0022", new String[] {e.getMsgCode(), this.getText(e.getMsgCode()), e.getMsgFullMessage()}));
		}
		return isValid;
	}
	
	public List<LabDcSuppRel> remNullList(List<LabDcSuppRel> alist){
		alist.removeAll(Collections.singleton(null));
//		for (Iterator<LabDcSuppRel> itr = alist.iterator(); itr.hasNext();) {
//			if (itr.next() == null) { itr.remove(); }
//		}
		return alist;
	}
	
	public List<CommOptCde> getDcDistAreaList() {
		if(dcDistAreaList == null){
			this.setDcDistAreaList(this.getCommOptCdeService().getList("DC02", this.getUserInfo()));
		}
		return dcDistAreaList;
	}

	public void setDcDistAreaList(List<CommOptCde> dcDistAreaList) {
		
		this.dcDistAreaList = dcDistAreaList;
	}
	
	public List<CommOptCde> getDcTimePerIodList() {
		if(dcDistAreaList == null){
			this.setDcTimePerIodList(this.getCommOptCdeService().getList("DC01", this.getUserInfo()));
		}
		return dcTimePerIodList;
	}

	public void setDcTimePerIodList(List<CommOptCde> dcTimePerIodList) {
		this.dcTimePerIodList = dcTimePerIodList;
	}

	public List<Map<String, Object>> getLabSuppMstListMap() {
		if(labSuppMstListMap == null){
			this.setLabSuppMstListMap(this.getLabSuppMstService().getList(this.getUserInfo()));
		}
		return labSuppMstListMap;
	}

	public void setLabSuppMstListMap(List<Map<String, Object>> labSuppMstListMap) {
		this.labSuppMstListMap = labSuppMstListMap;
	}

	public ILabDcMstService getLabDcMstService() {
		return labDcMstService;
	}

	public void setLabDcMstService(ILabDcMstService labDcMstService) {
		this.labDcMstService = labDcMstService;
	}

	public ICommOptCdeService getCommOptCdeService() {
		return commOptCdeService;
	}

	public void setCommOptCdeService(ICommOptCdeService commOptCdeService) {
		this.commOptCdeService = commOptCdeService;
	}

	public List<Map<String, String>> getLabDcMstListMap() {
		return labDcMstListMap;
	}

	public void setLabDcMstListMap(List<Map<String, String>> labDcMstListMap) {
		this.labDcMstListMap = labDcMstListMap;
	}

	public LabDcMst getLabDcMst() {
		return labDcMst;
	}

	public void setLabDcMst(LabDcMst labDcMst) {
		this.labDcMst = labDcMst;
	}
		
	public String getSuppId() {
		return suppId;
	}

	public void setSuppId(String suppId) {
		this.suppId = suppId;
	}

	public List<String> getDistAreaList() {
		return distAreaList;
	}

	public void setDistAreaList(List<String> distAreaList) {
		this.distAreaList = distAreaList;
	}

	public ILabSuppMstService getLabSuppMstService() {
		return labSuppMstService;
	}

	public void setLabSuppMstService(ILabSuppMstService labSuppMstService) {
		this.labSuppMstService = labSuppMstService;
	}
	
	public Map<String, String> getCheckedSuppIdMap() {
		if(checkedSuppIdMap == null && labDcMst.getLabDcSuppRelList() !=null){
			
			Map<String,String> aMap = new HashMap<String,String>();
			String suppId;
			for(int i=0;i<labDcMst.getLabDcSuppRelList().size();i++){
				suppId = labDcMst.getLabDcSuppRelList().get(i).getLabSuppMst().getSuppId();
				aMap.put(suppId, "Y");
			}
			setCheckedSuppIdMap(aMap);
		}
		
		return checkedSuppIdMap;
	}
	
	public void setCheckedSuppIdMap(Map<String, String> checkedSuppIdMap) {
		this.checkedSuppIdMap = checkedSuppIdMap;
	}

	public List<String> getEditDistAreaList() {
		return editDistAreaList;
	}

	public void setEditDistAreaList(List<String> editDistAreaList) {
		this.editDistAreaList = editDistAreaList;
	}
}
