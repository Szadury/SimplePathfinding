import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ANode {
	public int g;
	public int h;
	public int f;
	ANode parentNode;
	int[] coords;
	static List<ANode> objectList = new ArrayList<>();
	
	static int[] endCoords = new int[2];
	static int[] startCoords = new int[2];
	
	public ANode(ANode parentNode, int[] coords) {
		this.parentNode = parentNode;
		this.coords = coords;
//		g = Math.sqrt((coords[0]-startCoords[0])^2 + (coords[1]-startCoords[1])^2);
//		h = Math.sqrt((coords[0]-endCoords[0])^2 + (coords[1]-endCoords[1])^2);
		double tmp = Math.pow(coords[0] - startCoords[0], 2) + Math.pow(coords[1] - startCoords[1], 2);
		g = (int) Math.sqrt(g);
		tmp = Math.pow(coords[0] - endCoords[0], 2) + Math.pow(coords[1] - endCoords[1], 2);
		h = (int) Math.sqrt(h);
		
		f = g + h;
		objectList.add(this);
	}
	
	public ANode(ANode parentNode, int i, int j) {
		//policz G, H i F
		this.parentNode = parentNode;
		coords = new int[2];
		coords[0] = i;
		coords[1] = j;
		double tmp = Math.pow(coords[0] - startCoords[0], 2) + Math.pow(coords[1] - startCoords[1], 2);
		g = (int) Math.sqrt(tmp);
		tmp = Math.pow(coords[0] - endCoords[0], 2) + Math.pow(coords[1] - endCoords[1], 2);
		h = (int) Math.sqrt(tmp);
		f = g + h;
		objectList.add(this);
	}

	@Override
	public String toString() {
		if(parentNode != null)
			return "ANode [g=" + g + ", h=" + h + ", f=" + f + ", parentNode=" + Arrays.toString(parentNode.coords) + ", coords="
				+ Arrays.toString(coords) + "]";
		else
			return "ANode [g=" + g + ", h=" + h + ", f=" + f + ", parentNode=" + parentNode + ", coords="
			+ Arrays.toString(coords) + "]";
	
	}

	public static void endCords(int[] x) {
		endCoords[0] = x[0];
		endCoords[1] = x[1];
	}
	public static void startCords(int[] start) {
		startCoords[0] = start[0];
		startCoords[1] = start[1];
	}
	

	public static boolean existsCoords(int i, int j) {
		for(ANode tmp: objectList) {
			if(i == tmp.coords[0] && j == tmp.coords[1])
				return true;
		}
		return false;
		}
	}
	


