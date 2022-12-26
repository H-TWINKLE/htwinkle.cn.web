package cn.htwinkle.web.kit;

import cn.htwinkle.web.constants.Constants;
import cn.htwinkle.web.model.Visit;
import cn.hutool.core.util.StrUtil;
import com.jfinal.core.Controller;

import java.time.LocalDateTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

/**
 * PoolExecutorKit
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
    private static final ThreadPoolExecutor POOL_EXECUTOR;

    static {
        int corePoolSize = getCorePoolSize();
        POOL_EXECUTOR = new ThreadPoolExecutor(corePoolSize + 2, corePoolSize * 4, 0,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
    }

    /**
     * 获取核心线程数
     *
     * @return int
     */
    private static int getCorePoolSize() {
        int i = Runtime.getRuntime().availableProcessors();
        return i > 0 ? i : 1;
    }

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
     * 本地ip则直接禁止
     *
     * @param controller controller
     */
    public void asyncSaveVisitorInfo(Controller controller) {
        String ip = IpPlaceKit.INSTANCE.getRemoteAddrIp(controller);
        if (StrUtil.isBlank(ip) || Stream.of("127.0.0.1", "localhost", Constants.LOCALIP).anyMatch(item -> item.contains(ip))) {
            return;
        }
        String userAgent = IpPlaceKit.INSTANCE.getUserAgent(controller);
        PrintKit.INSTANCE.printHeader(controller);
        POOL_EXECUTOR.execute(() -> {
            String place = IpPlaceKit.INSTANCE.getIpPlace(ip);
            Visit visit = new Visit()
                    .setVisitIp(ip)
                    .setVisitUserAgent(userAgent)
                    .setVisitPlace(place)
                    .setVisitDate(LocalDateTime.now());
            visit.save();
        });
    }

}
