package assignment9;

import java.util.LinkedList;

public class Snake {

	private static final double SEGMENT_SIZE = 0.02;
	private static final double MOVEMENT_SIZE = SEGMENT_SIZE * 1.5;
	private LinkedList<BodySegment> segments;
	private double deltaX;
	private double deltaY;
	//for attaching new segment to tail position
	private double lastTailX;
	private double lastTailY;
	//score
	private int score;
	
	public Snake() {
		deltaX = 0;
		deltaY = 0;
		segments = new LinkedList<BodySegment>();
		segments.add(new BodySegment(0.5, 0.5, SEGMENT_SIZE));
		lastTailX = 0;
		lastTailY = 0;
	}
	
	// Score getter
	public int getScore() {
		return score;
	}
	
	public void changeDirection(int direction) {
		if(direction == 1) { //up
			deltaY = MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 2) { //down
			deltaY = -MOVEMENT_SIZE;
			deltaX = 0;
		} else if (direction == 3) { //left
			deltaY = 0;
			deltaX = -MOVEMENT_SIZE;
		} else if (direction == 4) { //right
			deltaY = 0;
			deltaX = MOVEMENT_SIZE;
		}
	}
	
	/**
	 * Moves the snake by updating the position of each of the segments
	 * based on the current direction of travel
	 */
	public void move() {
		//save tail pos before move
		BodySegment tail = segments.get(segments.size() - 1);
		lastTailX = tail.getX();
		lastTailY = tail.getY();
		
		//move each seg forwards
		for(int i = segments.size() - 1; i > 0; i--) {
			BodySegment previous = segments.get(i - 1);
			BodySegment current = segments.get(i);
			
			current.setX(previous.getX());
			current.setY(previous.getY());
		}
		//move head based on delta values
		BodySegment head = segments.get(0);
		head.setX(head.getX() + deltaX);
		head.setY(head.getY() + deltaY);
	}
	
	/**
	 * Draws the snake by drawing each segment
	 */
	public void draw() {
		for (int i = 0; i < segments.size(); i++) {
			BodySegment current = segments.get(i);
			current.draw();
			System.out.println(segments.size());
		}
	}
	
	/**
	 * The snake attempts to eat the given food, growing if it does so successfully
	 * @param f the food to be eaten
	 * @return true if the snake successfully ate the food
	 */
	public boolean eatFood(Food f) {
		BodySegment head = segments.get(0);
		
		double foodX = head.getX() - f.getX();
		double foodY = head.getY() - f.getY();

		double distance = Math.sqrt(foodX * foodX + foodY * foodY);
		
		if(distance <= SEGMENT_SIZE + Food.FOOD_SIZE) {
			segments.add(new BodySegment(lastTailX, lastTailY, SEGMENT_SIZE));
			score++;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns true if the head of the snake is in bounds
	 * @return whether or not the head is in the bounds of the window
	 */
	public boolean isInbounds() {
		//FIXME
		double margin = SEGMENT_SIZE;
		BodySegment head = segments.get(0);
		
		double headX = head.getX();
		double headY = head.getY();
		
		return headX >= margin && headX <= 1 - margin && headY >= margin && headY <= 1 - margin;
	}
}
