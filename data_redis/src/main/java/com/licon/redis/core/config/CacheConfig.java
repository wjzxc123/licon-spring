package com.licon.redis.core.config;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@EnableCaching
@Configuration
public class CacheConfig {

    private final RedisProperties redisProperties;
    private final Environment env;
    private final static String COM_ENV="com";
    private final static String HOM_ENV="hom";

    @Bean(destroyMethod = "shutdown")
    RedissonClient redisson() {
        Config config = new Config();
        String[] activeProfiles = env.getActiveProfiles();
        if (Arrays.stream(activeProfiles).anyMatch(COM_ENV::equalsIgnoreCase)){
            SingleServerConfig singleServerConfig = config.useSingleServer()
                    .setClientName(redisProperties.getClientName())
                    .setAddress("redis://"+redisProperties.getHost()+":"+redisProperties.getPort())
                    .setDatabase(redisProperties.getDatabase());
            if (redisProperties.getPassword() != null) {
                singleServerConfig.setPassword(redisProperties.getPassword());
            }
        }else if(Arrays.stream(activeProfiles).anyMatch(HOM_ENV::equalsIgnoreCase)) {
            List<String> clusterNodes = new ArrayList<>();
            for (int i = 0; i < redisProperties.getCluster().getNodes().size(); i++) {
                clusterNodes.add("redis://" + redisProperties.getCluster().getNodes().get(i));
            }
            ClusterServersConfig clusterServersConfig = config.useClusterServers()
                    .addNodeAddress(clusterNodes.toArray(new String[0]));
            if (redisProperties.getPassword() != null) {
                clusterServersConfig.setPassword(redisProperties.getPassword());
            }
        }
        return Redisson.create(config);
    }

    @Bean
    CacheManager cacheManager(RedissonClient redissonClient) {
        log.debug("生成缓存处理器");
        return new RedissonSpringCacheManager(redissonClient);
    }
}
