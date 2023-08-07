package apis.stock;

import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class StockReportsController implements Initializable {

    @FXML
    private Button btn_export;

    @FXML
    private TableView<StockLedger> table;
    @FXML
    private TableColumn<StockLedger, String> transaction_date_column;
    @FXML
    private TableColumn<StockLedger, String> item_name_column;
    @FXML
    private TableColumn<StockLedger, String> order_type_column;//
    @FXML
    private TableColumn<StockLedger, String> order_number_column;//
    @FXML
    private TableColumn<StockLedger, String> approved_by_column;
    @FXML
    private TableColumn<StockLedger, String> audited_by_column;//
    @FXML
    private TableColumn<StockLedger, String> audited_column;
    @FXML
    private TableColumn<StockLedger, String> date_audited_column;//    
    @FXML
    private TableColumn<StockLedger, String> qty_transacted_column;//    
    @FXML
    private TableColumn<StockLedger, String> suom_column;
    @FXML
    private TableColumn<StockLedger, String> location_column;

    @FXML
    private DatePicker from_date_picker;
    @FXML
    private DatePicker to_date_picker;

    @FXML
    private ComboBox<String> location_combobox;

    @FXML
    private RadioButton audited_radio_button;
    @FXML
    private RadioButton not_audited_radio_button;
    @FXML
    private RadioButton all_audits_radio_button;

    @FXML
    private Button fetch_data_btn;

    @FXML
    private TextField searchTextField;

    @FXML
    private Label current_page_label;
    @FXML
    private Hyperlink go_to_next_page_btn;
    @FXML
    private Hyperlink go_to_previous_page_btn;
    @FXML
    private Hyperlink go_to_start_page_btn;

    @FXML
    private StackPane main_page_pane;

    @FXML
    private HBox progressHbox;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private Label progressInfoLabel;

    private List<StockLedger> data = new ArrayList<>();
    List<String> locations = new ArrayList<>();

    int currentpage = 1;
    int i = 1;
    Timestamp dataFetchStartTimestamp = null;
    Timestamp dataFetchEndTimestamp = null;
    String audited = "X";
    String location = null;
    String searchText = null;

    StringProperty progressInfoLabelText = new SimpleStringProperty();

    private static final Logger LOG = Logger.getLogger(StockReportsController.class.getName());

    public static LocalDate earliestTRNDate = LocalDate.MIN;

    @FXML
    void refreshContent(ActionEvent event) {
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                earliestTRNDate = StockTakingDataHandler.getEarliestCreationDate();
//                return null;
//            }
//        };
//
//        task.setOnSucceeded(e -> {
//            from_date_picker.setValue(earliestTRNDate);
//        });
//        new Thread(task).start();

        YearMonth currentYearMonth = YearMonth.now();
        LocalDate startOfMonth = currentYearMonth.atDay(1);
        from_date_picker.setValue(startOfMonth);
        to_date_picker.setValue(LocalDate.now());
        searchTextField.setText("");

        dataFetchStartTimestamp = null;
        dataFetchEndTimestamp = null;

        not_audited_radio_button.setSelected(true);

        location_combobox.getSelectionModel().select(0);

        loadTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        to_date_picker.setValue(LocalDate.now());
        YearMonth currentYearMonth = YearMonth.now();
        LocalDate startOfMonth = currentYearMonth.atDay(1);
        startOfMonth.format(formatter);
        System.out.println(startOfMonth);
        from_date_picker.setValue(startOfMonth);

        LocalDate intialDate = from_date_picker.getValue();

        dataFetchStartTimestamp = Timestamp.valueOf(intialDate.atStartOfDay());

//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                earliestTRNDate = StockTakingDataHandler.getEarliestCreationDate();
//                return null;
//            }
//        };
//
//        task.setOnSucceeded(e -> {
//            from_date_picker.setValue(earliestTRNDate);
//        });
//        new Thread(task).start();
        initializeLocationsCombobox();

        ToggleGroup toggleGroup = new ToggleGroup();
        audited_radio_button.setToggleGroup(toggleGroup);
        not_audited_radio_button.setToggleGroup(toggleGroup);
        all_audits_radio_button.setToggleGroup(toggleGroup);
        not_audited_radio_button.setSelected(true);

        loadTable();

        go_to_next_page_btn.setOnAction(e -> {
            currentpage += 1;
            loadTable();
        });
        go_to_previous_page_btn.setOnAction(e -> {
            currentpage = (currentpage == 1) ? 1 : currentpage--;
            loadTable();
        });
        go_to_start_page_btn.setOnAction(e -> {
            currentpage = 1;
            loadTable();
        });

        audited_radio_button.setOnAction(e -> {
            audited = "Y";
            loadTable();
        });
        not_audited_radio_button.setOnAction(e -> {
            audited = "X";
            loadTable();
        });
        all_audits_radio_button.setOnAction(e -> {
            audited = "All";
            loadTable();
        });

        from_date_picker.setOnAction(e -> {
            LocalDate start = from_date_picker.getValue();

            if (start != null) {
                this.dataFetchStartTimestamp = Timestamp.valueOf(start.atStartOfDay());
                System.out.println(dataFetchStartTimestamp);
            } else {
                dataFetchStartTimestamp = null;
            }
            loadTable();
        });

        to_date_picker.setOnAction(e -> {
            LocalDate end = to_date_picker.getValue();
            if (end != null) {
                this.dataFetchEndTimestamp = Timestamp.valueOf(end.atStartOfDay());
            } else {
                dataFetchEndTimestamp = null;
            }
            loadTable();
        });

        location_combobox.setOnAction(e -> {
            location = location_combobox.getSelectionModel().getSelectedItem();
            loadTable();
        });
        btn_export.setOnAction(e -> {
            FileChooser();
        });

//		searchTextField.setOnAction(e -> {
//			searchText = searchTextField.getText();
//			loadTable();
//		});
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = searchTextField.getText();
            if (searchText.length() == 0) {
                searchText = null;
            }
            loadTable();
        });
    }

    private void initializeLocationsCombobox() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                locations = StockTakingDataHandler.getLocations();
                return null;
            }
        };
        task.setOnSucceeded(e -> {

            locations.add(0, "ALL LOCATIONS");
            location_combobox.setItems(FXCollections.observableArrayList(locations));
        });
        new Thread(task).start();
    }

    private void loadTable() {

        current_page_label.setText(String.valueOf(currentpage));

        List<StockLedger> stocksList = new ArrayList<>();

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                data.clear();
                i = 1;
                try {
                    updateMessage("Initializing");
                    Long numberOfItems = StockTakingDataHandler.getDataCount(null, -1, location, audited,
                            dataFetchStartTimestamp, dataFetchEndTimestamp, currentpage, searchText);
                    updateMessage("Fetching data");
                    updateProgress(0, numberOfItems);

                    ResultSet rs = StockTakingDataHandler.loadData(null, -1, location, audited, dataFetchStartTimestamp,
                            dataFetchEndTimestamp, currentpage, searchText, "");

                    while (rs.next()) {
                        updateMessage("Retrieving data");
                        updateProgress(1, Double.parseDouble(String.valueOf(numberOfItems)));
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

                        StockLedger stockLedger = new StockLedger(serialNo, orderType, orderNo, journalType,
                                journalNumber, trnType, orderQty, createdBy, dateCreated, dateModified, approvedBy,
                                dateApproved, itemCode, description, suom, confirmed, confirmedOrderNo,
                                confirmedSerialNo, confirmedQty, confirmedApprovalDate, confirmedapprovedBy, deviceId,
                                packSize, itemTYpe, expiryDate, lotSerial, itemLocation, barcode);
                        data.add(stockLedger);
                    }

                } catch (SQLException ex) {
                    LOG.log(Level.WARNING, ex.getLocalizedMessage());
                    data = new ArrayList<>();
                }

                stocksList.clear();
                stocksList.addAll(data);

                return null;
            }
        };

        task.setOnRunning(e -> {
            progressbar.progressProperty().bind(task.progressProperty());
            progressHbox.setVisible(true);
            progressHbox.setPrefHeight(10);
            progressInfoLabel.textProperty().bind(task.messageProperty().concat("..."));
        });

        task.setOnSucceeded(e -> {
            progressInfoLabelText.setValue("Displaying data...");
            progressHbox.setVisible(false);
            progressHbox.setPrefHeight(0);

            ObservableList<StockLedger> tableData = FXCollections.observableArrayList(stocksList);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            DecimalFormat stringFormatter0d = new DecimalFormat("0");

            transaction_date_column.setCellValueFactory(col -> {
                String date = dateFormatter.format(col.getValue().getDateApproved());
                return new SimpleStringProperty(date);
            });
            item_name_column.setCellValueFactory(
                    new PropertyValueFactory<>("description")
            );
            order_type_column.setCellValueFactory(
                    new PropertyValueFactory<>("orderType")
            );
            order_number_column.setCellValueFactory(
                    new PropertyValueFactory<>("orderNo")
            );
            approved_by_column.setCellValueFactory(
                    new PropertyValueFactory<>("createdBy")
            );
            location_column.setCellValueFactory(
                    new PropertyValueFactory<>("itemLocation")
            );
            qty_transacted_column.setCellValueFactory(col -> {
                String orderQty = stringFormatter0d.format(col.getValue().getOrderQty());
                return new SimpleStringProperty(orderQty);
            });
            suom_column.setCellValueFactory(
                    new PropertyValueFactory<>("suom")
            );
            audited_column.setCellValueFactory(col -> {
                String rawVal = col.getValue().getConfirmed();
                String cellValue = (rawVal.equalsIgnoreCase("Y")) ? "Yes" : "No";
                return new SimpleStringProperty(cellValue);
            });
            date_audited_column.setCellValueFactory(col -> {
                String date = dateFormatter.format(col.getValue().getConfirmedApprovalDate());
                return new SimpleStringProperty(date);
            });
            audited_by_column.setCellValueFactory(
                    new PropertyValueFactory<>("approvedBy")
            );

//			table.getItems().clear();
            table.setItems(tableData);
        });
        task.setOnFailed(e -> {

        });
        new Thread(task).start();
    }

    void FileChooser() {
        Window stage = btn_export.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Records");
        fileChooser.setInitialFileName("Stock analysis details");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel", "xls"));
        try {
            File file = fileChooser.showSaveDialog(stage);
            fileChooser.setInitialDirectory(file.getParentFile());
            exportToexcel(file.toString());
            System.out.println(file);
        } catch (Exception e) {
        }

    }

    public void exportToexcel(String dir) {
        FileOutputStream fileOut = null;
        try {
            Workbook workbook = new HSSFWorkbook();
            Sheet spreadsheet = workbook.createSheet("Data");
            Row row = spreadsheet.createRow(0);
            for (int j = 0; j < table.getColumns().size(); j++) {
                row.createCell(j).setCellValue(table.getColumns().get(j).getText());
            }
            for (int i = 0; i < table.getItems().size(); i++) {
                row = spreadsheet.createRow(i + 1);
                for (int j = 0; j < table.getColumns().size(); j++) {
                    if (table.getColumns().get(j).getCellData(i) != null) {
                        row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                    } else {
                        row.createCell(j).setCellValue("");
                        //  spreadsheet.setC
                    }
                }
            }
            fileOut = new FileOutputStream(dir + ".xls");
            workbook.write(fileOut);
            fileOut.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(StockReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(StockReportsController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileOut.close();
            } catch (IOException ex) {
                Logger.getLogger(StockReportsController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
