import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import static java.nio.file.Files.lines;

public class BitArray implements Clusterable<BitArray>{
	private ArrayList<Boolean> bits;

	public BitArray(String str){
		bits = Arrays.stream(str.split(","))
				.map(Boolean::parseBoolean)
				.collect(Collectors.toCollection(ArrayList::new));
	}
	public BitArray(boolean[] bits) {
		this.bits= IntStream.range(0, bits.length)
				.mapToObj(i -> bits[i])
				.collect(Collectors.toCollection(ArrayList::new));
	}

	@Override
	public double distance(BitArray other) {
		return IntStream.range(0, this.bits.size())
				.filter(i -> !this.bits.get(i).equals(other.bits.get(i)))
				.count();
	}

	public static Set<BitArray> readClusterableSet(String path) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			List<String> lines = br.lines().collect(Collectors.toList());

			// מציאת האורך המקסימלי של מערכי הביטים בקובץ
			int maxLength = lines.stream()
					.mapToInt(line -> line.split(",").length)
					.max()
					.orElse(0);

			// סינון השורות על פי האורך המקסימלי והמרה ל-Set של BitArray
			Set<BitArray> arraySet = lines.stream()
					.filter(line -> line.split(",").length == maxLength)
					.map(line -> new BitArray(line))
					.collect(Collectors.toSet());
			return arraySet;
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
