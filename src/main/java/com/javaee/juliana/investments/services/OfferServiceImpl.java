package com.javaee.juliana.investments.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.juliana.investments.domain.Investor;
import com.javaee.juliana.investments.domain.Market;
import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;
import com.javaee.juliana.investments.domain.StockMarket;
import com.javaee.juliana.investments.domain.StockOffer;
import com.javaee.juliana.investments.config.EmailSender;
import com.javaee.juliana.investments.repositories.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService {
	
	@Autowired
	OfferRepository offerRepository;
	@Autowired
	InvestorService investorService;
	@Autowired
	DemandService demandService;
	@Autowired
	TransactionService transactionService;
	@Autowired
	MarketService marketService;

	@Override
	public StockOffer createNew(StockMarket stockMarket) {
		EmailSender emailSender = new EmailSender();
		Investor investor = null;
		if (!stockMarket.isCompanyOffer()) {
			investor = investorService.getById(stockMarket.getInvestorId());
		}
		Transaction transaction = transactionService.getById(stockMarket.getStockId());

		StockOffer stockOffer = new StockOffer();
		stockOffer.setQuantity(stockMarket.getQuantity());
		stockOffer.setQuantitySold(0);
		stockOffer.setPrice(stockMarket.getPrice());
		if (stockMarket.isCompanyOffer()) {
			stockOffer.setCompanyOffer(true);
		} else {
			stockOffer.setInvestor(investor);
		}
		stockOffer.setTransaction(transaction);
		
		Set<StockDemand> stockDemands = transaction.getDemands();
		stockDemands.stream().forEach((stockDemand) -> {
			int quant = 0;
			if (stockDemand.getPrice() == stockOffer.getPrice() && stockDemand.getQuantity() - stockDemand.getQuantityBought() > 0) {
				if (stockOffer.getQuantity() - stockOffer.getQuantitySold() > stockDemand.getQuantity() - stockDemand.getQuantityBought()) {
					quant = stockDemand.getQuantity() - stockDemand.getQuantityBought();
				} else {
					quant = stockOffer.getQuantity() - stockOffer.getQuantitySold();
				}

				if (quant > 0) {
					stockDemand.setQuantityBought(stockDemand.getQuantityBought() + quant);
					stockOffer.setQuantitySold(stockOffer.getQuantitySold() + quant);
					demandService.save(stockDemand);
					
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
		offerRepository.save(stockOffer);
		
		Set<StockOffer> sOffers = transaction.getOffers();
		sOffers.add(stockOffer);
		transaction.setOffers(sOffers);
		transactionService.save(transaction.getId(), transaction);

		if (!stockMarket.isCompanyOffer()) {
			Set<StockOffer> iDemands = investor.getOffers();
			iDemands.add(stockOffer);
			investor.setOffers(iDemands);
			investorService.save(investor.getId(), investor);
		}

		return stockOffer;
	}

	@Override
	public StockOffer save(StockOffer offer) {
		offer.setId(offer.getId());
		return offerRepository.save(offer);
	}

}
