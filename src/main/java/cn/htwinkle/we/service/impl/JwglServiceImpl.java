package cn.htwinkle.we.service.impl;

import cn.htwinkle.we.WeApplication;
import cn.htwinkle.we.constant.Status;
import cn.htwinkle.we.entity.Jwgl;
import cn.htwinkle.we.kit.JwglKit;
import cn.htwinkle.we.mapper.JwglMapper;
import cn.htwinkle.we.service.IJwglService;
import cn.htwinkle.we.utils.Utils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author twinkle
 * @since 2019-05-01
 */
@Service
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WeApplication.class})
public class JwglServiceImpl extends ServiceImpl<JwglMapper, Jwgl> implements IJwglService {


    @Override
    public void toLogin(String admin, String pass) {

        List<Future<Jwgl>> futureList = new ArrayList<>();

        Jwgl jwgl;


        for (int x = 0; x < 20; x++) {

            Callable<Jwgl> callable = () -> new JwglKit().toLogin(admin, pass);

            futureList.add(Utils.INSTANCE.addExecute(callable));

        }

        for (Future<Jwgl> future : futureList) {
            while (true) {

                try {
                    if (future.isDone() || future.isCancelled()) {

                        jwgl = future.get(10, TimeUnit.SECONDS);

                        if (jwgl.getCode() == Status.LOGIN_SUCCESS.CODE) {

                            System.out.println(jwgl.toString());

                            break;


                            /*if (jwgl.getCode() != Status.NEVER_TO_EVAL.CODE) {          //没有评价不能够查成绩！
                            }*/
                            // return jwgl;
                        } else {

                            System.out.println(jwgl.toString());
                            break;
                        }


                    } else {
                        Thread.sleep(1);
                    }

                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    e.printStackTrace();
                }

            }

        }
        // return null;

    }

    @Test
    public void Test() {

        toLogin("150015511131", "WANAN116224");

    }


}