// YOU ARE ALLOWED TO MODIFY THIS FILE

public class RequestProcessor implements IRequestProcessor
{
	/*
		This is dependency injection. Everything the class and this method needs to do their job is
		passed to it. This allows you to perfectly test every aspect of your class by writing mock
		objects that implement these interfaces such that you can test every possible path through
		your code.
	*/
	public String processRequest(String json,
								 IAuthentication authentication,
								 IShipMate shipMate,
								 IDatabase database)
	{
		String response = "";
		Request request = new Request();
		try
		{
			request.jsonToObject(json);
		}
		catch (Exception e)
		{
			response = new ResponseError(400L, ResponseError.GNRAL_BAD_REQUEST).toJsonString();
		}
		if (response.isEmpty())
		{
			if (authentication.authenticate(request.apikey))
			{
				if (authentication.authorize(request.username, request.action))
				{
					if (database.existsDrug(request.drug))
					{
						if (request.action == RequestAction.QUERY)
						{
							Long stock = database.stockForDrug(request.drug);
							response = new ResponseForQuery(stock).toJsonString();
						}
						else if (request.action == RequestAction.SHIP)
						{
							if (shipMate.isKnownAddress(request.address))
							{
								if (database.stockForDrug(request.drug).compareTo(request.quantity) >= 0)
								{
									database.claimDrug(request.drug, request.quantity);
									try
									{
										String deliveryDate = shipMate.shipToAddress(request.address, request.quantity.intValue(), request.drug);
										response = new ResponseForShip(deliveryDate).toJsonString();
									}
									catch (Exception e)
									{
										response = new ResponseError(ResponseError.SHIP_UNK_ADDRESS).toJsonString();
									}
								}
								else
								{
									response = new ResponseError(ResponseError.SHIP_INSUF_STOCK).toJsonString();
								}
							}
							else
							{
								response = new ResponseError(ResponseError.SHIP_UNK_ADDRESS).toJsonString();
							}
						}
					}
					else
					{
						response = new ResponseError(ResponseError.SHIP_UNK_DRUG).toJsonString();
					}
				}
				else
				{
					response = new ResponseError(ResponseError.SECUR_NOT_AUTHORIZED).toJsonString();
				}
			}
			else
			{
				response = new ResponseError(ResponseError.SECUR_AUTH_FAILURE).toJsonString();
			}
		}
		return response;
	}

	/*
		Insert all of your instantiation of mock objects and RequestProcessor(s)
		here. Then insert calls to all of your unit tests for the RequestProcessor
		class.  These tests should send different combinations of JSON strings
		to your class with mock objects such that you test all paths through the
		API.  Write one test function per "path" you are testing.  For example,
		to test authentication you would write two unit tests: authenticateSuccess()
		that passes JSON with a known API key that should be authenticated by your
		mock security object and tests for the correct JSON response from processRequest(),
		and authenticateFailure() that passes JSON with a bad API key that should fail to
		be authenticated by your mock security object and tests for the correct JSON
		response from processRequest().

		The runUnitTests() method will be called by Main.java. It must run your unit tests.
		All of your unit tests should System.out.println() one line indicating pass or
		failure with the following format:
		PASS - <Name of test>
		FAIL - <Name of test>
	*/

	public static IAuthentication authentication;
	public static IShipMate shipMate;
	public static IDatabase database;
	public static IRequestProcessor processor;

	public static void testSetup()
	{
		authentication = new MockAuthentication();
		shipMate = new MockShipMate();
		database = new MockDatabase();
		processor = new RequestProcessor();
		
		DrugRepository.getInstance().cleanData();
		DrugRepository.getInstance().insertData("Tylenol", 630L, "Ceftacor", 250L, "Nalopizole", 320L);
	}

	public static void malformedJsonRequest()
	{
		String json = "{\n" + 
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.GNRAL_BAD_REQUEST))
		{
			System.out.println("PASS - malformedJsonRequest");
		}
		else
		{
			System.out.println("FAIL - malformedJsonRequest");
		}
	}

	public static void missedApikeyInRequest()
	{
		String json = "{\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\"Tylenol\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.GNRAL_BAD_REQUEST))
		{
			System.out.println("PASS - missedApikeyInRequest");
		}
		else
		{
			System.out.println("FAIL - missedApikeyInRequest");
		}
	}

	public static void unknownActionInRequest()
	{
		String json = "{\n" + 
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"OTHERACTION\",\n" + 
				"   \"drug\":\"Tylenol\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.GNRAL_BAD_REQUEST))
		{
			System.out.println("PASS - unknownActionInRequest");
		}
		else
		{
			System.out.println("FAIL - unknownActionInRequest");
		}
	}

	public static void missedQuantityInShipRequest()
	{
		String json = "{\n" + 
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Tylenol\",\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Halifax\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.GNRAL_BAD_REQUEST))
		{
			System.out.println("PASS - missedQuantityInShipRequest");
		}
		else
		{
			System.out.println("FAIL - missedQuantityInShipRequest");
		}
	}

	public static void incompleteAddressInShipRequest()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhas8989lkxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Tylenol\",\n" + 
				"   \"quantity\":100,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.GNRAL_BAD_REQUEST))
		{
			System.out.println("PASS - incompleteAddressInShipRequest");
		}
		else
		{
			System.out.println("FAIL - incompleteAddressInShipRequest");
		}
	}

	public static void authenticateFailure()
	{
		String json = "{\n" + 
				"   \"apikey\":\"qwertyfalseasdfg\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\"Tylenol\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SECUR_AUTH_FAILURE))
		{
			System.out.println("PASS - authenticateFailure");
		}
		else
		{
			System.out.println("FAIL - authenticateFailure");
		}
	}

	public static void authenticateSuccessAndAuthorizedForQueryFailure()
	{
		String json = "{\n" + 
				"   \"apikey\":\"qwertytrueasdfg\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\"Tylenol\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SECUR_NOT_AUTHORIZED))
		{
			System.out.println("PASS - authenticateSuccessAndAuthorizedForQueryFailure");
		}
		else
		{
			System.out.println("FAIL - authenticateSuccessAndAuthorizedForQueryFailure");
		}
	}

	public static void authenticateSuccessAndAuthorizedForQuerySuccess()
	{
		String json = "{\n" + 
				"   \"apikey\":\"qwertytrueasdfg\",\n" + 
				"   \"username\":\"rhawkeyquery\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\"Tylenol\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		Long status = (Long)Response.getFieldFromJson("status", response);
		if (status.equals(200L))
		{
			System.out.println("PASS - authenticateSuccessAndAuthorizedForQuerySuccess");
		}
		else
		{
			System.out.println("FAIL - authenticateSuccessAndAuthorizedForQuerySuccess");
		}
	}

	public static void authenticateSuccessAndAuthorizedForShipFailure()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkey\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Tylenol\",\n" + 
				"   \"quantity\":100,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Halifax\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SECUR_NOT_AUTHORIZED))
		{
			System.out.println("PASS - authenticateSuccessAndAuthorizedForShipFailure");
		}
		else
		{
			System.out.println("FAIL - authenticateSuccessAndAuthorizedForShipFailure");
		}
	}

	public static void authenticateSuccessAndAuthorizedForShipSuccess()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkeyship\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Tylenol\",\n" + 
				"   \"quantity\":100,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Halifax\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		Long status = (Long)Response.getFieldFromJson("status", response);
		if (status.equals(200L))
		{
			System.out.println("PASS - authenticateSuccessAndAuthorizedForShipSuccess");
		}
		else
		{
			System.out.println("FAIL - authenticateSuccessAndAuthorizedForShipSuccess");
		}
	}

	public static void existsDrugFailure()
	{
		String json = "{\n" + 
				"   \"apikey\":\"qwertytrueasdfg\",\n" + 
				"   \"username\":\"rhawkeyquery\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\"Proranon\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SHIP_UNK_DRUG))
		{
			System.out.println("PASS - existsDrugFailure");
		}
		else
		{
			System.out.println("FAIL - existsDrugFailure");
		}
	}

	public static void existsDrugSuccessAndStockQuerySuccess()
	{
		String json = "{\n" + 
				"   \"apikey\":\"qwertytrueasdfg\",\n" + 
				"   \"username\":\"rhawkeyquery\",\n" + 
				"   \"action\":\"QUERY\",\n" + 
				"   \"drug\":\"Tylenol\"\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		Long count = (Long)Response.getFieldFromJson("count", response);
		if (count.compareTo(0L)>=0)
		{
			System.out.println("PASS - existsDrugSuccessAndStockQuerySuccess");
		}
		else
		{
			System.out.println("FAIL - existsDrugSuccessAndStockQuerySuccess");
		}
	}

	public static void knownAddressFailure()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkeyship\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Tylenol\",\n" + 
				"   \"quantity\":100,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Robert Thomas\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Manhattan\",\n" + 
				"      \"province\":\"New York\",\n" + 
				"      \"country\":\"United States\",\n" + 
				"      \"postalCode\":\"123456\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SHIP_UNK_ADDRESS))
		{
			System.out.println("PASS - knownAddressFailure");
		}
		else
		{
			System.out.println("FAIL - knownAddressFailure");
		}
	}

	public static void emptyAddressFailure()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkeyship\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Tylenol\",\n" + 
				"   \"quantity\":100,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"\",\n" + 
				"      \"street\":\"\",\n" + 
				"      \"city\":\"\",\n" + 
				"      \"province\":\"k\",\n" + 
				"      \"country\":\"\",\n" + 
				"      \"postalCode\":\"\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SHIP_UNK_ADDRESS))
		{
			System.out.println("PASS - emptyAddressFailure");
		}
		else
		{
			System.out.println("FAIL - emptyAddressFailure");
		}
	}

	public static void knownAddressSuccessAndSufficientStockFailure()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkeyship\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Ceftacor\",\n" + 
				"   \"quantity\":251,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Halifax\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String error = (String)Response.getFieldFromJson("error", response);
		if (error.equals(ResponseError.SHIP_INSUF_STOCK))
		{
			System.out.println("PASS - knownAddressSuccessAndSufficientStockFailure");
		}
		else
		{
			System.out.println("FAIL - knownAddressSuccessAndSufficientStockFailure");
		}
	}

	public static void knownAddressSuccessAndSufficientStockSuccess()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkeyship\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Ceftacor\",\n" + 
				"   \"quantity\":249,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Halifax\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String deliverydate = (String)Response.getFieldFromJson("estimateddeliverydate", response);
		if (deliverydate.equals("01-06-2019"))
		{
			System.out.println("PASS - knownAddressSuccessAndSufficientStockSuccess");
		}
		else
		{
			System.out.println("FAIL - knownAddressSuccessAndSufficientStockSuccess");
		}
	}

	public static void knownAddressSuccessAndExactStockSuccess()
	{
		String json = "{\n" +
				"   \"apikey\":\"ab882jjhastruekxl;klasdf8u22j\",\n" + 
				"   \"username\":\"rhawkeyship\",\n" + 
				"   \"action\":\"SHIP\",\n" + 
				"   \"drug\":\"Nalopizole\",\n" + 
				"   \"quantity\":320,\n" + 
				"   \"address\":{ \n" + 
				"      \"customer\":\"Rob Hawkey\",\n" + 
				"      \"street\":\"123 Street\",\n" + 
				"      \"city\":\"Halifax\",\n" + 
				"      \"province\":\"Nova Scotia\",\n" + 
				"      \"country\":\"Canada\",\n" + 
				"      \"postalCode\":\"H0H0H0\"\n" + 
				"   }\n" + 
				"}";
		String response = processor.processRequest(json, authentication, shipMate, database);
		String deliverydate = (String)Response.getFieldFromJson("estimateddeliverydate", response);
		if (deliverydate.equals("01-06-2019"))
		{
			System.out.println("PASS - knownAddressSuccessAndExactStockSuccess");
		}
		else
		{
			System.out.println("FAIL - knownAddressSuccessAndExactStockSuccess");
		}
	}

	public static void runUnitTests()
	{
		testSetup();
		malformedJsonRequest();
		missedApikeyInRequest();
		unknownActionInRequest();
		missedQuantityInShipRequest();
		incompleteAddressInShipRequest();
		authenticateFailure();
		authenticateSuccessAndAuthorizedForQueryFailure();
		authenticateSuccessAndAuthorizedForQuerySuccess();
		authenticateSuccessAndAuthorizedForShipFailure();
		authenticateSuccessAndAuthorizedForShipSuccess();
		existsDrugFailure();
		existsDrugSuccessAndStockQuerySuccess();
		knownAddressFailure();
		emptyAddressFailure();
		knownAddressSuccessAndSufficientStockFailure();
		knownAddressSuccessAndSufficientStockSuccess();
		knownAddressSuccessAndExactStockSuccess();
	}
}
