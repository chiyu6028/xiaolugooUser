package com.xiaolugoo.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.Configuration;  
import org.springframework.data.redis.connection.RedisPassword;  
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;  
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;  
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;  
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;  
import org.springframework.session.web.http.HttpSessionIdResolver;  
  
/** 
 * 说明：自定义session 
 *
 */  
@Configuration  
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 36000)  
public class HttpSessionConfig {

    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * RedisHttpSession 创建 连接工厂
     *
     * @return LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setPassword(RedisPassword.of(password));
        return new LettuceConnectionFactory(config);
    }
}