package com.sf.sfpp.service.kafka;

import com.alibaba.fastjson.JSON;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.pcomp.common.model.UserHistoryMessageVo;
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

	private static final String DELETE = "delete";
	private static final String UPDATE = "update";
	private static final String ADD = "add";
	private static final String TITLE = "pcomp_title";
	private static final String KIND = "pcomp_kind";
	private static final String SOFTWARE = "pcomp_software";
	private static final String VERSION = "pcomp_version";
	
	/**
	 * 获取添加内容的UserHistory对象
	 * @return UserHistory
	 */
	public synchronized static UserHistory getUserHistory(String message) {

	    String messageType = StrUtils.getKafkaMessageType(message);
	    
	    UserHistoryMessageVo userHistoryMessageVo = JSON.parseObject
	            (StrUtils.getKafkaMessageContent(message),UserHistoryMessageVo.class);
	    
	    UserHistory userHistory = new UserHistory();
	    userHistory.setUserId(userHistoryMessageVo.getModifiedBy());
	    userHistory.setTargetId(userHistoryMessageVo.getId());
	    userHistory.setCreatedTime(userHistoryMessageVo.getModifiedTime());
	    
	    String action = getAction(userHistoryMessageVo);
	    userHistory.setAction(action);
	    
	    userHistory.setTargetKind(messageType);
	    
	    String descrition = getDescription(action,userHistoryMessageVo,messageType);
	    userHistory.setDescription(descrition);
	    return userHistory;
	}
	
	
	
	/**
	 * 获取描述description字段
	 * @param messageType
	 * @param userHistoryMessageVo
	 * @return String 
	 */
	static String getDescription(String action,UserHistoryMessageVo userHistoryMessageVo,String type){
	    String description =null;
	    if (DELETE.equals(action)) {
	        description = "删除了\""+userHistoryMessageVo.getName()+"\""+toChinese(type);
        } else {
            if (ADD.equals(action)) {
                description = "<a href=\"../pcomp/index?pcomp_title="+userHistoryMessageVo.getId()+"\">"+"添加了\""+userHistoryMessageVo.getName()+"\""+toChinese(type)+"</a>";
            } else {
                description = "<a href=\"../pcomp/index?pcomp_title="+userHistoryMessageVo.getId()+"\">"+"更新了\""+userHistoryMessageVo.getName()+"\""+toChinese(type)+"</a>";
            }
        }
	    return description;
	}
	/**
	 * 将类型转换成中文
	 * @return String
	 */
	
	static String toChinese(String message){
	    String str = null;
	    switch (message) {
        case PcompConstants.PCOMP_TITLE:
            str = PcompConstants.PCOMP_TITLE_ZH;
            break;
        case PcompConstants.PCOMP_KIND:
            str = PcompConstants.PCOMP_KIND_ZH;
            break;
        case PcompConstants.PCOMP_SOFTWARE:
            str =  PcompConstants.PCOMP_SOFTWARE_ZH;
            break;
        case PcompConstants.PCOMP_VERSION:
            str =  PcompConstants.PCOMP_VERSION_ZH;
            break;
        }
	    return str;
	}
	
	/**
	 * 获取用户操作类型action字段（增/删/改） 
	 * @return String
	 */
	static String getAction(UserHistoryMessageVo userHistoryMessageVo){
	    if (isDelete(userHistoryMessageVo)) {
            return DELETE; 
        } else {
            if (isAdd(userHistoryMessageVo)) {
                return ADD;
            } else {
                return UPDATE;
            }
        }
	}

	/**
	 * 判断是否为删除操作
	 * @return boolean
	 */
	static boolean isDelete(UserHistoryMessageVo userHistoryMessageVo) {
		if (userHistoryMessageVo.getIsDeleted() == true)
			return true;
		return false;
	}

	/**
	 * 判断是否为添加操作
	 * @return boolean
	 */
	static boolean isAdd(UserHistoryMessageVo userHistoryMessageVo) {
		if (userHistoryMessageVo.getCreatedTime().compareTo(userHistoryMessageVo.getModifiedTime()) == 0)
			return true;
		return false;
	}

}
