package geometry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hitbox extends Shape implements Serializable{

	private static final long serialVersionUID = 21L;

	private List<Polygon> polygonlist;
	
	public Hitbox(double[] offset,Polygon... p){
		this(offset,new ArrayList<Polygon>(Arrays.asList(p)));
	}
	
	public Hitbox(Polygon... p){
		this(new double[]{0,0},new ArrayList<Polygon>(Arrays.asList(p)));
	}
	
	public Hitbox(List<Polygon> polygonlist){
		this(new double[]{0,0},polygonlist);
		
	}
	
	public Hitbox(double[] offset,List<Polygon> polygonlist){
		super();
		this.polygonlist=polygonlist;
		this.offset=offset;
		calcValues();
	}
	
	public void calcValues(){
		int length=polygonlist.size();
		middle[0]=0;
		middle[1]=0;
		for(int i=0; i<length;i++){
			middle[0]+=polygonlist.get(i).getMiddleX();
			middle[1]+=polygonlist.get(i).getMiddleY();
		}

		middle[0]=middle[0]/length;
		middle[1]=middle[1]/length;
		
		radius=0;
		double d;
		for(Polygon p: polygonlist){
			for(int i=0; i<p.getNumPoints();i++){
				d=Geometry.distance(middle, p.getX(i),p.getY(i));
				if(d>radius){
					radius=d;
				}
			}
		}
	}
	
	
	public List<Polygon> getPolygons(){
		return polygonlist;
	}
	
	public void rotate(double alpha){
		//TODO polygon rotation around middle
		//-> middle as negative vector for reformatting coor-system
	}
	
	@Override
	public void setOffset(double[] vec){
		this.offset=vec;
		for(Polygon p:polygonlist){
			p.setOffset(vec);
		}
	}
}
