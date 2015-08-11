package geometry;

import java.io.Serializable;

/**
 * 2D Shape
 */
public abstract class Shape implements Serializable{

	private static final long serialVersionUID = 20L;

	protected double[] offset;//offset to get translated position in the world
	protected double[] middle;//middle point
	protected double radius;//radius
	
	public Shape(double[] offset, double[] middle, double radius){
		this.offset=offset;
		this.middle=middle;
		this.radius=radius;
	}
	
	public Shape(){
		this(new double[]{0,0},new double[]{0,0},0);
	}
	
	//getter/setter
	
	public double getTranslatedMiddleX(){
		return middle[0]+offset[0];
	}
	
	public double getTranslatedMiddleY(){
		return middle[1]+offset[1];
	}
	
	public double getMiddleX(){
		return middle[0];
	}
	
	public double getMiddleY(){
		return middle[1];
	}
	
	public double[] getMiddle(){
		return middle;
	}
	
	public double getRadius(){
		return radius;
	}
	
	public void setOffset(double[] offset){
		this.offset=offset;
	}
	
	public void setMiddle(double[] middle){
		this.middle=middle;
	}
	
	public void setRadius(double radius){
		this.radius=radius;
	}
}
