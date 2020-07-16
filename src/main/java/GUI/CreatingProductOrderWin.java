package GUI;

import ProductOP.ProductNode;
import ProductOP.ProductOrder;
import ProductOP.ProductTree;
import javafx.scene.input.KeyCode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;

public class CreatingProductOrderWin {

    private ProductTree productTree;
    private ArrayList<HBox> rows = new ArrayList<>();
    private ArrayList<Integer> demand = new ArrayList<>();

    public CreatingProductOrderWin(ProductTree productTree){

        this.productTree = productTree;
        Stage stage = new Stage();
        stage.setScene(paint());
        stage.show();
    }

    public Scene paint(){

        BorderPane bPane = new BorderPane();
        HBox hBoxTextFields = nameTexts();
        HBox productPeriod = new HBox();
        HBox hBoxLast = new HBox();
        hBoxLast.setAlignment(Pos.CENTER);
        productPeriod.getChildren().addAll(period());
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(productPeriod,hBoxTextFields);
        VBox.setMargin(productPeriod,new Insets(0,0,20,0));

        int i=0;
        while (true){

            ProductNode node = productTree.get(i);

            if(node==null){
                break;
            }
            else {
                HBox hBox = textFields(i);
                vBox.getChildren().add(hBox);
                rows.add(hBox);
            }

            i++;
        }

        Button showOrderButton = new Button("Show MRP Lists");
        goButtonAction(showOrderButton);
        hBoxLast.getChildren().addAll(showOrderButton);

        vBox.getChildren().addAll(hBoxLast);
        VBox.setMargin(hBoxLast,new Insets(20,0,0,0));

        bPane.setCenter(vBox);
        return new Scene(bPane, 900, 600);
    }

    private HBox textFields(int product){

        HBox hBox =new HBox(5);
        hBox.setAlignment(Pos.CENTER);

        for(int i=0; i<6; i++){

            if(i==0){
                TextField textField = new TextField();
                textField.setText(productTree.get(product).getId()+"");
                textField.setEditable(false);
                hBox.getChildren().add(textField);
            }
            else {

                TextField textField = eventHandler();
                hBox.getChildren().add(textField);
            }
        }
        return hBox;
    }

    private HBox nameTexts(){

        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.CENTER);
        TextField textField = new TextField("Item ID");
        textField.setEditable(false);
        TextField textField1 = new TextField("Amount \nOn Hand");
        textField1.setEditable(false);
        TextField textField2 = new TextField("Scheduled \n Receipt");
        textField2.setEditable(false);
        TextField textField3 = new TextField("Arrival \n On Week");
        textField3.setEditable(false);
        TextField textField4 = new TextField("Lead Time");
        textField4.setEditable(false);
        TextField textField5 = new TextField("Lot Sizing \nRule");
        textField5.setEditable(false);
        hBox.getChildren().addAll(textField,textField1,textField2,textField3,textField4,textField5);

        return hBox;
    }

    private VBox period(){

        HBox hBoxTop = new HBox(5);
        hBoxTop.setAlignment(Pos.CENTER);
        HBox hBoxBottom = new HBox(5);
        hBoxBottom.setAlignment(Pos.CENTER);

        for(int i=0; i<11; i++){

            if(i==0){
                TextField textField = new TextField("Period");
                textField.setEditable(false);
                hBoxTop.getChildren().addAll(textField);
            }
            else {
                TextField textField = new TextField(i+"");
                textField.setEditable(false);

                hBoxTop.getChildren().addAll(textField);
            }
        }

        for(int i=0; i<11; i++){

            if(i==0){
                TextField textField = new TextField("ID: "+productTree.root.getId());
                textField.setEditable(false);
                hBoxBottom.getChildren().addAll(textField);
            }
            else {
                TextField textField = eventHandler();
                hBoxBottom.getChildren().addAll(textField);
            }
        }

        rows.add(hBoxBottom);

        VBox vBox = new VBox(5);
        vBox.getChildren().addAll(hBoxTop,hBoxBottom);
        return vBox;
    }

    private TextField eventHandler() {
        TextField textField = new TextField();
        textField.setOnKeyPressed(event -> {
            if (!event.getCode().isDigitKey()&&!event.getCode().equals(KeyCode.BACK_SPACE)&&!event.getCode().equals(KeyCode.DELETE)&&!event.getCode().isArrowKey()&&!event.getCode().equals(KeyCode.NUM_LOCK)&&!event.getCode().equals(KeyCode.CAPS)&&!event.getCode().equals(KeyCode.TAB)){
                JOptionPane.showMessageDialog(null, "You should put number");
                textField.setEditable(false);
            }
            else if (event.getCode().isDigitKey()){
                textField.setEditable(true);
                textField.appendText(event.getCharacter());
            }
        });
        return textField;
    }

    private void goButtonAction(Button button){

        button.setOnAction(e->{

            demand.clear();

            for(int m=0; m<10; m++){
                demand.add(0);
            }

            ArrayList<Integer> list = new ArrayList<>();

            for(int i=0; i<rows.size(); i++){
                for (int j=0; j<rows.get(i).getChildren().size(); j++) {

                    String str = ((TextField)rows.get(i).getChildren().get(j)).getText();
                    if(str.equals("")){
                        str="0";
                    }
                    if(i==0 && j>=1){
                        demand.set(j-1,Integer.parseInt(str));
                    }
                    else if(j >= 1){
                        list.add(Integer.parseInt(str));
                    }
                }

                if(i>0) {

                    ProductNode node = productTree.getList().get(i-1);

                    for(int j=0; j<productTree.getDuplicateNodes(node).size(); j++){
                        productTree.getDuplicateNodes(node).get(j).setMemeber(list);
                    }
                    list.clear();
                }
            }

 /*
            // Demo Part
            demand.add(0);
            demand.add(0);
            demand.add(0);
            demand.add(60);
            demand.add(100);
            demand.add(0);
            demand.add(50);
            demand.add(0);
            demand.add(30);
            demand.add(0);

            productTree.root.setAmountOnHand(30);
            productTree.root.setScheduledReceipt(0);
            productTree.root.setArrivalOnWeek(0);
            productTree.root.setLeadTime(1);
            productTree.root.setLotRule(1);

            productTree.root.childs.get(0).setAmountOnHand(0);
            productTree.root.childs.get(0).setScheduledReceipt(70);
            productTree.root.childs.get(0).setArrivalOnWeek(3);
            productTree.root.childs.get(0).setLeadTime(1);
            productTree.root.childs.get(0).setLotRule(40);

            productTree.root.childs.get(1).setAmountOnHand(30);
            productTree.root.childs.get(1).setScheduledReceipt(0);
            productTree.root.childs.get(1).setArrivalOnWeek(0);
            productTree.root.childs.get(1).setLeadTime(3);
            productTree.root.childs.get(1).setLotRule(30);

            productTree.root.childs.get(2).setAmountOnHand(0);
            productTree.root.childs.get(2).setScheduledReceipt(50);
            productTree.root.childs.get(2).setArrivalOnWeek(2);
            productTree.root.childs.get(2).setLeadTime(2);
            productTree.root.childs.get(2).setLotRule(1);

            productTree.root.childs.get(3).setAmountOnHand(50);
            productTree.root.childs.get(3).setScheduledReceipt(100);
            productTree.root.childs.get(3).setArrivalOnWeek(6);
            productTree.root.childs.get(3).setLeadTime(2);
            productTree.root.childs.get(3).setLotRule(1);

            productTree.root.childs.get(4).setAmountOnHand(60);
            productTree.root.childs.get(4).setScheduledReceipt(0);
            productTree.root.childs.get(4).setArrivalOnWeek(0);
            productTree.root.childs.get(4).setLeadTime(1);
            productTree.root.childs.get(4).setLotRule(100);

            productTree.root.childs.get(5).setAmountOnHand(0);
            productTree.root.childs.get(5).setScheduledReceipt(50);
            productTree.root.childs.get(5).setArrivalOnWeek(5);
            productTree.root.childs.get(5).setLeadTime(1);
            productTree.root.childs.get(5).setLotRule(50);

            productTree.root.childs.get(0).childs.get(1).setAmountOnHand(0);
            productTree.root.childs.get(0).childs.get(1).setScheduledReceipt(0);
            productTree.root.childs.get(0).childs.get(1).setArrivalOnWeek(0);
            productTree.root.childs.get(0).childs.get(1).setLeadTime(2);
            productTree.root.childs.get(0).childs.get(1).setLotRule(1);

            productTree.root.childs.get(0).childs.get(0).setAmountOnHand(0);
            productTree.root.childs.get(0).childs.get(0).setScheduledReceipt(20);
            productTree.root.childs.get(0).childs.get(0).setArrivalOnWeek(2);
            productTree.root.childs.get(0).childs.get(0).setLeadTime(2);
            productTree.root.childs.get(0).childs.get(0).setLotRule(1);

            productTree.root.childs.get(0).childs.get(2).setAmountOnHand(120);
            productTree.root.childs.get(0).childs.get(2).setScheduledReceipt(0);
            productTree.root.childs.get(0).childs.get(2).setArrivalOnWeek(0);
            productTree.root.childs.get(0).childs.get(2).setLeadTime(1);
            productTree.root.childs.get(0).childs.get(2).setLotRule(50);


            productTree.root.childs.get(5).childs.get(0).setAmountOnHand(80);
            productTree.root.childs.get(5).childs.get(0).setScheduledReceipt(0);
            productTree.root.childs.get(5).childs.get(0).setArrivalOnWeek(0);
            productTree.root.childs.get(5).childs.get(0).setLeadTime(2);
            productTree.root.childs.get(5).childs.get(0).setLotRule(100);

            productTree.root.childs.get(5).childs.get(1).setAmountOnHand(50);
            productTree.root.childs.get(5).childs.get(1).setScheduledReceipt(40);
            productTree.root.childs.get(5).childs.get(1).setArrivalOnWeek(5);
            productTree.root.childs.get(5).childs.get(1).setLeadTime(2);
            productTree.root.childs.get(5).childs.get(1).setLotRule(50);

            productTree.root.childs.get(5).childs.get(2).setAmountOnHand(0);
            productTree.root.childs.get(5).childs.get(2).setScheduledReceipt(0);
            productTree.root.childs.get(5).childs.get(2).setArrivalOnWeek(0);
            productTree.root.childs.get(5).childs.get(2).setLeadTime(1);
            productTree.root.childs.get(5).childs.get(2).setLotRule(100);

            productTree.root.childs.get(0).childs.get(2).childs.get(0).setAmountOnHand(0);
            productTree.root.childs.get(0).childs.get(2).childs.get(0).setScheduledReceipt(100);
            productTree.root.childs.get(0).childs.get(2).childs.get(0).setArrivalOnWeek(8);
            productTree.root.childs.get(0).childs.get(2).childs.get(0).setLeadTime(4);
            productTree.root.childs.get(0).childs.get(2).childs.get(0).setLotRule(40);

            productTree.root.childs.get(0).childs.get(2).childs.get(1).setAmountOnHand(30);
            productTree.root.childs.get(0).childs.get(2).childs.get(1).setScheduledReceipt(0);
            productTree.root.childs.get(0).childs.get(2).childs.get(1).setArrivalOnWeek(0);
            productTree.root.childs.get(0).childs.get(2).childs.get(1).setLeadTime(3);
            productTree.root.childs.get(0).childs.get(2).childs.get(1).setLotRule(1);
*/
            ProductOrder productOrder = new ProductOrder(demand,productTree);
            ResultOrderWin resultOrderWin = new ResultOrderWin(productTree,productOrder);

        });
    }
}
