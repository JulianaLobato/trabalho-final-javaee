package com.javaee.juliana.investments.controllers.v1;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.juliana.investments.domain.MessageId;
import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;
import com.javaee.juliana.investments.domain.StockDemandRest;
import com.javaee.juliana.investments.domain.StockMarket;
import com.javaee.juliana.investments.domain.StockOffer;
import com.javaee.juliana.investments.domain.StockOfferRest;
import com.javaee.juliana.investments.domain.TransactionRest;
import com.javaee.juliana.investments.services.CompanyService;
import com.javaee.juliana.investments.services.TransactionService;

@RestController
@RequestMapping(TransactionController.BASE_URL)
public class TransactionController {

	public static final String BASE_URL = "/api/v1/stocks";

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private CompanyService companyService;

	/* Listar ações (Inicio)*/
	// GET
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<TransactionRest> getAll() {
		Set<TransactionRest> transactionRest = new HashSet<>();
		transactionService.getAll().forEach((Transaction transaction) -> {
			transactionRest.add(new TransactionRest(transaction));
		});
		return transactionRest;
	}

	@GetMapping({ "/{id}" })
	@ResponseStatus(HttpStatus.OK)
	public TransactionRest getById(@PathVariable String id) {
		return new TransactionRest(transactionService.getById(id));
	}
	/* Listar ações (Fim)*/


	/* Emissão de Ações (Inicio)*/	
	// GET
	@GetMapping({ "/emit/{companyId}" })
	@ResponseStatus(HttpStatus.OK)
	public Set<TransactionRest> getAllCompany(@PathVariable String companyId) {
		Set<TransactionRest> transactionRest = new HashSet<>();
		companyService.getAllStocks(companyId).forEach((Transaction transaction) -> {
			transactionRest.add(new TransactionRest(transaction));
		});
		return transactionRest;
	}

	@GetMapping({ "/emit/{companyId}/{stockId}" })
	@ResponseStatus(HttpStatus.OK)
	public TransactionRest getById(@PathVariable String companyId, @PathVariable String stockId) {
		return new TransactionRest (companyService.getStockById(companyId, stockId));
	}

	// POST
	@PostMapping({ "/emit/{companyId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionRest createNew(@PathVariable String companyId, @RequestBody Transaction transaction) {
		return new TransactionRest(transactionService.emitNew(companyId, transaction));
	}
	/* Emissão de Ações (Fim)*/


	/* Vender ações (Inicio)*/
	// GET
	@GetMapping({ "/sell" })
	@ResponseStatus(HttpStatus.OK)
	public Set<StockOfferRest> getOffers() {
		Set<StockOfferRest> offersRest = new HashSet<>();
		transactionService.getAll().forEach((Transaction transaction) -> {
			transaction.getOffers().forEach((StockOffer offer) -> {
				if (offer.getQuantity() - offer.getQuantitySold() > 0) {
					offersRest.add(new StockOfferRest(offer));
				}
			});
		});
		return offersRest;
	}

	// POST
	@PostMapping({ "/sell" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sell(@RequestBody StockMarket stockMarket) {
		return transactionService.sendMessage("offer", stockMarket);
	}

	@PostMapping({ "/sell/{stockId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sellStock(@PathVariable String stockId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		return transactionService.sendMessage("offer", stockMarket);
	}

	@PostMapping({ "/sell/{stockId}/{investorId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId sellStockInvestor(@PathVariable String stockId, @PathVariable String investorId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		stockMarket.setInvestorId(investorId);
		return transactionService.sendMessage("offer", stockMarket);
	}
	/* Vender ações (Fim)*/


	/* Comprar ações (Inicio)*/
	// GET
	@GetMapping({ "/buy" })
	@ResponseStatus(HttpStatus.OK)
	public Set<StockDemandRest> getDemands() {
		Set<StockDemandRest> demandsRest = new HashSet<>();
		transactionService.getAll().forEach((Transaction transaction) -> {
			transaction.getDemands().forEach((StockDemand demand) -> {
				if (demand.getQuantity() - demand.getQuantityBought() > 0) {
					demandsRest.add(new StockDemandRest(demand));
				}
			});
		});
		return demandsRest;
	}

	// POST
	@PostMapping({ "/buy" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buy(@RequestBody StockMarket stockMarket) {
		return transactionService.sendMessage("demand", stockMarket);
	}

	@PostMapping({ "/buy/{stockId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buyStock(@PathVariable String stockId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		return transactionService.sendMessage("demand", stockMarket);
	}

	@PostMapping({ "/buy/{stockId}/{investorId}" })
	@ResponseStatus(HttpStatus.CREATED)
	public MessageId buyStockInvestor(@PathVariable String stockId, @PathVariable String investorId, @RequestBody StockMarket stockMarket) {
		stockMarket.setStockId(stockId);
		stockMarket.setInvestorId(investorId);
		return transactionService.sendMessage("demand", stockMarket);
	}
	/* Comprar ações (Fim)*/
}
