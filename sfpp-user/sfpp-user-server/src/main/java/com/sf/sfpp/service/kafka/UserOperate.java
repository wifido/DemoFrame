package com.sf.sfpp.service.kafka;

import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompState;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.user.dao.domain.UserHistory;

/**
 * @author 01139940 zyt
 * @version 1.0.0
 * @date 2016年9月8日上午10:46:58
 */
/*
 * 前4位：0000增 ; 0001更 ; 0010删 后四位：0000 title ; 0001 kind ; 0010 software ; 0011
 * version targetId:0 title ; 1 kind ; 2 software ; 3 version
 */

public class UserOperate {

	private static final String DELETE = "0000";
	private static final String UPDATE = "0001";
	private static final String ADD = "0010";
	private static final String TITLE = "0000";
	private static final String KIND = "0001";
	private static final String SOFTWARE = "0010";
	private static final String VERSION = "0011";

	// 获取用户行为码
	public synchronized static UserHistory getUserHistory(Object object) {
		UserHistory uh = new UserHistory();
		if (object instanceof PcompTitle) {
			PcompTitle pt = (PcompTitle) object;
			uh.setUserId(pt.getModifiedBy());
			uh.setTargetId(pt.getId());
			uh.setModifiedTime(pt.getModifiedTime());
			if (isDelete(pt)) {
				uh.setAction(StrUtils.makeString(DELETE, TITLE));
				uh.setDescription("删除了\""+pt.getName()+"\"主题");
			} else {
				if (isAdd(pt)) {
					uh.setAction(StrUtils.makeString(ADD, TITLE));
					uh.setDescription("<a href=\"../pcomp/index?pcomp_title="+pt.getId()+"\">"+"添加了\""+pt.getName()+"\"主题</a>");
				} else {
					uh.setAction(StrUtils.makeString(UPDATE, TITLE));
					uh.setDescription("<a href=\"../pcomp/index?pcomp_title="+pt.getId()+"\">"+"更新了\""+pt.getName()+"\"主题</a>");
				}
			}
			return uh;
		} else if (object instanceof PcompKind) {
			if (object instanceof PcompKind) {
				PcompKind pk = (PcompKind) object;
				uh.setUserId(pk.getModifiedBy());
				uh.setTargetId(pk.getId());
				uh.setModifiedTime(pk.getModifiedTime());
				if (isDelete(pk)) {
					uh.setAction(StrUtils.makeString(DELETE, KIND));
					uh.setDescription("删除了\""+pk.getName()+"\"类别");
				} else {
					if (isAdd(pk)) {
						uh.setAction(StrUtils.makeString(ADD, KIND));
						uh.setDescription("<a href=\"../pcomp/pcomp_kind/index?pcomp_kind="+pk.getId()+"\">"+"添加了\""+pk.getName()+"\"类别</a>");
					} else {
						uh.setAction(StrUtils.makeString(UPDATE, KIND));
						uh.setDescription("<a href=\"../pcomp/pcomp_kind/index?pcomp_kind="+pk.getId()+"\">"+"更新了\""+pk.getName()+"\"类别</a>");
					}
				}
			}
		} else if (object instanceof PcompSoftware) {
			if (object instanceof PcompSoftware) {
				PcompSoftware ps = (PcompSoftware) object;
				uh.setUserId(ps.getModifiedBy());
				uh.setTargetId(ps.getId());
				uh.setModifiedTime(ps.getModifiedTime());
				if (isDelete(ps)) {
					uh.setAction(StrUtils.makeString(DELETE, SOFTWARE));
					uh.setDescription("删除了\""+ps.getName()+"\"软件");
				} else {
					if (isAdd(ps)) {
						uh.setAction(StrUtils.makeString(ADD, SOFTWARE));
						uh.setDescription("<a href=\"../pcomp/pcomp_software/index?pcomp_software="+ps.getId()+"\">"+"添加了\""+ps.getName()+"\"软件</a>");
					} else {
						uh.setAction(StrUtils.makeString(UPDATE, SOFTWARE));
						uh.setDescription("<a href=\"../pcomp/pcomp_software/index?pcomp_software="+ps.getId()+"\">"+"更新了\""+ps.getName()+"\"软件</a>");
					}
				}
			}
		} else if (object instanceof PcompVersion) {
			if (object instanceof PcompVersion) {
				PcompVersion pv = (PcompVersion) object;
				uh.setUserId(pv.getModifiedBy());
				uh.setTargetId(pv.getId());
				uh.setModifiedTime(pv.getModifiedTime());
				if (isDelete(pv)) {
					uh.setAction(StrUtils.makeString(DELETE, VERSION));
					uh.setDescription("删除了\""+pv.getVersionNumber()+"\"版本");
				} else {
					if (isAdd(pv)) {
						uh.setAction(StrUtils.makeString(ADD, VERSION));
						uh.setDescription("<a href=\"../pcomp/pcomp_software/index?pcomp_software="+pv.getPcompSoftwareId()+"&sw_nav=download&pcomp_version="+pv.getId()+"\">"+"添加了\""+pv.getVersionNumber()+"\"版本</a>");
					} else {
						uh.setAction(StrUtils.makeString(UPDATE, VERSION));
						uh.setDescription("<a href=\"../pcomp/pcomp_software/index?pcomp_software="+pv.getPcompSoftwareId()+"&sw_nav=download&pcomp_version="+pv.getId()+"\">"+"更新了\""+pv.getVersionNumber()+"\"版本</a>");
					}
				}
			}
		}
		return uh;

	}

	// 是否为删除操作
	static boolean isDelete(PcompState object) {
		if (object.getIsDeleted() == true)
			return true;
		return false;
	}

	// 是否为添加操作
	static boolean isAdd(PcompState object) {
		if (object.getCreatedTime().compareTo(object.getModifiedTime()) == 0)
			return true;
		return false;
	}

}
