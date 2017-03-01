package cn.com.circlecloud.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
    @ConfigurationProperties(
    prefix = "circlecloud",
    ignoreUnknownFields = false
)
public class CircleCloudProperties {

    public CircleCloudProperties() {
    }

    private Api api;

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }


    public static class Api {

        private String homeUrl;

        private String version;

        public Api() {
        }

        Headers headers = new Headers();

        private transient String username;
        protected transient String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPassword() {
            return password;
        }

        public Headers getHeaders() {
            return headers;
        }

        public String getHomeUrl() {
            return homeUrl;
        }

        public void setHomeUrl(String homeUrl) {
            this.homeUrl = homeUrl;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setHeaders(Headers headers) {
            this.headers = headers;
        }

        public static class Headers {
            public Headers() {
            }

            private transient String applicationKey;
            private transient String applicationRestKey;

            public String getApplicationKey() {
                return applicationKey;
            }

            public void setApplicationKey(String applicationKey) {
                this.applicationKey = applicationKey;
            }

            public String getApplicationRestKey() {
                return applicationRestKey;
            }

            public void setApplicationRestKey(String applicationRestKey) {
                this.applicationRestKey = applicationRestKey;
            }
        }
    }


    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

}
