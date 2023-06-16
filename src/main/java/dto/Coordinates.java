package dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coordinates {
	float longitude;
	float latitude;
	
	public Coordinates() {
		this.latitude = 0.f;
		this.longitude = -90.f;
	}
	public Coordinates(float latitude , float longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public String longLat() {
		return Float.toString(longitude)+","+Float.toString(latitude);
	}
	public boolean compareCoord(Coordinates c) {
		return inrange(latitude,c.getLatitude()) && inrange(longitude,c.getLongitude());
	}
	public boolean inrange(float a, float b) {
		float range = 0.01f;
		return Math.abs(a-b) < range;
	}
	
	public boolean areValid() {
		return latitude >-90.f && latitude <90.f && longitude >-180f && longitude <180f;
	}
	/*
	public void validate() {
		latitude = Math.min(Math.max(latitude,-90.f),90.f);
		longitude = Math.min(Math.max(longitude,-180.f),180.f);
	}*/
}
