package springsecurity.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Counter {
	
	private int counter = 1;

	public int getNextCounter() {
		return counter++;
	}

}
