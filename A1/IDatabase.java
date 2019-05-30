// YOU ARE ALLOWED TO MODIFY THIS FILE
public interface IDatabase
{
	public Long stockForDrug(String drugName);

	public boolean existsDrug(String drugName);

	public void claimDrug(String drugName, Long quantity);
}