package fa.training.dao;

import fa.training.entities.LineItem;

import java.util.List;

public interface LineItemDAO extends CommonDAO<LineItem> {
    List<LineItem> findByOrderId(int orderId);
}
