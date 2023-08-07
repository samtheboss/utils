package apis.stock;

import java.util.List;

public class StockLedgerDTO {

	private boolean error;
	private String message;
	private List<StockLedger> data;
	
	public StockLedgerDTO() {
		super();
	}

	public StockLedgerDTO(boolean error, String message, List<StockLedger> data) {
		super();
		this.error = error;
		this.message = message;
		this.data = data;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<StockLedger> getData() {
		return data;
	}

	public void setData(List<StockLedger> data) {
		this.data = data;
	}

}
