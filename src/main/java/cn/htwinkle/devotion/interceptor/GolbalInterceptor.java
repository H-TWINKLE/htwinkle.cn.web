package cn.htwinkle.devotion.interceptor;

import cn.htwinkle.devotion._front.CommService;
import cn.htwinkle.devotion.kit.PoolExecutorKit;
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
     * 原子计数器
     */
    public static AtomicInteger ATOMIC_INTEGER;

    @Inject
    CommService service;

    @Override
    public void intercept(Invocation invocation) {

        toAddAtomicNumber();
        PoolExecutorKit.INSTANCE.asyncSaveVisitorInfo(invocation.getController());
        invocation.invoke();
    }

    private void toAddAtomicNumber() {
        if (ATOMIC_INTEGER == null || ATOMIC_INTEGER.getAndIncrement() == 0) {
            ATOMIC_INTEGER = new AtomicInteger(service.getAllVisitUserNum());
        } else {
            ATOMIC_INTEGER.getAndIncrement();
        }
    }


}
