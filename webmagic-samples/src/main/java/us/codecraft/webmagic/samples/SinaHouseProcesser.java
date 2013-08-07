package us.codecraft.webmagic.samples;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 *         Date: 13-4-21
 *         Time: 下午1:48
 */
public class SinaHouseProcesser implements PageProcessor {

    private Site site;

    public SinaHouseProcesser() {

    }

    public SinaHouseProcesser(String startUrl) {
        site = Site.me().setDomain("data.house.sina.com.cn").addStartUrl(startUrl)
                .setEncoding("gb2312").setSleepTime(1000);
    }

    @Override
    public void process(Page page) {
        List<String> strings = page.getHtml()
                .regex("href=\"(http://data.house.sina.com.cn/nu\\d+/)\"").toStrings();
        page.addTargetRequests(strings);
        page.putField("title", page.getHtml().xpath("//title"));
        page.putField("body", page.getHtml().xpath("//dl[@class=\"public det_info\"]"));
    }

    @Override
    public Site getSite() {
        if (site != null) {
            return site;
        }
        return Site.me().setDomain("data.house.sina.com.cn")
                .addStartUrl("http://data.house.sina.com.cn/sc/kaipan/").setEncoding("gb2312"); //To change body of implemented methods use File | Settings | File Templates.
    }
}
