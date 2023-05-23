package dto;

import lombok.Data;

@Data
public class Coordinates {
	float longitude;
	float latitude;
	
	public Coordinates(float latitude , float longitude) {
		this.longitude = longitude;
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return Float.toString(longitude)+","+Float.toString(latitude);
	}
	public String latLong() {
		return Float.toString(latitude)+","+Float.toString(longitude);
	}
}
