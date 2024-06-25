import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class represents an array of bits and implements the Clusterable interface.
 * It provides methods to calculate the distance to another bit array and to read bit arrays from a file.
 */
public class BitArray implements Clusterable<BitArray> {
	private ArrayList<Boolean> bits;

	/**
	 * Constructs a bit array from a comma-separated string of boolean values.
	 *
	 * @param str the string containing the boolean values
	 */
	public BitArray(String str) {
		bits = Arrays.stream(str.split(","))
				.map(Boolean::parseBoolean)
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Constructs a bit array from a boolean array.
	 *
	 * @param bits the boolean array
	 */
	public BitArray(boolean[] bits) {
		this.bits = IntStream.range(0, bits.length)
				.mapToObj(i -> bits[i])
				.collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * Calculates the Hamming distance to another bit array.
	 *
	 * @param other the other bit array
	 * @return the Hamming distance to the other bit array
	 */
	@Override
	public double distance(BitArray other) {
		return IntStream.range(0, this.bits.size())
				.filter(i -> !this.bits.get(i).equals(other.bits.get(i)))
				.count();
	}

	/**
	 * Reads a set of bit arrays from a file.
	 *
	 * @param path the path to the file
	 * @return a set of bit arrays read from the file
	 * @throws IOException if an I/O error occurs
	 */
	public static Set<BitArray> readClusterableSet(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			List<String> lines = br.lines().collect(Collectors.toList());

			// Find the maximum length of bit arrays in the file
			int maxLength = lines.stream()
					.mapToInt(line -> line.split(",").length)
					.max()
					.orElse(0);

			// Filter lines by maximum length and convert to a set of BitArray
			return lines.stream()
					.filter(line -> line.split(",").length == maxLength)
					.map(BitArray::new)
					.collect(Collectors.toSet());
		}
	}

	@Override
	public String toString() {
		return bits.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BitArray bitArray = (BitArray) o;
		return bits.equals(bitArray.bits);
	}

	@Override
	public int hashCode() {
		return Objects.hash(bits);
	}
}
