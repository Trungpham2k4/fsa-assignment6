package fa.training.service;

import fa.training.dao.LineItemDAO;
import fa.training.dao.OrderDAO;
import fa.training.dao.ProductDAO;
import fa.training.dao.impl.LineItemDAOImpl;
import fa.training.dao.impl.OrderDAOImpl;
import fa.training.dao.impl.ProductDAOImpl;
import fa.training.entities.LineItem;

import java.util.List;

public class LineItemService {
    private final LineItemDAO lineItemDAO;
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;
    public LineItemService(){
        lineItemDAO = new LineItemDAOImpl();
        orderDAO = new OrderDAOImpl();
        productDAO = new ProductDAOImpl();
    }
    public List<LineItem> getAllItemsByOrderId(int orderId) {
        return lineItemDAO.findByOrderId(orderId);
    }
    public boolean addLineItem(LineItem item) {
        if(orderDAO.findById(item.getOrderId()) == null) {
            return false;
        }
        if(productDAO.findById(item.getProductId()) == null) {
            return false;
        }
        return lineItemDAO.save(item);
    }
}
