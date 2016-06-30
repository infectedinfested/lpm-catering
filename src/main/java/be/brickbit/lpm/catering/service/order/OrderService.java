package be.brickbit.lpm.catering.service.order;

import be.brickbit.lpm.catering.domain.Product;
import be.brickbit.lpm.catering.domain.ProductReceiptLine;
import be.brickbit.lpm.catering.domain.StockProduct;
import be.brickbit.lpm.catering.repository.ProductRepository;
import be.brickbit.lpm.catering.repository.StockProductRepository;
import be.brickbit.lpm.catering.service.order.command.OrderLineCommand;
import be.brickbit.lpm.catering.service.product.dto.ReceiptDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import be.brickbit.lpm.catering.domain.Order;
import be.brickbit.lpm.catering.domain.OrderStatus;
import be.brickbit.lpm.catering.repository.OrderRepository;
import be.brickbit.lpm.catering.service.order.command.DirectOrderCommand;
import be.brickbit.lpm.catering.service.order.command.RemoteOrderCommand;
import be.brickbit.lpm.catering.service.order.mapper.DirectOrderCommandToOrderEntityMapper;
import be.brickbit.lpm.catering.service.order.mapper.OrderMapper;
import be.brickbit.lpm.catering.service.order.mapper.RemoteOrderCommandToEntityMapper;
import be.brickbit.lpm.core.domain.User;
import be.brickbit.lpm.infrastructure.AbstractService;

import java.util.List;

@Service
public class OrderService extends AbstractService<Order> implements IOrderService {
	@Autowired
	private OrderRepository orderRepository;

    @Autowired
    private StockProductRepository stockProductRepository;

    @Autowired
    private ProductRepository productRepository;

	@Autowired
	private DirectOrderCommandToOrderEntityMapper directOrderCommandMapper;

	@Autowired
	private RemoteOrderCommandToEntityMapper remoteOrderCommandToEntityMapper;

	@Override
	@Transactional
	public <T> T placeDirectOrder(DirectOrderCommand command, OrderMapper<T> dtoMapper, User user) {
        updateStockLevels(command.getOrderLines());
		Order order = directOrderCommandMapper.map(command);
		order.setPlacedByUserId(user.getId());

		setOrderLineStatus(order);

		orderRepository.save(order);
		return dtoMapper.map(order);
	}

    private void updateStockLevels(List<OrderLineCommand> orderLines) {
        for(OrderLineCommand orderLine : orderLines){
            Integer orderAmount = orderLine.getQuantity();
            Product product = productRepository.findOne(orderLine.getProductId());
            for(ProductReceiptLine receiptLine : product.getReceipt()){
                StockProduct stockProductToUpdate = receiptLine.getStockProduct();
                Integer totalQuantity = receiptLine.getQuantity() * orderAmount;
                if(stockProductToUpdate.getStockLevel() >= totalQuantity){
                    stockProductToUpdate.setStockLevel(stockProductToUpdate.getStockLevel() - totalQuantity);
                    stockProductRepository.save(stockProductToUpdate);
                }else{
                    throw new RuntimeException("Not enough stock to process order!");
                }
            }
        }
    }

    private void setOrderLineStatus(Order order) {
		order.getOrderLines().stream()
				.filter(line -> !line.getProduct().getEnableDirectQueueing())
				.forEach(line -> line.setStatus(OrderStatus.COMPLETED));
	}

	@Override
	@Transactional
	public <T> T placeRemoteOrder(RemoteOrderCommand command, OrderMapper<T> dtoMapper, User user) {
        updateStockLevels(command.getOrderLines());
		Order order = remoteOrderCommandToEntityMapper.map(command);
		order.setPlacedByUserId(user.getId());
		order.setUserId(user.getId());
		orderRepository.save(order);
		return dtoMapper.map(order);
	}

    @Override
    @Transactional(readOnly = true)
    public <T> T findOrderByOrderLineId(Long orderLineId, OrderMapper<T> dtoMapper) {
        return dtoMapper.map(orderRepository.findByOrderLinesId(orderLineId));
    }

    @Override
	protected OrderRepository getRepository() {
		return orderRepository;
	}
}
