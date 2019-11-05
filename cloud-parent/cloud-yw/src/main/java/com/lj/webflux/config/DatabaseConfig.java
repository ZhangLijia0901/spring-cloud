package com.lj.webflux.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.lj.webflux.mapper")
public class DatabaseConfig {

}
