import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Response {

	public Long status;

	public Response(Long status)
	{
		this.status = status;
	}

	public String toJsonString()
	{
		JSONObject jsonObj = new JSONObject();
		objectToJson(jsonObj);
		return jsonObj.toJSONString();
	}

	@SuppressWarnings("unchecked")
	protected void objectToJson(JSONObject jsonObj)
	{
		jsonObj.put("status", this.status);
	}
	
	public static Object getFieldFromJson(String field, String json)
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try
		{
			jsonObj = (JSONObject)parser.parse(json);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return jsonObj.get(field);
	}
}
