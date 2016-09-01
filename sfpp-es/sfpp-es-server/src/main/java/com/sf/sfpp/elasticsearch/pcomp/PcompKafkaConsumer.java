package com.sf.sfpp.elasticsearch.pcomp;

import com.alibaba.fastjson.JSON;
import com.sf.kafka.api.consume.ConsumeConfig;
import com.sf.kafka.api.consume.IStringMessageConsumeListener;
import com.sf.kafka.api.consume.KafkaConsumeRetryException;
import com.sf.kafka.api.consume.KafkaConsumerRegister;
import com.sf.kafka.exception.KafkaException;
import com.sf.sfpp.common.Constants;
import com.sf.sfpp.common.utils.StrUtils;
import com.sf.sfpp.elasticsearch.ESClient;
import com.sf.sfpp.pcomp.common.PcompConstants;
import com.sf.sfpp.pcomp.common.model.*;
import com.sf.sfpp.pcomp.common.model.extend.PcompSoftwareExtend;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/17
 */
public class PcompKafkaConsumer {
    private final static Logger log = LoggerFactory.getLogger(PcompKafkaConsumer.class);

    private final ConsumeConfig consumeConfig;

    private final ESClient esClient;

    public PcompKafkaConsumer(String systemIdToken, String url, String clusterName, String topic, int consumeThreadCount, ESClient esClient) throws KafkaException {
        this.esClient = esClient;
        consumeConfig = new ConsumeConfig(systemIdToken, url, clusterName, topic, consumeThreadCount);
        KafkaConsumerRegister.registerStringConsumer(consumeConfig, new EsMessageWriter());
    }

    class EsMessageWriter implements IStringMessageConsumeListener {
        //// TODO: 2016/8/17 考虑ES写入失败
        public void onMessage(List<String> list) throws KafkaConsumeRetryException {
            for (String message : list) {
                switch (StrUtils.getKafkaMessageType(message)) {
                    case PcompConstants.PCOMP_TITLE:
                        PcompTitle pcompTitle = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompTitle.class);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_TITLE, pcompTitle.getId(), pcompTitle);
                        break;
                    case PcompConstants.PCOMP_KIND:
                        PcompKind pcompKind = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompKind.class);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_KIND, pcompKind.getId(), pcompKind);
                        break;
                    case PcompConstants.PCOMP_SOFTWARE:
                        PcompSoftware pcompSoftware = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompSoftware.class);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_SOFTWARE, pcompSoftware.getId(), pcompSoftware);
                        break;
                    case PcompConstants.PCOMP_VERSION:
                        PcompVersion pcompVersion = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompVersion.class);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION, pcompVersion.getId(), pcompVersion);
                        break;
                    case PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD:
                        PcompVersionPlatformDownload pcompVersionPlatformDownload = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompVersionPlatformDownload.class);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD, pcompVersionPlatformDownload.getId(), pcompVersionPlatformDownload);
                        break;
                    case PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT:
                        PcompVersionDoucumentDownload pcompVersionDoucumentDownload = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompVersionDoucumentDownload.class);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT, pcompVersionDoucumentDownload.getId(), pcompVersionDoucumentDownload);
                        break;
                    case PcompConstants.PCOMP_SOFTWARE_EXTEND:
                        PcompSoftwareExtend pcompSoftwareExtend = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompSoftwareExtend.class);
                        pcompSoftware = PcompSoftwareExtend.toPcompSoftware(pcompSoftwareExtend);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_SOFTWARE, pcompSoftware.getId(), pcompSoftware);
                        for (PcompVersion pcompVersion1 : pcompSoftwareExtend.getPcompVersions()) {
                            PcompVersionExtend pcompVersionExtend = (PcompVersionExtend) pcompVersion1;
                            pcompVersion1 = PcompVersionExtend.toPcompVersion(pcompVersionExtend);
                            esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION, pcompVersion1.getId(), pcompVersion1);
                            for (PcompVersionDoucumentDownload pcompVersionDoucumentDownload1 : pcompVersionExtend.getPcompVersionDoucumentDownloads()) {
                                esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT, pcompVersionDoucumentDownload1.getId(), pcompVersionDoucumentDownload1);
                            }

                            for (PcompVersionPlatformDownload pcompVersionPlatformDownload1 : pcompVersionExtend.getPcompVersionPlatformDownloads()) {
                                esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD, pcompVersionPlatformDownload1.getId(), pcompVersionPlatformDownload1);
                            }
                        }
                        break;
                    case PcompConstants.PCOMP_VERSION_EXTEND:
                        PcompVersionExtend pcompSoftwareExtend1 = JSON.parseObject(StrUtils.getKafkaMessageContent(message), PcompVersionExtend.class);
                        PcompVersion pcompVersion1 = PcompVersionExtend.toPcompVersion(pcompSoftwareExtend1);
                        esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION, pcompVersion1.getId(), pcompVersion1);
                        for (PcompVersionDoucumentDownload pcompVersionDoucumentDownload1 : pcompSoftwareExtend1.getPcompVersionDoucumentDownloads()) {
                            esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION_DESCRIPTION_DOCUMENT, pcompVersionDoucumentDownload1.getId(), pcompVersionDoucumentDownload1);
                        }

                        for (PcompVersionPlatformDownload pcompVersionPlatformDownload1 : pcompSoftwareExtend1.getPcompVersionPlatformDownloads()) {
                            esClient.addOrUpdateDocument(Constants.PUBLIC_COMPONENT_SYSTEM, PcompConstants.PCOMP_VERSION_PLATFORM_DOWNLOAD, pcompVersionPlatformDownload1.getId(), pcompVersionPlatformDownload1);
                        }
                        break;
                    default:
                        log.warn(message);
                }
            }
        }
    }
}

