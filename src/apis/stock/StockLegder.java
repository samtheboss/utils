package apis.stock;


/**
 * @author Fidelis
 *
 */
public class StockLegder {
	private String ledgerName;
	private String ledgerNumber;
	private double amountValue;
	private double revenuePerc;
	private double revenuePercCumul;
	
	public StockLegder(String ledgerNumber, String ledgerName, double amountValue, double revenuePerc,
			double revenuePercCumul) {
		super();
		this.ledgerName = ledgerName;
		this.ledgerNumber = ledgerNumber;
		this.amountValue = amountValue;
		this.revenuePerc = revenuePerc;
		this.revenuePercCumul = revenuePercCumul;
	}
	
	public String getLedgerName() {
		return ledgerName;
	}
	public void setLedgerName(String ledgerName) {
		this.ledgerName = ledgerName;
	}
	public String getLedgerNumber() {
		return ledgerNumber;
	}
	public void setLedgerNumber(String ledgerNumber) {
		this.ledgerNumber = ledgerNumber;
	}
	public double getAmountValue() {
		return amountValue;
	}
	public void setAmountValue(double amountValue) {
		this.amountValue = amountValue;
	}
	public double getRevenuePerc() {
		return revenuePerc;
	}
	public void setRevenuePerc(double revenuePerc) {
		this.revenuePerc = revenuePerc;
	}
	public double getRevenuePercCumul() {
		return revenuePercCumul;
	}
	public void setRevenuePercCumul(double revenuePercCumul) {
		this.revenuePercCumul = revenuePercCumul;
	}
	
	@Override
	public String toString() {
		return "StockLegder [ledgerName=" + ledgerName + ", ledgerNumber=" + ledgerNumber + ", amountValue="
				+ amountValue + ", revenuePerc=" + revenuePerc + ", revenuePercCumul=" + revenuePercCumul + "]";
	}
	
}
