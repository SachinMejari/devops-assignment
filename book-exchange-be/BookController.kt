package com.bookExchange.controller

import com.bookExchange.dto.*
import com.bookExchange.exception.ServiceException
import com.bookExchange.service.BookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class BookController {
    @Autowired
    private lateinit var bookService: BookService

    @PostMapping("/books/new-book")
    fun addNewBook(@RequestBody request : NewBook):ResponseEntity<out NewBookResponse> {
        try{
            val newBook = bookService.addNewBooks(request)
            return ResponseEntity.status(200).body(newBook)
        } catch (e: ServiceException) {
            return ResponseEntity.status(e.status).body(NewBookResponse("failed", e.message.toString()))
        }
        catch (e: Exception) {
            return ResponseEntity.status(500).body(NewBookResponse("failed", "Failed to add new book"))
        }
    }

    @GetMapping("/books/category/{categoryId}")
    fun getBooksByCategory(@PathVariable("categoryId", required = true) categoryId:Long): ResponseEntity<out BooksByCategoryResponse> {
        try{
            val books = bookService.getBooksByCategoryId(categoryId)
            return ResponseEntity.status(200).body(books)
        } catch (e: ServiceException) {
            return ResponseEntity.status(e.status).body(BooksByCategoryResponse("failed", e.message.toString(), null))
        }
        catch (e: Exception) {
            return ResponseEntity.status(500).body(BooksByCategoryResponse("failed", "Failed to get books by category", null))
        }
    }

    @GetMapping("/books/book-details/{bookId}")
    fun getBookDetails(@PathVariable("bookId", required = true) bookId:Long): ResponseEntity<out BookDetailResponse> {
        try{
            val books = bookService.getBookDetails(bookId)
            return ResponseEntity.status(200).body(books)
        } catch (e: ServiceException) {
            return ResponseEntity.status(e.status).body(BookDetailResponse("failed", e.message.toString(), null))
        }
        catch (e: Exception) {
            return ResponseEntity.status(500).body(BookDetailResponse("failed", "Failed to get book details", null))
        }
    }

    @GetMapping("/books/all-books")
    fun getAllBooksByFilter(@RequestParam("filter", required = true) filter: String): ResponseEntity<out BooksByFiltersResponse> {
        try{
            val books = bookService.getBooksbyFilters(filter)
            return ResponseEntity.status(200).body(books)
        } catch (e: ServiceException) {
            return ResponseEntity.status(e.status).body(BooksByFiltersResponse("failed", e.message.toString(), null))
        }
        catch (e: Exception) {
            return ResponseEntity.status(500).body(BooksByFiltersResponse("failed", "Failed to get all books", null))
        }
    }
}