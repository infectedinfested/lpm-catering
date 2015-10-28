package be.brickbit.lpm.catering.service.stockflow.mapper;

import be.brickbit.lpm.catering.domain.StockFlow;
import be.brickbit.lpm.catering.service.user.mapper.UserDtoMapper;
import be.brickbit.lpm.infrastructure.mapper.Mapper;
import be.brickbit.lpm.catering.service.stockflow.dto.StockFlowDto;
import be.brickbit.lpm.core.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StockFlowDtoMapper implements StockFlowMapper<StockFlowDto> {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Override
    public StockFlowDto map(StockFlow someStockFlow) {
        return new StockFlowDto(
                someStockFlow.getId(),
                someStockFlow.getQuantity(),
                someStockFlow.getPricePerUnit(),
                userService.findOne(someStockFlow.getUserId(), userDtoMapper).getUsername(),
                someStockFlow.getStockFlowType(),
                someStockFlow.getIncluded(),
                someStockFlow.getTimestamp(),
                someStockFlow.getStockProduct().getName()
        );
    }
}
