package apis.stock;

import java.util.List;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class AnalysisService
{

    static BgColors colors;
    private static int count;

    public static Label createLabel(String text, int bgColorIndex, Pos pos)
    {
        colors = BgColors.getColors();
        Label label = new Label(text);
        if (bgColorIndex == 1) {
            label.setBackground(new Background(new BackgroundFill(BgColors.hex2Rgb(colors.getFirstColor()), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (bgColorIndex == 2) {
            label.setBackground(new Background(new BackgroundFill(BgColors.hex2Rgb(colors.getSecondColor()), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (bgColorIndex == 3) {
            label.setBackground(new Background(new BackgroundFill(BgColors.hex2Rgb(colors.getThirdColor()), CornerRadii.EMPTY, Insets.EMPTY)));
        } else if (bgColorIndex == 4) {
            label.setBackground(new Background(new BackgroundFill(BgColors.hex2Rgb(colors.getDeadColor()), CornerRadii.EMPTY, Insets.EMPTY)));
        }

        label.setAlignment(pos);
        label.setMinWidth(75);
        label.setMinHeight(20);
        return label;
    }

    public static GridPane createGridPane(List<itemDetails> items, String[] analysisLabels)
    {
        GridPane gridPane = new GridPane();
        count = 1;

        gridPane.getChildren().clear();
        gridPane.setGridLinesVisible(true);

        gridPane.addRow(
                count, AnalysisService.createLabel("Code", count, Pos.CENTER),
                AnalysisService.createLabel("% Revenue", count, Pos.CENTER),
                AnalysisService.createLabel("No. of Items", count, Pos.CENTER),
                AnalysisService.createLabel("% of Items", count, Pos.CENTER));
        count++;
        count = 1;
        items.forEach(item -> {
            gridPane.addRow(
                    count, AnalysisService.createLabel(analysisLabels[count - 1], count, Pos.CENTER),
                    AnalysisService.createLabel(String.valueOf(Math.round(item.getItemRevenuePercCumul())), count, Pos.CENTER),
                    AnalysisService.createLabel(String.valueOf(Math.round(item.getItemPosition())), count, Pos.CENTER),
                    AnalysisService.createLabel(String.valueOf(Math.round(item.getItemPositionPercCumul())).concat("%"), count, Pos.CENTER));
            count++;
        });

        return gridPane;
    }

    public static void displayTarnsitionLabel(Label label)
    {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(5), label);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
}
