import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.stream.Collectors;

public class BitArray implements Clusterable<BitArray>{
	private ArrayList<Boolean> bits;

	public BitArray(String str){
		// TODO: Transform to stream syntax
		bits = new ArrayList<>();
		for(int i = 0; i < str.length(); i++){
			bits.add(str.charAt(i) == '1');
		}
	}
	public BitArray(boolean[] bits) {
		// TODO: Transform to stream syntax
		this.bits = new ArrayList<>(bits.length);
        for (boolean bit : bits) {
            this.bits.add(bit);
        }
	}

	@Override
	public double distance(BitArray other) {
		// TODO: Transform to stream syntax
		int distance = 0;
		for (int i = 0; i < this.bits.size(); i++) {
			if (this.bits.get(i) != other.bits.get(i)) {
				distance++;
			}
		}
		return distance;
	}

	public static Set<BitArray> readClusterableSet(String path) throws IOException {
		// TODO: Complete. If the file contains bitarrays of different lengths,
		//  retain only those of maximal length
		Set<BitArray> set = new HashSet<>();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			set = br.lines().map(BitArray::new).collect(Collectors.toSet());
		}

		// Determine the maximal length of BitArray objects
		int maxLength = set.stream().mapToInt(ba -> ba.bits.size()).max().orElse(0);

		// Filter the set to retain only BitArray objects of maximal length
		return set.stream().filter(ba -> ba.bits.size() == maxLength).collect(Collectors.toSet());
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
