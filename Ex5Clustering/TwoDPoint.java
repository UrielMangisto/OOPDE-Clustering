import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TwoDPoint implements Clusterable<TwoDPoint>{
	double x;
	double y;
	public TwoDPoint(String str){
		String[] split = str.split(",");
		x = Double.parseDouble(split[0]);
		y = Double.parseDouble(split[1]);
	}
	public TwoDPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}
	@Override
	public double distance(TwoDPoint other) {
		double dx = x - other.x;
		double dy = y - other.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public static Set<TwoDPoint> readClusterableSet(String path) throws IOException{
		// TODO: Transform to stream syntax
		Set<TwoDPoint> set = new HashSet<>();
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = br.readLine();
		while(line != null){
			set.add(new TwoDPoint(line));
			line = br.readLine();
		}
		return set;
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	@Override
	public boolean equals(Object other) {
		TwoDPoint otherP = (TwoDPoint) other;
		return (otherP.x == x && otherP.y == y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
