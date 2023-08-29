package apis.stock;

import com.jfoenix.controls.JFXCheckBox;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.Notifications;

public class StockLedgerController implements Initializable {

    @FXML
    private JFXCheckBox ch_sales;

    @FXML
    private JFXCheckBox ch_purchase;

    @FXML
    private JFXCheckBox ch_adjustment;
    @FXML
    private Label lb_trn_type;
    @FXML
    private JFXCheckBox ch_transfer;
    @FXML
    private TableColumn<StockLedger, String> description_column;

    @FXML
    private TableColumn<StockLedger, String> itemCode_column;

    @FXML
    private StackPane main_page_pane;

    @FXML
    private TableColumn<StockLedger, String> orderNo_column;

    @FXML
    private TableColumn<StockLedger, String> orderQty_column;

    @FXML
    private TableColumn<StockLedger, String> serialNo_column;

    @FXML
    private TableColumn<StockLedger, String> suom_column;

    @FXML
    private TableColumn<StockLedger, ?> confirmation_column;

    @FXML
    private TableView<StockLedger> table;

    @FXML
    private TextField order_number_textfield;
    @FXML
    private Button search_button;
    @FXML
    private ComboBox<String> stocktype_combobox;
    @FXML
    private Button save_stocks_button;
    @FXML
    private Button cancel_button;

    @FXML
    private Label progress_label;

    @FXML
    private HBox progressHbox;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private Label progressInfoLabel;

    private static final Logger LOG = Logger.getLogger(StockLedgerController.class.getName());
    String systemModule = "";
    List<StockLedger> data = new ArrayList<>();
    StockTakingDataHandler stockTakingDataHandler = new StockTakingDataHandler();
    String trns;
    double orderNumber;
    String audited = "X";

    int i = 1;

    ObservableList<String> saleTypes = FXCollections.observableArrayList();

    ;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setTrnnType();

        search_button.setOnAction(e -> {
            trns = (getSaleTypeList(stocktype_combobox.getValue(), systemModule).length() > 0)
                    ? getSaleTypeList(stocktype_combobox.getValue(), systemModule) : null;
            orderNumber = Double.parseDouble(order_number_textfield.getText());
            //	data = StockTakingDataHandler.loadData(trns, orderNumber, null, audited, null, null, 1, null);
            loadTable();
        });

        save_stocks_button.setOnAction(e -> {
            if (data.size() > 0) {

                List<StockLedger> confirmedStocks = new ArrayList<>();
                data.forEach(stk -> {
                    if (stk.getConfirmed().equals("Y") && "N".equals(stk.getAlreadyConfirmed())) {
                        stk.setDeviceId(getDeviceId());
                        confirmedStocks.add(stk);
                        System.out.println(stk.getAlreadyConfirmed() + "  " + stk.getConfirmed() + "  " + stk.getDeviceId());
                    }

                });
//                System.out.println(confirmedStocks);

                Task<Void> task = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        stockTakingDataHandler.saveStocks(confirmedStocks);
                        return null;
                    }
                };

                task.setOnSucceeded(se -> {
                    progress_label.setStyle("-fx-text-fill: green");
                    progress_label.setText("Saved successfully");
                    AnalysisService.displayTarnsitionLabel(progress_label);
                    loadTable();
                });
                new Thread(task).start();

            }
        });
    }

    public void setTrnnType() {

        ch_sales.setOnAction(e -> {
            handleCheckBoxSelection(ch_sales,
                    Arrays.asList("CASH SALES", "SALE INVOICE",
                            "SALES RETURN", "CASH ORDERS", "CREDIT ORDERS", "ORDERS RETURN"), "SALES");
            lb_trn_type.setText("SALES TYPE");
        });
        ch_purchase.setOnAction(e -> {
            handleCheckBoxSelection(ch_purchase,
                    Arrays.asList("CASH PURCHASES", "PURCHASE INVOICE", "PURCHASE RETURN"), "PURCHASES");
            lb_trn_type.setText("PURCHASES TYPE");
        });
        ch_adjustment.setOnAction(e -> {
            handleCheckBoxSelection(ch_adjustment,
                    Arrays.asList("ADJUSTMENT", "STOCK TAKING", "OPENING STOCK", "EXPENSES", "BREAKAGES"), "ADJUSTMENTS");
            lb_trn_type.setText("ADJUSTMENT TYPE");
        });
        ch_transfer.setOnAction(e -> {
            handleCheckBoxSelection(ch_transfer,
                    Arrays.asList("TRANSFER", "RECEIVED"), "TRANSFER");
            lb_trn_type.setText("TRANSFER TYPE");

        });

    }

    private void handleCheckBoxSelection(CheckBox selectedCheckBox, List<String> saleTypesList, String module) {
        ch_sales.setSelected(false);
        ch_purchase.setSelected(false);
        ch_adjustment.setSelected(false);
        ch_transfer.setSelected(false);
        selectedCheckBox.setSelected(true);
        if (saleTypesList != null) {
            saleTypes.clear();
            saleTypes.addAll(saleTypesList);
            stocktype_combobox.setItems(saleTypes);
            systemModule = module;
            table.getItems().clear();
        }
    }

//  
    public void loadTable() {
        List<StockLedger> stocksList = new ArrayList<>();
        data.clear();
        table.getItems().clear();

        try {
            Long numberOfItems = StockTakingDataHandler.getDataCount(
                    getSaleTypeList(stocktype_combobox.getSelectionModel().getSelectedItem(), systemModule),
                    (order_number_textfield.getText() == null) ? (double) -1
                    : Double.parseDouble(order_number_textfield.getText()),
                    null, "ALL", null, null, 1, null);
            
            
                    ResultSet rs = StockTakingDataHandler.loadData(
                            getSaleTypeList(stocktype_combobox.getSelectionModel().getSelectedItem(), systemModule),
                            (order_number_textfield.getText() == null) ? (double) -1
                            : Double.parseDouble(order_number_textfield.getText()),
                            null, "ALL", null, null, 1, null, systemModule);

                    while (rs.next()) {
                        
                        i++;
                        double serialNo = rs.getDouble("serialNo");
                        String orderType = rs.getString("orderType");
                        double orderNo = rs.getDouble("orderNo");
                        String journalType = rs.getString("journalType");
                        double journalNumber = rs.getDouble("journalNumber");
                        String trnType = rs.getString("trnType");
                        double orderQty = rs.getDouble("orderQty");
                        String createdBy = rs.getString("createdBy");
                        Date dateCreated = rs.getDate("dateCreated");
                        Date dateModified = rs.getDate("dateModified");
                        String approvedBy = rs.getString("approvedBy");
                        Date dateApproved = rs.getDate("dateApproved");
                        String itemCode = rs.getString("itemCode");
                        String description = rs.getString("description");
                        String suom = rs.getString("suom");
                        String confirmed = rs.getString("confirmed");
                        double confirmedOrderNo = rs.getDouble("confirmedOrderNo");
                        double confirmedSerialNo = rs.getDouble("confirmedSerialNo");
                        double confirmedQty = rs.getDouble("confirmedQty");
                        Date confirmedApprovalDate = rs.getDate("confirmedApprovalDate");
                        String confirmedapprovedBy = rs.getString("confirmedapprovedBy");
                        String deviceId = rs.getString("deviceId");
                        double packSize = rs.getDouble("packSize");
                        String itemTYpe = rs.getString("itemTYpe");
                        Date expiryDate = rs.getDate("expiryDate");
                        String lotSerial = rs.getString("lotSerial");
                        String itemLocation = rs.getString("itemLocation");
                        String barcode = rs.getString("barcode");
                        String alreadyConfirmed;
                        if (confirmed.endsWith("Y")) {
                            alreadyConfirmed = "Y";
                        } else {
                            alreadyConfirmed = "N";
                        }
                        StockLedger stockLedger = new StockLedger(serialNo, orderType, orderNo, journalType,
                                journalNumber, trnType, orderQty, createdBy, dateCreated, dateModified, approvedBy,
                                dateApproved, itemCode, description, suom, confirmed, confirmedOrderNo,
                                confirmedSerialNo, confirmedQty, confirmedApprovalDate, confirmedapprovedBy, deviceId,
                                packSize, itemTYpe, expiryDate, lotSerial, itemLocation, barcode, alreadyConfirmed);
                        data.add(stockLedger);

                        System.out.println("");
                    }
        } catch (NumberFormatException | SQLException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
        }
        stocksList.addAll(data);
          ObservableList<StockLedger> list = FXCollections.observableArrayList(data);

            DecimalFormat format0d = new DecimalFormat("0");

            orderNo_column.setCellValueFactory(col -> {
                String value = format0d.format(col.getValue().getOrderNo());
                return new SimpleStringProperty(value);
            });
            itemCode_column.setCellValueFactory(
                    new PropertyValueFactory<>("itemCode"));
            orderQty_column.setCellValueFactory(col -> {
                String value = format0d.format(col.getValue().getOrderQty());
                return new SimpleStringProperty(value);
            });
            serialNo_column.setCellValueFactory(col -> {
                String value = format0d.format(col.getValue().getSerialNo());
                return new SimpleStringProperty(value);
            });
            suom_column.setCellValueFactory(
                    new PropertyValueFactory<>("suom"));
            description_column.setCellValueFactory(
                    new PropertyValueFactory<>("description"));
            confirmation_column.setCellValueFactory(c -> {
                StockLedger stockLedger = c.getValue();
                CheckBox checkBox = new CheckBox();

//			      if(c.getValue().getConfirmed().equals("Y"))
//			    	  checkBox.setSelected(true);
//			    	  checkBox.setDisable(true);			      	  
                Boolean isConfirmed = (stockLedger.getConfirmed().equalsIgnoreCase("Y"));
                if (isConfirmed) {
                    checkBox.setDisable(true);

                } else {
                    checkBox.setDisable(false);

                }
                if (checkBox.isDisable()) {
                    stockLedger.setAlreadyConfirmed("Y");
                } else {
                    stockLedger.setAlreadyConfirmed("N");
                }

                checkBox.selectedProperty().setValue(isConfirmed);
                checkBox.setOnAction(cba -> {
                    if (checkBox.isSelected()) {
                        c.getValue().setConfirmed("Y");
                    } else {
                        c.getValue().setConfirmed("X");
                    }
                });
                return new SimpleObjectProperty(checkBox);
            });

            table.getItems().clear();
            table.setItems(list);
    }

    private static final HashMap<String, HashMap<String, String>> typeCodeMappings = new HashMap<>();

    static {
        HashMap<String, String> salesMappings = new HashMap<>();
        salesMappings.put("CASH SALES", "'CSO'");
        salesMappings.put("SALE INVOICE", "'CUS'");
        salesMappings.put("SALES RETURN", "'SRT'");
        salesMappings.put("CASH ORDERS", "'ISO'");
        salesMappings.put("CREDIT ORDERS", "'ISS'");
        salesMappings.put("ORDERS RETURN", "'IRT'");
        typeCodeMappings.put("SALES", salesMappings);

        HashMap<String, String> transfersMappings = new HashMap<>();
        transfersMappings.put("TRANSFER", "'ITF','TSF'");
        transfersMappings.put("RECEIVED", "'RCV'");
        typeCodeMappings.put("TRANSFERS", transfersMappings);

        // Mapping for ADJUSTMENTS module
        HashMap<String, String> adjustmentsMappings = new HashMap<>();
        adjustmentsMappings.put("ADJUSTMENT", "'ADJ'");
        adjustmentsMappings.put("STOCK TAKING", "'OPN'");
        adjustmentsMappings.put("OPENING STOCK", "'OPN'");
        adjustmentsMappings.put("BREAKAGES", "'BRK'");
        adjustmentsMappings.put("EXPENSES", "'EXP'");
        typeCodeMappings.put("ADJUSTMENTS", adjustmentsMappings);

        //Mapping for PURCHASES module
        HashMap<String, String> purchasesMappings = new HashMap<>();
        purchasesMappings.put("CASH PURCHASES", "'CPO'");
        purchasesMappings.put("PURCHASE INVOICE", "'SUP'");
        purchasesMappings.put("PURCHASE RETURN", "'PRT'");
        typeCodeMappings.put("PURCHASES", purchasesMappings);
    }

    public static String getSaleTypeList(String trn_type, String module) {
        HashMap<String, String> mappings = typeCodeMappings.get(module);
        if (mappings != null) {
            String code = mappings.get(trn_type);
            if (code != null) {
                return code;
            }
        }
        return "";
    }

    private String getDeviceId() {
        Process SerNumProcess;
        String machineId = "";
        String command = "";

        String operatingSystem = System.getProperty("os.name").toLowerCase();
        if (operatingSystem.contains("win")) {
            command = "wmic csproduct get UUID";
        } else if (operatingSystem.contains("mac")) {
            command = "system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }'";
        } else if (operatingSystem.contains("sunos")) {

        } else if (operatingSystem.contains("nix") || operatingSystem.contains("nux")
                || operatingSystem.indexOf("aix") > 0) {
            command = "cat /sys/class/dmi/id/product_uuid";
        }

        try {

            StringBuffer output = new StringBuffer();
            SerNumProcess = Runtime.getRuntime().exec(command);
            BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));

            String line = "";
            while ((line = sNumReader.readLine()) != null) {
                output.append(line + "\n");
            }
            machineId = output.toString().substring(output.indexOf("\n"), output.length()).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return machineId;
    }

}
