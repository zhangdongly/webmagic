/**
 * 
 */
package us.codecraft.webmagic.samples.model;

import java.util.Map;

/**
 * @author zhangdong zhangdong147896325@163.com
 * 
 *         2013-7-10 上午9:38:37
 */
public class SinaHouseInfo {

    private String url;

    private String name;

    private Map<String, String> infoMap;

    private Map<String, String> picMap;

    private Map<String, String> huxingMap;

    /**
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the infoMap
     */
    public Map<String, String> getInfoMap() {
        return this.infoMap;
    }

    /**
     * @param infoMap the infoMap to set
     */
    public void setInfoMap(Map<String, String> infoMap) {
        this.infoMap = infoMap;
    }

    /**
     * @return the picMap
     */
    public Map<String, String> getPicMap() {
        return this.picMap;
    }

    /**
     * @param picMap the picMap to set
     */
    public void setPicMap(Map<String, String> picMap) {
        this.picMap = picMap;
    }

    /**
     * @return the huxingMap
     */
    public Map<String, String> getHuxingMap() {
        return huxingMap;
    }

    /**
     * @param huxingMap the huxingMap to set
     */
    public void setHuxingMap(Map<String, String> huxingMap) {
        this.huxingMap = huxingMap;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SinaHouseInfo [url=" + this.url + ", name=" + this.name + ", infoMap="
                + this.infoMap + ", picMap=" + this.picMap + ", huxingMap=" + this.huxingMap + "]";
    }

}
