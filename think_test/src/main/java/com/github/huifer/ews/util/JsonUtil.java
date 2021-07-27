package com.github.huifer.ews.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class JsonUtil {

	public static Properties getProperties(String json) throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder og = new StringBuilder();
		Map props = JsonUtil.transformJsonToMap(objectMapper.readTree(json), null, og);

		System.out.println(og);
		Properties properties1 = new Properties();
		properties1.load(new StringReader(og.toString()));
		return properties1;
	}

	public static Map<String, String> transformJsonToMap(JsonNode node, String prefix,
														 StringBuilder OG) {

		Map<String, String> jsonMap = new HashMap<>();

		if (node.isArray()) {
			//Iterate over all array nodes
			int i = 0;
			for (JsonNode arrayElement : node) {
				jsonMap.putAll(transformJsonToMap(arrayElement, prefix + "[" + i + "]", OG));
				i++;
			}
		} else if (node.isObject()) {
			Iterator<String> fieldNames = node.fieldNames();
			String curPrefixWithDot =
					(prefix == null || prefix.trim().length() == 0) ? "" : prefix + ".";
			//list all keys and values
			while (fieldNames.hasNext()) {
				String fieldName = fieldNames.next();
				JsonNode fieldValue = node.get(fieldName);
				jsonMap.putAll(transformJsonToMap(fieldValue, curPrefixWithDot + fieldName, OG));
			}
		} else {
			//basic type
			jsonMap.put(prefix, node.asText());
			OG.append(prefix + "=" + node.asText());
			OG.append("\r\n");
		}

		return jsonMap;
	}
}
