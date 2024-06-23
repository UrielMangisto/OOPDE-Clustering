import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BitArray implements Clusterable<BitArray>{
	private ArrayList<Boolean> bits;

	public BitArray(String str){
		bits = str.chars()
				.mapToObj(c -> c == '1')
				.collect(Collectors.toCollection(ArrayList::new));
	}
	public BitArray(boolean[] bits) {
		this.bits= IntStream.range(0, bits.length)
				.mapToObj(i -> bits[i])
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public double distance(BitArray other) {
		// TODO: Transform to stream syntax
		return IntStream.range(0, this.bits.size())
				.filter(i -> !this.bits.get(i).equals(other.bits.get(i)))
				.count();
	}

//	public static Set<BitArray> readClusterableSet(String path) throws IOException {
//		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
//			Set<BitArray> set = br.lines()
//					.map(BitArray::new)
//					.collect(Collectors.toSet());
//
//			// קביעת האורך המקסימלי של אובייקטי BitArray
//			int maxLength = set.stream()
//					.mapToInt(ba -> ba.bits.size())
//					.max()
//					.orElse(0);
//
//			// סינון הקבוצה כדי לשמור רק אובייקטי BitArray בעלי אורך מקסימלי
//			return set.stream()
//					.filter(ba -> ba.bits.size() == maxLength)
//					.collect(Collectors.toSet());
//		}
//	}
public static Set<BitArray> readClusterableSet(String path) throws IOException {
	// קריאה מכל השורות בקובץ
	List<String> lines = Files.readAllLines(Paths.get(path));

	// המרה ל-Set של BitArray
	Set<BitArray> set = lines.stream()
			.map(BitArray::new)
			.collect(Collectors.toSet());

	// קביעת האורך המקסימלי של אובייקטי BitArray
	int maxLength = set.stream()
			.mapToInt(ba -> ba.bits.size())
			.max()
			.orElse(0);

	// סינון הקבוצה כדי לשמור רק אובייקטי BitArray בעלי אורך מקסימלי
	return set.stream()
			.filter(ba -> ba.bits.size() == maxLength)
			.collect(Collectors.toSet());
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
