package com.eminokumus.bookvox

import com.eminokumus.bookvox.model.Book

object Constants {
    val bookList = listOf(
        Book(
            id = 1,
            name = "1984",
            author = "George Orwell",
            imageUrl = "https://m.media-amazon.com/images/I/71kxa1-0mfL.jpg"
        ),
        Book(
            id = 2,
            name = "To Kill a Mockingbird",
            author = "Harper Lee",
            imageUrl = "https://m.media-amazon.com/images/I/81OdwZ23H8L.jpg"
        ),
        Book(
            id = 3,
            name = "The Great Gatsby",
            author = "F. Scott Fitzgerald",
            imageUrl = "https://m.media-amazon.com/images/I/81t2CVWEsUL.jpg"
        ),
        Book(
            id = 4,
            name = "Pride and Prejudice",
            author = "Jane Austen",
            imageUrl = "https://m.media-amazon.com/images/I/81OthjkJBuL.jpg"
        ),
        Book(
            id = 5,
            name = "Moby-Dick",
            author = "Herman Melville",
            imageUrl = "https://m.media-amazon.com/images/I/81I-QxD5QIL.jpg"
        )
    )
}