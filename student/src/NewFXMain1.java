import javafx.scene.image.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.floor;
import static java.lang.String.format;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Platform.runLater;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import javax.swing.JOptionPane;

public class NewFXMain1 extends Application implements MouseListener {
    FileChooser fc;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Media media;
    Image image;
    DropShadow dropshadow;
    BorderPane borderPane;
    Button enter = new Button();
    String fileName="";
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.getIcons().add(new Image("media_player_icon_by_xylomon.png"));
        ImageView img=new ImageView();
        image = new Image("572703.png");
        img.setImage(image);
        borderPane = new BorderPane();
        
        dropshadow = new DropShadow();
        dropshadow.setOffsetY(5.0);
        dropshadow.setOffsetX(5.0);
        enter.setScaleY(1.0);
        enter.setMinHeight(100);
        enter.setMaxHeight(100);
        enter.setPrefHeight(100);
        enter.setStyle(
            "-fx-background-radius: 10em; " +
            "-fx-background-repeat: stretch;   \n" +
            "-fx-background-size: 110 110;\n" +
            "-fx-background-position: center center;\n" +
            "-fx-min-width: 100px; " +
            "-fx-min-height: 100px; " +
            "-fx-max-width: 100px; " +
            "-fx-max-height: 100px; " +
            "-fx-background-image: url('opened_folder1600.png');" +
            "-fx-background-insets: 0px; " +
            "-fx-padding: 0px;"
        );
        enter.setText("");
        enter.setOnAction((ActionEvent e) -> {
            try {
                int flag2=1;
                fc = new FileChooser();
                fc.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg","*.mp3"));
                File file = fc.showOpenDialog(null);
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                String std=Long.toString(file.getUsableSpace()/(1024*1024*1024))+"MB";
                Date d = new Date(file.lastModified());
                fileName = "Name: " + (String)file.getName() + "\nPath: " + path + "\nSize: " + std+"\n Last Modified: "+d.toString();
        
                if(file.getName().substring(file.getName().length()-3,file.getName().length()).compareTo("mp3")==0)
                {
                    flag2=0;
                    ImageView img2=new ImageView();
                    img2.setImage(image);
                    img2.setFitWidth(1200);
                    img2.setFitHeight(600);
                    img2.autosize();
                    img2.setEffect(dropshadow);
                    borderPane.setCenter(img2);
                }
                media = new Media(new File(path).toURI().toString());
                //mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                if(flag2==1){
                    
                    mediaView = new MediaView(mediaPlayer);
                    mediaView.setFitWidth(1200);
                    mediaView.setFitHeight(600);
                    mediaView.autosize();
                    mediaView.setEffect(dropshadow);
                    borderPane.setCenter(mediaView);
                }
                borderPane.setBottom(addToolBar());
                borderPane.setStyle("-fx-background-color: Black");
                
            } catch (Exception ex) {
                Logger.getLogger(NewFXMain1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        borderPane.setStyle("-fx-background-image: url('573012.png');\n" +
            "    -fx-background-repeat: stretch;   \n" +
            "    -fx-background-size: 1400 800;\n" +
            "    -fx-background-position: center center;\n" +
            "    -fx-effect: dropshadow(three-pass-box, black, 30, 0.5, 0, 0);");
        borderPane.setCenter(enter);
        Scene scene = new Scene(borderPane, 1200, 700);
        img.setEffect(dropshadow);
        primaryStage.setTitle("Buzzaado Player");
        primaryStage.setScene(scene);
        primaryStage.show();
     
    }
    Button playButton,pauseButton,forwardButton,backButton,filesButton,startButton,endButton,MediaProp,speedButton;
    Slider volumeSlider,timeSlider,slider,speedSlider;
    Button volbtn;
    Label time,speed;
    Duration duration;
    int flag=0;
    double prev;
    Label filename;
    //Double time2 = mediaPlayer.getTotalDuration().toSeconds();
    private HBox addToolBar() throws FileNotFoundException {
        HBox toolBar = new HBox();
        //toolBar.setPadding(new Insets(10,10,10,10));
        toolBar.setAlignment(Pos.BOTTOM_LEFT);
        toolBar.alignmentProperty().isBound();
        toolBar.setSpacing(5);
        toolBar.setStyle("-fx-background-color: white");
        playButton = new Button();
        playButton.setMaxSize(32,32);
        pauseButton = new Button();
        pauseButton.setMaxSize(32,32);
        startButton=new Button();
        startButton.setMaxSize(32,32);
        startButton.setStyle("-fx-graphic: url('if_Replay.png')");
        backButton = new Button();
        backButton.setMaxSize(32,32);
        forwardButton = new Button();
        forwardButton.setMaxSize(32,32);
        filesButton = new Button();
        pauseButton.setStyle("-fx-graphic: url('if_PauseNormalRed_22967.png')");
        pauseButton.setMaxSize(32,32);
        playButton.setStyle("-fx-graphic: url('if_Play1Pressed_22932.png')");
        playButton.setMaxSize(32,32);
        backButton.setStyle("-fx-graphic: url('if_Rewind_2001873.png')");
        backButton.setMaxSize(32,32);
        forwardButton.setStyle("-fx-graphic: url('if_StepForwardDisabled_22933.png')");
        filesButton.setMaxSize(32,32);
        filesButton.setStyle("-fx-graphic: url('if_open-file_85334.png')");
        volbtn=new Button();
        volbtn.setMaxSize(32,32);
        volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png')");
        MediaProp=new Button();
        MediaProp.setTooltip(new Tooltip("Details"));
        MediaProp.setMaxSize(32,32);
        MediaProp.setStyle("-fx-graphic: url('if_stock_view-details_24335.png')");
        
        playButton.setOnAction((ActionEvent e) -> {
           mediaPlayer.play();
        });
        pauseButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.pause();
        });
        startButton.setOnAction((ActionEvent e)->{
        
            mediaPlayer.seek(Duration.ZERO);
        });
        
       
        backButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.2));
        });
        forwardButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.2));
        });
        MediaProp.setOnAction((ActionEvent e) -> {
            JOptionPane.showMessageDialog(null,fileName,"Details", JOptionPane.PLAIN_MESSAGE);
        });
        volbtn.setOnAction((ActionEvent e) -> {
                flag++;
                if(flag%2==1)
                {
                    volbtn.setStyle("-fx-graphic: url('if_Volume_Mute_2001875.png')");
                    prev=volumeSlider.getValue();
                    volbtn.setTooltip(new Tooltip("Mute"));
                    mediaPlayer.setVolume(0);
                    volumeSlider.setValue(0);
                    
                }
                else
                {
                    volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png')");
                    volbtn.setTooltip(new Tooltip("Volume"));
                    mediaPlayer.setVolume(prev/100.0);
                    volumeSlider.setValue(prev);
                }
                  
        });
        filesButton.setOnAction((ActionEvent e) -> {
               try {
                int flag2=1;
                fc = new FileChooser();
                fc.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg","*.mp3"));
                File file = fc.showOpenDialog(null);
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                String std=Long.toString(file.getUsableSpace()/(1024*1024*1024))+"MB";
                Date d = new Date(file.lastModified());
                fileName = "Name: " + (String)file.getName() + "\nPath: " + path + "\nSize: " + std+"\n Last Modified: "+d.toString();
        
                if(file.getName().substring(file.getName().length()-3,file.getName().length()).compareTo("mp3")==0)
                {
                    flag2=0;
                    ImageView img2=new ImageView();
                    img2.setImage(image);
                    img2.setFitWidth(1200);
                    img2.setFitHeight(600);
                    img2.autosize();
                    img2.setEffect(dropshadow);
                    borderPane.setCenter(img2);
                }
                media = new Media(new File(path).toURI().toString());
                
                mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                if(flag2==1){
                    
                    mediaView = new MediaView(mediaPlayer);
                    mediaView.setFitWidth(1200);
                    mediaView.setFitHeight(600);
                    mediaView.autosize();
                    mediaView.setEffect(dropshadow);
                    borderPane.setCenter(mediaView);
                }
                borderPane.setBottom(addToolBar());
                borderPane.setStyle("-fx-background-color: Black");
            } catch (Exception ex) {
                Logger.getLogger(NewFXMain1.class.getName()).log(Level.SEVERE, null, ex);
            }
          
        });
        
       
        speed=new Label("Speed: ");
        volumeSlider = new Slider();        
        volumeSlider.setPrefWidth(120);
        volumeSlider.setMaxWidth(300);
        volumeSlider.setMinWidth(10);
        volumeSlider.setValue(50);
        mediaPlayer.setVolume(volumeSlider.getValue());
        volumeSlider.valueProperty().addListener((Observable ov) -> {
            if (volumeSlider.isValueChanging()) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);  
            }
            if(volumeSlider.isPressed())
            {
                mediaPlayer.setVolume(volumeSlider.getValue()/100.0);
            }
            if(volumeSlider.getValue()==0)
            {
                volbtn.setStyle("-fx-graphic: url('if_Volume_Mute_2001875.png')");
                volbtn.setTooltip(new Tooltip("Mute"));
            }
            else
            {
                volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png')");
                volbtn.setTooltip(new Tooltip("Volume"));
            }
        });
        time = new Label();
        time.setPrefWidth(80);

        mediaPlayer.currentTimeProperty().addListener((Observable ov) -> {
            currentTime = mediaPlayer.getCurrentTime();
            updateValues(currentTime);
        });
        
        mediaPlayer.setOnReady(() -> {
        duration = mediaPlayer.getMedia().getDuration();
            currentTime = mediaPlayer.getCurrentTime();
            updateValues(currentTime);
        });
        timeSlider = new Slider();
        HBox.setHgrow(timeSlider,Priority.ALWAYS);
        timeSlider.setMinWidth(10);
        timeSlider.setMaxWidth(8*Double.SIZE);
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        if (!timeSlider.isValueChanging()) {
            timeSlider.setValue(newTime.toSeconds());
        }
        });
        timeSlider.valueProperty().addListener((Observable ov) -> {
            if (timeSlider.isValueChanging()) {
                //System.out.println(timeSlider.getValue());
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                currentTime = Duration.seconds(timeSlider.getValue());
            }
            if(timeSlider.isPressed())
            {
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                currentTime = Duration.seconds(timeSlider.getValue());
            }
        });
        speedSlider = new Slider();
        HBox.setHgrow(speedSlider, Priority.ALWAYS);
        speedSlider.setMinWidth(10);
        speedSlider.setMaxWidth(100);
        speedSlider.setPrefWidth(70);
        speedSlider.setValue(50);
        speedSlider.valueProperty().addListener((Observable ov) -> {
            if (speedSlider.isValueChanging() || speedSlider.isPressed()) {
                double rate = speedSlider.getValue()/10;
                if(rate<1)
                    rate = 0.5;
                else if(rate < 2)
                    rate = 0.6;
                else if(rate < 3)
                    rate = 0.7;
                else if(rate < 4)
                    rate = 0.8;
                else if(rate < 5)
                    rate = 0.9;
                else if(rate < 6)
                    rate = 1;
                else if(rate < 7)
                    rate = 1.1;
                else if(rate < 8)
                    rate = 1.2;
                else if(rate < 9)
                    rate = 1.3;
                else
                    rate = 1.4;
                mediaPlayer.setRate(rate);
            }
        });
        speedSlider.setMaxHeight(100);
        volumeSlider.setMaxHeight(100);
        timeSlider.setMaxHeight(100);
        speed.setMaxHeight(100);
        time.setMaxHeight(100);
        filesButton.setTooltip(new Tooltip("Open New File"));
        startButton.setTooltip(new Tooltip("Replay"));
        backButton.setTooltip(new Tooltip("Rewind"));
        playButton.setTooltip(new Tooltip("Play"));
        pauseButton.setTooltip(new Tooltip("Pause"));
        forwardButton.setTooltip(new Tooltip("Forward"));
        volbtn.setTooltip(new Tooltip("Volume"));
        speedButton=new Button();
        speedButton.setText("Speed:");
        speedButton.setMaxSize(70, 40);
        speedButton.setTooltip(new Tooltip("Default Speed"));
        speedButton.setOnAction((ActionEvent e)->{
            mediaPlayer.setRate(1);
            speedSlider.setValue(50.0);
        });
      toolBar.getChildren().addAll(filesButton,speedButton,speedSlider,startButton,backButton,playButton,pauseButton,forwardButton,time,timeSlider,volbtn,volumeSlider,MediaProp);
        return toolBar;
    }
    
    Duration currentTime;
    protected void updateValues(Duration currentTime) {
        if (time != null) {
        runLater(() -> {
        time.setText(formatTime(currentTime, duration));  

    });
    }
    }
    
    private static String formatTime(Duration elapsed, Duration duration) {
    int intElapsed = (int) floor(elapsed.toSeconds());
    int elapsedHours = intElapsed / (60 * 60);
    if (elapsedHours > 0) {
    intElapsed -= elapsedHours * 60 * 60;
    }
    int elapsedMinutes = intElapsed / 60;
    int elapsedSeconds = intElapsed - elapsedHours * 60 * 60
    - elapsedMinutes * 60;

    if (duration.greaterThan(Duration.ZERO)) {
    int intDuration = (int) floor(duration.toSeconds());
    int durationHours = intDuration / (60 * 60);
    if (durationHours > 0) {
    intDuration -= durationHours * 60 * 60;
    }
    int durationMinutes = intDuration / 60;
    int durationSeconds = intDuration - durationHours * 60 * 60
    - durationMinutes * 60;
    if (durationHours > 0) {
    return format("%d:%02d:%02d/%d:%02d:%02d",
    elapsedHours, elapsedMinutes, elapsedSeconds,
    durationHours, durationMinutes, durationSeconds);
    } else {
    return format("%02d:%02d/%02d:%02d",
    elapsedMinutes, elapsedSeconds, durationMinutes,
    durationSeconds);
    }
    } else {
    if (elapsedHours > 0) {
    return format("%d:%02d:%02d", elapsedHours,
    elapsedMinutes, elapsedSeconds);
    } else {
    return format("%02d:%02d", elapsedMinutes,
    elapsedSeconds);
    }
    }
    }
   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Startframe startfr = new Startframe();
        launch(args);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        playButton.setStyle("-fx-background-color: Black");
        playButton.setStyle("-fx-body-color: Black");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
