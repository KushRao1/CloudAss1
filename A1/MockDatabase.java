public class MockDatabase implements IDatabase
{
	@Override
	public Long stockForDrug(String drugName)
	{
		DrugRepository repo = DrugRepository.getInstance();
		return repo.getStockByName(drugName);
	}

	@Override
	public boolean existsDrug(String drugName)
	{
		DrugRepository repo = DrugRepository.getInstance();
		return repo.existsDrug(drugName);
	}

	@Override
	public void claimDrug(String drugName, Long quantity)
	{
		DrugRepository repo = DrugRepository.getInstance();
		Long currentStock = repo.getStockByName(drugName);
		Long updatedStock = Long.valueOf(currentStock.longValue() - quantity.longValue());
		repo.updateStock(drugName, updatedStock);
	}

}
