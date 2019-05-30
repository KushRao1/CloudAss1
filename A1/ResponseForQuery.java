import org.json.simple.JSONObject;

public class ResponseForQuery extends Response {

	public Long count;

	public ResponseForQuery(Long count)
	{
		super(200L);
		this.count = count;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void objectToJson(JSONObject jsonObj)
	{
		super.objectToJson(jsonObj);
		jsonObj.put("count", this.count);
	}
}
