package de.tutous.spring.boot.citest.backend;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ResponseEntityBuilder
{

    private String url;
    private RestTemplate restTemplate;
    private HttpHeaders headers;

    private ResponseEntityBuilder(String url, String username, String password)
    {
        restTemplate = new RestTemplate(getClientHttpRequestFactory());
        headers = createHeaders(username, password);
        this.url = url;
    }

    public static ResponseEntityBuilder create(String url, String username, String password)
    {
        return new ResponseEntityBuilder(url, username, password);
    }

    public ResponseEntityBuilder setContentType(MediaType mediaType)
    {
        headers.setContentType(mediaType);
        return this;
    }

    public ResponseEntityBuilder setAccept(List<MediaType> acceptableMediaTypes)
    {
        headers.setAccept(acceptableMediaTypes);
        return this;
    }

    public <T> ResponseEntity<T> postForEntity(Object body, Class<T> responseType)
    {
        return restTemplate.postForEntity(url, new HttpEntity<>(body, headers), responseType);
    }

    private HttpHeaders createHeaders(String username, String password)
    {
        return new HttpHeaders()
        {
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = org.apache.commons.codec.binary.Base64
                        .encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory()
    {
        int timeout = 5000;
        RequestConfig config = RequestConfig.custom()//
                .setConnectTimeout(timeout)//
                .setConnectionRequestTimeout(timeout)//
                .setSocketTimeout(timeout)//
                .build();
        CloseableHttpClient client = HttpClientBuilder//
                .create()//
                .setDefaultRequestConfig(config)//
                .build();
        return new HttpComponentsClientHttpRequestFactory(client);
    }

}
