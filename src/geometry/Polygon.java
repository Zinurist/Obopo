package geometry;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Polygon extends Shape implements Serializable{

	private static final long serialVersionUID = 22L;

	//[0][i] x-coor of point i
	//[1][i] y-coor of point i
	private int[][] points;
	private int idMaxPoint;
	
	public Polygon(double[] offset,int[][] points){
		super();
		if(points.length !=2 || points[0].length<=0 || points[0].length!=points[1].length){
			throw new RuntimeException("oh shit polygon problems: point-array is of wrong length");
		}
		this.points=points;
		
		this.offset=offset;
		calcValues();
	}
	
	public Polygon(int[][] points){
		this(new double[]{0,0},points);
	}
	
	public Polygon(BufferedImage img) {
		this(new int[][]{ {0,0,img.getWidth(),img.getWidth()} , {0,img.getHeight(),img.getHeight(),0}  });
	}

	public void calcValues(){
		int nPoints=points[0].length;

		middle[0]=0;
		middle[1]=0;
		for(int i=0; i<nPoints; i++){
			middle[0]+=points[0][i];
			middle[1]+=points[1][i];
		}
		middle[0]=middle[0]/nPoints;
		middle[1]=middle[1]/nPoints;
		
		radius=0;
		double d;
		for(int i=0; i<nPoints;i++){
			d=Geometry.distance(middle, points[0][i],points[1][i]);
			if(d>radius){
				radius=d;
				idMaxPoint=i;
			}
		}
	}
	
	public double getTranslatedX(int id){
		return points[0][id]+offset[0];
	}
	
	public double getTranslatedY(int id){
		return points[1][id]+offset[1];
	}
	
	public double getX(int id){
		return points[0][id];
	}
	
	public double getY(int id){
		return points[1][id];
	}
	
	public int[] getXPoints(){
		return points[0];
	}
	
	public int[] getYPoints(){
		return points[1];
	}
	
	public int getNumPoints(){
		return points[0].length;
	}
	
	public int getIdMaxPoint(){
		return idMaxPoint;
	}
	
	public double getMaxPointX(){
		return points[0][idMaxPoint];
	}
	
	public double getMaxPointY(){
		return points[1][idMaxPoint];
	}

	public double[] getOffset() {
		return offset;
	}

	public int[][] getPoints() {
		return points;
	}
	
}
