import React, { useContext } from "react";
import { useEffect, useState } from "react";
import { BookContext } from "../App";
import BookComponent from "./BookComponent";
import './styling/Home.css';

function Home() {
  const { books } = useContext(BookContext);
  const [borrowedBooks, setBorrowedBooks] = useState([]);
  const [availableBooks, setAvailableBooks] = useState([]);

  useEffect(() => {
    console.log("Home - Books:", books);
    if (books) {
      setBorrowedBooks(books.filter((book) => book.borrowed));
      setAvailableBooks(books.filter((book) => !book.borrowed));
    }
  }, [books]);

  const handleBorrowReturn = async (id, borrowed) => {
    const url = `http://localhost:5000/library/${id}/${borrowed ? 'return' : 'borrow'}`;
    
    try {
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({}),
      });

      if (response.ok) {
        const updatedBooks = books.map((book) => 
          book.id === id ? { ...book, borrowed: !borrowed } : book
        );
        setBorrowedBooks(updatedBooks.filter((book) => book.borrowed));
        setAvailableBooks(updatedBooks.filter((book) => !book.borrowed));
      }
    } catch (error) {
      console.error("Error:", error);
    }
  };

  return (
    <div>
      <h2>Available Books</h2>
      <div className="bookList">
        {availableBooks.map((book) => (
          <BookComponent key={book.id} book={book} handleBorrowReturn={handleBorrowReturn} />
        ))}
      </div>

      <h2>Borrowed Books</h2>
      <div className="bookList">
        {borrowedBooks.map((book) => (
          <BookComponent key={book.id} book={book} handleBorrowReturn={handleBorrowReturn} />
        ))}
      </div>
    </div>
  );
};

export default Home;