package com.ramyjoo.fashionstore.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ramyjoo.fashionstore.dto.AddressDTO;
import com.ramyjoo.fashionstore.dto.ProductResponseDTO;
import com.ramyjoo.fashionstore.dto.ReviewDTO;
import com.ramyjoo.fashionstore.model.Address;
import com.ramyjoo.fashionstore.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;


@Configuration
public class AppConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-key}")
    private String apiKey;

    @Value("${cloudinary.api-secret}")
    private String apiSecret;

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Product, ProductResponseDTO> typeMap = modelMapper.createTypeMap(Product.class, ProductResponseDTO.class);

        typeMap.addMappings(m -> {
            m.map(Product::getId, ProductResponseDTO::setId);
            m.map(Product::getProductName, ProductResponseDTO::setProductName);
            m.map(src -> src.getSubCategory().getSubCategoryName(), ProductResponseDTO::setCategory);
            m.map(Product::getDescription, ProductResponseDTO::setDescription);
            m.map(Product::getColorList, ProductResponseDTO::setColor);
            m.map(Product::getImages, ProductResponseDTO::setImages);
            m.map(Product::getPrice, ProductResponseDTO::setPrice);
            m.map(Product::getBrand, ProductResponseDTO::setBrand);
        });

        typeMap.setPostConverter(ctx -> {
            ProductResponseDTO dto = ctx.getDestination();

            dto.setAboutItem(List.of(
                    "WHY APPLE WATCH SERIES 9 — Your essential companion for a healthy life is now even more powerful. The S9 chip enables a super-bright display and a magical new way to quickly and easily interact with your Apple Watch without touching the screen. Advanced health, safety and activity features provide powerful insights and help when you need it. And redesigned apps in watchOS give you more information at a glance.",
                    "CARBON NEUTRAL — An aluminium Apple Watch Series 9 paired with the latest Sport Loop is carbon neutral.",
                    "ADVANCED HEALTH FEATURES—Keep an eye on your blood oxygen. Take an ECG anytime. Get notifications if you have an irregular heart rhythm. See how much time you spent in REM, Core, or Deep sleep with sleep stages. Temperature sensing provides insights into overall well-being and cycle tracking. And take note of your state of mind to help build emotional awareness and resilience."
            ));

            dto.setRating(4.5F);
            dto.setDiscount(10);
            dto.setStockItems(5);

            dto.setReviews(List.of(
                    new ReviewDTO(
                            "Shohag miah",
                            "/images/people/person.jpg",
                            "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Distinctio voluptatem aliquam reprehenderit debitis quidem accusantium",
                            4,
                            new Date()
                    )
            ));

            return dto;
        });

        return modelMapper;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,
                "secure", true
        ));
    }
}
