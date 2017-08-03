package com.ddsc.km.exam.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ddsc.common.comm.entity.CommOptCde;
import com.ddsc.core.entity.BaseEntity;

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

@Entity
@Table(name="LAB_DC_MST")
@NamedQuery(name="findDcMstByKey", query="select dc.dcId, dc.dcName from LabDcMst dc where dc.dcId=:dcId")
public class LabDcMst extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 444990956188357816L;
	
	private String dcId;
	private String dcName;
	private String dcTel;
	private String dcAddr;
	private CommOptCde dcTimePerIod;
	private String dcDistArea;
//	private CommOptCde commDcDistArea;
	
	private List<LabDcSuppRel> labDcSuppRelList;
	
	@Id
	@Column(name="DC_ID")
	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}
	
	@Column(name="DC_NAME")
	public String getDcName() {
		return dcName;
	}

	public void setDcName(String dcName) {
		this.dcName = dcName;
	}
	
	@Column(name="DC_TEL")
	public String getDcTel() {
		return dcTel;
	}

	public void setDcTel(String dcTel) {
		this.dcTel = dcTel;
	}
	
	@Column(name="DC_ADDR")
	public String getDcAddr() {
		return dcAddr;
	}

	public void setDcAddr(String dcAddr) {
		this.dcAddr = dcAddr;
	}

	
	@Column(name="DC_DIST_AREA")
	public String getDcDistArea() {
		return dcDistArea;
	}

	public void setDcDistArea(String dcDistArea) {
		this.dcDistArea = dcDistArea;
	}
	
	@OneToOne(targetEntity = CommOptCde.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "DC_TIME_PERIOD", referencedColumnName = "OPT_CDE_OID")
	public CommOptCde getDcTimePerIod() {
		return dcTimePerIod;
	}

	public void setDcTimePerIod(CommOptCde dcTimePerIod) {
		this.dcTimePerIod = dcTimePerIod;
	}
	
//	@Transient
//	public CommOptCde getCommDcDistArea() {
//		return commDcDistArea;
//	}
//
//	public void setCommDcDistArea(CommOptCde commDcDistArea) {
//		this.commDcDistArea = commDcDistArea;
//		if (null != commDcDistArea ){
//			setDcDistArea(commDcDistArea.getOptCde());
//		}
//	}

	@Transient
	public List<LabDcSuppRel> getLabDcSuppRelList() {
		return labDcSuppRelList;
	}

	public void setLabDcSuppRelList(List<LabDcSuppRel> labDcSuppRelList) {
		this.labDcSuppRelList = labDcSuppRelList;
	}
	
	
	
	
	
	
}
