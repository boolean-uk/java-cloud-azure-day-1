import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../../App';

function UserSwitcher() {
  const { currentUser, setCurrentUser } = useContext(UserContext);
  const [userId, setUserId] = useState("");
  const navigate = useNavigate();

  const handleUserSwitch = () => {
    fetch(`http://localhost:5000/borrowables/user/${userId}`)
      .then(response => {
        if (response.status === 404) {
          navigate("/create/user"); // If user not found, navigate to create user
        } else {
          return response.json();
        }
      })
      .then(data => {
        setCurrentUser(data.result);
      });
  };

  return (
    <div className="user-switcher">
      <input
        type="text"
        placeholder="Enter your user ID"
        value={userId}
        onChange={(e) => setUserId(e.target.value)}
      />
      <button onClick={handleUserSwitch}>Switch User</button>
      {currentUser && <p>Current User: {currentUser.username} (ID: {currentUser.id})</p>}
    </div>
  );
}

export default UserSwitcher;