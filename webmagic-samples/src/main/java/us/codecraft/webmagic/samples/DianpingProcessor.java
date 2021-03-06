package us.codecraft.webmagic.samples;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-4-21
 * Time: 下午8:08
 */
public class DianpingProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        List<String> requests = page.getHtml().links().regex(".*shop.*").toStrings();
        page.addTargetRequests(requests);
        requests = page.getHtml().regex(".*search/category/.*").toStrings();
        page.addTargetRequests(requests);
        if (page.getUrl().toString().contains("shop")) {
            page.putField("title", page.getHtml().xpath("//h1[@class='shop-title']"));
            page.putField("content", page.getHtml().smartContent());
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain("www.dianping.com").addStartUrl("http://www.dianping.com/").
                setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    }

    public static void main(String[] args) {
        DianpingProcessor dianpingProcessor = new DianpingProcessor();
        Spider.create(dianpingProcessor).run();
    }
}
