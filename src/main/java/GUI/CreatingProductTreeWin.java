package GUI;

import ProductOP.ProductNode;
import ProductOP.ProductTree;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class CreatingProductTreeWin {

    private int texts = 0;
    int count=0;
    double labelY=0;
    double labelX=10;
    double setStartX=0;
    double setStartY=0;
    private Label info = new Label("Create Product Tree: ");
    private List<Label> labels = new LinkedList<>();
    int labelListSize=0;
    static boolean flag=false;
    private TextField idText = new TextField();
    private TextField nameText = new TextField();
    private Button addButton = new Button("Add Product");
    private Button prepareOrderButton = new Button("Prepare Order");
    private int parentId = 0;
    public ProductTree productTree = new ProductTree();
    private TextField reqText = new TextField();
    private Button shovelButton = new Button("Snow Shovel");


    public CreatingProductTreeWin(){
    }

    public Scene paint(){

        BorderPane bPane = new BorderPane();
        idText=eventHandler();
        reqText=eventHandler();
        idText.setMaxSize(200,10);
        nameText.setMaxSize(200,10);
        reqText.setMaxSize(200,10);
        nameText.setPromptText("Product Name");
        idText.setPromptText("Product ID");
        reqText.setPromptText("Product Requirement");
        idText.setFocusTraversable(false);
        nameText.setFocusTraversable(false);
        reqText.setFocusTraversable(false);

        HBox hBox= new HBox(10);
        VBox vBox = new VBox(10);
        vBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().addAll(addButton,prepareOrderButton,shovelButton);
        hBox.setAlignment(Pos.CENTER);
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().addAll(info,idText,nameText,reqText,hBox);

        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: white");

        bPane.setCenter(pane);
        bPane.setBottom(vBox);

        addProductToTree(pane,bPane);
        giveProductOrder();

        return new Scene(bPane,950,950);
    }

    public void addProductToTree (Pane pane, BorderPane bPane){

        addButton.setOnAction(e->{
            if (nameText.getText().equals("")||idText.getText().equals("")||reqText.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Fill these blanks");
            }

            else if(texts==0) {
                bPane.setCenter(showProductInPane(pane, Integer.parseInt(idText.getText()), nameText.getText(),Integer.parseInt(reqText.getText())));
                productTree.add(parentId,new ProductNode(Integer.parseInt(idText.getText()),nameText.getText(),Integer.parseInt(reqText.getText())));
                texts++;
            }
            else if(parentId!=0) {
                bPane.setCenter(showProductInPane(pane, Integer.parseInt(idText.getText()), nameText.getText(),Integer.parseInt(reqText.getText())));
                productTree.add(parentId,new ProductNode(Integer.parseInt(idText.getText()),nameText.getText(),Integer.parseInt(reqText.getText())));
            }
            else {
                JOptionPane.showMessageDialog(null, "You should choose the root with right click on it.");
            }
            idText.clear();
            nameText.clear();
            reqText.clear();
        });
    }

    public Pane showProductInPane(Pane pane,int id, String name,int requirement){

        Label label = new Label(id+"/"+name+"/"+requirement);
        label.setLayoutX(425);
        label.setLayoutY(50);

        count++;
        if (count==1){
            flag=false;

        }
       else if (label.getLayoutX()==425){
            label.setLayoutX(labelX);
            label.setLayoutY(labelY);
            intersects(labels,label);
            labels.add(label);
            labelListSize++;
               label.setLayoutX(labelX);
               label.setLayoutY(labelY);
            Line line = new Line();
            line.setStartX(setStartX);
            line.setStartY(setStartY);
            line.setEndX(label.getLayoutX()+35);
            line.setEndY(label.getLayoutY());
            line.setStroke(Color.PURPLE);
            pane.getChildren().add(line);
        }
        label.setStyle("-fx-border-color: red;-fx-font-family:Arial;-fx-font-size:14;");

        labelAction(label,pane);
        pane.getChildren().addAll(label);
        return pane;
    }

    public void labelAction(Label label , Pane pane){
        label.setOnMouseClicked(e->{

            if(e.getButton()== MouseButton.SECONDARY) {
                label.setTextFill(Color.BLUE);
                String[] list = label.getText().split("/");
                parentId = Integer.parseInt(list[0]);
                labelY=120+label.getLayoutY();
                labelX=10;
               setStartX=label.getLayoutX()+35;
               setStartY=label.getLayoutY()+25;
                label.setStyle("-fx-border-color: red;-fx-font-family:Arial;-fx-font-size:14;-fx-font-weight: bold;");


            }

            else if(e.getButton() == MouseButton.MIDDLE){
                pane.getChildren().removeAll(label);
                String[] list = label.getText().split("/");
                //productTree.remove(new ProductNode(Integer.parseInt(list[0]),list[1]));
            }
        });
    }

    public void giveProductOrder(){

        prepareOrderButton.setOnAction(e-> new CreatingProductOrderWin(productTree));
        shovelButton.setOnAction(event -> new CreatingProductOrderWin(createShovelTree()));

    }

    private ProductTree createShovelTree(){

        ProductTree productTree = new ProductTree();

        productTree.root = new ProductNode(1605,"Shovel",1);
        productTree.root.childs.add(new ProductNode(13122,"Top Handle",1));
        productTree.root.childs.add(new ProductNode(48,"Scoop Shaft",1));
        productTree.root.childs.add(new ProductNode(118,"Shaft",1));
        productTree.root.childs.add(new ProductNode(62,"Nail",4));
        productTree.root.childs.add(new ProductNode(14127,"Rivet",4));
        productTree.root.childs.add(new ProductNode(314,"Scoop Assembly",1));

        productTree.root.childs.get(0).childs.add(new ProductNode(457,"Top Handle",1));
        productTree.root.childs.get(0).childs.add(new ProductNode(62,"Nail",2));
        productTree.root.childs.get(0).childs.add(new ProductNode(11495,"Bracelet",1));

        productTree.root.childs.get(0).setParent(productTree.root);
        productTree.root.childs.get(1).setParent(productTree.root);
        productTree.root.childs.get(2).setParent(productTree.root);
        productTree.root.childs.get(3).setParent(productTree.root);
        productTree.root.childs.get(4).setParent(productTree.root);
        productTree.root.childs.get(5).setParent(productTree.root);

        productTree.root.childs.get(5).childs.add(new ProductNode(2142,"Scoop",1));
        productTree.root.childs.get(5).childs.add(new ProductNode(19,"Blade",1));
        productTree.root.childs.get(5).childs.add(new ProductNode(14127,"Rivet",6));

        productTree.root.childs.get(0).childs.get(2).childs.add(new ProductNode(129,"Top Handle",1));
        productTree.root.childs.get(0).childs.get(2).childs.add(new ProductNode(1118,"Top Handle",1));

        productTree.root.childs.get(0).childs.get(0).setParent(productTree.root.childs.get(0));
        productTree.root.childs.get(0).childs.get(1).setParent(productTree.root.childs.get(0));
        productTree.root.childs.get(0).childs.get(2).setParent(productTree.root.childs.get(0));

        productTree.root.childs.get(0).childs.get(2).childs.get(0).setParent(productTree.root.childs.get(0).childs.get(2));
        productTree.root.childs.get(0).childs.get(2).childs.get(1).setParent(productTree.root.childs.get(0).childs.get(2));

        productTree.root.childs.get(5).childs.get(0).setParent(productTree.root.childs.get(5));
        productTree.root.childs.get(5).childs.get(1).setParent(productTree.root.childs.get(5));
        productTree.root.childs.get(5).childs.get(2).setParent(productTree.root.childs.get(5));

        return productTree;
    }
    public void intersects(List<Label> labels,Label label ){
        for (Label value : labels) {
            if (label.getLayoutX()==value.getLayoutX()&&label.getLayoutY()==value.getLayoutY()) {
                labelX+=150;
                label.setLayoutX(labelX);
                label.setLayoutY(labelY);
            }
        }

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



}
