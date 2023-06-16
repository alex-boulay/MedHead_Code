package dto;
import com.ocal.medhead.model.Hospital;


import lombok.*;
@Getter
@Setter
public class HospitalDTO{
	private float distance;
	private Hospital hospital;
	private int availableBed;
	private int time;
	
	public HospitalDTO(float f,Hospital h, int ab) {
		this.distance = f;
		this.hospital = h;
		this.availableBed = ab;
	}
	
}
