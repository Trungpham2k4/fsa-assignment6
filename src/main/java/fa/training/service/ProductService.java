package fa.training.service;

import fa.training.dao.ProductDAO;
import fa.training.dao.impl.ProductDAOImpl;
import fa.training.entities.Product;

import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;
    public ProductService(){
        productDAO = new ProductDAOImpl();
    }
    public boolean addProduct(Product product) {
        return productDAO.save(product);
    }
    public Product getProductById(int id) {
        return productDAO.findById(id);
    }
    public boolean updateProduct(Product product) {
        return productDAO.update(product);
    }
    public boolean deleteProduct(int id) {
        return productDAO.delete(id);
    }
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
}
