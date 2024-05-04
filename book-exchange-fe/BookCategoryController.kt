package com.bookExchange.controller

import com.bookExchange.dto.BookCategoriesResponse
import com.bookExchange.dto.BookCategoryRequest
import com.bookExchange.dto.BookCategoryResponse
import com.bookExchange.exception.ServiceException
import com.bookExchange.service.BookCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BookCategoryController {
    @Autowired
    private lateinit var bookCategoryService: BookCategoryService
    @PostMapping("/book/category")
    fun addBookCategory(@RequestBody request: BookCategoryRequest): ResponseEntity<out BookCategoryResponse> {
        try{
            // Add book category
            val bookCategory = bookCategoryService.addBookCategory(request)
            return ResponseEntity.status(200).body(bookCategory)
        } catch (e: ServiceException) {
            return ResponseEntity.status(e.status).body(BookCategoryResponse("failed", e.message.toString()))
        }
        catch (e: Exception) {
            return ResponseEntity.status(500).body(BookCategoryResponse("failed", "Failed to add book category"))
        }
    }
    @GetMapping("/book/categories")
    fun getBookCategories(): ResponseEntity<out BookCategoriesResponse> {
        try{
            // Get all book categories
            val bookCategories = bookCategoryService.getBookCategories()
            return ResponseEntity.status(200).body(BookCategoriesResponse(status = "success", message = "Book categories fetched successfully", bookCategories))
        } catch (e: ServiceException) {
            return ResponseEntity.status(e.status).body(BookCategoriesResponse("failed", e.message.toString(), null))
        }
        catch (e: Exception) {
            return ResponseEntity.status(500).body(BookCategoriesResponse("failed", "Failed to get book categories", null))
        }
    }
}