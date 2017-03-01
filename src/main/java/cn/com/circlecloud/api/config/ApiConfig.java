package cn.com.circlecloud.api.config;

import cn.com.circlecloud.api.common.service.SirqulRequestSvc;
import cn.com.circlecloud.config.CircleCloudProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dawno on 2017/2/27.
 */

@Configuration
public class ApiConfig {

    @Bean
    public SirqulRequestSvc sirqulRequestSvc(CircleCloudProperties circleCloudProperties, RestTemplate template){
        return new SirqulRequestSvc(circleCloudProperties,template);
    }
}
