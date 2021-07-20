import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.huifer.ews.util.JsonUtil;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import java.io.StringReader;
import java.util.Map;
import java.util.Properties;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pl.jalokim.propertiestojson.util.PropertiesToJsonConverter;

public class A {

	public static final String json = "{\n"
			+ "    \"username\":\"zhangsan\",\n"
			+ "    \"is_new\": true,\n"
			+ "    \"mail\":\"xxx@xxx.com\",\n"
			+ "    \"age\":18\n"
			+ "}";

	public static void main(String[] args) throws Exception {
		String jsonpathCreatorNamePath = "$['age']";
		DocumentContext jsonContext = JsonPath.parse(json);
		Object jsonpathCreatorName = jsonContext.read(jsonpathCreatorNamePath);
		System.out.println();

		ExpressionParser parser = new SpelExpressionParser();
//    Expression exp = parser.parseExpression(jsonpathCreatorName.toString()+  " == true");
		Expression exp = parser.parseExpression("2!=1");
		EvaluationContext context = new StandardEvaluationContext();
		Boolean value = exp.getValue(context, Boolean.class);

		System.out.println(value);

		Properties properties = new Properties();
		properties.setProperty("age", "123");
		properties.setProperty("event.age", "123");
		properties.setProperty("event.aaa", "123");
		properties.setProperty("array[0]", "123");
		properties.setProperty("array[1]", "aaa");

		String json = new PropertiesToJsonConverter().convertToJson(properties);
		System.out.println(json);

		ObjectMapper objectMapper = new ObjectMapper();
		StringBuilder og = new StringBuilder();
		Map props = JsonUtil.transformJsonToMap(objectMapper.readTree(json), null, og);

		System.out.println(og);
		Properties properties1 = new Properties();
		properties1.load(new StringReader(og.toString()));
		System.out.println();

		exp = parser.parseExpression("true && true || false");
		context = new StandardEvaluationContext();
		value = exp.getValue(context, Boolean.class);
		System.out.println(value);
	}


}
