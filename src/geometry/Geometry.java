package geometry;

import java.util.List;

public class Geometry {
	public static double distance(double x1,double y1,double x2,double y2) {
		double dx=x1-x2;
		double dy=y1-y2;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public static double distance(double[] p1,double x2,double y2) {
		double dx=p1[0]-x2;
		double dy=p1[1]-y2;
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public static double distance(double[] p1,double[] p2) {
		double dx=p1[0]-p2[0];
		double dy=p1[1]-p2[1];
		return Math.sqrt(dx*dx+dy*dy);
	}
	
	public static double[] crossPoint(double[] s1,double[] v1,double[] s2,double[] v2){
		
		double[] cross=new double[2];
		
		//g(k)=start1+k*vec1
		//h(l)=start2+l*vec2
		
		double l= ( (s1[1]-s2[1])*v1[0]+(s2[0]-s1[0])*v1[1] ) / ( v1[0]*v2[1]-v2[0]*v1[1] );
		double k= ( s2[0]-s1[0]+l*v2[0] )/( v1[0] );

		cross[0]=s1[0]+k*v1[0];//alt:start2[0]+l*vec2[0]
		cross[1]=s1[1]+k*v1[1];//alt:start2[1]+l*vec2[1]
		return cross;
	}
	
	public static void fillCrossPoints(double[][] crossPoints,double[][] points,double[] vec1,double[] start2,double[] vec2){
		for(int i=0; i<points.length;i++){
			crossPoints[i]=Geometry.crossPoint(points[i],vec1,start2,vec2);
		}
	}
	
	public static double[] crossPointBounds(double[] s1,double[] v1,double[] s2,double[] v2){
		
		double[] crossBounds=new double[2];
		
		//g(k)=start1+k*vec1
		//h(l)=start2+l*vec2

		double l= ( (s1[1]-s2[1])*v1[0]+(s2[0]-s1[0])*v1[1] ) / ( v1[0]*v2[1]-v2[0]*v1[1] );
		double k= ( s2[0]-s1[0]+l*v2[0] )/( v1[0] );

		crossBounds[0]=k;//alt:start2[0]+l*vec2[0]
		crossBounds[1]=l;//alt:start2[1]+l*vec2[1]
		return crossBounds;
	}
	

	public static double[][] collision(Hitbox h1,Hitbox h2){
		double middleDis=Geometry.distance(h1.getTranslatedMiddleX(),h1.getTranslatedMiddleY(),h2.getTranslatedMiddleX(),h2.getTranslatedMiddleY());
		
		//circle check
		if(middleDis > (h1.getRadius()+h2.getRadius()) ){
			return null;
		}
		
		double[][] colData;
		List<Polygon> polys1=h1.getPolygons();
		List<Polygon> polys2=h2.getPolygons();
		int polys1L=polys1.size();
		int polys2L=polys2.size();
		for(int i=0; i<polys1L;i++){
			for(int j=0; j<polys2L;j++){
				colData=Geometry.collision(polys1.get(i),polys2.get(j));
				if(colData!=null){
					return colData;
				}
			}
		}
		return null;
	}
	
	public static int counter=0;
	
	public static double[][] collision(Polygon p1,Polygon p2){
		double middleDis=Geometry.distance(p1.getTranslatedMiddleX(),p1.getTranslatedMiddleY(),p2.getTranslatedMiddleX(),p2.getTranslatedMiddleY());
		
		//circle check
		if(middleDis > (p1.getRadius()+p2.getRadius()) ){
			return null;
		}	
		
		double[] middle1=new double[]{p1.getTranslatedMiddleX(),p1.getTranslatedMiddleY()};
		double[] middle2=new double[]{p2.getTranslatedMiddleX(),p2.getTranslatedMiddleY()};
		
		//vec/nvec not needed in this version
		/*
		double[] vec=new double[2];
		
		vec[0]=middle2[0]-middle1[0];
		vec[1]=middle2[1]-middle1[1];
		
		
		double[] nvec=new double[2];
		nvec[0]=vec[1];
		nvec[1]=-vec[0];
		*/

		//get translated x/y coors of polygon points
		double[][] points1=new double[p1.getNumPoints()][2];
		double[][] points2=new double[p2.getNumPoints()][2];
		
		for(int i=0; i<points1.length;i++){
			points1[i][0]=p1.getTranslatedX(i);
			points1[i][1]=p1.getTranslatedY(i);
		}
		
		for(int i=0; i<points2.length;i++){
			points2[i][0]=p2.getTranslatedX(i);
			points2[i][1]=p2.getTranslatedY(i);
		}
		
		int id1=-1;
		int id2=-1;
		
		double minDis=Double.MAX_VALUE;
		
		double tempD;
		
		//crossPoints1.length = p1.getNumPoitns() !
		//getting id of point with shortest distance between point of 1 (/2) and middle of 2 (/1)

		for(int i=0; i<points1.length;i++){
			tempD=Geometry.distance(middle2,points1[i]);
			if(tempD<minDis){
				minDis=tempD;
				id1=i;
			}
		}
		minDis=Double.MAX_VALUE;
		for(int i=0; i<points2.length;i++){
			tempD=Geometry.distance(middle1,points2[i]);
			if(tempD<minDis){
				minDis=tempD;
				id2=i;
			}
		}

		double vecs1[][]=new double[2][2];
		double vecs2[][]=new double[2][2];
		
		vecs1[0]=calcPrevVec(points1,id1);
		vecs1[1]=calcNextVec(points1,id1);
		vecs2[0]=calcPrevVec(points2,id2);
		vecs2[1]=calcNextVec(points2,id2);

		
		
		//Debug dump:
		/*
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.println(" ---DEBUG DUMP--- "+counter++);
		//System.out.println("xCorrect: "+xCorrect+", maxDis:"+maxX+", disP1M1:"+( Math.abs(middle1[0]-points1[id1][0])+", disP2M2:"+Math.abs(middle2[0]-points2[id2][0]) ));
		//System.out.println("yCorrect: "+yCorrect+", maxDis:"+maxY+", disP1M1:"+( Math.abs(middle1[1]-points1[id1][1])+", disP2M2:"+Math.abs(middle2[1]-points2[id2][1]) ));
		//System.out.println("collision:"+!(xCorrect||yCorrect));
		System.out.println("Entity1: "+Arrays.toString(p1.getOffset()));
		System.out.println("Entity2: "+Arrays.toString(p2.getOffset()));
		System.out.println("Mid1: "+Arrays.toString(middle1)+" -> "+Arrays.toString(p1.getMiddle()));
		System.out.println("Mid2: "+Arrays.toString(middle2)+" -> "+Arrays.toString(p2.getMiddle()));
		System.out.println("Vec: "+Arrays.toString(vec));
		//System.out.println("Nvec: "+Arrays.toString(nvec));
		//System.out.println("Crosspoints 1: "+Arrays.toString(crossPoints1[0])+" ; "+Arrays.toString(crossPoints1[1])+" ; "+Arrays.toString(crossPoints1[2])+" ; "+Arrays.toString(crossPoints1[3]));
		//System.out.println("Crosspoints 2: "+Arrays.toString(crossPoints2[0])+" ; "+Arrays.toString(crossPoints2[1])+" ; "+Arrays.toString(crossPoints2[2])+" ; "+Arrays.toString(crossPoints2[3]));
		System.out.println("1st point:"+id1+" -> ["+p1.getTranslatedX(id1)+","+p1.getTranslatedY(id1)+"]"+" -> ["+p1.getX(id1)+","+p1.getY(id1)+"]");
		System.out.println("2nd point:"+id2+" -> ["+p2.getTranslatedX(id2)+","+p2.getTranslatedY(id2)+"]"+" -> ["+p2.getX(id2)+","+p2.getY(id2)+"]");
		System.out.println("Distance: "+middleDis);
		//System.out.println("Distance Mid1 to P1: "+distance(crossPoints1[id1],middle1));
		//System.out.println("Distance Mid2 to P2: "+distance(crossPoints2[id2],middle2));
		//System.out.println("Distance Mid1 to P2: "+minDisP2M1);
		//System.out.println("Distance Mid2 to P1: "+minDisP1M2);
		System.out.println("vecs1: "+Arrays.toString(vecs1[0])+" ; "+Arrays.toString(vecs1[1]));
		System.out.println("vecs2: "+Arrays.toString(vecs2[0])+" ; "+Arrays.toString(vecs2[1]));
		*/
		
		double[] bounds;
		

		//bounds[0]=k, multiplier of vecs1
		//bounds[1]=l, multiplier of vecs2
		//-> 0<=k<=1 or 0<=l<=1 means collision
		bounds=crossPointBounds(points1[id1],vecs1[0],points2[id2],vecs2[0]);
		//System.out.println("Bounds00: "+bounds[0]+" , "+bounds[1]);
		if( 0<=bounds[0] && bounds[0]<=1 && 0<=bounds[1] && bounds[1]<=1){
			//System.out.println("collision"+counter++);
			return new double[][]{points1[id1],points2[id2]};
		}
		bounds=crossPointBounds(points1[id1],vecs1[1],points2[id2],vecs2[0]);
		//System.out.println("Bounds10: "+bounds[0]+" , "+bounds[1]);
		if( 0<=bounds[0] && bounds[0]<=1 && 0<=bounds[1] && bounds[1]<=1){
			//System.out.println("collision"+counter++);
			return new double[][]{points1[id1],points2[id2]};
		}
		bounds=crossPointBounds(points1[id1],vecs1[0],points2[id2],vecs2[1]);
		//System.out.println("Bounds01: "+bounds[0]+" , "+bounds[1]);
		if( 0<=bounds[0] && bounds[0]<=1 && 0<=bounds[1] && bounds[1]<=1){
			//System.out.println("collision"+counter++);
			return new double[][]{points1[id1],points2[id2]};
		}
		bounds=crossPointBounds(points1[id1],vecs1[1],points2[id2],vecs2[1]);
		//System.out.println("Bounds11: "+bounds[0]+" , "+bounds[1]);
		if( 0<=bounds[0] && bounds[0]<=1 && 0<=bounds[1] && bounds[1]<=1){
			//System.out.println("collision"+counter++);
			return new double[][]{points1[id1],points2[id2]};
		}
		//System.out.println("NO collision"+counter++);
		return null;
		
		
		
		
		/*old version, using differences in x and y-direction
		double maxX,maxY;
		double[] sortedPOI=new double[4];
		sortedPOI[0]=middle1[0];
		sortedPOI[1]=points1[id1][0];
		sortedPOI[2]=middle2[0];
		sortedPOI[3]=points2[id2][0];
		//sort
		sortingNetwork(sortedPOI);
		maxX=sortedPOI[3]-sortedPOI[0];
		
		sortedPOI[0]=middle1[1];
		sortedPOI[1]=points1[id1][1];
		sortedPOI[2]=middle2[1];
		sortedPOI[3]=points2[id2][1];
		//sort
		sortingNetwork(sortedPOI);
		maxY=sortedPOI[3]-sortedPOI[0];
		
		
		boolean xCorrect=maxX >  ( Math.abs(middle1[0]-points1[id1][0])  + Math.abs(middle2[0]-points2[id2][0]) ) ;
		boolean yCorrect=maxY >  ( Math.abs(middle1[1]-points1[id1][1])  + Math.abs(middle2[1]-points2[id2][1]) ) ;
		if(xCorrect||yCorrect){
			return null;
		}
		
		return new double[][]{points1[id1],points2[id2]};
		*/
		
		
		
		
		
		/*old version, using crosspoints, wrong results!
		
		double[][] crossPoints1=new double[p1.getNumPoints()][2];
		double[][] crossPoints2=new double[p2.getNumPoints()][2];
		
		fillCrossPoints(crossPoints1,points1,nvec,middle1,vec);
		fillCrossPoints(crossPoints2,points2,nvec,middle2,vec);
		double minDisP1M2=Double.MAX_VALUE;
		double minDisP2M1=Double.MAX_VALUE;
		double tempD;
		
		for(int i=0; i<crossPoints1.length;i++){
			tempD=Geometry.distance(crossPoints1[i],middle2);
			if(tempD<minDisP1M2){
				minDisP1M2=tempD;
				id1=i;
			}
		}
		for(int i=0; i<crossPoints2.length;i++){
			tempD=Geometry.distance(crossPoints2[i],middle1);
			if(tempD<minDisP2M1){
				minDisP2M1=tempD;
				id2=i;
			}
		}
		
		double disP1M1=distance(crossPoints1[id1],middle1);
		double disP2M2=distance(crossPoints2[id2],middle2);
		
		if(disP1M1+disP2M2>=middleDis){
			return new double[]{p1.getTranslatedX(id1),p1.getTranslatedY(id1),p2.getTranslatedX(id2),p2.getTranslatedY(id2)};
		}
		
		return null;
		*/
		
	}
	
	//IMPORTANT: nextPoint-point, so that the vector points at the next point!!!!!
	public static double[] calcNextVec(double[][] points, int id){
		int nextId=id+1;
		if(nextId>=points.length){
			nextId=0;
		}
		
		return new double[]{points[nextId][0]-points[id][0], points[nextId][1]-points[id][1]};
	}
	
	public static double[] calcPrevVec(double[][] points, int id){
		int nextId=id-1;
		if(nextId<0){
			nextId=points.length-1;
		}
		
		return new double[]{points[nextId][0]-points[id][0], points[nextId][1]-points[id][1]};
	}
	
	/**
	 * 4-lined sorting network
	 * @param array
	 */
	public static void sortingNetwork(double[] array){
		double t;
		if(array[0]>array[2]){
			t=array[0];
			array[0]=array[2];
			array[2]=t;
		}
		
		if(array[1]>array[3]){
			t=array[1];
			array[1]=array[3];
			array[3]=t;
		}
		
		if(array[0]>array[1]){
			t=array[0];
			array[0]=array[1];
			array[1]=t;
		}
		
		if(array[2]>array[3]){
			t=array[2];
			array[2]=array[3];
			array[3]=t;
		}
		
		if(array[1]>array[2]){
			t=array[1];
			array[1]=array[2];
			array[2]=t;
		}
	}
	
}
