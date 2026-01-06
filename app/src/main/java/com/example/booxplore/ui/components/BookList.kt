package com.example.booxplore.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.example.booxplore.data.domain.model.Book
import androidx.compose.ui.unit.dp

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(books) { book ->
            BookListItem(
                book = book,
                onClick = { onBookClick(book) }
            )
        }
    }
}