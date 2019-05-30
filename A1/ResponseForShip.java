import org.json.simple.JSONObject;

public class ResponseForShip extends Response {

	public String estimateddeliverydate;

	public ResponseForShip(String estimateddeliverydate)
	{
		super(200L);
		this.estimateddeliverydate = estimateddeliverydate;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void objectToJson(JSONObject jsonObj)
	{
		super.objectToJson(jsonObj);
		jsonObj.put("estimateddeliverydate", this.estimateddeliverydate);
	}
}
