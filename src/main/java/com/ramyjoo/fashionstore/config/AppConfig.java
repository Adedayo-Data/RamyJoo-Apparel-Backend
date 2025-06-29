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
                    "WHY RAMYJOO APPAREL — Your go-to destination for bold, elegant fashion that speaks confidence. At RamyJoo, every piece is thoughtfully designed to elevate your style, making you feel empowered, expressive, and effortlessly chic. Whether you're dressing up for a special occasion or adding flair to your everyday look, RamyJoo Apparel blends comfort and class for the modern trendsetter.",
                    "CONSCIOUSLY MADE —\n" +
                            "\n" +
                            "We’re committed to sustainability and quality. From responsibly sourced materials to mindful production practices, RamyJoo is on a journey to ensure every outfit reflects care—for you, and for the planet.",
                    "STYLE MEETS FUNCTION —\n" +
                            "\n" +
                            "Beyond aesthetics, our pieces are crafted with comfort, durability, and versatility in mind. Whether it's breathable fabrics, flattering fits, or timeless cuts, RamyJoo Apparel is fashion that works with your lifestyle—day and night."
            ));

            dto.setRating(4.5F);
            dto.setDiscount(10);
            dto.setStockItems(5);

            dto.setReviews(List.of(
                    new ReviewDTO(
                            "Ade Dayo",
                            "/images/people/person.jpg",
                            "Amazing!",
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
