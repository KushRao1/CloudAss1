public class MockAuthentication implements IAuthentication
{
	@Override
	public boolean authenticate(String apiKey)
	{
		boolean pass = true;
		if (apiKey.toUpperCase().indexOf("TRUE")>-1)
		{
			pass = true;
		}
		else if (apiKey.isEmpty() || apiKey.toUpperCase().indexOf("FALSE")>-1)
		{
			pass = false;
		}
		return pass;
	}

	@Override
	public boolean authorize(String username, RequestAction action)
	{
		boolean pass = false;
		if (!username.isEmpty())
		{
			if (action == RequestAction.SHIP && username.toUpperCase().indexOf("SHIP")>-1)
			{
				pass = true;
			}
			if (action == RequestAction.QUERY && username.toUpperCase().indexOf("QUERY")>-1)
			{
				pass = true;
			}
		}
		return pass;
	}
}
