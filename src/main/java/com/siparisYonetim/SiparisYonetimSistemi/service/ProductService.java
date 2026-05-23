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

    public boolean createProduct(ProductData productData, String companyName) {
        if (productRepository.existsByCodeAndCompanyName(productData.getCode(), companyName)) {
            return false;
        }

        ProductModel productModel = modelMapper.map(productData, ProductModel.class);
        productModel.setCompanyName(companyName);
        productRepository.save(productModel);

        return true;
    }

    public List<ProductData> getAllProducts(String companyName) {
        return productRepository.findAllByCompanyName(companyName)
                .stream()
                .map(product -> modelMapper.map(product, ProductData.class))
                .collect(Collectors.toList());
    }

    public List<ProductData> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductData.class))
                .collect(Collectors.toList());
    }

    public ProductData getProductByCode(String code, String companyName) {
        return productRepository.findByCodeAndCompanyName(code, companyName)
                .map(product -> modelMapper.map(product, ProductData.class))
                .orElse(null);
    }

    public ProductData getProductById(Long productId, String companyName) {
        return productRepository.findByIdAndCompanyName(productId, companyName)
                .map(product -> modelMapper.map(product, ProductData.class))
                .orElse(null);
    }

    public boolean updateProduct(ProductData productData, String companyName) {
        ProductModel existingProduct = productRepository.findByIdAndCompanyName(productData.getId(), companyName)
                .orElseThrow(() -> new RuntimeException("Urun bulunamadi."));

        boolean codeUsedByAnotherProduct = productRepository.findByCodeAndCompanyName(productData.getCode(), companyName)
                .filter(product -> !product.getId().equals(productData.getId()))
                .isPresent();

        if (codeUsedByAnotherProduct) {
            return false;
        }

        existingProduct.setName(productData.getName());
        existingProduct.setCode(productData.getCode());
        existingProduct.setPrice(productData.getPrice());
        existingProduct.setStock(productData.getStock());
        existingProduct.setCompanyName(companyName);

        productRepository.save(existingProduct);

        return true;
    }

    public boolean deleteProductByCode(String code, String companyName) {
        ProductModel deleteProduct = productRepository.findByCodeAndCompanyName(code, companyName)
                .orElseThrow(() -> new RuntimeException("Urun bulunamadi."));

        productRepository.delete(deleteProduct);

        return true;
    }

    public boolean deleteProductById(Long productId, String companyName) {
        ProductModel deleteProduct = productRepository.findByIdAndCompanyName(productId, companyName)
                .orElseThrow(() -> new RuntimeException("Urun bulunamadi."));

        productRepository.delete(deleteProduct);

        return true;
    }
}
