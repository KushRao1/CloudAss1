import java.util.LinkedHashMap;
import java.util.Map;

public class DrugRepository
{
	private static DrugRepository repository;
	private Map<String, Long> drugData;
	
	private DrugRepository()
	{
		drugData = new LinkedHashMap<String, Long>();
	}

	public void insertData(Object... data)
	{
		if (data.length % 2 != 0)
		{
			throw new IllegalArgumentException("Odd number of arguments");
		}

		String drugName = null;
		Long stock = null;
		int step = 0;

		for (Object keyOrValue : data) {
			switch (step++ % 2) {
			case 0:
				if (keyOrValue == null)
				{
					throw new IllegalArgumentException("Null key value");
				}
				drugName = (String)keyOrValue;
				continue;
			case 1:
				stock = (Long)keyOrValue;
				drugData.put(drugName, stock);
				break;
			}
		}
	}

	public void cleanData()
	{
		drugData.clear();
	}

	public Long getStockByName(String drugName)
	{
		return drugData.get(drugName);
	}

	public boolean existsDrug(String drugName)
	{
		return drugData.containsKey(drugName);
	}

	public void updateStock(String drugName, Long stock)
	{
		drugData.put(drugName, stock);
	}

	public static DrugRepository getInstance()
	{
		if (repository == null)
		{
			repository = new DrugRepository();
		}
		return repository;
	}
}
