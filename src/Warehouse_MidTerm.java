import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Warehouse_MidTerm extends Application
{


	final Random r = new Random();
	final Line path = new Line();
	final Text info_text = new Text();
	final StackPane stackpane = new StackPane();
	final Image winImage = new Image("file:///C:/Users/micha/IdeaProjects/Warehouse_2/src/party.png");
	final Image imagePlayer = new Image("file:///C:/Users/micha/IdeaProjects/Warehouse_2/src/triangle-32.gif");
	final ImageView winIcon = new ImageView();
	final FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), winIcon);
	final RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), winIcon);
	final FadeTransition fadeTransitionEnd = new FadeTransition(Duration.millis(800), winIcon);
	final ParallelTransition parallelTransition = new ParallelTransition();
	final Polyline poly = new Polyline();
	public int xbuffer;
	public int ybuffer;
	//final char[][] array = new char[9][9];
	char[][] array;
	int cost = 0;
	int fieldx = 0;
	int fieldy = 0;
	Line line1;
	Line line2;
	Line path1;
	Line path2;
	int[][] distance;
	ObservableList<Double> list = poly.getPoints();

	@Override
	public void start(Stage stage)
	{


		winIcon.setImage(winImage);
		winIcon.setFitHeight(200);
		winIcon.setPreserveRatio(true);
		winIcon.setVisible(false);
		winIcon.setX(150);
		winIcon.setY(150);


		fadeTransition.setFromValue(0);
		fadeTransition.setToValue(1.0f);
		fadeTransition.setCycleCount(1);
		fadeTransition.setAutoReverse(false);

		//rotateTransition.setByAngle(20);
		rotateTransition.setFromAngle(-20);
		rotateTransition.setToAngle(20);
		rotateTransition.setAxis(Rotate.Z_AXIS);
		rotateTransition.setCycleCount(4);
		rotateTransition.setAutoReverse(true);

		fadeTransitionEnd.setFromValue(1.0f);
		fadeTransitionEnd.setToValue(0);
		fadeTransitionEnd.setCycleCount(1);
		fadeTransitionEnd.setAutoReverse(false);
		parallelTransition.getChildren().addAll(fadeTransition, rotateTransition);

		//---------------------------------------


		// load the image
		ImageView robot = new ImageView();
		robot.setImage(imagePlayer);
		robot.setCache(true);
		robot.setFitHeight(30);
		//robot.setX(250-15);
		//robot.setY(250-15);
		robot.setPreserveRatio(true);
		path.setStroke(Color.DARKBLUE);
		path.setVisible(true);


		Circle circle = new Circle();
		circle.setRadius(15);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.RED);
		circle.setVisible(false);
		Circle circle_start = new Circle();
		circle_start.setFill(Color.BLACK);
		circle_start.setRadius(5);
		circle_start.setVisible(false);


		Pane pane = new Pane();
		Pane pane2 = new Pane();
		double HORIZ = 500;
		double VERTI = 500;
		//with = breite


		HBox hBoxText = new HBox();
		hBoxText.setSpacing(20);
		hBoxText.setAlignment(Pos.BASELINE_LEFT);
		hBoxText.setPadding(new Insets(10, 10, 10, 10));

		HBox hBoxTop = new HBox();
		hBoxTop.setSpacing(15);
		hBoxTop.setAlignment(Pos.BASELINE_CENTER);
		hBoxTop.setPadding(new Insets(20, 10, 10, 10));

		VBox vBox = new VBox();
		vBox.setFillWidth(false);

		VBox vBoxRight = new VBox();
		vBoxRight.setPadding(new Insets(10, 50, 10, 10));
		vBoxRight.setSpacing(10);
		vBoxRight.setAlignment(Pos.CENTER);

		BorderPane border = new BorderPane();
		border.setBottom(hBoxText);
		border.setCenter(stackpane);
		//border.setCenter(pane);
		border.setRight(vBoxRight);
		//border.setTop(hBoxTop);
		border.setTop(vBox);
		//border.setLeft(vBox);


		// Create a button
		Button bt_set_robot_position_ = new Button("Set Robot Position ");
		Button bt_createField = new Button("Create Field");
		Button bt_circle_ran = new Button("Circle Random ");
		Button bt_Find_path = new Button("Find path ");
		TextField x_coor_Robot = new TextField("5");
		TextField y_coor_Robot = new TextField("5");
		Text x_posinfo = new Text("Robot Field X");
		Text y_posinfo = new Text("Robot Field Y");


		Text x_Fieldsize = new Text("Field X Size ");
		Text y_Fieldsize = new Text("Field Y Size");
		TextField x_coorField = new TextField("9");
		TextField y_coorField = new TextField("9");
		//bt_reset.setMaxWidth(100);

		bt_Find_path.setDisable(true);
		bt_set_robot_position_.setDisable(true);
		bt_circle_ran.setDisable(true);
		robot.setVisible(false);
		x_coor_Robot.setDisable(true);
		y_coor_Robot.setDisable(true);

		x_coor_Robot.setPrefWidth(100);
		x_coor_Robot.setMaxWidth(120);
		y_coor_Robot.setPrefWidth(100);
		y_coor_Robot.setMaxWidth(120);
		x_coorField.setPrefWidth(100);
		x_coorField.setMaxWidth(200);
		y_coorField.setPrefWidth(100);
		y_coorField.setMaxWidth(200);

		//x_coor.setText(String.valueOf((int) robot.getX()+15));
		//y_coor.setText(String.valueOf((int) robot.getY()+15));


		//info_text.setText("X: " + ((int) robot.getX() + 15) + " Y: " + ((int) robot.getY() + 15));


		bt_set_robot_position_.setOnAction(e ->
		{
			xbuffer = 0;
			ybuffer = 0;
			winIcon.setVisible(false);
			pane.getChildren().removeAll(path1, path2);


			if ((!x_coor_Robot.getText().isEmpty()) & (!y_coor_Robot.getText().isEmpty()))
			{

				setCost(0);

				int x = Integer.parseInt(x_coor_Robot.getText());
				int y = Integer.parseInt(y_coor_Robot.getText());

				if ((x > 0) & (y <= getFieldy()))
				{
					x_coor_Robot.setStyle(null);
					y_coor_Robot.setStyle(null);

					x = (x * 50);
					y = (y * 50);

					System.out.println(x + " " + y);


					robot.setX(x - 15);
					robot.setY(y - 15);

					circle_start.setCenterX(robot.getX() + 15);
					circle_start.setCenterY(robot.getY() + 15);

					//robot.setX(250-15);
					//robot.setY(250-15);
					robot.setRotate(0);
					bt_Find_path.setDisable(false);
					bt_Find_path.setStyle("-fx-color:green");
					circle_start.setVisible(true);
					robot.setVisible(true);
					circle.setVisible(true);
					info_text.setText("X: " + ((int) robot.getX() + 15) + " Y: " + ((int) robot.getY() + 15));

				} else
				{
					System.out.println("erorr");

					//x_coor_Robot.setStyle("-fx-background-color: #ff0000; ");
					//y_coor_Robot.setStyle("-fx-background-color: #ff0000; ");


					x_coor_Robot.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");
					y_coor_Robot.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");

					x_coor_Robot.setPromptText("1");
					y_coor_Robot.setPromptText("1");

					x_coor_Robot.setFocusTraversable(false);
					y_coor_Robot.setFocusTraversable(false);

				}

			} else
			{
				x_coor_Robot.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");
				y_coor_Robot.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");


				x_coor_Robot.setPromptText("Canot be empty");
				y_coor_Robot.setPromptText("Canot be empty");

				x_coor_Robot.setFocusTraversable(false);
				y_coor_Robot.setFocusTraversable(false);
			}

		});

		bt_createField.setOnAction(e ->
		{
			winIcon.setVisible(false);
			//x_Fieldsize
			//y_Fieldsize.getText()


			if ((!x_coorField.getText().isEmpty()) & (!y_coorField.getText().isEmpty()))
			{

				int fieldx = Integer.parseInt(x_coorField.getText());
				int fieldy = Integer.parseInt(y_coorField.getText());

				System.out.println(fieldx + " " + fieldy);


				if (((fieldx) & (fieldy)) != 0)
				{
					x_coorField.setStyle(null);
					y_coorField.setStyle(null);

					array = new char[fieldy][fieldx];
					drawField(pane2, fieldx, fieldy);
					setFieldx(fieldx);
					setFieldy(fieldy);
					circle.setCenterX(ranX());
					circle.setCenterY(ranY());
					bt_set_robot_position_.setDisable(false);
					x_coor_Robot.setDisable(false);
					y_coor_Robot.setDisable(false);
					bt_circle_ran.setDisable(false);

				} else
				{

					x_coorField.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");
					y_coorField.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");
					x_coorField.setPromptText("Size != 0");
					y_coorField.setPromptText("Size  != 0");
					x_coorField.clear();
					y_coorField.clear();
				}

			} else
			{

				//x_coorField.setStyle("-fx-background-color: #ff0000; ");
				//y_coorField.setStyle("-fx-background-color: #ff0000; ");
				x_coorField.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");
				y_coorField.setStyle("  -fx-text-box-border: red ; -fx-focus-color: red ;");

				x_coorField.setPromptText("Canot be empty");
				y_coorField.setPromptText("Canot be empty");

				x_coorField.setFocusTraversable(false);
				y_coorField.setFocusTraversable(false);


			}


		});


		bt_circle_ran.setOnAction(e ->
		{
			//circle.setCenterX(150);
			//circle.setCenterY(50);

			circle.setCenterX(ranX());
			circle.setCenterY(ranY());
			circle.setVisible(true);

		});

		bt_Find_path.setOnAction(e ->
		{
			winIcon.setVisible(false);
			xbuffer = 0;
			ybuffer = 0;
			setCost(0);
			//setRobotXABSOLUTE(robot.getX()+15);
			circle_start.setCenterX(robot.getX() + 15);
			circle_start.setCenterY(robot.getY() + 15);
			makeArray(circle, robot);
			findDistance(array);
			System.out.println("X: " + (robot.getX() + 15) + " Y: " + (robot.getY() + 15));
			searchPath(robot, 0, path, pane, circle_start, 0, (int) (robot.getY() + 15) / 50 - 1, (int) (robot.getX() + 15) / 50 - 1);
			//find_path(circle,robot);


		});


		pane.getChildren().addAll(circle, circle_start, robot, path, poly, winIcon);
		hBoxTop.getChildren().addAll(x_posinfo, x_coor_Robot, y_posinfo, y_coor_Robot, bt_set_robot_position_, bt_Find_path);
		hBoxText.getChildren().addAll(x_Fieldsize, x_coorField, y_Fieldsize, y_coorField, bt_createField, bt_circle_ran);
		vBox.getChildren().addAll(hBoxTop, hBoxText);
		vBoxRight.getChildren().addAll(info_text);

		stackpane.getChildren().addAll(pane, pane2);


		Scene scene2 = new Scene(border, HORIZ + 150, VERTI + 100); // horizon , vertik
		stage.setTitle("Warehouse ");
		stage.setScene(scene2);
		stage.show();


	}


	//public void movement(ImageView robot, int x,int y, Text info_text)


	public void drawField(Pane pane2, int x, int y)
	{

		//pane.getChildren().removeAll(line1,line2);
		pane2.getChildren().clear();

		x = x * 50;
		y = y * 50;

		for (int i = 50; i <= x; i += 50)
		{

			line1 = new Line(i, 50, i, y);
			//line1.setStroke(Color.BLUE);
			line1.setStroke(Color.LIGHTGRAY);
			//line1.setOpacity(25);
			pane2.getChildren().add(line1);
			pane2.getChildren().add(new Text(i, 30, String.valueOf((i / 50))));


		}

		for (int i = 50; i <= y; i += 50)
		{


			line2 = new Line(50, i, x, i);
			//line2.setStroke(Color.RED);
			line2.setStroke(Color.LIGHTGRAY);
			//line2.setOpacity(25);
			pane2.getChildren().add(line2);
			pane2.getChildren().add(new Text(5, i, String.valueOf((i / 50))));
		}


	}


	public void movementX(ImageView robot, int x, int y)
	{
		int distanceX = x * 50;
		int distanceY = y * 50;

		int curX = (int) (robot.getX() + 15);
		int curY = (int) (robot.getY() + 15);
		System.out.println("\n------Movement X -----");
		//System.out.println("DistanceX "+distanceX+" |  DistanceY "+distanceY);
		//System.out.println("curX "+curX+" |  curY "+curY);

		PathTransition mover = new PathTransition();
		Line pathAnimation = new Line();


		pathAnimation.setStartX(robot.getX() + 15);
		pathAnimation.setStartY(robot.getY() + 15);


		if (distanceX > 0)    //right
		{
			rotate(robot, 90);
			cost = cost + 1;
			System.out.println("Moving Right " + x + "x   Distance is: " + distanceX);
			//info_text.setText("Moving "+x+"x   Distance is: "+distanceX);
			System.out.println("Moving to X " + (curX + distanceX));
			mover.setOnFinished(e -> movementY(robot, x, y));
			pathAnimation.setEndX(curX + distanceX);
			pathAnimation.setEndY(robot.getY() + 15);
			mover.setDuration(Duration.millis(1500));
			mover.setPath(pathAnimation);
			//mover.setPath(poly);
			mover.setNode(robot);
			mover.play();
			robot.setX((curX + distanceX - 15));
			robot.setY(curY - 15);
			curX = (int) (robot.getX() + 15);
			//curY= (int) (robot.getY()+15);

			System.out.print("CurX: " + curX + "  ");
			System.out.println("CurY: " + curY);
			//System.out.println("X: "+(robot.getX()+15)+" Y: "+(robot.getY()+15));


		} else if (distanceX < 0) //Left
		{
			//distanceX= Math.abs(distanceX);

			rotate(robot, -90);
			cost = cost + 1;
			pathAnimation.setEndX(curX + distanceX);
			pathAnimation.setEndY(robot.getY() + 15);
			mover.setDuration(Duration.millis(1500));
			mover.setPath(pathAnimation);
			//mover.setPath(poly);
			mover.setNode(robot);
			mover.setOnFinished(e -> movementY(robot, x, y));
			mover.play();

			System.out.println("Moving Left " + x + "x   Distance is: " + distanceX);
			//info_text.setText("Moving "+x+"x   Distance is: "+distanceX);


			robot.setX((curX + distanceX - 15));
			//robot.setY(curY-15);

			curX = (int) (robot.getX() + 15);
			curY = (int) (robot.getY() + 15);

			System.out.print("CurX: " + curX + "  ");
			System.out.println("CurY: " + curY);

			System.out.println("X: " + (robot.getX() + 15) + " Y: " + (robot.getY() + 15));


		} else movementY(robot, x, y);


	}


	public void movementY(ImageView robot, int x, int y)
	{

		int distanceX = x * 50;
		int distanceY = y * 50;

		int curX = (int) (robot.getX() + 15);
		int curY = (int) (robot.getY() + 15);
		System.out.println("\n------Movement Y -----");

		PathTransition mover = new PathTransition();
		Line pathAnimation = new Line();
		mover.setOnFinished(e -> win(winIcon));
		pathAnimation.setStartX(curX);
		pathAnimation.setStartY(curY);


		if (distanceY > 0)  //Down
		{
			rotate(robot, 180);
			cost = cost + 1;
			System.out.println("\nMoving Down " + y + "x   Distance is: " + distanceY);

			pathAnimation.setEndX(robot.getX() + 15);
			pathAnimation.setEndY(curY + distanceY);
			mover.setDuration(Duration.millis(1500));
			mover.setPath(pathAnimation);
			//mover.setPath(poly);
			mover.setNode(robot);
			mover.play();

			robot.setY((curY + distanceY - 15));
			//robot.setX(curX-15);

			curX = (int) (robot.getX() + 15);
			curY = (int) (robot.getY() + 15);

			System.out.print("CurX: " + curX + "  ");
			System.out.println("CurY: " + curY);


		} else if (distanceY < 0)  //Up
		{
			rotate(robot, 0);
			cost = cost + 1;
			distanceY = Math.abs(distanceY);
			System.out.println("Moving Up " + y + "x   Distance is: -" + distanceY);
			//info_text.setText("Moving "+y+"x   Distance is: "+distanceY);

			pathAnimation.setStartX(robot.getX() + 15);
			pathAnimation.setStartY(robot.getY() + 15);

			pathAnimation.setEndX(robot.getX() + 15);
			pathAnimation.setEndY(curY - distanceY);
			mover.setDuration(Duration.millis(1500));
			mover.setPath(pathAnimation);
			//mover.setPath(poly);
			mover.setNode(robot);
			mover.play();
			robot.setY((curY - distanceY - 15));
			//robot.setX(curX-15);


			curX = (int) (robot.getX() + 15);
			curY = (int) (robot.getY() + 15);

			System.out.print("CurX: " + curX + "  ");
			System.out.println("CurY: " + curY);

		} else win(winIcon);


	}


	void rotate(ImageView robot, int steps)
	{
		cost = cost + 1;
		if (steps > 360) steps = 0;
		robot.setRotate(steps);

	}

	private double ranX()
	{
		return (r.nextInt((getFieldx() - 1)) + 1) * 50;
	}

	private double ranY()
	{
		return (r.nextInt((getFieldy() - 1)) + 1) * 50;
	}


	void drawPath(@NotNull Pane pane, @NotNull Circle circle_start)
	{
		System.out.println("\n----DrawPath---");


		pane.getChildren().removeAll(path1, path2);

		path1 = new Line();
		path2 = new Line();
		path1.setStroke(Color.BLUE);
		path2.setStroke(Color.BLUE);
		pane.getChildren().addAll(path1, path2);
		int distanceX = xbuffer * 50;
		int distanceY = ybuffer * 50;
		//System.out.println("DistanceX "+distanceX+" |  DistanceY "+distanceY);
		int startx = (int) circle_start.getCenterX();
		int starty = (int) circle_start.getCenterY();

		path1.setStartX(startx);
		path1.setStartY(starty);
		path1.setEndX(startx + distanceX);
		path1.setEndY(path1.getStartY());
		path2.setStartX(path1.getEndX());
		path2.setStartY(path1.getEndY());
		path2.setEndX(path2.getStartX());
		path2.setEndY(starty + distanceY);
	}


	void searchPath(ImageView robot, int mov, Line path, Pane pane, Circle circle_start, int trys, int ry, int rx)
	{

		//System.out.println("ADD TO LIST: rx "+(rx*50)+" ry"+(ry*50));

		//list.add((double) (rx*50+50));
		//list.add((double) (ry*50+50));


		if (trys == 0)
		{
			System.out.println("Line Cleard");
			//pane.getChildren().remove(path);
		}

		//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
		System.out.println("\n-------------------Try: " + trys + "--------------------------------");
		//System.out.println("\nTrys: "+trys);
		if (trys >= 1000) return;


		array[ry][rx] = 'R';
		print_array();

		boolean exit = false;
		int dist = distance[ry][rx];
		int distleft = Integer.MAX_VALUE;
		int distbottom = Integer.MAX_VALUE;
		int disttop = Integer.MAX_VALUE;
		int distright = Integer.MAX_VALUE;

		//Robot Koordinaten

		System.out.println(dist);
		System.out.print("Robo Current Field  --> ");
		System.out.println("X: " + (rx * 50) + " Y: " + (ry * 50));
		System.out.print("Robo Current Array Field  --> ");
		System.out.println("X: " + rx + " Y: " + ry);

		//System.out.println("RX: "+rx+" Calc :"+rx*50+35 );
		//System.out.println("RY: "+ry+" Calc: "+ry*50+35 );

		//path.setEndX((rx*50));
		//path.setEndY((ry*50));

		//path.setStartX((rx*50));
		//path.setStartY((ry*50));

		System.out.println(array.length);
		System.out.println(array[0].length);

		System.out.print("Path Start: X" + path.getStartX() + " Y " + path.getStartY() + "  ");
		System.out.println("Path End: X" + path.getEndX() + " Y " + path.getEndY());
		array[ry][rx] = 'R';


		if (dist == 0)
		{

			System.out.println("\n---------Finish------------");
			System.out.println("Trys: " + trys);
			System.out.println("NO SEARCH BEHINDE THIS POINT\n");
			System.out.println("Start Movement");
			drawPath(pane, circle_start);
			System.out.println("X Buff: " + xbuffer);
			System.out.println("Y Buff: " + ybuffer);

			movementX(robot, xbuffer, ybuffer);

			//robot.setX((rx*50+35));
			//robot.setY((ry*50+35));


			exit = true;
		}


		if (exit) return;

		//path.setEndX((rx*50));
		//path.setEndY((ry*50));

		System.out.println("\n");


		System.out.println("Distance Current Field " + dist);


		if (rx > 0)
		{

			distleft = distance[ry][rx - 1];
			//System.out.println("Distance Left Field " + distleft);

		}
		if (rx < (getFieldx() - 1))
		{
			distright = distance[ry][rx + 1];
			//System.out.println("Distance Right Field " + distright);

		}
		if (ry < (getFieldy() - 1))
		{
			distbottom = distance[ry + 1][rx];
			//System.out.println("Distance Bottom Field " + distbottom);

		}
		if (ry > 0)
		{

			disttop = distance[ry - 1][rx];
			//System.out.println("Distance Top Field " + disttop);
		}

		System.out.println("Distance Left Field " + distleft);
		System.out.println("Distance Right Field " + distright);
		System.out.println("Distance Bottom Field " + distbottom);
		System.out.println("Distance Top Field " + disttop);


		int leftORright = Integer.compare(distright, distleft);
		int leftORbottom = Integer.compare(distbottom, distleft);
		int leftORtop = Integer.compare(disttop, distleft);


		int topORbottom = Integer.compare(distbottom, disttop);


		int bottomORtop = Integer.compare(disttop, distbottom);


		int rightORtop = Integer.compare(disttop, distright);
		int rightORbottom = Integer.compare(distbottom, distright);

		/*
		 System.out.println("Left&Right "+ leftORright);        // -1 Right
		 System.out.println("Left&Bottom "+ leftORbottom);       //-1 Bottom
		 System.out.println("Left&Top "+ leftORtop);             //-1 top
		 System.out.println("Right&Top "+ rightORtop);           //-1 top
		 System.out.println("Right&Bottom "+ rightORbottom);     //-1 bottom
		 System.out.println("Top&Bottom "+ topORbottom);         //-1 bottom
		 System.out.println("Bottom&Top "+ bottomORtop);         //-1 Up
		 */


		//Direction Probability
		System.out.println("\nDIRECTION PROBABILITY WEIGHT");
		System.out.println("Value Left: " + (leftORbottom + leftORright + leftORtop));
		System.out.println("Value Right: " + (rightORbottom + rightORtop + leftORright));
		System.out.println("Value Top: " + (topORbottom));
		//System.out.println("Value Top: "+(topORbottom+leftORtop+rightORtop));
		//System.out.println("Value Bottom: "+(bottomORtop+leftORbottom+rightORbottom));
		System.out.println("Value Bottom: " + (bottomORtop));


		int valueleft = (leftORbottom + leftORright + leftORtop);
		int valueright = (rightORbottom + rightORtop + leftORright);
		//int valuetop = topORbottom;
		//int valuebottom = bottomORtop;


		//int direction = Integer.compare(leftORright,topORbottom);

		//System.out.println("Direction: "+direction);

		if ((valueleft > 0) & (distleft != -1))
		{

			System.out.println("Going Left");
			array[ry][rx] = 'O';
			xbuffer = xbuffer - 1;


			searchPath(robot, mov + 1, path, pane, circle_start, trys + 1, ry, rx - 1);

		} else if (valueright > 0)
		{


			System.out.println("Going right");
			array[ry][rx] = 'O';
			xbuffer = xbuffer + 1;
			searchPath(robot, mov + 1, path, pane, circle_start, trys + 1, ry, rx + 1);

		} else if (topORbottom > 0)
		{
			System.out.println("Going Up");
			array[ry][rx] = 'O';
			ybuffer = ybuffer - 1;
			searchPath(robot, mov + 1, path, pane, circle_start, trys + 1, ry - 1, rx);
		} else if (bottomORtop > 0)
		{

			System.out.println("Going Down");
			array[ry][rx] = 'O';
			ybuffer = ybuffer + 1;
			searchPath(robot, mov + 1, path, pane, circle_start, trys + 1, ry + 1, rx);
		}

		//System.out.println("No Path?");
		//drawPath(robot,pane,circle_start);

	}


	void makeArray(Circle circle, ImageView robot)
	{

		for (int i = 0; i < getFieldy(); i++)
		{
			for (int j = 0; j < getFieldx(); j++)
			{
				array[i][j] = 'O';
			}
		}

		int cx = (int) circle.getCenterX();
		int cy = (int) circle.getCenterY();
		int rx = (int) robot.getX() + 15;
		int ry = (int) robot.getY() + 15;


		rx = (rx / 50) - 1;
		ry = (ry / 50) - 1;

		cx = (cx / 50) - 1;
		cy = (cy / 50) - 1;

		System.out.print("CX: " + cx + " ");
		System.out.print("CY: " + cy + " | ");
		System.out.print("RX: " + rx + " ");
		System.out.println("RY: " + ry);

		array[cy][cx] = 'G';
		//array[4][2]= 'W';

		//System.out.println("   1 2 3 4 5 6 7 8");

		//print_array();


	}

	//Search Magic   !! dont touch it !!
	void findDistance(char[][] matrix)
	{

		System.out.println("DISTANCE MAP");
		distance = new int[matrix.length][matrix[0].length];
		Queue<Guard_Companion> q = new LinkedList<>();
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[0].length; j++)
			{
				if (matrix[i][j] == 'G')
				{
					distance[i][j] = 0;
					Guard_Companion companion1 = new Guard_Companion();
					companion1.distance = 0;
					companion1.i = i;
					companion1.j = j;
					q.offer(companion1);
				} else
				{
					distance[i][j] = -1;
				}
			}

		}

		while (!q.isEmpty())
		{
			Guard_Companion companion2 = q.peek();
			int i = companion2.i;
			int j = companion2.j;
			int dist = companion2.distance;
			int[] rowNeighbour = {0, 0, 1, -1};
			int[] columnNeighbour = {1, -1, 0, 0};
			for (int k = 0; k < 4; k++)
			{
				if (isSafe(i + rowNeighbour[k], j + columnNeighbour[k], distance, matrix))
				{
					distance[i + rowNeighbour[k]][j + columnNeighbour[k]] = dist + 1;
					Guard_Companion companion3 = new Guard_Companion();
					companion3.i = i + rowNeighbour[k];
					companion3.j = j + columnNeighbour[k];
					companion3.distance = dist + 1;
					q.offer(companion3);
				}

			}
			q.poll();
		}

		for (int[] ints : distance)
		{
			for (int j = 0; j < distance[0].length; j++)
			{
				System.out.printf("%3.5s", ints[j]);
				//System.out.print(ints[j] + " ");
			}
			System.out.println();
		}
	}

	boolean isSafe(int row, int column, int[][] distance, char[][] matrix)
	{
		return row < matrix.length && row >= 0 && column < matrix[0].length && column >= 0 && distance[row][column] == -1 && matrix[row][column] == 'O';
	}

	//Now you may touch it

	void print_array()
	{
		System.out.println("   0 1 2 3 4 5 6 7 8");

		for (int i = 0; i < getFieldy(); i++)
		{
			//System.out.print(i+1+" ");
			System.out.print(i + " ");
			System.out.print("|");

			for (int j = 0; j < getFieldx(); j++)
			{
				System.out.print(array[i][j]);
				System.out.print("|");
			}
			System.out.print("\n");
		}
		System.out.println("\n");


	}

	void win(ImageView party)
	{
		party.setVisible(true);


		parallelTransition.play();

		parallelTransition.setOnFinished(e -> fadeTransitionEnd.play());
		//rotateTransition.play();
		info_text.setText("Finish\n Cost " + cost);

	}


	public int getFieldx()
	{
		return fieldx;
	}

	public void setFieldx(int fieldx)
	{
		this.fieldx = fieldx;
	}

	public int getFieldy()
	{
		return fieldy;
	}

	public void setFieldy(int fieldy)
	{
		this.fieldy = fieldy;
	}

	public void setCost(int cost)
	{
		this.cost = cost;
	}

	static class Guard_Companion
	{
		int i;
		int j;
		int distance;
	}

}
