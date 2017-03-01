package cn.com.circlecloud.api.common.service;

import cn.com.circlecloud.config.CircleCloudProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AutoConfigureAfter(value = {CircleCloudProperties.class})
public class SirqulRequestSvc {

    private CircleCloudProperties circleCloudProperties;

    private RestTemplate template;

    public SirqulRequestSvc(CircleCloudProperties circleCloudProperties, RestTemplate template) {
        this.circleCloudProperties = circleCloudProperties;
        this.template = template;
    }

    public HttpHeaders createRequestHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Application-Key", circleCloudProperties.getApi().getHeaders().getApplicationKey());
        requestHeaders.set("Application-Rest-Key", circleCloudProperties.getApi().getHeaders().getApplicationRestKey());
        requestHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return requestHeaders;
    }


    public static interface IRequestBuilder {
        public void build(HttpHeaders headers, MultiValueMap<String, Object> params);
    }

    public ResponseEntity<?> request(HttpMethod method, String serviceUrl, IRequestBuilder builder, Object... uriVariables) {
        HttpHeaders requestHeaders = createRequestHeaders();
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
        builder.build(requestHeaders, parts);
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(parts, requestHeaders);
        return template.exchange(this.circleCloudProperties.getApi().getHomeUrl() + "/{version}/" + serviceUrl, method, httpEntity, Map.class, this.circleCloudProperties.getApi().getVersion(), uriVariables);
    }
}
