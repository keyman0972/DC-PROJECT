package com.ddsc.km.exam.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

import com.ddsc.core.dao.hibernate.GenericDaoHibernate;
import com.ddsc.core.entity.UserInfo;
import com.ddsc.core.exception.DdscApplicationException;
import com.ddsc.core.util.HibernateScalarHelper;
import com.ddsc.core.util.LocaleDataHelper;
import com.ddsc.core.util.Pager;
import com.ddsc.km.exam.dao.ILabDcMstDao;
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

public class LabDcMstDaoHibernate extends GenericDaoHibernate<LabDcMst,String> implements ILabDcMstDao {

	@Override
	public Pager searchByConditions(Map<String, Object> conditions, Pager pager, UserInfo userInfo) throws DdscApplicationException {
		
		String optCdeNam_lang = LocaleDataHelper.getPropertityWithLocalUpper("OPT_CDE_NAM", userInfo.getLocale());
		
		StringBuffer sbsql = new StringBuffer();
		sbsql.append("SELECT DC.DC_ID, DC.DC_NAME, DC.DC_TEL, DC.DC_ADDR, ");
		sbsql.append("OPT1." + optCdeNam_lang + " AS DC_TIME_PERIOD, ");
		sbsql.append("OPT2." + optCdeNam_lang + " AS DC_DIST_AREA, REL2.SUPP_COUNT ");
		sbsql.append("FROM LAB_DC_MST DC ");
		sbsql.append("LEFT JOIN LAB_DC_SUPP_REL REL ON (DC.DC_ID = REL.DC_ID) ");
		sbsql.append("LEFT JOIN ( ");
		sbsql.append("SELECT DC_ID, COUNT(SUPP_ID) AS SUPP_COUNT FROM LAB_DC_SUPP_REL GROUP BY DC_ID ");
		sbsql.append(") REL2 ON (REL.DC_ID = REL2.DC_ID) ");
		sbsql.append("LEFT JOIN COMM_OPT_CDE OPT1 ON (DC.DC_TIME_PERIOD = OPT1.OPT_CDE_OID)  ");
		sbsql.append("INNER JOIN COMM_OPT_CDE OPT2 ON (DC.DC_DIST_AREA = OPT2.OPT_CDE AND OPT2.OPT_CTG_CDE = 'DC02')  ");

		String keyword = "WHERE ";
		List<Object> value = new ArrayList<Object>();
		if (StringUtils.isNotEmpty((String) conditions.get("dcId"))) {
			sbsql.append(keyword + "DC.DC_ID LIKE ? ");
			value.add(conditions.get("dcId") + "%");
			keyword = "AND ";
		}
		if (StringUtils.isNotEmpty((String) conditions.get("dcName"))) {
			sbsql.append(keyword +"DC.DC_NAME LIKE ? ");
			value.add("%"+conditions.get("dcName") + "%");
			keyword = "AND ";
		}
		if (StringUtils.isNotEmpty((String) conditions.get("suppId"))) {
			sbsql.append(keyword +" REL.SUPP_ID LIKE ? ");
			value.add(conditions.get("suppId") + "%");
			keyword = "AND ";
		}
		List<String> dcdistArea = (List<String>) conditions.get("dcdistArea");
		if (dcdistArea != null && !dcdistArea.isEmpty()) {
			sbsql.append(keyword +"OPT2.OPT_CDE IN( "+ this.getSqlQuestionMark(dcdistArea.size())+" ) ");
			value.addAll(dcdistArea);
			keyword = "AND ";
		}
		
		sbsql.append("GROUP BY DC.DC_ID, DC.DC_NAME, DC.DC_TEL, DC.DC_ADDR, DC.DC_DIST_AREA, OPT1." + optCdeNam_lang + ", OPT2." + optCdeNam_lang + ",  REL2.SUPP_COUNT ");
		sbsql.append("ORDER BY DC.DC_ID ");
		
		// 建立List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>(); 並add
		List<HibernateScalarHelper> scalarList = new ArrayList<HibernateScalarHelper>();
		scalarList.add(new HibernateScalarHelper("DC_ID", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_NAME", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_TEL", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_ADDR", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_TIME_PERIOD", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("DC_DIST_AREA", Hibernate.STRING));
		scalarList.add(new HibernateScalarHelper("SUPP_COUNT", Hibernate.STRING));
		
		// 回傳
		return super.findBySQLQueryMapPagination(sbsql.toString(), pager, scalarList, value, userInfo);
	}

}
