package dto;
import com.OCAL.MedHead.model.Hospital;

import lombok.*;

@Data
public class HospitalEx{
	private float distance;
	private Hospital hospital;
	
	public HospitalEx(float f,Hospital h) {
		this.distance = f;
		this.hospital = h;
	}
}
