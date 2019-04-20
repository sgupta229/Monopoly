//package View;
//
//import Model.spaces.AbstractSpace;
//import Model.spaces.ActionCardSpace;
//import Model.spaces.PropSpace;
//import Model.spaces.TaxSpace;
//import View.SpaceDisplay.*;
//import javafx.scene.layout.Pane;
//
//import java.awt.geom.Point2D;
//import java.util.Map;
//
//public class SpaceDisplayFactory {
//    private Map<String,String> nameToColor;
//    private Map<String,Integer> nameToPrice;
//
//
//    public SpaceDisplayFactory(){
//        BoardConfigReader configs = new BoardConfigReader();
//        nameToColor = configs.getNameToColor();
//        nameToPrice = configs.getNameToPrice();
//
//    }
//
//    public SpaceDisplay getSpaceDisplay(Map.Entry<Point2D.Double, AbstractSpace> entry, Pane myBoardPane){
//        String baseColor = "#c7edc9";
//
//        String name = entry.getValue().getMyName().replace("_", " ");
//        if (entry.getValue() instanceof PropSpace) {
//            String price = nameToPrice.get(name).toString();
//            String color = nameToColor.get(name);
//            if (entry.getKey().getY() == 10) {
//                BottomPropertyDisplay propSpaces = new BottomPropertyDisplay(name, price, color, myBoardPane, baseColor);
//            }
//            if (entry.getKey().getX() == 0) {
//                LeftPropertyDisplay propSpaces = new LeftPropertyDisplay(name, price, color, myBoardPane, baseColor);
//            }
//            if (entry.getKey().getY() == 0) {
//                TopPropertyDisplay propSpaces = new TopPropertyDisplay(name, price, color, myBoardPane, baseColor);
//            }
//            if (entry.getKey().getX() == 10) {
//                RightPropertyDisplay propSpaces = new RightPropertyDisplay(name, price, color, myBoardPane, baseColor);
//            }
//        }
//        if (entry.getValue() instanceof TaxSpace) {
//            if (entry.getKey().getY() == 10) {
//                BottomPropertyDisplay propSpaces = new BottomPropertyDisplay(myBoardPane, baseColor, "tax.png");
//            }
//            if (entry.getKey().getX() == 10) {
//                RightPropertyDisplay propSpaces = new RightPropertyDisplay(myBoardPane, baseColor, "tax.png");
//            }
//        }
//        if (entry.getValue() instanceof ActionCardSpace) {
//            String image;
//            PropertyDisplay propSpaces = null;
//            if (name.equals("COMMUNITY CHEST")) {
//                image = "chest.png";
//            } else {
//                image = "chance.png";
//            }
//            if (entry.getKey().getY() == 10) {
//                propSpaces = new BottomPropertyDisplay(myBoardPane, baseColor, image);
//            }
//            if (entry.getKey().getX() == 10) {
//                propSpaces = new RightPropertyDisplay(myBoardPane, baseColor, image);
//            }
//            if (entry.getKey().getX() == 0) {
//                propSpaces = new LeftPropertyDisplay(myBoardPane, baseColor, image);
//            }
//            if (entry.getKey().getY() == 0) {
//                propSpaces = new TopPropertyDisplay(myBoardPane, baseColor, image);
//            }
//            return propSpaces;
//        } else {
//            if (entry.getKey().getX() == 10 && entry.getKey().getY() == 10) {
//                CornerDisplay goSpace = new CornerDisplay(baseColor, myBoardPane, "go.png");
//                return goSpace;
//            } else if (entry.getKey().getX() == 0 && entry.getKey().getY() == 0) {
//                CornerDisplay parkingSpace = new CornerDisplay(baseColor, myBoardPane, "freeParking.png");
//                return parkingSpace;
//            } else if (entry.getKey().getX() == 0 && entry.getKey().getY() == 10) {
//                CornerDisplay jailSpace = new CornerDisplay(baseColor, myBoardPane, "jail.png");
//                return  jailSpace;
//            } else if (entry.getKey().getX() == 10 && entry.getKey().getY() == 0) {
//                CornerDisplay goToJail = new CornerDisplay(baseColor, myBoardPane, "goToJail.png");
//                return goToJail;
//            }
//        }
//        return null;
//    }
//
//}
