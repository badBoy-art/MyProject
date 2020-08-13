package com.spring.rest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.common.collect.Lists;

/**
 * @author zhaoxuedui <zhaoxuedui@kuaishou.com>
 * Created on 2020-07-25
 * @Description
 */
public class Template {

    private static String token = "5400dc94c26a4824bd56c49398050939";

    @Test
    public void test() {
        String URL = "https://oncall-test.corp.kuaishou.com/api/v3/messages/standard";

        RestTemplate template = new RestTemplate(getFactory());

        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", 1595688155);
        map.put("status", "PROBLEM");
        map.put("priority", "P0");
        map.put("channel", "wechat:textcard");
        map.put("title", "test");
        map.put("msg", "test");
        map.put("aggregation", "test");
        map.put("aggregation_interval", 0L);
        map.put("uic", "zhaoxuedui");
        map.put("source", "test");
        map.put("event_id", "9f86d081884c7d659a2feaa0c55ad015a3bf4f1b2b0b822cd15d6c15b0f00a08");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));

        HttpEntity<Map> request = new HttpEntity<>(map, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(URL).queryParam("token", token);

        ResponseEntity<String> responseEntity = template
                .exchange(builder.toUriString(), HttpMethod.POST, request, String.class);

        System.out.print(responseEntity.getBody());
    }

    private ClientHttpRequestFactory getFactory() {
        SimpleClientHttpRequestFactory clientHttpRequestFactory
                = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(10 * 1000);
        clientHttpRequestFactory.setReadTimeout(10 * 1000);
        return clientHttpRequestFactory;
    }

}