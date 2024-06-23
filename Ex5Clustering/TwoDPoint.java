import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		try (Stream<String> lines = Files.lines(Paths.get(path))) {
			return lines.map(TwoDPoint::new)
					.collect(Collectors.toSet());
		}
	}

	@Override
	public String toString() {
		return x + "," + y;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass()) return false;
		TwoDPoint otherP = (TwoDPoint) other;
		return (otherP.x == x && otherP.y == y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
}
