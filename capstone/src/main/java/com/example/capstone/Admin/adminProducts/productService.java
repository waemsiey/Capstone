package com.example.capstone.Admin.adminProducts;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.capstone.IdGeneratorService;

@Service
public class productService {

    private final productRepository productRepository;
    private final IdGeneratorService idGeneratorService;
    private final Cloudinary cloudinary;
   

    @Autowired
    public productService(productRepository productRepository, IdGeneratorService idGeneratorService, Cloudinary cloudinary) {
        this.productRepository = productRepository;
        this.idGeneratorService = idGeneratorService;
        this.cloudinary = cloudinary;
    }

    public List<products> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<products> getProductById(String prodID){
        return productRepository.findById(prodID);
    }

    public void addNewProduct (products product, MultipartFile imageFile) throws IOException{
        try{
            if(imageFile != null && !imageFile.isEmpty()){
                var uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),ObjectUtils.emptyMap());
                String imageURL = uploadResult.get("url").toString();
                product.setImgURL(imageURL);

            }

            product.setProdID(idGeneratorService.generateProductId());

            productRepository.save(product);
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProduct(String prodID, products updatedProduct, MultipartFile imageFile) throws IOException{
        Optional<products> productOptional = productRepository.findById(prodID); 
        if(productOptional.isPresent()){
            products existingProduct = productOptional.get();
            existingProduct.setProdName(updatedProduct.getProdName());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setProdDescription(updatedProduct.getProdDescription());

            try{
                if(imageFile != null && !imageFile.isEmpty()){
                    var uploadResult = cloudinary.uploader().upload(imageFile.getBytes(),ObjectUtils.emptyMap());
                    String imageURL = uploadResult.get("url").toString();
                    existingProduct.setImgURL(imageURL);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            productRepository.save(existingProduct);
        }
    }

    public void deleteProduct(String prodID) {
        productRepository.deleteById(prodID);
    }
}

