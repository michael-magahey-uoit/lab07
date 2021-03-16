import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static java.awt.Font.*;
import static javafx.scene.text.Font.font;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class lab07 extends Application
{
	private Canvas canvas;
	
	@Override
	
	public void start(Stage primaryStage) throws Exception
	{
		Group root = new Group();
		Scene scene = new Scene(root, 1024, 720);
		canvas = new Canvas();
		canvas.widthProperty().bind(primaryStage.widthProperty());
		canvas.heightProperty().bind(primaryStage.heightProperty());
		root.getChildren().add(canvas);
		
		primaryStage.setTitle("Weather");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		draw(root);
	}
	
	private void draw(Group root)
	{
		Color[] pieColors = {Color.AQUA, Color.GOLD, Color.DARKORANGE, Color.LAWNGREEN, Color.PLUM};
		List<String> keys = new ArrayList<String>();
		
		HashMap<String, Integer> events = new HashMap<String, Integer>();
		int eventsCount = 0;
		Scanner scanner = null;
		try
		{
			scanner = new Scanner(new File(System.getProperty("user.home") + "\\Desktop\\weatherwarnings-2015.csv"));
		}
		catch (Exception e)
		{
			System.out.println("The file does not exist!");
		}
		while (scanner.hasNextLine())
		{
			List<String> data = new ArrayList<String>();
			try (Scanner rowScanner = new Scanner(scanner.nextLine()))
			{
				rowScanner.useDelimiter(",");
				while (rowScanner.hasNext())
				{
					data.add(rowScanner.next());
				}
			}
			if (!events.containsKey(data.get(5)))
			{
				events.put(data.get(5), 0);
				keys.add(data.get(5));
			}
			events.put(data.get(5), events.get(data.get(5)) + 1);
			eventsCount += 1;
		}
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		double begin = 0.0;
		for (int i = 0; i < keys.size(); i++)
		{
			double slicePercentage = (double)events.get(keys.get(i)) / (double)eventsCount;
			double sweepAngle = slicePercentage * 360.0;
			
			gc.setFill(pieColors[i]);
			gc.fillArc(600, 150, 300, 300, begin, sweepAngle, ArcType.ROUND);
			
			gc.fillRect(150, 100 + i * 100, 100, 60);
			gc.setFill(Color.BLACK);
			gc.fillText(keys.get(i), 300, 125 + i * 100);
			
			begin += sweepAngle;
		}
	}
	
	public static void main(String[] args)
	{
		launch();
	}
}