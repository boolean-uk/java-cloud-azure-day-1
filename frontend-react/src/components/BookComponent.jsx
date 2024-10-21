import { Link } from 'react-router-dom';
import './styling/BookComponent.css';
import React from 'react';

const BookComponent = ({ book, handleBorrowReturn }) => {

  return (
    <div className="book">
      <h3><Link to={`/book/${book.id}`}> {book.title} </Link> </h3>
      <p>Status: {book.borrowed ? 'Borrowed' : 'Available'}</p>
      <button onClick={() => handleBorrowReturn(book.id, book.borrowed)}>
        {book.borrowed ? 'Return' : 'Borrow'}
      </button>
    </div>
  );
};

export default BookComponent;