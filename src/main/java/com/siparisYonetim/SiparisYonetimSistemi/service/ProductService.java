package com.siparisYonetim.SiparisYonetimSistemi.service;

import com.siparisYonetim.SiparisYonetimSistemi.data.ProductData;
import com.siparisYonetim.SiparisYonetimSistemi.model.ProductModel;
import com.siparisYonetim.SiparisYonetimSistemi.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public boolean createProduct(ProductData productData) {
        if (productRepository.findByCode(productData.getCode()).isPresent()) {
            return false;
        }

        ProductModel productModel = modelMapper.map(productData, ProductModel.class);
        productRepository.save(productModel);

        return true;
    }

    public List<ProductData> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductData.class))
                .collect(Collectors.toList());
    }

    public ProductData getProductByCode(String code) {
        return productRepository.findByCode(code)
                .map(product -> modelMapper.map(product, ProductData.class))
                .orElse(null);
    }

    public ProductData getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(product -> modelMapper.map(product, ProductData.class))
                .orElse(null);
    }

    public boolean updateProduct(ProductData productData) {
        ProductModel existingProduct = productRepository.findById(productData.getId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));

        boolean codeUsedByAnotherProduct = productRepository.findByCode(productData.getCode())
                .filter(product -> !product.getId().equals(productData.getId()))
                .isPresent();

        if (codeUsedByAnotherProduct) {
            return false;
        }

        existingProduct.setName(productData.getName());
        existingProduct.setCode(productData.getCode());
        existingProduct.setPrice(productData.getPrice());
        existingProduct.setStock(productData.getStock());

        productRepository.save(existingProduct);

        return true;
    }

    public boolean deleteProductByCode(String code) {
        ProductModel deleteProduct = productRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));

        productRepository.delete(deleteProduct);

        return true;
    }

    public boolean deleteProductById(Long productId) {
        ProductModel deleteProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı."));

        productRepository.delete(deleteProduct);

        return true;
    }
}