package com.javaee.juliana.investments.controllers.v1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.juliana.investments.domain.Investor;
import com.javaee.juliana.investments.domain.InvestorRest;
import com.javaee.juliana.investments.services.InvestorService;

@RestController
@RequestMapping(InvestorController.BASE_URL)
public class InvestorController {

	public static final String BASE_URL = "/api/v1/investors";

	@Autowired
	private InvestorService investorService;

	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<InvestorRest> getAll() {
		Set<InvestorRest> investorsRest = new HashSet<>();
		investorService.getAll().forEach((Investor investor) -> {
			investorsRest.add(new InvestorRest(investor));
		});
		return investorsRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public InvestorRest getById(@PathVariable String id) {
		return new InvestorRest(investorService.getById(id));
	}

	// POST
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public InvestorRest createNew(@RequestBody Investor buyer) {
		return new InvestorRest(investorService.createNew(buyer));
	}

	// PUT
	@PutMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public InvestorRest update(@PathVariable String id, @RequestBody Investor buyer) {
		return new InvestorRest(investorService.save(id, buyer));
	}

	// DELETE
	@DeleteMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String id) {
		investorService.deleteById(id);
	}
}
