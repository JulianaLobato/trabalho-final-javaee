package com.javaee.juliana.investments.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaee.juliana.investments.domain.Company;
import com.javaee.juliana.investments.domain.Message;
import com.javaee.juliana.investments.domain.MessageId;
import com.javaee.juliana.investments.domain.Transaction;
import com.javaee.juliana.investments.domain.StockDemand;
import com.javaee.juliana.investments.domain.StockMarket;
import com.javaee.juliana.investments.domain.StockOffer;
import com.javaee.juliana.investments.repositories.TransactionRepository;
import com.javaee.juliana.investments.config.RabbitMQConfig;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private DemandService demandService;
	@Autowired
	private OfferService offerService;
	
	private final RabbitTemplate rabbitTemplate;

	public TransactionServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
	}
	
	@Override
	public MessageId sendMessage(String source, StockMarket stockMarket) {
		Message message = messageService.createNew(new Message(source, stockMarket));
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_MESSAGES, message);
		return new MessageId(message.getId());
	}

	@Override
	public Set<Transaction> getAll() {
		Set<Transaction> transaction = new HashSet<>();
		transactionRepository.findAll().iterator().forEachRemaining(transaction::add);
		return transaction;
	}

	@Override
	public Transaction getById(String id) {
		return getStockById(id);
	}

	private Transaction getStockById(String id) {
		Optional<Transaction> stockOptional = transactionRepository.findById(id);

		if (!stockOptional.isPresent()) {
			throw new IllegalArgumentException("Stock not found for ID value: " + id.toString());
		}

		return stockOptional.get();
	}
	
	@Override
	public Transaction createNew(Transaction transaction) {
		Transaction stockInd0;
		try {
			stockInd0 = transactionRepository.findByName(transaction.getName()).get(0);
		} catch (Exception e) {
			return transactionRepository.save(transaction);
		}
		throw new IllegalArgumentException("stock already exists with ID: " + stockInd0.getId());
	}

	@Override
	public Transaction emitNew(String companyId, Transaction transaction) {
		Company company = companyService.getById(companyId);
		transaction.setCompany(company); //stock.setCompanyId(company.getId());
		transaction.setQuantityCompany(transaction.getQuantity());
		Set<Transaction> transactions = company.getStocks();
		transactions.add(transaction);
		company.setStocks(transactions);
		this.createNew(transaction);
		companyService.save(companyId, company);

		StockMarket stockMarket = new StockMarket();
		stockMarket.setStockId(transaction.getId());
		stockMarket.setQuantity(transaction.getQuantity());
		stockMarket.setPrice(transaction.getInitialPrice());
		stockMarket.setCompanyOffer(true);
		offerService.createNew(stockMarket);

		return transaction;
	}

	@Override
	public Transaction save(String id, Transaction transaction) {
		transaction.setId(id);
		return transactionRepository.save(transaction);
	}

	@Override
	public void deleteById(String id) {
		transactionRepository.deleteById(id);
	}

	@Override
	public Set<StockDemand> getAllDemands() {
		return demandService.getAll();
		
	    //Comparator<Person> comparator = Comparator.comparing(person -> person.name);
	    //comparator = comparator.thenComparing(Comparator.comparing(person -> person.age));
		//return null;
	}

	@Override
	public Set<StockOffer> getAllOffers() {
		
		return null;
	}

}
