import com.github.huifer.ews.util.JsonUtil;
import com.google.gson.Gson;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.assertj.core.util.Arrays;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class A {
	public static final String JSON = "{\n" +
			"  \"username\": \"aaa\",\n" +
			"  \"age\": \"10\",\n" +
			"  \"role\": [\n" +
			"    {\n" +
			"      \"id\": 1,\n" +
			"      \"name\": \"zs\",\n" +
			"      \"age\": 30\n" +
			"    },\n" +
			"    {\n" +
			"      \"id\": 2,\n" +
			"      \"name\": \"ls\",\n" +
			"      \"age\": 23\n" +
			"    }\n" +
			"\n" +
			"  ]\n" +
			"}";
	static Gson gson = new Gson();

	public static void main(String[] args) throws Exception {

		String key = "role[*].age";
		Map map = gson.fromJson(JSON, Map.class);
		Object role = map.get("role");
		int size = -1;
		if (role instanceof Collection) {
			size = ((Collection<?>) role).size();
		}
		if (Arrays.isArray(role)) {
			size = Arrays.array(role).length;
		}
		Properties properties = JsonUtil.getProperties(JSON);
		for (int i = 0; i < size; i++) {

			String replace = key.replace("*", String.valueOf(i));
			String property = properties.getProperty(replace);
			System.out.println(property);
		}


		DocumentContext jsonContext = JsonPath.parse(JSON);
		Object jsonpathCreatorName = jsonContext.read("$['role'][*]['age']");
		System.out.println();
	}


}
