public class MockShipMate implements IShipMate
{
	Address mockAddress;

	public MockShipMate()
	{
		// For testing purpose, every address from Canada will be a known address.
		mockAddress = new Address();
		mockAddress.country = "CANADA";
	}

	@Override
	public boolean isKnownAddress(Address address)
	{
		boolean isKnown = false;
		if (address.customer.isEmpty() || address.street.isEmpty() ||
			address.city.isEmpty() || address.province.isEmpty() ||
			address.country.isEmpty() || address.postalCode.isEmpty())
		{
			isKnown = false;
		}
		else if (address.country.toUpperCase().equals(mockAddress.country))
		{
			isKnown = true;
		}
		return isKnown;
	}

	@Override
	public String shipToAddress(Address address, int count, String drugName) throws Exception
	{
		if (!isKnownAddress(address))
		{
			throw new Exception("Unknown address");
		}
		return "01-06-2019";
	}
}
