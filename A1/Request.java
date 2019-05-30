import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Request
{
	public String apikey;
	public String username;
	public RequestAction action;
	public String drug;
	public Long quantity;
	public Address address;
	
	public void jsonToObject(String json) throws ParseException, IllegalArgumentException
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = (JSONObject)parser.parse(json);

		this.apikey = (String)jsonObj.get("apikey");
		this.username = (String)jsonObj.get("username");
		this.drug = (String)jsonObj.get("drug");
		if (this.apikey==null || this.username==null || this.drug==null)
		{
			throw new IllegalArgumentException("Invalid arguments");
		}
		this.action = RequestAction.valueOf(((String)jsonObj.get("action")).toUpperCase());

		if (this.action == RequestAction.SHIP)
		{
			this.quantity = (Long)jsonObj.get("quantity");
			JSONObject jsonAddress = (JSONObject)jsonObj.get("address");
			Address address = new Address();
			address.customer = (String)jsonAddress.get("customer");
			address.street = (String)jsonAddress.get("street");
			address.city = (String)jsonAddress.get("city");
			address.province = (String)jsonAddress.get("province");
			address.country = (String)jsonAddress.get("country");
			address.postalCode = (String)jsonAddress.get("postalCode");
			this.address = address;
			if (this.quantity==null || this.address.customer==null || this.address.street==null
				|| this.address.city==null || this.address.province==null
				|| this.address.country==null || this.address.postalCode==null)
			{
				throw new IllegalArgumentException("Invalid arguments");
			}
		}
	}
}
