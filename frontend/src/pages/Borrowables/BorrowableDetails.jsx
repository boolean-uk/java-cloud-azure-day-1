import React, { useContext, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { BorrowablesContext } from '../../App';

function BorrowableDetails() {
  const { id } = useParams();
  const { borrowables } = useContext(BorrowablesContext);
  const [borrowable, setBorrowable] = useState(null);

  useEffect(() => {
    const item = borrowables.find(b => b.id === parseInt(id));
    if (item) {
      setBorrowable(item);
    }
  }, [borrowables, id]);

  if (!borrowable) {
    return <p>Loading...</p>;
  }

  return (
    <div>
      <h2>{borrowable.title}</h2>
      <p>Type: {borrowable.type}</p>
      <p>Owner: {borrowable.title}</p>
      <p>Available: {borrowable.user ? "Yes" : "No"}</p>
      <p>Genre: {borrowable.genre}</p>
    </div>
  );
}

export default BorrowableDetails;
