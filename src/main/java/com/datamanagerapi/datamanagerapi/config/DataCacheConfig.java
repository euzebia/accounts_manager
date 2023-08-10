package com.datamanagerapi.datamanagerapi.config;

import com.hazelcast.config.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ObjectInputFilter;

@Configuration
public class DataCacheConfig {

    @Bean
    public Config cacheConfig() {
        return new Config()
                .setInstanceName("hazel-instance")
                .addMapConfig(new MapConfig()
                        .setName("institution-cache")
                        .setTimeToLiveSeconds(0)
                        .setEvictionConfig(createEvictionConfigWithEntryCountPolicy()
                                .setMaxSizePolicy(MaxSizePolicy.FREE_HEAP_SIZE))


                );
    }


    public EvictionConfig createEvictionConfigWithEntryCountPolicy() {
        return new EvictionConfig()
                .setEvictionPolicy(EvictionPolicy.NONE)
                //.setMaximumSizePolicy(EvictionConfig.MaxSizePolicy.ENTRY_COUNT)
                .setSize(2000);
    }
}
