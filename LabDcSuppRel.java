package com.ddsc.km.exam.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ddsc.core.entity.BaseEntity;
import com.ddsc.km.lab.entity.LabSuppMst;
import com.ddsc.km.exam.entity.LabDcMst;

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
@Table(name="LAB_DC_SUPP_REL")
public class LabDcSuppRel extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = -3082263281733268822L;
	
	private String dcSuppOid;
	private LabDcMst labDcMst;
	private LabSuppMst labSuppMst;
	
	@Id
	@Column (name = "DC_SUPP_OID")
	@GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getDcSuppOid() {
		return dcSuppOid;
	}
	
	public void setDcSuppOid(String dcSuppOid) {
		this.dcSuppOid = dcSuppOid;
	}
	
	@OneToOne(targetEntity = LabDcMst.class, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "DC_ID", referencedColumnName = "DC_ID")
	public LabDcMst getLabDcMst() {
		return labDcMst;
	}

	public void setLabDcMst(LabDcMst labDcMst) {
		this.labDcMst = labDcMst;
	}

	@OneToOne(targetEntity = LabSuppMst.class, fetch = FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "SUPP_ID", referencedColumnName = "SUPP_ID")
	public LabSuppMst getLabSuppMst() {
		return labSuppMst;
	}

	public void setLabSuppMst(LabSuppMst labSuppMst) {
		this.labSuppMst = labSuppMst;
	}
}
