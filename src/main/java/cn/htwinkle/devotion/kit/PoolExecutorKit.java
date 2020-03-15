package cn.htwinkle.devotion.kit;

import cn.htwinkle.devotion.model.Visit;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/3/10 18:17
 */
public enum PoolExecutorKit {

    /**
     * 单例对象
     */
    INSTANCE;

    /**
     * 线程池
     */
    private static ThreadPoolExecutor POOL_EXECUTOR =
            new ThreadPoolExecutor(1, 5, 0,
                    TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));

    /**
     * 执行一个线程
     *
     * @param runnable runnable
     */
    public void execute(Runnable runnable) {
        POOL_EXECUTOR.execute(runnable);
    }


    /**
     * 异步的保存用户访问信息
     *
     * @param controller controller
     */
    public void asyncSaveVistorInfo(Controller controller) {
        String ip = IpPlaceKit.INSTANCE.getRemoteAddrIp(controller);

        if (skipRecord(ip))
            return;

        String userAgent = IpPlaceKit.INSTANCE.getUserAgent(controller);

        Runnable runnable = () -> {
            String place = IpPlaceKit.INSTANCE.getIpPlace(ip);
            Visit visit = new Visit()
                    .setVisitIp(ip)
                    .setVisitUserAgent(userAgent)
                    .setVisitPlace(place)
                    .setVisitDate(new Date());
            visit.save();
        };
        POOL_EXECUTOR.execute(runnable);
    }

    private boolean skipRecord(String ip) {
        return StrKit.isBlank(ip) ||
                ip.startsWith("192.168") ||
                ip.contains("127.0.0.1") ||
                ip.contains("localhost");
    }


}
