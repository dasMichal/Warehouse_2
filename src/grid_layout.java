import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class grid_layout extends Application
{


	public int xbuffer;
	public int ybuffer;

	public int movmentX;
	public int movmentY;
	 int MinX =50;
	 int MinY =50;
	 int MaxX =450;
	 int MaxY =450;
	char[][] array = new char[9][9];
	int[][] distance;
	Random r = new Random();


	@Override
	public void start(Stage stage) throws Exception
	{

		// load the image
		Image image = new Image("file:///C:/Users/micha/IdeaProjects/Warehouse_2/src/triangle-32.gif");
		ImageView robot = new ImageView();
		robot.setImage(image);
		robot.setCache(true);
		robot.setFitHeight(30);
		robot.setX(200-15);
		robot.setY(200-15);
		robot.setPreserveRatio(true);

		Circle circle = new Circle();
		Circle circle_start = new Circle();
		circle.setCenterX(ran());
		circle.setCenterY(ran());
		circle.setRadius(15);
		circle.setStroke(Color.BLACK);
		circle.setFill(Color.RED);
		circle_start.setFill(Color.BLACK);
		circle_start.setRadius(5);
		circle_start.setCenterX(robot.getX()+15);
		circle_start.setCenterY(robot.getY()+15);


		Pane pane = new Pane();
		double HORIZ=500;
		double VERTI =500;
		//with = breite

		HBox hBox1 = new HBox(5);
		hBox1.setSpacing(80);

		HBox hBox2 = new HBox();
		hBox2.setSpacing(33);

		VBox vBox = new VBox();
		vBox.setSpacing(20);
		vBox.setAlignment(Pos.CENTER);
		
		VBox vBox2 = new VBox();
		vBox2.setSpacing(33);
		
		BorderPane border = new BorderPane();
		border.setBottom(hBox1);
		border.setCenter(pane);
		border.setRight(vBox);
		border.setTop(hBox2);
		border.setLeft(vBox2);


		// Create a button
		Button bt_left = new Button("Left");
		Button bt_right = new Button("Right");
		Button bt_up = new Button("up");
		Button bt_down = new Button("Down");
		Button bt_turnR = new Button("Turn Right");
		Button bt_turnL = new Button("Turn Left");
		Button bt_reset = new Button("Reset");
		Button bt_movement = new Button("Move ");
		Button bt_circle_ran = new Button("Circle Random ");
		Button bt_Find_path = new Button("Find path ");
		TextField x_coor = new TextField("X_Coordinaten");
		TextField y_coor = new TextField("Y_Coordinaten");
		TextField move_count = new TextField("1");
		Text info_text = new Text();
		x_coor.setPrefWidth(80);
		x_coor.setMaxWidth(80);
		y_coor.setPrefWidth(80);
		y_coor.setMaxWidth(80);
		x_coor.setText(String.valueOf((int) robot.getX()+15));
		y_coor.setText(String.valueOf((int) robot.getY()+15));
		info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		/**
		for (int i = 0; i <= 400; i+=50)
		{
			//System.out.println(i);
			Text text1 = new Text(String.valueOf(i));
			Text text2 = new Text(String.valueOf(i));
			Line line1 = new Line(i, 0, i, VERTI);
			Line line2 = new Line(0, i, HORIZ, i);
			pane.getChildren().addAll(line1,line2);
			hBox2.getChildren().add(text1);
			vBox2.getChildren().add(text2);
		}
		
		**/

		for (int i = 50; i <= 450 ; i+=50)
		{
			Line line1 = new Line(i, 50, i, 450);
			//line1.setStroke(Color.BLUE);
			line1.setStroke(Color.LIGHTGRAY);
			line1.setOpacity(25);
			pane.getChildren().add(line1);

		}

		for (int i = 50; i <=450 ; i+=50)
		{

			Line line2 = new Line(50, i, 450, i);
			//line2.setStroke(Color.RED);
			line2.setStroke(Color.LIGHTGRAY);
			line2.setOpacity(25);
			pane.getChildren().add(line2);
		}

		bt_left.setOnAction(e ->
		{
			/**
			double test = robot.getX()-50;
			if(test > 50)
			{
				robot.setX(robot.getX()-50);
			}
			 **/
			//movement(robot,-1,0,info_text);
			movement(robot,-1,0, circle_start);
			info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
		});

		bt_right.setOnAction(e ->
		{
			/**
			double test = robot.getX()+50;
			if(test <= 450)  //375
			{

				robot.setX(robot.getX()+50);
			}
			 **/
			movement(robot,1,0,circle_start);

			info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
		});

		bt_down.setOnAction(e ->
		{
			//double test = robot.getY()+50;
			//System.out.println("TEST Down: "+test);
			movement(robot,0,1, circle_start);
			/**
			if(test <= 435)
			{
				PathTransition pt = new PathTransition(Duration.millis(1000), new Line(robot.getX()+15, robot.getY()+15, robot.getX()+15, robot.getY()+65), robot);
				//pt.setCycleCount(Timeline.INDEFINITE);
				pt.play(); // Start animation
				robot.setY(robot.getY()+50);
			}else System.out.println("Nope");
			 **/
			info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
		});

		bt_up.setOnAction(e ->
		{

			movement(robot,0,-1, circle_start);
			/**
			double test = robot.getY()-50;
			if(test >=35)
			{
				PathTransition pt = new PathTransition(Duration.millis(1000), new Line(robot.getX()+15, robot.getY()+15, robot.getX()+15, robot.getY()-35), robot);
				//pt.setCycleCount(Timeline.INDEFINITE);
				pt.play(); // Start animation
				robot.setY(robot.getY()-50);
			}
			 **/
			info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		});

		bt_turnL.setOnAction(e ->
		{

			robot.setRotate(robot.getRotate()-90);



		});

		bt_turnR.setOnAction(e ->
		{

			robot.setRotate(robot.getRotate()+90);

		});

		bt_reset.setOnAction(e ->
		{

			robot.setX(200-15);
			robot.setY(200-15);
			robot.setRotate(0);
			info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		});

		bt_movement.setOnAction(e ->
		{
			int y = Integer.parseInt(move_count.getText());
			int x=0;
			movement(robot,x,y, circle_start);
			x_coor.setText(String.valueOf(robot.getX()+15));
			y_coor.setText(String.valueOf(robot.getY()));

		});

		bt_circle_ran.setOnAction(e ->
		{
			circle.setCenterX(ran());
			circle.setCenterY(ran());
			//makeArray(circle,robot);
			//findDistance(array);
			//find_path(circle,robot);

		});

		bt_Find_path.setOnAction(e ->
		{

			circle_start.setCenterX(robot.getX()+15);
			circle_start.setCenterY(robot.getY()+15);

			makeArray(circle,robot);
			findDistance(array);

			searchPath(robot,0, pane,circle_start, 0,(int) (robot.getX()+15)/50-1,(int) (robot.getX()+15)/50-1);

			//find_path(circle,robot);
		});


		vBox.getChildren().addAll(x_coor,y_coor,bt_turnL,bt_turnR,bt_left,bt_right,bt_down,bt_up,bt_reset,bt_circle_ran);
		//hBox1.getChildren().addAll(bt_left,bt_right,bt_down,bt_up);
		hBox1.getChildren().addAll(move_count,bt_movement,bt_Find_path);
		hBox2.getChildren().addAll(info_text);
		pane.getChildren().addAll(circle,circle_start,robot);


		//Scene scene = new Scene(pane,HORIZ,VERTI); // horizon , vertik
		Scene scene2 = new Scene(border,HORIZ+150,VERTI+100); // horizon , vertik
		stage.setTitle("Warehouse");
		stage.setScene(scene2);
		stage.show();

		//makeArray(circle,robot);
		//findDistance(array);
		//searchPath(robot,0, pane,circle_start, 0,(int) (robot.getX()+15)/50-1,(int) (robot.getX()+15)/50-1);
		//find_path(circle,robot);


	}




	//public void movement(ImageView robot, int x,int y, Text info_text)
	public void movement(ImageView robot, int x, int y, Circle circle_start)
	{

		System.out.println("In Movement funtion");

		int distanceX = x * 50;
		int distanceY = y * 50;

		if (distanceX>0)    //right
		{
			rotate(robot,90);

			System.out.println("Moving Right "+x+"x   Distance is: "+distanceX);
			//info_text.setText("Moving "+x+"x   Distance is: "+distanceX);

			PathTransition pt = new PathTransition(Duration.millis(2900), new Line(robot.getX()+15, robot.getY()+15, robot.getX()+distanceX+15, robot.getY()+15), robot);
			//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			//pt.setCycleCount(Timeline.INDEFINITE);
			pt.play(); // Start animation

			//robot.setX(robot.getX()+distanceX);

			//info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		}else if (distanceX<0) //Left
		{
			distanceX= Math.abs(distanceX);

			rotate(robot,-90);


			System.out.println("Moving Left "+x+"x   Distance is: -"+distanceX);
			//info_text.setText("Moving "+x+"x   Distance is: "+distanceX);

			PathTransition pt = new PathTransition(Duration.millis(2500), new Line(robot.getX()+15, robot.getY()+15,  robot.getX()-distanceX+15, robot.getY()+15), robot);
			//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			//pt.setCycleCount(Timeline.INDEFINITE);
			pt.play(); // Start animation

			//robot.setX(robot.getX()-distanceX);
			//info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		}


		if (distanceY >0)  //Down
		{
			rotate(robot,180);

			System.out.println("Moving Up "+y+"x   Distance is: "+distanceY);
			//info_text.setText("Moving "+y+"x   Distance is: "+distanceY);
			//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

			PathTransition pt = new PathTransition(Duration.millis(2500), new Line(robot.getX()+15, robot.getY()+15,  robot.getX()+15, robot.getY()+distanceY+15), robot);
			//pt.setCycleCount(Timeline.INDEFINITE);
			pt.play(); // Start animation

			//robot.setY(robot.getY()+distanceY);
			//info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		}else if (distanceY <0)  //Up
		{
			rotate(robot,0);
			distanceY = Math.abs(distanceY);
			System.out.println("Moving Up "+y+"x   Distance is: -"+distanceY);
			//info_text.setText("Moving "+y+"x   Distance is: "+distanceY);

			PathTransition pt = new PathTransition(Duration.millis(2500), new Line(robot.getX()+15, robot.getY()+15,  robot.getX()+15, robot.getY()-distanceY+15), robot);
			//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			//pt.setCycleCount(Timeline.INDEFINITE);
			pt.play(); // Start animation

			//robot.setY(robot.getY()-distanceY);
			//info_text.setText("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		}

	}
	void rotate(ImageView robot,int steps)
	{

		if (steps >360) steps = 0;
		robot.setRotate(steps);

	}

	public double ran()
	{

		int low = 1;
		int high = 8;
		//int result = r.nextInt(high-low) + low;
		int rand = (r.nextInt(8) + 1) * 50; // 100, 200, 300


		//int rand = (int) (Math.random() * 50)+25;
		//double rand = (Math.random() * 500)+25;
		//System.out.println(ran);
		//System.out.println(rand);
		return rand;
	}



	void searchPath(ImageView robot, int mov, Pane pane, Circle circle_start, int trys, int ry, int rx)
	{
		Line path = new Line();
		pane.getChildren().add(path);
		if (trys == 0)
		{
			System.out.println("Line Cleard");
			pane.getChildren().remove(path);

		}

		//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
		System.out.println("\n----------------------------------------------------------");
		System.out.println("\nTrys: "+trys);
		if (trys>=500) return;


		boolean exit = false;
		int dist = distance[ry][rx];
		int distleft = Integer.MAX_VALUE;
		int distbottom = Integer.MAX_VALUE;
		int disttop= Integer.MAX_VALUE;
		int distright= Integer.MAX_VALUE;

		//Robot Koordinaten
		int rx1 = (int) robot.getX()+15;
		int ry1= (int) robot.getY()+15;

		rx1= (rx1/50)-1;
		ry1= (ry1/50)-1;


		//System.out.println("RX: "+rx+" Calc :"+rx*50+35 );
		//System.out.println("RY: "+ry+" Calc: "+ry*50+35 );

		path.setStartX(robot.getX()+15);
		path.setStartY(robot.getY()+15);
		path.setStroke(Color.DARKBLUE);

		robot.setX(rx*50+35);
		robot.setY(ry*50+35);

		path.setEndX(robot.getX()+15);
		path.setEndY(robot.getY()+15);
		array[ry][rx]= 'R';


		if (dist <= 0)
		{
			System.out.println("\n---------Finish------------");

			System.out.println("X Buff: "+xbuffer);
			System.out.println("Y Buff: "+ybuffer);
			//movement(robot,xbuffer,0, circle_start);
			robot.setX(rx1*50+35);
			//movement(robot,0,ybuffer, circle_start);
			robot.setX(rx1*50+35);

			//movement(robot,xbuffer-1,0, circle_start);
			//movement(robot,0,ybuffer-1, circle_start);
			
			//robot.setX(rx1*50+35);
			//robot.setY(ry1*50+35);


			//System.out.println("Movements: "+mov);
			System.out.println("Trys: "+trys);
			//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));
			System.out.println("NO SEARCH BEHINDE THIS POINT\n");
			exit=true;
		}
		
		if(exit) return;
		

		//System.out.println("X: "+((int) robot.getX()+15)+" Y: "+((int) robot.getY()+15));

		//System.out.println(rx +" "+ry);
		//System.out.println(rx1 +" "+ry1);

		System.out.println("\n");
		print_array();




		System.out.println("Distance Current Field "+dist);


		if (rx > 0)
		{

			distleft = distance[ry][rx - 1];
			System.out.println("Distance Left Field " + distleft);
			if (distleft==0)
			{
				System.out.println("Fastlane Left");
				array[ry][rx]= 'O';
				xbuffer=xbuffer-1;
				searchPath(robot,mov+1, pane, circle_start, trys+1,ry, rx-1);
				}

		}
		if (rx < 8)
		{
			distright = distance[ry][rx + 1];
			System.out.println("Distance Right Field " + distright);
			if (distright==0)
			{
				System.out.println("Fastlane right");
				array[ry][rx]= 'O';
				xbuffer=xbuffer+1;
				searchPath(robot,mov+1, pane, circle_start, trys+1,ry, rx+1);

			}



		}
		if (ry < 8)
		{

			distbottom = distance[ry + 1][rx];
			System.out.println("Distance Bottom Field " + distbottom);
			if (distbottom==0)
			{
				System.out.println("Fastlane Down");
				array[ry][rx]= 'O';
				ybuffer=ybuffer+1;
				searchPath(robot,mov+1, pane, circle_start, trys+1,ry+1, rx);
			}



		}
		if (ry > 0)
		{

			disttop = distance[ry - 1][rx];
			System.out.println("Distance Top Field " + disttop);
			if (disttop==0)
			{
				System.out.println("Fastlane Up");
				array[ry][rx]= 'O';
				ybuffer=ybuffer-1;
				searchPath(robot,mov+1, pane, circle_start, trys+1,ry-1, rx);
			}


		}





		int leftorright = Integer.compare(distright,distleft);
		int toporbottom = Integer.compare(disttop,distbottom);
		System.out.println("Left&Right "+ leftorright);
		System.out.println("Top&Bottom "+ toporbottom);

		int direction = Integer.compare(leftorright,toporbottom);

		System.out.println("Direction: "+direction);

		if (leftorright<0)
		{
			System.out.println("Going right");
			array[ry][rx]= 'O';
			xbuffer=xbuffer+1;
			searchPath(robot,mov+1, pane, circle_start, trys+1,ry, rx+1);
			//searchPath(robot, pane, trys+1,ry+1, rx);
		}else if (leftorright>0)
		{
			System.out.println("Going Left");
			array[ry][rx]= 'O';
			xbuffer=xbuffer-1;
			searchPath(robot,mov+1, pane, circle_start, trys+1,ry, rx-1);
			//searchPath(robot, pane, trys+1,ry-1, rx);
		}else if (toporbottom<0)
		{
			System.out.println("Going Up");
			array[ry][rx]= 'O';
			ybuffer=ybuffer-1;
			searchPath(robot,mov+1, pane, circle_start, trys+1,ry-1, rx);
			//searchPath(robot, pane, trys+1,ry, rx-1);
		}else if (toporbottom>0)
		{
			System.out.println("Going Down");
			array[ry][rx]= 'O';
			ybuffer=ybuffer+1;
			searchPath(robot,mov+1, pane, circle_start, trys+1,ry+1, rx);
			//searchPath(robot, pane, trys+1,ry, rx+1);
		}
		//03:39 pm    AHHHHHHHHHHHHHHHHHHHHHHHHHHH


	}

	void check(int A,int B)
	{
		if (A<B)
		{


		}


	}


	void makeArray(Circle circle, ImageView robot)
	{

		for (int i = 0; i < 9; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				array[i][j] = 'O';
			}
		}

		int cx = (int) circle.getCenterX();
		int cy = (int) circle.getCenterY();
		int rx = (int) robot.getX()+15;
		int ry = (int) robot.getY()+15;


		rx= (rx/50)-1;
		ry= (ry/50)-1;

		cx= (cx/50)-1;
		cy= (cy/50)-1;

		System.out.println("CX: "+cx);
		System.out.println("CY: "+cy);
		System.out.println("RX: "+rx);
		System.out.println("RY: "+ry);

		array[cy][cx]= 'G';


		//System.out.println("   1 2 3 4 5 6 7 8");

		print_array();


	}


	void findDistance(char[][] matrix){

		System.out.println("DISTANCE MAP");
		distance = new int[matrix.length][matrix[0].length];
		Queue<Guard_Companion> q = new LinkedList<Guard_Companion>();
		for(int i=0;i<matrix.length;i++){
			for(int j=0;j<matrix[0].length;j++){
				if(matrix[i][j]=='G'){
					distance[i][j]=0;
					Guard_Companion companion1 = new Guard_Companion();
					companion1.distance=0;
					companion1.i=i;
					companion1.j=j;
					q.offer(companion1);
				}else{
					distance[i][j]=-1;
				}
			}

		}

		while(!q.isEmpty()){
			Guard_Companion companion2 = q.peek();
			int i = companion2.i;
			int j = companion2.j;
			int dist = companion2.distance;
			int[] rowNeighbour =    {0,0,1,-1};
			int[] columnNeighbour = {1,-1,0,0};
			for(int k=0;k<4;k++){
				if(isSafe(i+rowNeighbour[k], j+columnNeighbour[k], distance, matrix)){
					distance[i+rowNeighbour[k]][j+columnNeighbour[k]] = dist+1;
					Guard_Companion companion3 = new Guard_Companion();
					companion3.i = i+rowNeighbour[k];
					companion3.j = j+columnNeighbour[k];
					companion3.distance = dist+1;
					q.offer(companion3);
				}

			}
			q.poll();
		}

		for(int p = 0 ; p<distance.length;p++){
			for(int j=0;j<distance[0].length;j++)
			{
				System.out.print(distance[p][j]+" ");
			}
			System.out.println();
		}
	}
	static class Guard_Companion {
		int i;
		int j;
		int distance;
	}

	boolean isSafe(int row, int column, int[][] distance, char[][] matrix){
		return row<matrix.length && row>=0 && column<matrix[0].length && column>=0 && distance[row][column]==-1 && matrix[row][column]=='O';
	}

	void print_array()
	{
		System.out.println("   0 1 2 3 4 5 6 7 8");

		for (int i = 0; i < 9; i++)
		{
			//System.out.print(i+1+" ");
			System.out.print(i+" ");
			System.out.print("|");

			for (int j = 0; j < 9; j++)
			{
				System.out.print(array[i][j]);
				System.out.print("|");
			}
			System.out.print("\n");
		}
		System.out.println("\n");


	}

}
