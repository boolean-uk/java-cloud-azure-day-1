import { useContext } from "react";
import { BorrowablesContext, BorrowedContext, UserContext } from "../../App";

function Dashboard() {
  const { borrowables, setBorrowables } = useContext(BorrowablesContext);
  const { borrowed, setBorrowed } = useContext(BorrowedContext);
  const { currentUser } = useContext(UserContext);

  const handleBorrow = (itemId) => {
    fetch(
      `http://localhost:5000/borrowables/${currentUser.id}/borrow/${itemId}`,
      {
        method: "POST",
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        if (data.result) {
          setBorrowed((prevBorrowed) => [...prevBorrowed, data.result]); // Add
          setBorrowables((prevBorrowables) =>
            prevBorrowables.filter((b) => b.id !== itemId)
          ); // Remove from available
        }
      })
      .catch((error) => {
        console.error("Error borrowing item:", error);
      });
  };

  const handleReturn = (itemId) => {
    fetch(
      `http://localhost:5000/borrowables/${currentUser.id}/return/${itemId}`,
      {
        method: "POST",
      }
    )
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((data) => {
        if (data.result) {
          setBorrowed((prevBorrowed) =>
            prevBorrowed.filter((b) => b.id !== itemId)
          ); // Remove
          setBorrowables((prevBorrowables) => [
            ...prevBorrowables,
            data.result,
          ]); // Add back to available
        }
      })
      .catch((error) => {
        console.error("Error returning item:", error);
      });
  };

  return (
    <div>
      <h2>Available Borrowables</h2>
      <ul>
        {typeof borrowables !== 'undefined' ? borrowables.map(item => (
          <li key={item.id}>
            {item.title} ({item.type}) - <button onClick={() => handleBorrow(item.id)}>Borrow</button>
          </li>
        )) : ""}
      </ul>

      <h2>Borrowed Items</h2>
      <ul>
        {typeof borrowed !== 'undefined' ? borrowed.map(item => (
          <li key={item.id}>
            {item.title} ({item.type}) - <button onClick={() => handleReturn(item.id)}>Return</button>
          </li>
        )) : ""}
      </ul>
    </div>
  );
}

export default Dashboard;