package com.sf.sfpp.web.common.utils;

import com.sf.sfpp.pcomp.common.model.PcompVersionDoucumentDownload;
import com.sf.sfpp.pcomp.common.model.PcompVersionPlatformDownload;
import com.sf.sfpp.pcomp.common.model.extend.PcompVersionExtend;

/**
 * @author Hash Zhang
 * @version 1.0.0
 * @date 2016/8/16
 */
public class EditorMDUtils {
    public static String formatDownload(PcompVersionExtend pcompVersionExtend) {
        StringBuilder stringBuilder = new StringBuilder().append("## 软件下载\n")
                .append("|描述|下载|\n")
                .append("| ------------ | ------------ |\n");
        for (PcompVersionPlatformDownload pcompVersionPlatformDownload : pcompVersionExtend.getPcompVersionPlatformDownloads()) {
            stringBuilder.append(mkRow(pcompVersionPlatformDownload.getPlatform(), formatLink(pcompVersionPlatformDownload.getDownload(),"点我下载")));
        }

        stringBuilder.append("\n## 文档下载\n")
                .append("|描述|下载|\n")
                .append("| ------------ | ------------ |\n");
        for (PcompVersionDoucumentDownload pcompVersionDoucumentDownload : pcompVersionExtend.getPcompVersionDoucumentDownloads()) {
            stringBuilder.append(mkRow(pcompVersionDoucumentDownload.getDescription(),formatLink(pcompVersionDoucumentDownload.getDownload(),"点我下载")));
        }
        return stringBuilder.toString();
    }

    private static String formatLink(String link, String title) {
        return new StringBuilder().append("[").append(title).append("](").append(link).append(")").toString();
    }

    private static String mkRow(String... columns) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|");
        for (String column : columns) {
            stringBuilder.append(column).append("|");
        }
        return stringBuilder.append("\n").toString();
    }
}
