package us.codecraft.webmagic.samples;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-4-21
 * Time: 下午1:48
 */
public class SinaBlogProcesser implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(http://blog\\.sina\\.com\\.cn/s/blog_.*)").toStrings());
        page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
        page.putField("content",page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
        page.putField("id",page.getUrl().regex("http://blog\\.sina\\.com\\.cn/s/blog_(\\w+)"));
        page.putField("date",page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
//        page.putField("tags",page.getHtml().xpath("//td[@class='blog_tag']/h3/a"));
    }

    @Override
    public Site getSite() {
        if (site==null){
            site = Site.me().setDomain("blog.sina.com.cn").addStartUrl("http://blog.sina.com.cn/flashsword20").setSleepTime(3000).
                    setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
        }
        return site;
    }
}
