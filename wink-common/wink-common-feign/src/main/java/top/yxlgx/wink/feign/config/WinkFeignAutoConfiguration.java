package top.yxlgx.wink.feign.config;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author yanxin
 * @description
 */
@Configuration
public class WinkFeignAutoConfiguration {

    /**
     * OkHttp 客户端配置
     *
     * @return OkHttp 客户端配
     */
    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().retryOnConnectionFailure(true) // 是否开启缓存
                .connectTimeout(30L, TimeUnit.SECONDS) // 连接超时时间
                .readTimeout(30L, TimeUnit.SECONDS) // 读取超时时间
                .writeTimeout(30L, TimeUnit.SECONDS)
                .followRedirects(true) // 是否允许重定向
                .build();
    }

}
