package dto;

import lombok.Data;

@Data
public class Coordinates {
	float longitude;
	float latitude;
	
	public Coordinates() {
		this.longitude = -999.f;
		this.latitude = -999.f;
	}
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
	public boolean compareCoord(Coordinates c) {
		return inrange(latitude,c.getLatitude()) && inrange(longitude,c.getLongitude());
	}
	public boolean inrange(float a, float b) {
		float range = 0.01f;
		return Math.abs(a-b) < range;
	}
}
