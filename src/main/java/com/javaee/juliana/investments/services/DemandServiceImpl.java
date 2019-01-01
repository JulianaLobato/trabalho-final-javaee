package com.javaee.juliana.investments.services;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.juliana.investments.services.StockDemandComparatorByStockAndTimestamp;
import com.javaee.juliana.investments.domain.Investor;
import com.javaee.juliana.investments.domain.Market;
import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;
import com.javaee.juliana.investments.domain.StockMarket;
import com.javaee.juliana.investments.domain.StockOffer;
import com.javaee.juliana.investments.config.EmailSender;
import com.javaee.juliana.investments.repositories.DemandRepository;

@Service
public class DemandServiceImpl implements DemandService {
	
	@Autowired
	DemandRepository demandRepository;
	@Autowired
	InvestorService investorService;
	@Autowired
	OfferService offerService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	MarketService marketService;

	@Override
	public StockDemand createNew(StockMarket stockMarket) {
		EmailSender emailSender = new EmailSender();
		Investor investor = investorService.getById(stockMarket.getInvestorId());
		Transaction transaction = transactionService.getById(stockMarket.getStockId());

		StockDemand stockDemand = new StockDemand();
		stockDemand.setQuantity(stockMarket.getQuantity());
		stockDemand.setQuantityBought(0);
		stockDemand.setPrice(stockMarket.getPrice());
		stockDemand.setInvestor(investor);
		stockDemand.setTransaction(transaction);

		// Verifica se possui alguma oferta no mesmo valor
		Set<StockOffer> stockOffers = transaction.getOffers();
		stockOffers.stream().forEach((stockOffer) -> {
			int quant = 0;
			if (stockOffer.getPrice() == stockDemand.getPrice() && stockOffer.getQuantity() - stockOffer.getQuantitySold() > 0) {
				if (stockOffer.getQuantity() - stockOffer.getQuantitySold() > stockDemand.getQuantity() - stockDemand.getQuantityBought()) {
					quant = stockDemand.getQuantity() - stockDemand.getQuantityBought();
				} else {
					quant = stockOffer.getQuantity() - stockOffer.getQuantitySold();
				}

				// Remove quantidade em ambos as ofertas
				if (quant > 0) {
					stockDemand.setQuantityBought(stockDemand.getQuantityBought() + quant);
					stockOffer.setQuantitySold(stockOffer.getQuantitySold() + quant);
					offerService.save(stockOffer);
					
					Market market = new Market();
					market.setQuantity(quant);
					market.setPrice(stockDemand.getPrice());
					market.setOffer(stockOffer);
					market.setDemand(stockDemand);
					marketService.save(market);
					
					if (stockOffer.isCompanyOffer()) {
						transaction.setQuantityCompany(transaction.getQuantityCompany() - quant);
					}
					
					if (stockOffer.isCompanyOffer()) {
						emailSender.SendEmail(stockOffer.getTransaction().getCompany().getEmail(), 
								"Notificação de venda ação " + stockOffer.getTransaction().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + stockDemand.getPrice() + " (preço unitário)."
						);
					} else {
						emailSender.SendEmail(stockOffer.getInvestor().getEmail(), 
								"Notificação de venda ação " + stockOffer.getTransaction().getId(), 
								Integer.toString(quant) + " ações foram vendidas com sucesso no valor de " + stockDemand.getPrice() + " (preço unitário)."
						);
					}
					emailSender.SendEmail(stockDemand.getInvestor().getEmail(), 
							"Notificação de compra ação " + stockOffer.getTransaction().getId(), 
							Integer.toString(quant) + " ações foram compradas com sucesso no valor de " + stockDemand.getPrice() + " (preço unitário)."
					);

				}
			}
		});
		demandRepository.save(stockDemand);

		Set<StockDemand> sDemands = transaction.getDemands();
		sDemands.add(stockDemand);
		transaction.setDemands(sDemands);
		transactionService.save(transaction.getId(), transaction);

		Set<StockDemand> iDemands = investor.getDemands();
		iDemands.add(stockDemand);
		investor.setDemands(iDemands);
		investorService.save(investor.getId(), investor);
		
		return stockDemand;
	}

	@Override
	public Set<StockDemand> getAll() {
		Set<StockDemand> stockDemands = new TreeSet<StockDemand>(new StockDemandComparatorByStockAndTimestamp());
		demandRepository.findAll().iterator().forEachRemaining(stockDemands::add);

		// Remove itens consumidos
		Predicate<StockDemand> demandPredicate = d-> ( d.getQuantity() - d.getQuantityBought() ) == 0;
		stockDemands.removeIf(demandPredicate);

		// Ordena saída
		//Set<StockDemand> sorted = new TreeSet<StockDemand>(new StockDemandComparatorByIdAndTimestamp());
		//sorted.addAll(stockDemands);

		return stockDemands;
	}

	@Override
	public Set<StockDemand> getAllByStock(Transaction transaction) {
		Set<StockDemand> stockDemands = this.getAll();

		// Remove itens de outros Stocks
		Predicate<StockDemand> demandPredicate = d-> d.getTransaction().getId() != transaction.getId();
		stockDemands.removeIf(demandPredicate);

		return stockDemands;
	}

	@Override
	public StockDemand save(StockDemand demand) {
		demand.setId(demand.getId());
		return demandRepository.save(demand);
	}

}
