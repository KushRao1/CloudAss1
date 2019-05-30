import org.json.simple.JSONObject;

public class ResponseError extends Response {
	
	public static final String GNRAL_BAD_REQUEST = "Bad Request";
	public static final String SECUR_AUTH_FAILURE = "Authentication Failure";
	public static final String SECUR_NOT_AUTHORIZED = "Not Authorized";
	public static final String SHIP_UNK_DRUG = "Unknown Drug";
	public static final String SHIP_UNK_ADDRESS = "Unknown Address";
	public static final String SHIP_INSUF_STOCK = "Insufficient Stock";

	public String error;

	public ResponseError(String error)
	{
		super(500L);
		this.error = error;
	}

	public ResponseError(Long status, String error)
	{
		super(status);
		this.error = error;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void objectToJson(JSONObject jsonObj)
	{
		super.objectToJson(jsonObj);
		jsonObj.put("error", this.error);
	}
}
