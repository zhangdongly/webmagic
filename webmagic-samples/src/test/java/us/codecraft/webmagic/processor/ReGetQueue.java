/**
 * 
 */
package us.codecraft.webmagic.processor;

import java.util.Set;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.schedular.QueueScheduler;

/**
 * @author zhangdong zhangdong147896325@163.com
 * 
 *         2013-7-15 上午9:48:17
 */
public class ReGetQueue extends QueueScheduler {

    private Set<String> urls;

    public ReGetQueue() {
        super();
    }

    public ReGetQueue(Set<String> urls) {
        super();
        this.urls = urls;

    }

    @Override
    public synchronized void push(Request request, Task task) {
        if (urls.add(request.getUrl())) {
            super.push(request, task);
        }

    }

    @Override
    public synchronized Request poll(Task task) {
        return super.poll(task);
    }

}
