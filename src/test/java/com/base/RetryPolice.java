package com.base;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.Nonnegative;

import com.github.phantomthief.scope.RetryPolicy;

/**
 * @author zhaoxuedui <zhaoxuedui>
 * Created on 2020-06-05
 * @Description
 */
public interface RetryPolice {

    long NO_RETRY = -1L;

    static RetryPolicy noRetry() {
        return retryNTimes(0);
    }

    static RetryPolicy retryNTimes(int times) {
        return retryNTimes(times, 0);
    }

    static RetryPolicy retryNTimes(int times, @Nonnegative long delayInMs) {
        return retryNTimes(times, delayInMs, true);
    }

    static RetryPolicy retryNTimes(int times, @Nonnegative long delayInMs, boolean hedge) {
        checkArgument(delayInMs >= 0, "delayInMs must be non-negative.");
        return new RetryPolicy() {

            @Override
            public long retry(int retryCount) {
                return retryCount <= times ? delayInMs : NO_RETRY;
            }

            @Override
            public boolean hedge() {
                return hedge;
            }
        };
    }

    /**
     * @param retryCount 当前重试的次数（1为第一次重试）
     * @return 下次重试的间隔时间，或者返回 {@link #NO_RETRY}
     */
    long retry(int retryCount);

    /**
     * 返回true则不cancel任何一次重试，重试过程中任何一次返回成功都拿来做最终结果
     * 返回false则开始下一次重试时，之前超时的请求就算后来结果成功返回也没有用
     */
    default boolean hedge() {
        return true;
    }

    default boolean triggerGetOnTimeout() {
        return true;
    }

    /**
     * 判断抛出某个异常后，是否要终止重试
     */
    default boolean abortRetry(Throwable t) {
        return false;
    }
}
