package net.mashihara;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.mashihara.domain.Court;

@RestController
public class CourtController {
	@GetMapping("/court/{id}")
	public Court getCourt(@PathVariable int id) {
		return new Court(id,"有明");
	}
	@PostMapping("/update/{id}")
	public Court changeCourt(@PathVariable int id,@RequestBody Court court) {
		court.setId(id);
		return court;
	}
}

