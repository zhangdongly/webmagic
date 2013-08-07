/**
 * 
 */
package us.codecraft.webmagic.samples.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.samples.model.SinaHouseInfo;

import com.snsxiu.common.util.JSONUtils;

/**
 * @author zhangdong zhangdong147896325@163.com
 * 
 *         2013-7-11 上午10:06:33
 */
public class CalaHouseInfo {

    public static void main(String[] args) {

        Set<String> downLoadedUrls = downLoadedUrl();

        List<SinaHouseInfo> houseInfoList = new ArrayList<SinaHouseInfo>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/data/temp/result.all1.txt"));
            String info = null;
            while ((info = reader.readLine()) != null) {
                houseInfoList.add(JSONUtils.fromJSON(info, SinaHouseInfo.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!CollectionUtils.isEmpty(houseInfoList)) {
            for (SinaHouseInfo s : houseInfoList) {
                if (!downLoadedUrls.contains(s.getUrl())) {
                    if (s.getPicMap() != null) {
                        for (String imgUrl : s.getPicMap().keySet()) {
                            SinaPicDownLoadManager.downLoadOriginPic(imgUrl);
                        }
                    }
                    if (s.getHuxingMap() != null) {
                        for (String imgUrl : s.getHuxingMap().keySet()) {
                            SinaPicDownLoadManager.downLoadOriginPic(imgUrl);
                        }
                    }

                    try {
                        System.out.println(s.getUrl() + "\t" + s.getName());
                        PrintWriter printWriter = new PrintWriter(new FileWriter(
                                "/data/temp/downLoadedPicUrl.txt", true));
                        printWriter.println(s.getUrl());
                        printWriter.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Set<String> downLoadedUrl() {
        Set<String> downLoadedUrls = new HashSet<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "/data/temp/downLoadedPicUrl.txt"));
            String info = null;
            while ((info = reader.readLine()) != null) {
                downLoadedUrls.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return downLoadedUrls;

    }
}
