import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents a point in 2D space and implements the Clusterable interface.
 * It provides methods to calculate the distance to another point and to read points from a file.
 */
public class TwoDPoint implements Clusterable<TwoDPoint> {
	double x;
	double y;

	/**
	 * Constructs a point from a comma-separated string of coordinates.
	 *
	 * @param str the string containing the coordinates
	 */
	public TwoDPoint(String str) {
		String[] split = str.split(",");
		x = Double.parseDouble(split[0]);
		y = Double.parseDouble(split[1]);
	}

	/**
	 * Constructs a point with the given coordinates.
	 *
	 * @param x the x-coordinate of the point
	 * @param y the y-coordinate of the point
	 */
	public TwoDPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Calculates the Euclidean distance to another point.
	 *
	 * @param other the other point
	 * @return the distance to the other point
	 */
	@Override
	public double distance(TwoDPoint other) {
		double dx = x - other.x;
		double dy = y - other.y;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * Reads a set of points from a file.
	 *
	 * @param path the path to the file
	 * @return a set of points read from the file
	 * @throws IOException if an I/O error occurs
	 */
	public static Set<TwoDPoint> readClusterableSet(String path) throws IOException {
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
