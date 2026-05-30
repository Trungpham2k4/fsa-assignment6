package fa.training.service;

import fa.training.dao.LineItemDAO;
import fa.training.dao.impl.LineItemDAOImpl;
import fa.training.entities.LineItem;

import java.util.List;

public class LineItemService {
    private final LineItemDAO lineItemDAO;
    public LineItemService(){
        lineItemDAO = new LineItemDAOImpl();
    }
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        return lineItemDAO.findByOrderId(orderId);
    }
    public boolean addLineItem(LineItem item) {
        return lineItemDAO.save(item);
    }
}
