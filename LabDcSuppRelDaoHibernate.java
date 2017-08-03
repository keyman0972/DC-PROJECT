package com.ddsc.km.exam.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.HibernateScalarHelper;
import com.ddsc.core.util.LocaleDataHelper;
import com.ddsc.km.exam.dao.ILabDcSuppRelDao;
import com.ddsc.km.exam.entity.LabDcSuppRel;

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

public class LabDcSuppRelDaoHibernate extends GenericDaoHibernate<LabDcSuppRel,String> implements ILabDcSuppRelDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<LabDcSuppRel> getList(String id, UserInfo info) throws DdscApplicationException {
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbsql= new StringBuffer();
		sbsql.append("select rel ");
		sbsql.append("from LabDcSuppRel rel ");
		
		String keyword = "where ";
		if(StringUtils.isNotEmpty(id)){
			sbsql.append(keyword + "rel.labDcMst.dcId = ? ");
			values.add(id);
			keyword = "and ";
		}
		
		return super.findByHQLString(sbsql.toString(), values, info);
	}	
	
	@Override
	public List<Map<String, Object>> getSuppIdList(String id, UserInfo userInfo) throws DdscApplicationException {
		
		String suppName_lang = LocaleDataHelper.getPropertityWithLocalUpper("SUPP_NAME", userInfo.getLocale());
		List<Object> values = new ArrayList<Object>();
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT REL.DC_SUPP_OID, REL.DC_ID,REL.SUPP_ID, ");
		sbsql.append("LSM."+suppName_lang+" AS SUPP_NAME ");
		sbsql.append("FROM LAB_DC_SUPP_REL REL ");
		sbsql.append("INNER JOIN LAB_SUPP_MST LSM ON (REL.SUPP_ID = LSM.SUPP_ID) ");
		
		String keyword = "WHERE ";
		if(StringUtils.isNotEmpty(id)){
			sbsql.append(keyword + "REL.DC_ID = ? ");
			values.add(id);
			keyword = "and";
		}
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("DC_SUPP_OID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("SUPP_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("SUPP_NAME", Hibernate.STRING));

	return super.findBySQLQueryMap(sbsql.toString(), scalarList, values, userInfo);
}
	
	
}
