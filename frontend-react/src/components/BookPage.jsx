import React, { useContext } from "react";
import { useParams } from "react-router-dom";
import { BookContext } from "../App";

function BookPage() {
  const { id } = useParams();
  const { books } = useContext(BookContext);
  const book = books.find((book) => book.id === Number(id));
  console.log("BookPage - Book:", book);

  return (
    <div>
      <h2>{book?.title}</h2>
      <p>Author: {book?.author}</p>
      <p>Available: {book?.borrowed.toString() ? "Yes" : "No"}</p>
    </div>
  );
}

export default BookPage;