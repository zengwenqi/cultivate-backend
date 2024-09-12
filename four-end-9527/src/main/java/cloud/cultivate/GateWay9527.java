package cloud.cultivate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //服务注册和发现
public class GateWay9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWay9527.class,args);
    }
}
