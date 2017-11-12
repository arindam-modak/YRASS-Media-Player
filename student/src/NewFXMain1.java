/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;

import javafx.scene.image.Image;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.floor;
import static java.lang.String.format;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Platform.runLater;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.util.Duration;
import javax.swing.JOptionPane;

/**
 *
 * @author Shiv
 */
public class NewFXMain1 extends Application implements MouseListener {
    FileChooser fc;
    MediaPlayer mediaPlayer;
    MediaView mediaView;
    Media media;
    Button enter = new Button();
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

    //The location of your file
        //Media media = new Media(new File("C:\\Users\\Shiv\\Videos\\Baarish - Half Girlfriend - Arjun K & Shraddha K - Ash King & Shashaa Tirupati - Tanishk Bagchi(1).mp4").toURI().toString());
        ImageView img=new ImageView();
        Image image = new Image("Play.png");
        img.setImage(image);
        BorderPane borderPane = new BorderPane();
        DropShadow dropshadow = new DropShadow();
        dropshadow.setOffsetY(5.0);
        dropshadow.setOffsetX(5.0);
        enter.setText("OPEN (whatever you want to do man!!!)");
        enter.setOnAction((ActionEvent e) -> {
            try {
                fc = new FileChooser();
                fc.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg"));
                File file = fc.showOpenDialog(null);
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                media = new Media(new File(path).toURI().toString());
                //mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaView = new MediaView(mediaPlayer);
                mediaView.setFitWidth(1200);
                mediaView.setFitHeight(600);
                mediaView.autosize();
                
                borderPane.setCenter(mediaView);
                
                borderPane.setBottom(addToolBar());
                borderPane.setStyle("-fx-background-color: Black");
                mediaView.setEffect(dropshadow);
            } catch (Exception ex) {
                Logger.getLogger(NewFXMain1.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        borderPane.setCenter(enter);
        Scene scene = new Scene(borderPane, 1000, 700);
        img.setEffect(dropshadow);
    
        primaryStage.setTitle("Media Player!");
        primaryStage.setScene(scene);
        primaryStage.show();
     
    }
    Button playButton,pauseButton,forwardButton,backButton,filesButton,startButton,endButton;
    Slider volumeSlider,timeSlider,slider;
    Label volumeLabel;
    Label time;
    Duration duration;
    //Double time2 = mediaPlayer.getTotalDuration().toSeconds();
    private HBox addToolBar() throws FileNotFoundException {
        HBox toolBar = new HBox();
        //toolBar.setPadding(new Insets(10,10,10,10));
        toolBar.setAlignment(Pos.BOTTOM_CENTER);
        toolBar.alignmentProperty().isBound();
        toolBar.setSpacing(5);
        toolBar.setStyle("-fx-background-color: Cyan");
        
        //File imageFile = new File("C:\\Users\\Shiv\\Documents\\NetBeansProjects\\student\\Play.png");
        //File pic=new File(this.getClass().getResource("C:\\Users\\Shiv\\Documents\\NetBeansProjects\\student\\Play.png").getFile());
        //FileInputStream fis=new FileInputStream(pic);
        //Image playimage = new Image(fis);
        //Image playimage = new Image(new FileInputStream("C:\\Users\\Shiv\\Documents\\NetBeansProjects\\student\\Play.png"));
        
        //Image playButtonImage = new Image(getClass().getResourceAsStream("Play.png")) {};
        playButton = new Button();
        //playButton.setGraphic(new ImageView(playButtonImage));
        //playButton.setStyle("-fx-background-color: Orange");
        pauseButton = new Button();
        //playButton.setGraphic(new ImageView(playimage));
        //pauseButton.setStyle("-fx-background-color: Orange");
        startButton=new Button();
        endButton=new Button();
        playButton.setOnAction((ActionEvent e) -> {
            //playButton.setStyle("-fx-background-color: Blue");   
            mediaPlayer.play();
        });
        pauseButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.pause();
        });
        startButton.setOnAction((ActionEvent e)->{
        
            mediaPlayer.seek(Duration.ZERO);
        });
        endButton.setOnAction((ActionEvent e)->{
        
            mediaPlayer.seek(Duration.INDEFINITE);
        });
        
        backButton = new Button();
        forwardButton = new Button();
        filesButton = new Button();
        //playButton.setGraphic(new ImageView(playButtonImage));
        pauseButton.setStyle("-fx-graphic: url('Pause.png')");
        playButton.setStyle("-fx-graphic: url('Play.png')");
        backButton.setStyle("-fx-graphic: url('back.png')");
        forwardButton.setStyle("-fx-graphic: url('fast.png')");
        filesButton.setStyle("-fx-graphic: url('file.png')");
        //playButton.setGraphic(new ImageView(playimage));
        //pauseButton.setStyle("-fx-background-color: Orange");
        
        backButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().divide(1.2));
        });
        forwardButton.setOnAction((ActionEvent e) -> {
            mediaPlayer.seek(mediaPlayer.getCurrentTime().multiply(1.2));
        });
        
        filesButton.setOnAction((ActionEvent e) -> {
            try{
                FileChooser fc1 = new FileChooser();
                fc1.getExtensionFilters().add(new ExtensionFilter("*.flv", "*.mp4", "*.mpeg"));
                File file = fc1.showOpenDialog(null);
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                media = new Media(new File(path).toURI().toString());
                mediaPlayer.stop();
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(true);
                mediaView.setMediaPlayer(mediaPlayer);
                time = new Label();
                //time.setTextFill((javafx.scene.paint.Paint)(Paint)Color.WHITE);
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
                timeSlider.setMinWidth(50);
                timeSlider.setMaxWidth(8*Double.SIZE);
                mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!timeSlider.isValueChanging()) {
                    timeSlider.setValue(newTime.toSeconds());
                }
                });
                timeSlider.valueProperty().addListener(new InvalidationListener() {
                public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                        System.out.println(timeSlider.getValue());
                        mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                        currentTime = Duration.seconds(timeSlider.getValue());
                    }
                }
                });

            }
            catch(Exception ex1) {
                ;
            }

        });
        
       
        // Add the volume label
        volumeLabel = new Label("Vol: ");
        // Add Volume slider
        volumeSlider = new Slider();        
        volumeSlider.setPrefWidth(70);
        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
        volumeSlider.setMinWidth(30);
        volumeSlider.setValue(50);
        mediaPlayer.setVolume(volumeSlider.getValue());
        volumeSlider.valueProperty().addListener(new InvalidationListener() {
        public void invalidated(Observable ov) {
        if (volumeSlider.isValueChanging()) {
                mediaPlayer.setVolume(volumeSlider.getValue() / 100.0);  
            }
        }
        });
        time = new Label();
        //time.setTextFill((javafx.scene.paint.Paint)(Paint)Color.WHITE);
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
        timeSlider.setMinWidth(50);
        timeSlider.setMaxWidth(8*Double.SIZE);
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
        if (!timeSlider.isValueChanging()) {
            timeSlider.setValue(newTime.toSeconds());
        }
        });
        timeSlider.valueProperty().addListener(new InvalidationListener() {
        public void invalidated(Observable ov) {
        if (timeSlider.isValueChanging()) {
                System.out.println(timeSlider.getValue());
                mediaPlayer.seek(Duration.seconds(timeSlider.getValue()));
                currentTime = Duration.seconds(timeSlider.getValue());
            }
        }
        });
       
        toolBar.getChildren().addAll(filesButton,startButton,backButton,playButton,pauseButton,forwardButton,endButton,time,timeSlider,volumeLabel,volumeSlider);
        return toolBar;
    }
    
    Duration currentTime;
    protected void updateValues(Duration currentTime) {
        if (time != null) {
        runLater(() -> {
        //currentTime = mediaPlayer.getCurrentTime();
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
