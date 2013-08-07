package us.codecraft.webmagic.samples;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author zhangdong147896325@163.com <br>
 *         Date: 13-4-21
 *         Time: 下午8:08
 */
public class ScolProcessor implements PageProcessor {

    private Site site;

    @Override
    public void process(Page page) {
        //a()表示提取链接，links()表示提取所有链接
        //getHtml()返回Html对象，支持链式调用
        //r()表示用正则表达式提取一条内容，regex()表示提取多条内容
        //toString()表示取单条结果，toStrings()表示取多条
        List<String> requests = page.getHtml().links().regex("(.*content.*)").toStrings();
        //.regex("(content/\\d+-\\d+/content_\\d+.htm)").toStrings();
        //使用page.addTargetRequests()方法将待抓取的链接加入队列
        page.addTargetRequests(requests);
        //page.putField(key,value)将抽取的内容加入结果Map
        //x()和xs()使用xpath进行抽取
        page.putField("title", page.getHtml().xpath("//title"));
        //smartContent()使用readability技术直接抽取正文，对于规整的文本有比较好的抽取正确率
        page.putField("content", page.getHtml().smartContent());

    }

    @Override
    public Site getSite() {
        //site定义抽取配置，以及开始url等
        if (site == null) {
            site = Site
                    .me()
                    .setDomain("scol.com.cn")
                    .addStartUrl("http://scol.com.cn/")
                    .setUserAgent(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31")
                    .setEncoding("gb2312");
        }
        return site;
    }
}
