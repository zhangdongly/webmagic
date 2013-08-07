package us.codecraft.webmagic.processor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.samples.ScolHousePipeline;
import us.codecraft.webmagic.samples.SinaHouseProcesser;
import us.codecraft.webmagic.samples.model.SinaHouseInfo;
import us.codecraft.webmagic.schedular.Scheduler;

import com.snsxiu.common.util.JSONUtils;

/**
 * @author code4crafter@gmail.com <br>
 *         Date: 13-6-9
 *         Time: 上午8:02
 */
public class SinahouseProcessorTest {

    @Test
    public void test() throws IOException {

        for (int i = 1; i <= 4; i++) {
            SinaHouseProcesser sinaHouseProcesser = new SinaHouseProcesser(
                    "http://data.house.sina.com.cn/nu/search/0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-0-"
                            + i + ".html?");
            Spider.create(sinaHouseProcesser).pipeline(new ScolHousePipeline())
                    .scheduler(getScheduler()).run();
        }

    }

    public static Scheduler getScheduler() {
        Set<String> houseInfoList = new HashSet<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/data/temp/result.all1.txt"));
            String info = null;
            while ((info = reader.readLine()) != null) {
                houseInfoList.add(JSONUtils.fromJSON(info, SinaHouseInfo.class).getUrl());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ReGetQueue(houseInfoList);
    }

}
