/*

 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apis.stock;

/**
 *
 * @author USER
 */
public class itemDetails
{

    String itemCode;
    String itemName;
    String suom;
    double buyingPrice;
    double sellingPrice;
    double profit;
    double profitPerc;
    double profitPercCumul;
    int quantityIssued;
    int quantitySold;
    double productConsumptionRate;
    double productConsumptionRateCumul;
    double quantityIssuedPerc;
    double quantityIssuedPercCumul;
    int itemPosition;
    int totalItems;
    double itemPositionPerc;
    double itemPositionPercCumul;
    double itemRevenuePerc;
    double itemRevenuePercCumul;
    double itemRevenue;
    double itemTurnOverRatio;
    double itemUsage;
    double cumulItemUsage;

    public enum PERIOD_TYPE
    {
        DAY, MONTH, YEAR
    }

    public enum TRANSACTION_TYPES
    {
        ALL, SALES, PURCHASES, ADJUSTMENTS, TRANSFERS
    }

    public itemDetails()
    {
    }

    public itemDetails(String itemCode, String itemName, String suom, double buyingPrice,
            double sellingPrice, double profit, double profitPerc,
            double profitPercCumul, int quantityIssued, int quantitySold, double productConsumptionRate,
            double productConsumptionRateCumul, double quantityIssuedPerc, double quantityIssuedPercCumul,
            int itemPosition, int totalItems, double itemPositionPerc, double itemPositionPercCumul,
            double itemRevenuePerc, double itemRevenuePercCumul, double itemRevenue, double itemTurnOverRatio,
            double itemUsage, double cumulItemUsage)
    {
        super();
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.suom = suom;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.profit = profit;
        this.profitPerc = profitPerc;
        this.profitPercCumul = profitPercCumul;
        this.quantityIssued = quantityIssued;
        this.quantitySold = quantitySold;
        this.productConsumptionRate = productConsumptionRate;
        this.productConsumptionRateCumul = productConsumptionRateCumul;
        this.quantityIssuedPerc = quantityIssuedPerc;
        this.quantityIssuedPercCumul = quantityIssuedPercCumul;
        this.itemPosition = itemPosition;
        this.totalItems = totalItems;
        this.itemPositionPerc = itemPositionPerc;
        this.itemPositionPercCumul = itemPositionPercCumul;
        this.itemRevenuePerc = itemRevenuePerc;
        this.itemRevenuePercCumul = itemRevenuePercCumul;
        this.itemRevenue = itemRevenue;
        this.itemTurnOverRatio = itemTurnOverRatio;
        this.itemUsage = itemUsage;
        this.cumulItemUsage = cumulItemUsage;
    }

    public String getItemCode()
    {
        return itemCode;
    }

    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public double getProfitPerc()
    {
        return profitPerc;
    }

    public void setProfitPerc(double profitPerc)
    {
        this.profitPerc = profitPerc;
    }

    public double getProfitPercCumul()
    {
        return profitPercCumul;
    }

    public void setProfitPercCumul(double profitPercCumul)
    {
        this.profitPercCumul = profitPercCumul;
    }

    public String getSuom()
    {
        return suom;
    }

    public double getProfit()
    {
        return profit;
    }

    public void setProfit(double profit)
    {
        this.profit = profit;
    }

    public int getQuantitySold()
    {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold)
    {
        this.quantitySold = quantitySold;
    }

    public void setSuom(String suom)
    {
        this.suom = suom;
    }

    public double getBuyingPrice()
    {
        return buyingPrice;
    }

    public void setBuyingPrice(double buyingPrice)
    {
        this.buyingPrice = buyingPrice;
    }

    public double getSellingPrice()
    {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice)
    {
        this.sellingPrice = sellingPrice;
    }

    public double getProductConsumptionRate()
    {
        return productConsumptionRate;
    }

    public void setProductConsumptionRate(double productConsumptionRate)
    {
        this.productConsumptionRate = productConsumptionRate;
    }

    public int getQuantityIssued()
    {
        return quantityIssued;
    }

    public void setQuantityIssued(int quantityIssued)
    {
        this.quantityIssued = quantityIssued;
    }

    public int getItemPosition()
    {
        return itemPosition;
    }

    public double getProductConsumptionRateCumul()
    {
        return productConsumptionRateCumul;
    }

    public void setProductConsumptionRateCumul(double productConsumptionRateCumul)
    {
        this.productConsumptionRateCumul = productConsumptionRateCumul;
    }

    public void setItemPosition(int itemPosition)
    {
        this.itemPosition = itemPosition;
    }

    public int getTotalItems()
    {
        return totalItems;
    }

    public void setTotalItems(int totalItems)
    {
        this.totalItems = totalItems;
    }

    public double getItemPositionPerc()
    {
        return itemPositionPerc;
    }

    public void setItemPositionPerc(double itemPositionPerc)
    {
        this.itemPositionPerc = itemPositionPerc;
    }

    public double getItemPositionPercCumul()
    {
        return itemPositionPercCumul;
    }

    public void setItemPositionPercCumul(double itemPositionPercCumul)
    {
        this.itemPositionPercCumul = itemPositionPercCumul;
    }

    public double getItemRevenuePerc()
    {
        return itemRevenuePerc;
    }

    public void setItemRevenuePerc(double itemRevenuePerc)
    {
        this.itemRevenuePerc = itemRevenuePerc;
    }

    public double getItemRevenuePercCumul()
    {
        return itemRevenuePercCumul;
    }

    public void setItemRevenuePercCumul(double itemRevenuePercCumul)
    {
        this.itemRevenuePercCumul = itemRevenuePercCumul;
    }

    public double getItemRevenue()
    {
        return itemRevenue;
    }

    public void setItemRevenue(double itemRevenue)
    {
        this.itemRevenue = itemRevenue;
    }

    public double getQuantityIssuedPerc()
    {
        return quantityIssuedPerc;
    }

    public void setQuantityIssuedPerc(double quantityIssuedPerc)
    {
        this.quantityIssuedPerc = quantityIssuedPerc;
    }

    public double getQuantityIssuedPercCumul()
    {
        return quantityIssuedPercCumul;
    }

    public void setQuantityIssuedPercCumul(double quantityIssuedPercCumul)
    {
        this.quantityIssuedPercCumul = quantityIssuedPercCumul;
    }

    public double getItemTurnOverRatio()
    {
        return itemTurnOverRatio;
    }

    public void setItemTurnOverRatio(double itemTurnOverRatio)
    {
        this.itemTurnOverRatio = itemTurnOverRatio;
    }

    public double getItemUsage()
    {
        return itemUsage;
    }

    public void setItemUsage(double itemUsage)
    {
        this.itemUsage = itemUsage;
    }

    @Override
    public String toString()
    {
        return "itemDetails [itemCode=" + itemCode + ", itemName=" + itemName + ", suom=" + suom + ", itemPosition="
                + itemPosition + "]";
    }

}
