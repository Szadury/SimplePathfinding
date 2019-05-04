import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		char[][] lab1 = { { 1, 1, 1, 0, 1, 0, 1, 0, 1, 1 }, { 1, 0, 0, 1, 1, 0, 0, 1, 0, 1 },
				{ 1, 1, 'S', 1, 1, 1, 1, 1, 1, 1 }, { 0, 0, 0, 0, 0, 0, 0, 0, 1, 1 }, { 1, 0, 1, 0, 0, 1, 0, 0, 0, 1 },
				{ 0, 1, 1, 1, 1, 1, 0, 0, 0, 1 }, { 0, 0, 0, 1, 0, 1, 0, 1, 0, 1 }, { 1, 1, 1, 0, 0, 1, 1, 0, 1, 1 },
				{ 0, 1, 0, 0, 0, 0, 1, 0, 0, 0 }, { 0, 0, 0, 0, 1, 1, 0, 0, 0, 'K' } };

		boolean hasFinish = false;
		boolean hasPath = false;
		
		//initalizing coords
		int xS = 0, yS = 0, xK = 0, yK = 0;
		for (int i = 0; i < lab1.length; i++) {
			for (int j = 0; j < lab1[i].length; j++) {
				if (lab1[i][j] == 'S') {
					xS = i;
					yS = j;
				} else if (lab1[i][j] == 'K') {
					// Jeśli nie ma K
					xK = i;
					yK = j;
					hasFinish = true;
				}

			}
		}
		//check if table has finish
		if (!hasFinish) {
			System.out.println("Nie ustawiono konca, brak trasy");
		} else {
			
			List<ANode> kolejka = new ArrayList<>();

			int[] coordsStart = { xS, yS };
			int[] coordsEnd = { xK, yK };
			//setting static end and start coordinates
			ANode.endCords(coordsEnd);
			ANode.startCords(coordsStart);

			int[] currentCoord = coordsStart;
			ANode start = new ANode(null, coordsStart);

			kolejka.add(start);
			ANode current = start;
			while (!reached(currentCoord, coordsEnd)) {
				// sprawdza 4 kierunki
				//Anode table that checks each of 4 directions
				ANode[] kierunki = new ANode[4];
				System.out.println("CURRENT: " + Arrays.toString(current.coords));

				int x = current.coords[0];
				int y = current.coords[1];
				//adding each near direction to table if it is not '0'
				//and then checking its distance to end cordinates
				if (x > 0 && lab1[x - 1][y] != 0) {
					if (!ANode.existsCoords(current.coords[0] - 1, current.coords[1]))
						kierunki[0] = new ANode(current, x - 1, y);
				} else
					kierunki[0] = null;
				if (x < lab1.length - 1 && lab1[x + 1][y] != 0) {
					if (!ANode.existsCoords(current.coords[0] + 1, current.coords[1]))
						kierunki[1] = new ANode(current, x + 1, y);
				} else
					kierunki[1] = null;
				if (y > 0 && lab1[x][y - 1] != 0) {
					if (!ANode.existsCoords(current.coords[0], current.coords[1] - 1))
						kierunki[2] = new ANode(current, x, y - 1);
				} else
					kierunki[2] = null;
				if (y < lab1[x].length - 1 && lab1[x][y + 1] != 0) {
					if (!ANode.existsCoords(current.coords[0], current.coords[1] + 1))
						kierunki[3] = new ANode(current, x, y + 1);
				} else
					kierunki[3] = null;
				// sorting direction table in order of distance to end Node. It adds them ordered to ArrayList "kolejka"
				kolejka.remove(0);
				checkDistance(kierunki, kolejka);
				if (kolejka.isEmpty()) {
					hasPath = false;
					break;
				} else {
					currentCoord = kolejka.get(0).coords;
					current = kolejka.get(0);
				}
			}
			if (hasPath) {
				System.out.println("ZNALEZIONO TRASE!");
				//creating finish list that will trace all previous nodes that has been used.
				List<ANode> finish = new ArrayList<>();
				while (current != start) {
					finish.add(0, current);
					current = current.parentNode;
				}
				for (ANode tmp : finish) {
					System.out.println("( " + Arrays.toString(tmp.coords) + " )");
				}
			}

			else {
				System.out.println("Nie ma mozliwosci dojsc do mety");
			}
		}

	}

	// g cost - distance from start node, h cost - distance from end node, sum cost

	private static void checkDistance(ANode[] kierunki, List<ANode> kolejka) {
		Arrays.sort(kierunki, new Comparator<ANode>() {

			@Override
			public int compare(ANode arg0, ANode arg1) {
				// jesli arg beda sobie rowne
				if (arg0 == null) {
					return 1;
				}
				if (arg1 == null)
					return -1;
				if (arg0.f < arg1.f)
					return -1;
				else if (arg0.f > arg1.f)
					return 1;
				else
					return 0;
			}
		});
		for (int i = 0; i < kierunki.length; i++) {
			if (kierunki[i] != null)
				System.out.println(
						"f : " + kierunki[i].f + " Y: " + kierunki[i].coords[0] + " X: " + kierunki[i].coords[1]);
			else
				System.out.println("Null");
		}
		dodajDoKolejki(kierunki, kolejka);
		System.out.println(kolejka);

	}

	private static void dodajDoKolejki(ANode[] kierunki, List<ANode> kolejka) {
		// sprawdz w jakie miejsce dodac
		for (int i = 0; i < 4; i++) {
			if (kierunki[i] != null) {
				if (kolejka.isEmpty())
					kolejka.add(kierunki[i]);
				else {
					if (!kolejka.contains(kierunki[i])) {
						int index = 0;
						for (int j = 0; j < kolejka.size(); j++) {
							if (kolejka.get(j).f < kierunki[i].f)
								index++;

						}
						kolejka.add(index, kierunki[i]);
					}
				}
			}

		}
	}

	private static boolean reached(int[] currentCoord, int[] coordsEnd) {
		if (currentCoord[0] == coordsEnd[0] && currentCoord[1] == coordsEnd[1])
			return true;
		else
			return false;
	}

}
