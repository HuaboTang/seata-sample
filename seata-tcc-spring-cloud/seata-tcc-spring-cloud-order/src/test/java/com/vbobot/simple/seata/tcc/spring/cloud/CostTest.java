package com.vbobot.sample.seata.tcc.spring.cloud;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.vbobot.common.utils.http.HttpClientUtils;
import com.vbobot.common.utils.http.SimpleHttpResponse;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.junit.jupiter.api.RepeatedTest;

/**
 * @author Bobo
 * @date 2021/9/3
 */
public class CostTest {

    @RepeatedTest(value = 10)
        public void costTest() throws InterruptedException {
        runCost(integer -> {
            final long begin = System.currentTimeMillis();
            final SimpleHttpResponse simpleHttpResponse = HttpClientUtils.getInst()
                    .httpGet("http://127.0.0.1:8080/order/create/rollback");
            System.out.println(simpleHttpResponse.getResponse() + ", cost:" + (System.currentTimeMillis() - begin));
        });
    }

    private void runCost(Consumer<Integer> eachExec) throws InterruptedException {
        final long begin = System.currentTimeMillis();

        final ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("redisson-task-%d")
                .setDaemon(true)
                .build();
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                threadFactory);

        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                for (int j = 0; j < 50; j++) {
                    eachExec.accept(j);
                }
            });
        }

        threadPoolExecutor.shutdown();
        if (threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS)) {
            System.out.println("Cost:" + (System.currentTimeMillis() - begin));
        }
    }
}
