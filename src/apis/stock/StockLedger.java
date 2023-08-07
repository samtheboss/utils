package apis.stock;

import java.util.Date;

public class StockLedger {

    private double serialNo;
    private String orderType;
    private double orderNo;
    private String journalType;
    private double journalNumber;
    private String trnType;
    private double orderQty;
    private String createdBy;
    private Date dateCreated = new Date();
    private Date dateModified = new Date();
    private String approvedBy;
    private Date dateApproved = new Date();
    private String itemCode;
    private String description;
    private String suom;
    private String confirmed;
    private double confirmedOrderNo;
    private double confirmedSerialNo;
    private double confirmedQty;
    private Date confirmedApprovalDate = new Date();
    private String confirmedapprovedBy;
    private String deviceId;
    private double packSize;
    private String itemTYpe;
    private Date expiryDate;
    private String lotSerial;
    private String itemLocation;
    private String barcode;
    private String alreadyConfirmed;

    public StockLedger() {
    }

    public StockLedger(double serialNo, String orderType, double orderNo, String journalType, double journalNumber, String trnType, double orderQty,
            String createdBy, Date dateCreated, Date dateModified, String approvedBy, Date dateApproved,
            String itemCode, String description, String suom, String confirmed, double confirmedOrderNo,
            double confirmedSerialNo, double confirmedQty, Date confirmedApprovalDate, String confirmedapprovedBy,
            String deviceId, double packSize, String itemTYpe, Date expiryDate, String lotSerial, String itemLocation,
            String barcode) {
        super();
        this.serialNo = serialNo;
        this.orderType = orderType;
        this.orderNo = orderNo;
        this.journalType = journalType;
        this.journalNumber = journalNumber;
        this.trnType = trnType;
        this.orderQty = orderQty;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.approvedBy = approvedBy;
        this.dateApproved = dateApproved;
        this.itemCode = itemCode;
        this.description = description;
        this.suom = suom;
        this.confirmed = confirmed;
        this.confirmedOrderNo = confirmedOrderNo;
        this.confirmedSerialNo = confirmedSerialNo;
        this.confirmedQty = confirmedQty;
        this.confirmedApprovalDate = confirmedApprovalDate;
        this.confirmedapprovedBy = confirmedapprovedBy;
        this.deviceId = deviceId;
        this.packSize = packSize;
        this.itemTYpe = itemTYpe;
        this.expiryDate = expiryDate;
        this.lotSerial = lotSerial;
        this.itemLocation = itemLocation;
        this.barcode = barcode;
    }

    public StockLedger(double serialNo, String orderType, double orderNo, String journalType, double journalNumber, String trnType, double orderQty,
            String createdBy, Date dateCreated, Date dateModified, String approvedBy, Date dateApproved,
            String itemCode, String description, String suom, String confirmed, double confirmedOrderNo,
            double confirmedSerialNo, double confirmedQty, Date confirmedApprovalDate, String confirmedapprovedBy,
            String deviceId, double packSize, String itemTYpe, Date expiryDate, String lotSerial, String itemLocation,
            String barcode, String alreadyConfirmed) {
        super();
        this.serialNo = serialNo;
        this.orderType = orderType;
        this.orderNo = orderNo;
        this.journalType = journalType;
        this.journalNumber = journalNumber;
        this.trnType = trnType;
        this.orderQty = orderQty;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
        this.approvedBy = approvedBy;
        this.dateApproved = dateApproved;
        this.itemCode = itemCode;
        this.description = description;
        this.suom = suom;
        this.confirmed = confirmed;
        this.confirmedOrderNo = confirmedOrderNo;
        this.confirmedSerialNo = confirmedSerialNo;
        this.confirmedQty = confirmedQty;
        this.confirmedApprovalDate = confirmedApprovalDate;
        this.confirmedapprovedBy = confirmedapprovedBy;
        this.deviceId = deviceId;
        this.packSize = packSize;
        this.itemTYpe = itemTYpe;
        this.expiryDate = expiryDate;
        this.lotSerial = lotSerial;
        this.itemLocation = itemLocation;
        this.barcode = barcode;
        this.alreadyConfirmed = alreadyConfirmed;
    }

    public double getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(double serialNo) {
        this.serialNo = serialNo;
    }

    public String getAlreadyConfirmed() {
        return alreadyConfirmed;
    }

    public void setAlreadyConfirmed(String alreadyConfirmed) {
        this.alreadyConfirmed = alreadyConfirmed;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public double getOrderNo() {
        return orderNo;
    }

    public String getJournalType() {
        return journalType;
    }

    public void setJournalType(String journalType) {
        this.journalType = journalType;
    }

    public double getJournalNumber() {
        return journalNumber;
    }

    public void setJournalNumber(double journalNumber) {
        this.journalNumber = journalNumber;
    }

    public void setOrderNo(double orderNo) {
        this.orderNo = orderNo;
    }

    public String getTrnType() {
        return trnType;
    }

    public void setTrnType(String trnType) {
        this.trnType = trnType;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(double orderQty) {
        this.orderQty = orderQty;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Date getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(Date dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuom() {
        return suom;
    }

    public void setSuom(String suom) {
        this.suom = suom;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public double getConfirmedOrderNo() {
        return confirmedOrderNo;
    }

    public void setConfirmedOrderNo(double confirmedOrderNo) {
        this.confirmedOrderNo = confirmedOrderNo;
    }

    public double getConfirmedSerialNo() {
        return confirmedSerialNo;
    }

    public void setConfirmedSerialNo(double confirmedSerialNo) {
        this.confirmedSerialNo = confirmedSerialNo;
    }

    public double getConfirmedQty() {
        return confirmedQty;
    }

    public void setConfirmedQty(double confirmedQty) {
        this.confirmedQty = confirmedQty;
    }

    public Date getConfirmedApprovalDate() {
        return confirmedApprovalDate;
    }

    public void setConfirmedApprovalDate(Date confirmedApprovalDate) {
        this.confirmedApprovalDate = confirmedApprovalDate;
    }

    public String getConfirmedapprovedBy() {
        return confirmedapprovedBy;
    }

    public void setConfirmedapprovedBy(String confirmedapprovedBy) {
        this.confirmedapprovedBy = confirmedapprovedBy;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getPackSize() {
        return packSize;
    }

    public void setPackSize(double packSize) {
        this.packSize = packSize;
    }

    public String getItemTYpe() {
        return itemTYpe;
    }

    public void setItemTYpe(String itemTYpe) {
        this.itemTYpe = itemTYpe;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getLotSerial() {
        return lotSerial;
    }

    public void setLotSerial(String lotSerial) {
        this.lotSerial = lotSerial;
    }

    public String getItemLocation() {
        return itemLocation;
    }

    public void setItemLocation(String itemLocation) {
        this.itemLocation = itemLocation;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return "StockLedger [serialNo=" + serialNo + ", orderType=" + orderType + ", orderNo=" + orderNo
                + ", journalType=" + journalType + ", journalNumber=" + journalNumber + ", trnType=" + trnType
                + ", orderQty=" + orderQty + ", createdBy=" + createdBy + ", dateCreated=" + dateCreated
                + ", dateModified=" + dateModified + ", approvedBy=" + approvedBy + ", dateApproved=" + dateApproved
                + ", itemCode=" + itemCode + ", description=" + description + ", suom=" + suom + ", confirmed="
                + confirmed + ", confirmedOrderNo=" + confirmedOrderNo + ", confirmedSerialNo=" + confirmedSerialNo
                + ", confirmedQty=" + confirmedQty + ", confirmedApprovalDate=" + confirmedApprovalDate
                + ", confirmedapprovedBy=" + confirmedapprovedBy + ", deviceId=" + deviceId + ", packSize=" + packSize
                + ", itemTYpe=" + itemTYpe + ", expiryDate=" + expiryDate + ", lotSerial=" + lotSerial
                + ", itemLocation=" + itemLocation + "]";
    }

}
