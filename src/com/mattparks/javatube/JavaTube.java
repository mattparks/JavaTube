package com.mattparks.javatube;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.web.*;
import javafx.stage.*;

import java.awt.*;

/**
 * JavaTube is a simple JavaFX application that can stream YouTube videos into a window that will stay in place above other windows with no space wasted.
 */
public class JavaTube extends Application {
	private static final MyFile FILE_CONFIG = new MyFile("config.conf");

	private static int width = 640;
	private static int height = 390;
	private static String videoURL = "https://www.youtube.com/embed/jQNCuD_hxdQ";
	private static String position = "upperRight";

	public static void main(String[] args) {
		// Loads the config and changed configs.
		Config config = new Config(FILE_CONFIG);
		width = config.getIntWithDefault("width", width);
		height = config.getIntWithDefault("height", height);
		videoURL = config.getStringWithDefault("videoURL", videoURL);
		position = config.getStringWithDefault("position", position);

		// Launches the JavaFX application.
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// Creates a new web viewer and loads the video.
		WebView webview = new WebView();
		webview.getEngine().load(videoURL);
		webview.setPrefSize(width, height);

		// Calculates the viewers position on the screen using the screens size.
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double positionX;
		double positionY;

		switch (position) {
			case "upperRight":
				positionX = screenSize.getWidth() - width;
				positionY = 0.0;
				break;
			case "upperLeft":
				positionX = 0.0;
				positionY = 0.0;
				break;
			case "lowerRight":
				positionX = screenSize.getWidth() - width;
				positionY = screenSize.getHeight() - height;
				break;
			case "lowerLeft":
				positionX = 0.0;
				positionY = screenSize.getHeight() - height;
				break;
			case "center":
				positionX = (screenSize.getWidth() - width) / 2.0;
				positionY = (screenSize.getHeight() - height) / 2.0;
				break;
			default:
				System.out.println(position + " is a invalid position for the display, please use {upperRight, upperLeft, lowerRight, lowerLeft, center}.");
				positionX = (screenSize.getWidth() - width) / 2.0;
				positionY = (screenSize.getHeight() - height) / 2.0;
		}

		// Sets the stages scene and other configs.
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(new Scene(webview));
		stage.setX(positionX);
		stage.setY(positionY);
		stage.setTitle("JavaTube");
		stage.setResizable(false);
		stage.setAlwaysOnTop(true);
		stage.show();
	}
}
