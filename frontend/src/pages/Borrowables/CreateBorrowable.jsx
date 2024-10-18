import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { BorrowablesContext } from '../../App';

export default function CreateBorrowable() {
  const { setBorrowables } = useContext(BorrowablesContext);
  const [borrowableType, setBorrowableType] = useState('book');
  const [formData, setFormData] = useState({
    title: '',
    author: '',
    publisher: '',
    genre: '',
    releaseYear: '', // Only for games
  });
  const navigate = useNavigate();

  const handleInputChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const url = `http://localhost:5000/borrowables/${borrowableType}`;

    fetch(url, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData),
    })
      .then((response) => response.json())
      .then((data) => {
        setBorrowables((prev) => [...prev, data.result]); // Add the new borrowable to context
        navigate('/'); // Redirect to the dashboard
      })
      .catch((error) => {
        console.error('Error creating borrowable:', error);
      });
  };

  return (
    <div className="create-borrowable">
      <h2>Create a New Borrowable</h2>
      <form onSubmit={handleSubmit}>
        <label htmlFor="type">Type</label>
        <select
          id="type"
          value={borrowableType}
          onChange={(e) => setBorrowableType(e.target.value)}
        >
          <option value="book">Book</option>
          <option value="game">Game</option>
        </select>

        <label htmlFor="title">Title</label>
        <input
          type="text"
          id="title"
          name="title"
          value={formData.title}
          onChange={handleInputChange}
          required
        />

        {borrowableType === 'book' && (
          <>
            <label htmlFor="author">Author</label>
            <input
              type="text"
              id="author"
              name="author"
              value={formData.author}
              onChange={handleInputChange}
              required
            />
          </>
        )}

        <label htmlFor="publisher">Publisher</label>
        <input
          type="text"
          id="publisher"
          name="publisher"
          value={formData.publisher}
          onChange={handleInputChange}
          required
        />

        <label htmlFor="genre">Genre</label>
        <input
          type="text"
          id="genre"
          name="genre"
          value={formData.genre}
          onChange={handleInputChange}
          required
        />

        {borrowableType === 'game' && (
          <>
            <label htmlFor="releaseYear">Release Year</label>
            <input
              type="number"
              id="releaseYear"
              name="releaseYear"
              value={formData.releaseYear}
              onChange={handleInputChange}
              required
            />
          </>
        )}

        <button type="submit">Create {borrowableType === 'book' ? 'Book' : 'Game'}</button>
      </form>
    </div>
  );
}
