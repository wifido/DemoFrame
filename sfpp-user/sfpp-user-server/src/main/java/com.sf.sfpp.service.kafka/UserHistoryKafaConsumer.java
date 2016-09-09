package com.sf.sfpp.service.kafka;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.sf.kafka.api.consume.ConsumeConfig;
import com.sf.kafka.api.consume.IStringMessageConsumeListener;
import com.sf.kafka.api.consume.KafkaConsumeRetryException;
import com.sf.kafka.api.consume.KafkaConsumerRegister;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.PcompKind;
import com.sf.sfpp.pcomp.common.model.PcompSoftware;
import com.sf.sfpp.pcomp.common.model.PcompTitle;
import com.sf.sfpp.pcomp.common.model.PcompVersion;
import com.sf.sfpp.user.dao.domain.UserHistory;
import com.sf.sfpp.user.service.UserHistoryService;

/**
 * @author 01139940 zyt
 * @version 1.0.0
 * @date 2016年9月7日下午9:06:06
 */
public class UserHistoryKafaConsumer {
	@Autowired
	private UserHistoryService userHistoryService;

	private final static Logger log = LoggerFactory.getLogger(UserHistoryKafaConsumer.class);

	private final ConsumeConfig consumeConfig;

	public UserHistoryKafaConsumer(String systemIdToken, String url, String clusterName, String topic,
			int consumeThreadCount) throws KafkaException {
		consumeConfig = new ConsumeConfig(systemIdToken, url, clusterName, topic, consumeThreadCount);
		KafkaConsumerRegister.registerStringConsumer(consumeConfig, new UserHistoryMessageWriter());
	}

	class UserHistoryMessageWriter implements IStringMessageConsumeListener {
		public void onMessage(List<String> list) throws KafkaConsumeRetryException {
			UserHistory userHistory = new UserHistory();
			for (String message : list) {
				switch (StrUtils.getKafkaMessageType(message)) {
				case PcompConstants.PCOMP_TITLE:
					PcompTitle pcompTitle = JSON.parseObject(StrUtils.getKafkaMessageContent(message),
							PcompTitle.class);
					userHistory = UserOperate.getUserHistory(pcompTitle);
					break;
				case PcompConstants.PCOMP_KIND:
					PcompKind pcompKind = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompKind.class);
					userHistory = UserOperate.getUserHistory(pcompKind);
					break;
				case PcompConstants.PCOMP_SOFTWARE:
					PcompSoftware pcompSoftware = JSON.parseObject(StrUtils.getKafkaMessageContent(message),
							PcompSoftware.class);
					userHistory = UserOperate.getUserHistory(pcompSoftware);
					break;
				case PcompConstants.PCOMP_VERSION:
					PcompVersion pcompVersion = JSON.parseObject(StrUtils.getKafkaMessageContent(message),
							PcompVersion.class);
					userHistory = UserOperate.getUserHistory(pcompVersion);
					break;
				default:
					log.warn(message);
				}
				userHistoryService.addUserHistory(userHistory);
			}
		}
	}
}
