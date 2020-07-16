
import GUI.CreatingProductTreeWin;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage){

        CreatingProductTreeWin creatingProductOrderWin = new CreatingProductTreeWin();
        primaryStage.setScene(creatingProductOrderWin.paint());
        primaryStage.getIcons().add(new Image("snow_shovel.jpeg"));
        primaryStage.show();
    }
}
