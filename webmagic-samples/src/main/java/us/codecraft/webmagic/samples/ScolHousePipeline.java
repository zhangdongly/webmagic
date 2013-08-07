/**
 * 
 */
package us.codecraft.webmagic.samples;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientPool;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.samples.model.SinaHouseInfo;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.UrlUtils;

import com.snsxiu.common.util.JSONUtils;

/**
 * @author zhangdong zhangdong147896325@163.com
 * 
 *         2013-7-9 下午6:12:55
 */
public class ScolHousePipeline implements Pipeline {

    /* (non-Javadoc)
     * @see us.codecraft.webmagic.pipeline.Pipeline#process(us.codecraft.webmagic.Page, us.codecraft.webmagic.Task)
     */
    @Override
    public void process(Page page, Task task) {
        String info = page.getFields().get("body").toString();
        if (info != null) {
            SinaHouseInfo shi = new SinaHouseInfo();
            shi.setInfoMap(this.getHuxingImag(info));
            shi.setName(page.getFields().get("title").toString());
            setPicture(page.getRequest().getUrl(), page, shi);
            shi.setUrl(page.getRequest().getUrl());
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(
                        "/data/temp/result.nu.txt", true));
                printWriter.println(JSONUtils.toJSON(shi));
                printWriter.close();
                System.out.println(page.getRequest().getUrl());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setPicture(String url, Page page, SinaHouseInfo shi) {
        Page picPage = getPage(url, page);
        if (picPage != null) {
            picPage.putField("pic", picPage.getHtml().xpath("//div[@class=\"img\"]"));
            picPage.putField("huxing",
                    picPage.getHtml().xpath("//ul[@class=\"unit_list clearfix\"]"));
        }

        String picInfo = picPage.getFields().get("pic").toString();
        String huxingInfo = picPage.getFields().get("huxing").toString();
        if (picInfo != null) {
            Map<String, String> picMap = this.getImgUrlAndAlt(picInfo);
            shi.setPicMap(picMap);
        }
        if (huxingInfo != null) {
            Map<String, String> huxingMap = this.getImgUrlAndAlt(huxingInfo);
            shi.setHuxingMap(huxingMap);
        }

    }

    public Page getPage(String url, Page page) {
        Site site = Site.me().setDomain("data.house.sina.com.cn")
                .addStartUrl("http://data.house.sina.com.cn/sc/kaipan/").setEncoding("gb2312");
        HttpClient httpClient = HttpClientPool.getInstance().getClient(site);
        try {
            HttpGet httpGet = new HttpGet(url);

            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (site.getAcceptStatCode().contains(statusCode)) {

                handleGzip(httpResponse);
                String content = IOUtils.toString(httpResponse.getEntity().getContent(),
                        site.getEncoding());
                Page picPage = new Page();
                picPage.setHtml(new Html(UrlUtils.fixAllRelativeHrefs(content, url)));
                picPage.setUrl(new PlainText(url));
                picPage.setRequest(page.getRequest());
                return picPage;
            }

        } catch (Exception e) {

        }
        return null;

    }

    private void handleGzip(HttpResponse httpResponse) {
        Header ceheader = httpResponse.getEntity().getContentEncoding();
        if (ceheader != null) {
            HeaderElement[] codecs = ceheader.getElements();
            for (int i = 0; i < codecs.length; i++) {
                if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                    httpResponse.setEntity(new GzipDecompressingEntity(httpResponse.getEntity()));
                }
            }
        }
    }

    public Map<String, String> getImgUrlAndAlt(String value) {
        Map<String, String> picMap = new HashMap<String, String>();
        try {
            Parser p = new Parser(value);
            NodeFilter imgFilter = new TagNameFilter("img");
            NodeList nodes = p.extractAllNodesThatMatch(imgFilter);
            for (int i = 0; i < nodes.size(); i++) {
                Node eachNode = nodes.elementAt(i);
                if (eachNode instanceof ImageTag) {
                    ImageTag imageTag = (ImageTag) eachNode;
                    String url = imageTag.getImageURL();
                    if (StringUtils.isEmpty(url)) {
                        url = imageTag.getAttribute("lsrc");
                    }
                    picMap.put(url, imageTag.getAttribute("alt"));
                }
            }

        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return picMap;
    }

    public Map<String, String> getHuxingImag(String value) {
        Map<String, String> info = new HashMap<String, String>();
        try {
            Parser p = new Parser(value);
            NodeFilter attrFilter = new HasAttributeFilter("class", "fl w75 fb tr");
            NodeList nodes = p.extractAllNodesThatMatch(attrFilter);
            for (int i = 0; i < nodes.size(); i++) {
                Node eachNode = nodes.elementAt(i);
                info.put(eachNode.toPlainTextString(), eachNode.getNextSibling()
                        .toPlainTextString());
            }
            Parser p1 = new Parser(value);
            attrFilter = new HasAttributeFilter("class", "p10 lh24 col_2");
            nodes = p1.extractAllNodesThatMatch(attrFilter);
            for (int i = 0; i < nodes.size(); i++) {
                Node eachNode = nodes.elementAt(i);
                info.put("项目介绍", eachNode.toPlainTextString());
            }
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return info;

    }
}
