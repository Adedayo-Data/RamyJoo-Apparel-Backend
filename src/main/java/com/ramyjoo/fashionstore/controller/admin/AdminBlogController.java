package com.ramyjoo.fashionstore.controller.admin;

import com.ramyjoo.fashionstore.dto.BlogDTO;
import com.ramyjoo.fashionstore.model.Blogs;
import com.ramyjoo.fashionstore.repository.BlogsRepository;
import com.ramyjoo.fashionstore.service.CloudinaryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/blogs")
@AllArgsConstructor
public class AdminBlogController {

    private final BlogsRepository blogRepo;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;

    // create blog post
    @PostMapping
    public ResponseEntity<Blogs> createBlog(@RequestBody BlogDTO blogDTO){

        Blogs blogs = modelMapper.map(blogDTO, Blogs.class);
        Blogs savedBlog = blogRepo.save(blogs);
        return new ResponseEntity<>(savedBlog, HttpStatus.CREATED);
    }

    // update blog post
    @PutMapping("/{id}")
    public ResponseEntity<Blogs> updateBlog(@PathVariable Long id,
                                              @RequestBody BlogDTO blogDTO){

        Optional<Blogs> optionalBlogs = blogRepo.findById(id);

        if(optionalBlogs.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        Blogs blogs = modelMapper.map(blogDTO, Blogs.class);
        blogs.setId(id);

        return new ResponseEntity<>(blogRepo.save(blogs), HttpStatus.OK);
    }

    // delete blog post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlogPost(@PathVariable Long id){
        blogRepo.deleteById(id);
        String message = "Blog deleted Successfully";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // Fetch all blog post
    @GetMapping
    public ResponseEntity<List<Blogs>> getAllBlogs(){
        List<Blogs> allBlogs = blogRepo.findAll();
        return new ResponseEntity<>(allBlogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Blogs> getBlogById(@PathVariable Long id){

        Optional<Blogs> optionalBlogs = blogRepo.findById(id);

        if(optionalBlogs.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Blogs blogsById = optionalBlogs.get();

        return new ResponseEntity<>(blogsById, HttpStatus.OK);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<Blogs> updateBlogImage(@PathVariable Long id,
                                                 @RequestParam("file") MultipartFile file){
        Optional<Blogs> optionalBlogs = blogRepo.findById(id);

        if(optionalBlogs.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Blogs blogs = optionalBlogs.get();

        String imageUrl = cloudinaryService.uploadImage(file);

        blogs.setFeaturedImage(imageUrl);

        Blogs updatedBlogs = blogRepo.save(blogs);

        return new ResponseEntity<>(updatedBlogs, HttpStatus.OK);
    }
}
