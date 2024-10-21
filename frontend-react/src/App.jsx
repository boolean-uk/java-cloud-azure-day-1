import React, { useState, createContext, useEffect } from "react";
import "./App.css";
import { Route, Routes } from "react-router-dom";
import Home from "./components/Home";
import BookPage from "./components/BookPage";

const BookContext = createContext();

function App() {
  const [books, setBooks] = useState([]);

  // Get books
  useEffect(() => {
    fetch("http://localhost:5000/library")
      .then((res) => res.json())
      .then((data) => {
        console.log("App - Books:", data);
        setBooks(data.data);
      });
  }, []);

  return (
    <>
      <BookContext.Provider value={{ books, setBooks }}>
        <div className="main-content">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/book/:id" element={<BookPage />} />
          </Routes>
        </div>
      </BookContext.Provider>
    </>
  );
}

export { App, BookContext };
