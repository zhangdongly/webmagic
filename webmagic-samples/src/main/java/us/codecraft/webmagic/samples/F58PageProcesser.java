package us.codecraft.webmagic.samples;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author code4crafter@gmail.com <br>
 * Date: 13-4-21
 * Time: 下午1:48
 */
public class F58PageProcesser implements PageProcessor {

    @Override
    public void process(Page page) {
        List<String> strings = page.getHtml().regex("<a[^<>]*href=[\"']{1}(/yewu/.*?)[\"']{1}").all();
        page.addTargetRequests(strings);
        page.putField("title",page.getHtml().regex("<title>(.*)</title>"));
        page.putField("body",page.getHtml().xpath("//dd[@class='w133']"));
    }

    @Override
    public Site getSite() {
        return Site.me().setDomain("sh.58.com").addStartUrl("http://sh.58.com/");  //To change body of implemented methods use File | Settings | File Templates.
    }
}
