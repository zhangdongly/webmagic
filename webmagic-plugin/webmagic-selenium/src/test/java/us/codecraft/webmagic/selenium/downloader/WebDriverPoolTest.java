package us.codecraft.webmagic.selenium.downloader;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

/**
 * @author yihua.huang@dianping.com <br>
 * @date: 13-7-26 <br>
 * Time: 下午2:12 <br>
 */
public class WebDriverPoolTest {

    private String chromeDriverPath = "/Users/yihua/Downloads/chromedriver";

    @Ignore("need chrome driver")
    @Test
    public void test() {
        System.getProperties().setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriverPool webDriverPool = new WebDriverPool(5);
        for (int i = 0; i < 5; i++) {
            try {
                WebDriver webDriver = webDriverPool.get();
                System.out.println(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        webDriverPool.closeAll();
    }
}
