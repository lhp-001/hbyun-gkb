package com.huabo.gkb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan("com.huabo")
public class GkbApplication {

	public static void main(String[] args) {
		SpringApplication.run(GkbApplication.class, args);
		System.out.println("====================管控宝小程序微服务启动成功====================");
	}
}
