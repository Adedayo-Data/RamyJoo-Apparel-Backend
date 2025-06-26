//package com.ramyjoo.fashionstore;
//
//public class TempClass {
//
//    @GetMapping("/filter")
//    public ResponseEntity<List<ProductResponseDTO>> filterProducts(
//            @RequestParam(required = false) String categoryName,
//            @RequestParam(required = false) String subCategoryName,
//            @RequestParam(required = false) BigDecimal price,
//            @RequestParam(required = false) String size,
//            @RequestParam(required = false) String color,
//            @RequestParam(required = false) String keyword
//    ) {
//        List<ProductResponseDTO> filtered = productService.filterProducts(categoryName, subCategoryName, price, size, color, keyword);
//        return ResponseEntity.ok(filtered);
//    }
//
//    public List<ProductResponseDTO> filterProducts(String categoryName, String subCategoryName, BigDecimal price, String size, String color, String keyword) {
//        // Implementation goes here
//    }
//
//    List<Product> products = productRepo.findAll();
//
//if (categoryName != null) {
//        products = products.stream()
//                .filter(p -> p.getCategory().getCategoryName().equalsIgnoreCase(categoryName))
//                .collect(Collectors.toList());
//    }
//
//if (subCategoryName != null) {
//        products = products.stream()
//                .filter(p -> p.getSubCategory().getSubCategoryName().equalsIgnoreCase(subCategoryName))
//                .collect(Collectors.toList());
//    }
//
//if (price != null) {
//        products = products.stream()
//                .filter(p -> p.getPrice().compareTo(price) == 0)
//                .collect(Collectors.toList());
//    }
//
//if (size != null) {
//        products = products.stream()
//                .filter(p -> p.getSizeList().contains(size))
//                .collect(Collectors.toList());
//    }
//
//if (color != null) {
//        products = products.stream()
//                .filter(p -> p.getColorList().contains(color))
//                .collect(Collectors.toList());
//    }
//
//if (keyword != null) {
//        products = products.stream()
//                .filter(p -> p.getProductName().toLowerCase().contains(keyword.toLowerCase()) ||
//                        p.getBrand().toLowerCase().contains(keyword.toLowerCase()) ||
//                        p.getColorList().stream().anyMatch(c -> c.toLowerCase().contains(keyword.toLowerCase())))
//                .collect(Collectors.toList());
//    }
//
//// Then map to DTOs
//return products.stream()
//        .map(productMapper::toProductResponseDTO)
//        .collect(Collectors.toList());
//
//
//}
