package cn.sluk3r.play.concurrent.executor.withLog;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by baiing on 2014/12/1.
 */
public interface RejectedExecutionHandlerWithLog {
    void rejectedExecution(Runnable r, ThreadPoolExecutorWithLog executor);
}
