package dto;
import com.ocal.medhead.model.Hospital;

import lombok.*;

@Data
public class HospitalDTO{
	private float distance;
	private Hospital hospital;
	private int availableBed;
	
	public HospitalDTO(float f,Hospital h) {
		this.distance = f;
		this.hospital = h;
	}
	public HospitalDTO(float f,Hospital h, int ab) {
		this.distance = f;
		this.hospital = h;
		this.availableBed = ab;
	}
	
}
