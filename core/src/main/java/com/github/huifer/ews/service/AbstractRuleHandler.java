package com.github.huifer.ews.service;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.github.huifer.ews.operator.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

import java.util.Properties;

public abstract class AbstractRuleHandler {
	private final RestTemplate restTemplate = new RestTemplate();

	abstract boolean canHandle(String json, Integer ruleId);

	abstract void handlerJsonWithRuleId(String json, Integer ruleId);

	public void work(String json, Integer... ruleIds) {
		for (Integer ruleId : ruleIds) {
			if (canHandle(json, ruleId)) {
				handlerJsonWithRuleId(json, ruleId);
			}
		}
	}

	protected void sendHttpMessage(String url, Properties params, HttpMethod httpMethod) {
		switch (httpMethod) {
			case GET:
				return;
			case POST_FORM:
				forPostForm(url, params);
				return;
			case POST_JSON:
				forPostJson(url, params);
				return;

		}

	}

	protected void forPostJson(String url, Properties params) {
		String paramsJson = new PropertiesToJsonConverter().convertToJson(params);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> request = new HttpEntity<>(paramsJson, headers);
		ResponseEntity<String> postForEntity = restTemplate.postForEntity(url, request, String.class);
		String body = postForEntity.getBody();
		System.out.println(body);
	}

	protected void forPostForm(String url, Properties params) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		params.forEach((k, v) -> {
			map.add(k.toString(), v.toString());
		});
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
		ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
		System.out.println(response.getBody());
	}
}
