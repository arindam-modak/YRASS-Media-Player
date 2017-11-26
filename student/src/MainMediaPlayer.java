import java.awt.Color;
import java.awt.Frame;
import java.awt.Paint;
import java.awt.Toolkit;
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
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light.Point;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;

public class MainMediaPlayer extends Application implements MouseListener {
    FileChooser fc;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Media media;
    Image image;
    DropShadow dropshadow;
    BorderPane borderPane;
    Button enter = new Button();
    String fileName="";
    Toolkit tk = Toolkit.getDefaultToolkit();
    Image cur = new Image("cursor.png");
    int playButtonCount = 0;
    int fullScreenClick=1;
    
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
            "-fx-background-size: 100 100;\n" +
            "-fx-background-position: center center;\n" +
            "-fx-min-width: 100px; " +
            "-fx-min-height: 100px; " +
            "-fx-max-width: 100px; " +
            "-fx-max-height: 100px; " +
            "-fx-background-image: url('opened_folder1600.png');" +
            "-fx-background-insets: 0px; " +
            "-fx-padding: 0px;"
        );
        
        enter.setOnMouseEntered(e -> {
           enter.setStyle(
            "-fx-background-radius: 10em; " +
            "-fx-background-repeat: stretch;   \n" +
            "-fx-background-size: 100 100;\n" +
            "-fx-background-position: center center;\n" +
            "-fx-min-width: 100px; " +
            "-fx-min-height: 100px; " +
            "-fx-max-width: 100px; " +
            "-fx-max-height: 100px; " +
            "-fx-background-image: url('opened_folder1600.png');" +
            "-fx-background-color: #80ced6;" +
            "-fx-background-insets: 0px; " +
            "-fx-padding: 0px;"
          );
        });
        enter.setOnMouseExited(e -> {
           enter.setStyle(
            "-fx-background-radius: 10em; " +
            "-fx-background-repeat: stretch;   \n" +
            "-fx-background-size: 100 100;\n" +
            "-fx-background-position: center center;\n" +
            "-fx-min-width: 100px; " +
            "-fx-min-height: 100px; " +
            "-fx-max-width: 100px; " +
            "-fx-max-height: 100px; " +
            "-fx-background-image: url('opened_folder1600.png');" +
            "-fx-background-color: white;" +
            "-fx-background-insets: 0px; " +
            "-fx-padding: 0px;"
          );
        });

        // ************* Choose File ******************
        enter.setText("");
        enter.setOnAction((ActionEvent e) -> {
            chooseFile(0);
        });

        borderPane.setStyle("-fx-background-image: url('573012.png');\n" +
            "    -fx-background-repeat: stretch;   \n" +
            "    -fx-background-size: 1400 800;\n" +
            "    -fx-background-position: center center;\n" +
            "    -fx-effect: dropshadow(three-pass-box, black, 30, 0.5, 0, 0);");
        borderPane.setCenter(enter);
        
        Scene scene = new Scene(borderPane, 1200, 700);
        borderPane.setMinSize(400, 300);
        img.setEffect(dropshadow);
        primaryStage.setFullScreen(false);
        primaryStage.setTitle("YRASS Player");
        primaryStage.setScene(scene);
        primaryStage.getScene().setCursor(new ImageCursor(cur,0,0));
        primaryStage.show();
        
        
        // ***************** volume scrolling ***************
        scene.setOnScroll(new EventHandler<javafx.scene.input.ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                double deltaY = event.getDeltaY();
                double prev = volumeSlider.getValue();
                volumeSlider.setValue((prev+deltaY/20.0));
                mediaPlayer.setVolume((prev+deltaY/20.0));
            }
            
        });
        borderPane.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() { 
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    if(event.getClickCount() == 2){
                        fullScreenClick++;
                        if(fullScreenClick%2==1){
                            try{
                                primaryStage.setFullScreen(true);
                                MediaProp.setVisible(false);
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        else{
                            try{
                                primaryStage.setFullScreen(false);
                                MediaProp.setVisible(true);
                            }
                            catch(Exception e)
                            {
                                
                            }
                        }
                        mediaPlayer.play();
                    }
                    if(event.getClickCount() == 1)
                    {
                        playButtonCount++;
                        if(playButtonCount%2==1){
                            playButton.setStyle("-fx-graphic: url('if_Play_2001879.png'); \n" + "-fx-background-color: #80ced6");
                            playButton.setTooltip(new Tooltip("Play"));
                            mediaPlayer.pause();
                        }
                        else{
                            playButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #80ced6");
                            playButton.setTooltip(new Tooltip("Pause"));
                            mediaPlayer.play();
                        }
                    }
                }
            
            }   
        });
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            public void handle(KeyEvent ke ){
                if(ke.getCode()==KeyCode.ALT){
                    playButtonCount++;
                        if(playButtonCount%2==1){
                            playButton.setStyle("-fx-graphic: url('if_Play_2001879.png'); \n" + "-fx-background-color: #80ced6");
                            playButton.setTooltip(new Tooltip("Play"));
                            mediaPlayer.pause();
                        }
                        else{
                            playButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #80ced6");
                            playButton.setTooltip(new Tooltip("Pause"));
                            mediaPlayer.play();
                        }
                }
                if(ke.getCode()==KeyCode.C){
                    chooseFile(1);
                }
            }

        });
    }
    
    Button playButton,pauseButton,forwardButton,backButton,filesButton,startButton,endButton,MediaProp,speedButton;
    Slider volumeSlider,timeSlider,slider,speedSlider;
    Button volbtn;
    Label time,speed,vollabel;
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
        toolBar.setStyle("-fx-background-color: #80ced6");
        playButton = new Button();
        playButton.setMaxSize(32,32);
        //pauseButton = new Button();
        //pauseButton.setMaxSize(32,32);
        startButton=new Button();
        startButton.setMaxSize(32,32);
        startButton.setStyle("-fx-graphic: url('if_Replay.png'); \n" + "-fx-background-color: #80ced6");
        backButton = new Button();
        backButton.setMaxSize(32,32);
        forwardButton = new Button();
        forwardButton.setMaxSize(32,32);
        filesButton = new Button();
        //pauseButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #80ced6");
        //pauseButton.setMaxSize(32,32);
        playButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #80ced6");
        playButton.setMaxSize(32,32);
        backButton.setStyle("-fx-graphic: url('if_Rewind_2001873.png'); \n" + "-fx-background-color: #80ced6");
        backButton.setMaxSize(32,32);
        forwardButton.setStyle("-fx-graphic: url('if_Fast_Forward_2001867.png'); \n" + "-fx-background-color: #80ced6");
        filesButton.setMaxSize(32,32);
        filesButton.setStyle("-fx-graphic: url('if_open-file_85334.png'); \n" + "-fx-background-color: #80ced6");
        volbtn=new Button();
        volbtn.setMaxSize(32,32);
        volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png'); \n" + "-fx-background-color: #80ced6");
        MediaProp=new Button();
        MediaProp.setTooltip(new Tooltip("Details"));
        MediaProp.setMaxSize(32,32);
        MediaProp.setStyle("-fx-graphic: url('if_stock_view-details_24335.png'); \n" + "-fx-background-color: #80ced6");
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
                vollabel.setText("Vol:" + Integer.toString((int) (volumeSlider.getValue())) + "%");
            }
            if(volumeSlider.isPressed())
            {
                mediaPlayer.setVolume(volumeSlider.getValue()/100.0);
                vollabel.setText("Vol:" + Integer.toString((int) (volumeSlider.getValue())) + "%");
            }
            if(volumeSlider.getValue()==0)
            {
                volbtn.setStyle("-fx-graphic: url('if_Volume_Mute_2001875.png'); \n" + "-fx-background-color: #80ced6");
                volbtn.setTooltip(new Tooltip("Mute"));
                vollabel.setText("Vol:" + Integer.toString((int) (volumeSlider.getValue())) + "%");
            }
            else
            {
                volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png'); \n" + "-fx-background-color: #80ced6");
                volbtn.setTooltip(new Tooltip("Volume"));
                vollabel.setText("Vol:" + Integer.toString((int) (volumeSlider.getValue())) + "%");
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
        time.setFont(Font.font("Comic Sans", FontWeight.BOLD,12));
        filesButton.setTooltip(new Tooltip("Open New File"));
        startButton.setTooltip(new Tooltip("Replay"));
        backButton.setTooltip(new Tooltip("Rewind"));
        playButton.setTooltip(new Tooltip("Pause"));
        //pauseButton.setTooltip(new Tooltip("Pause"));
        forwardButton.setTooltip(new Tooltip("Forward"));
        volbtn.setTooltip(new Tooltip("Volume"));
        speedButton=new Button();
        speedButton.setText("Speed:");
        speedButton.setMaxSize(70, 40);
        speedButton.setStyle("-fx-background-color: #80ced6");
        
        speedButton.setTooltip(new Tooltip("Default Speed"));
        speedButton.setOnAction((ActionEvent e)->{
            mediaPlayer.setRate(1);
            speedSlider.setValue(50.0);
        });
        
        
        vollabel=new Label();
       
        vollabel.setFont(Font.font("Comic Sans", FontWeight.BOLD, 15));
        vollabel.setMaxHeight(100);
        vollabel.setLabelFor(volumeSlider);
        vollabel.setText("Vol:" + Integer.toString((int) (volumeSlider.getValue())) + "%");
        playButton.setOnMouseEntered(e -> {
           if(playButtonCount%2==0)
            playButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #000777");
           else
            playButton.setStyle("-fx-graphic: url('if_Play_2001879.png'); \n" + "-fx-background-color: #000777");
        });
        playButton.setOnMouseExited(e -> {
           if(playButtonCount%2==0)
            playButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #80ced6");
           else
            playButton.setStyle("-fx-graphic: url('if_Play_2001879.png'); \n" + "-fx-background-color: #80ced6");
        });
        playButton.setOnAction((ActionEvent e) -> {           
           playButtonCount++;
           if(playButtonCount%2==1){
               playButton.setStyle("-fx-graphic: url('if_Play_2001879.png'); \n" + "-fx-background-color: #80ced6");
               playButton.setTooltip(new Tooltip("Play"));
               mediaPlayer.pause();
           }
           else{
               playButton.setStyle("-fx-graphic: url('if_Pause_2001889.png'); \n" + "-fx-background-color: #80ced6");
               playButton.setTooltip(new Tooltip("Pause"));
               mediaPlayer.play();
           }
        });
        
        /*pauseButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.pause();
        });*/
        startButton.setOnAction((ActionEvent e)->{
        
            mediaPlayer.seek(Duration.ZERO);
        });
        startButton.setOnMouseEntered(e -> {
           startButton.setStyle("-fx-graphic: url('if_Replay.png'); \n" + "-fx-background-color: #000777");
        });
        startButton.setOnMouseExited(e -> {
           startButton.setStyle("-fx-graphic: url('if_Replay.png'); \n" + "-fx-background-color: #80ced6");
        });
       
        backButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.2));
        });
        backButton.setOnMouseEntered(e -> {
           backButton.setStyle("-fx-graphic: url('if_Rewind_2001873.png'); \n" + "-fx-background-color: #000777");
        });
        backButton.setOnMouseExited(e -> {
           backButton.setStyle("-fx-graphic: url('if_Rewind_2001873.png'); \n" + "-fx-background-color: #80ced6");
        });
        
        forwardButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.2));
        });
        forwardButton.setOnMouseEntered(e -> {
           forwardButton.setStyle("-fx-graphic: url('if_Fast_Forward_2001867.png'); \n" + "-fx-background-color: #000777");
        });
        forwardButton.setOnMouseExited(e -> {
           forwardButton.setStyle("-fx-graphic: url('if_Fast_Forward_2001867.png'); \n" + "-fx-background-color: #80ced6");
        });
        
        //*************************** Alert box ******************************
        MediaProp.setOnAction((ActionEvent e) -> {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Details");
                a.setContentText(fileName);
                a.setTitle("Properties");
                a.showAndWait();
                
        });
        MediaProp.setOnMouseEntered(e -> {
           MediaProp.setStyle("-fx-graphic: url('if_stock_view-details_24335.png'); \n" + "-fx-background-color: #000777");
        });
        MediaProp.setOnMouseExited(e -> {
           MediaProp.setStyle("-fx-graphic: url('if_stock_view-details_24335.png'); \n" + "-fx-background-color: #80ced6");
        });
        
        
        volbtn.setOnAction((ActionEvent e) -> {
                flag++;
                if(flag%2==1)
                {
                    volbtn.setStyle("-fx-graphic: url('if_Volume_Mute_2001875.png'); \n" + "-fx-background-color: #80ced6");
                    prev=volumeSlider.getValue();
                    volbtn.setTooltip(new Tooltip("Mute"));
                    mediaPlayer.setVolume(0);
                    volumeSlider.setValue(0);
                    vollabel.setText("Vol:" + Integer.toString((int) (0)) + "%");
                    
                }
                else
                {
                    volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png'); \n" + "-fx-background-color: #80ced6");
                    volbtn.setTooltip(new Tooltip("Volume"));
                    mediaPlayer.setVolume(prev/100.0);
                    volumeSlider.setValue(prev);
                    vollabel.setText("Vol:" + Integer.toString((int) (prev)) + "%");
                }
                  
        });
        volbtn.setOnMouseEntered(e -> {
            if(flag%2==1)
                volbtn.setStyle("-fx-graphic: url('if_Volume_Mute_2001875.png'); \n" + "-fx-background-color: #000777");
            else
                volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png'); \n" + "-fx-background-color: #000777");
        });
        volbtn.setOnMouseExited(e -> {
           if(flag%2==1)
                volbtn.setStyle("-fx-graphic: url('if_Volume_Mute_2001875.png'); \n" + "-fx-background-color: #80ced6");
            else
                volbtn.setStyle("-fx-graphic: url('if_Volume_Max_2001874.png'); \n" + "-fx-background-color: #80ced6");
        });
        
        filesButton.setOnAction((ActionEvent e) -> {
               chooseFile(1);
        });
        filesButton.setOnMouseEntered(e -> {
           filesButton.setStyle("-fx-graphic: url('if_open-file_85334.png'); \n" + "-fx-background-color: #000777");
        });
        filesButton.setOnMouseExited(e -> {
           filesButton.setStyle("-fx-graphic: url('if_open-file_85334.png'); \n" + "-fx-background-color: #80ced6");
        });
        
        
      toolBar.getChildren().addAll(filesButton,speedButton,speedSlider,startButton,backButton,playButton,forwardButton,time,timeSlider,volbtn,volumeSlider,vollabel,MediaProp);
        return toolBar;
    }
    
    void chooseFile(int prev)
    {
        try {
                playButtonCount = 0;
                int flag2=1;
                fc = new FileChooser();
                //fc.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg","*.mp3","*.mkv"));
                File file = fc.showOpenDialog(null);
                if(file==null) return;
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                String std=Long.toString(file.getUsableSpace()/(1024*1024*1024))+"MB";
                Date d = new Date(file.lastModified());
                fileName = "Name: " + (String)file.getName() + "\nPath: " + path + "\nSize: " + std+"\n Last Modified: "+d.toString();
        
                if(file.getName().substring(file.getName().length()-3,file.getName().length()).compareTo("mp3")!=0 && file.getName().substring(file.getName().length()-3,file.getName().length()).compareTo("flv")!=0 && file.getName().substring(file.getName().length()-3,file.getName().length()).compareTo("mp4")!=0 && file.getName().substring(file.getName().length()-4,file.getName().length()).compareTo("mpeg")!=0)
                {
                    Alert err = new Alert(Alert.AlertType.ERROR);
                    err.setTitle("ERROR!");
                    err.setHeaderText("Not Supported");
                    err.setContentText("Invalid File Type. Please choose again among the following file types : mp3,mp4,mpeg,flv");
                    err.showAndWait();
                    
                }
                else
                    {
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
                        
                        if(prev == 1)
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
                    }
                
            } catch (Exception ex) {
                Logger.getLogger(MainMediaPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
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