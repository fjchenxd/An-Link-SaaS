package cn.com.circlecloud.api.account.service;

import cn.com.circlecloud.api.common.service.SirqulRequestSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Created by dawno on 2017/2/27.
 */

@Service
public class AccountApi {

    @Autowired
    private SirqulRequestSvc requestSvc;

    public ResponseEntity<?> get(String username, String password){
        return requestSvc.request(HttpMethod.POST, "account/get", new SirqulRequestSvc.IRequestBuilder() {
            @Override
            public void build(HttpHeaders headers, MultiValueMap<String, Object> params) {
                params.add("username", username);
                params.add("password", password);
                params.add("returnProfile", "true");
                params.add("responseFilters", "PROFILE,SETTINGS,APPS,CONNECTIONS");
            }
        });
    }

    public ResponseEntity<?> updateProfile(Long accountId, final SirqulRequestSvc.IRequestBuilder builder){
        return requestSvc.request(HttpMethod.POST, "account/profile/update", new SirqulRequestSvc.IRequestBuilder() {
            @Override
            public void build(HttpHeaders headers, MultiValueMap<String, Object> params) {
                builder.build(headers, params);
                if(params.containsKey("accountId")){
                    params.remove("accountId");
                }
                params.add("accountId", accountId);
            }
        });
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {

            ResponseEntity<?> responseEntity = this.get("theDawn@miteno.com","qwaszx");
            System.out.println(responseEntity);
        };
    }


}
