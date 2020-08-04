package springsecurity.helper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Counter {
	
	private int counter;

	public int getNextCounter() {
		return ++counter;
	}

}
