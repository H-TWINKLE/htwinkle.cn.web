package cn.htwinkle.web.interceptor;

import cn.htwinkle.web._front.CommService;
import cn.htwinkle.web.kit.PoolExecutorKit;
import com.jfinal.aop.Inject;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * TODO 描述用途
 *
 * @author : twinkle
 * @date : 2020/3/15 18:13
 */
public class GolbalInterceptor implements Interceptor {

    /**
     * 全局的原子计数器
     */
    public static final AtomicInteger GLOBAL_COUNTER = new AtomicInteger(0);

    @Inject
    CommService service;

    @Override
    public void intercept(Invocation invocation) {
        toAddAtomicNumber();
        PoolExecutorKit.INSTANCE.asyncSaveVisitorInfo(invocation.getController());
        invocation.invoke();
    }

    private void toAddAtomicNumber() {
        if (GLOBAL_COUNTER.getAndIncrement() == 0) {
            synchronized (this) {
                GLOBAL_COUNTER.set(service.getAllVisitUserNum());
            }
        } else {
            GLOBAL_COUNTER.getAndIncrement();
        }
    }
}
