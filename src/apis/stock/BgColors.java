package apis.stock;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javafx.scene.paint.Color;

public class BgColors implements Serializable
{

    private String firstColor;
    private String secondColor;
    private String thirdColor;
    private String deadColor = "#e5e4e2";

    public BgColors()
    {
        this.firstColor = "#80ff80";
        this.secondColor = "#ffff80";
        this.thirdColor = "#ffaa80";
    }

    public BgColors(Color color1, Color color2, Color color3)
    {
        super();
        //System.out.println("color1: " + color1 + " color2: " + color2 + " color3: " + color3);
        this.firstColor = (color1 != null) ? convertToHex(color1) : "";
        this.secondColor = (color2 != null) ? convertToHex(color2) : "";
        this.thirdColor = (color3 != null) ? convertToHex(color3) : "";

        BgColors defaults = new BgColors();

        if (firstColor.equalsIgnoreCase("#FFFFFF")) {
            firstColor = defaults.getFirstColor();
        }

        if (secondColor.equalsIgnoreCase("#FFFFFF")) {
            secondColor = defaults.getSecondColor();
        }

        if (thirdColor.equalsIgnoreCase("#FFFFFF")) {
            thirdColor = defaults.getThirdColor();
        }
    }

    public BgColors(String firstColor, String secondColor, String thirdColor)
    {
        super();
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.thirdColor = thirdColor;
    }

    public static BgColors getColors()
    {
        BgColors colors;
        try {
            FileInputStream fis = new FileInputStream("bg_colors.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            colors = (BgColors) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
//			e.printStackTrace();
            colors = new BgColors();
        }
        return colors;
    }

    private String convertToHex(Color color)
    {
        Double red = color.getRed() * 255;
        int redInt = red.intValue();

        double green = color.getGreen() * 255;
        int greenInt = (int) green;

        double blue = color.getBlue() * 255;
        int blackInt = (int) blue;

        String hex = String.format("#%02X%02X%02X", redInt, greenInt, blackInt);
//		String hex = "#" + Integer.toHexString(redInt) + Integer.toHexString(greenInt) + Integer.toHexString(blackInt);
        //	System.out.println("New Hex: " + hex);
        return hex;
    }

    public static Color hex2Rgb(String colorStr)
    {
        int value1 = Integer.valueOf(colorStr.substring(1, 3), 16);
        int value2 = Integer.valueOf(colorStr.substring(3, 5), 16);
        int value3 = Integer.valueOf(colorStr.substring(5, 7), 16);
        return Color.rgb(value1, value2, value3);
    }

    public String getFirstColor()
    {
        return firstColor;
    }

    public String getSecondColor()
    {
        return secondColor;
    }

    public String getThirdColor()
    {
        return thirdColor;
    }

    public String getDeadColor()
    {
        return deadColor;
    }

    @Override
    public String toString()
    {
        return "BgColors [firstColor=" + firstColor + ", secondColor=" + secondColor + ", thirdColor=" + thirdColor
                + "]";
    }

}
