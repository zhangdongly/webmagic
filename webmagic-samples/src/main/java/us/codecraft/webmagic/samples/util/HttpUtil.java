/**
 * 
 */
package us.codecraft.webmagic.samples.util;

import org.apache.commons.io.IOUtils;
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
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientPool;
import us.codecraft.webmagic.selector.PlainText;

/**
 * @author zhangdong zhangdong147896325@163.com
 * 
 *         2013-7-10 上午10:13:57
 */
public class HttpUtil {

    public static String getStringByUrl(String url, String domain, String encoding) {
        Site site = Site.me().setDomain(domain).addStartUrl(url).setEncoding(encoding);
        HttpClient httpClient = HttpClientPool.getInstance().getClient(site);
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (site.getAcceptStatCode().contains(statusCode)) {
                //charset
                if (encoding == null) {
                    String value = httpResponse.getEntity().getContentType().getValue();
                    site.setEncoding(new PlainText(value).regex("charset=([^\\s]+)").toString());
                }
                //
                handleGzip(httpResponse);
                String content = IOUtils.toString(httpResponse.getEntity().getContent(),
                        site.getEncoding());
                return content;
                // page.get
            }

        } catch (Exception e) {

        }
        return null;
    }

    private static void handleGzip(HttpResponse httpResponse) {
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

    public static void main(String[] args) {

        try {
            String value1 = "<li id=\"con_tupian_2\" style=\"display:block;\"><a target=\"_blank\" href=\"http://data.house.sina.com.cn/sc105515/slide/4565099/?wt_source=data_jdtp_01_sc\"><img lsrc=\"http://src.house.sina.com.cn/imp/imp/deal/de/e0/6/40fbdb6e2ca1fcdbddd82ea9ca8_p7_mk7_os7a78fd_cm440X330.jpg\" alt=\"北庭香澜郡效果图\" itemprop=\"image\" /></a>";
            String value = "<img alt=\"长虹·和悦府户型图 C3 87平米 87㎡\" src=\"http://src.house.sina.com.cn/imp/imp/deal/45/3f/9/20dc409fd1b6794279272f81593_p7_mk7_os39c88e_cm240X180.jpg\">";
            Parser p = new Parser(value + value1);
            NodeFilter attrFilter = new HasAttributeFilter("class", "fl w75 fb tr");
            NodeList nodes = p.extractAllNodesThatMatch(attrFilter);
            for (int i = 0; i < nodes.size(); i++) {
                Node eachNode = nodes.elementAt(i);
                System.out.println(eachNode.getText());
            }

        } catch (ParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
