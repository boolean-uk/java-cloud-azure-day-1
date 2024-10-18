import { useEffect, useState } from "react";
import "./App.css";
import Dashboard from "./pages/Dashboard";
import BorrowableDetails from "./pages/Borrowables/BorrowableDetails";
import CreateBorrowable from "./pages/Borrowables/CreateBorrowable";
import CreateUser from "./pages/Users/CreateUser";
import UserSwitcher from "./pages/UserSwitcher";
import { createContext } from "react";
import { Routes, Route, Link } from "react-router-dom";

const BorrowablesContext = createContext();
const BorrowedContext = createContext();
const UserContext = createContext();

export function App() {
  const [borrowables, setBorrowables] = useState([]);
  const [borrowed, setBorrowed] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    if (borrowables.length === 0) {
      fetch("http://localhost:5000/borrowables")
        .then((response) => {
          console.log("recieved response", response);
          console.log("recieved answer");
        })
        .then((data) => setBorrowables(data.results));
    }
  }, [borrowables]);

  return (
    <>
      <BorrowablesContext.Provider value={{ borrowables, setBorrowables }}>
        <BorrowedContext.Provider value={{ borrowed, setBorrowed }}>
          <UserContext.Provider value={{ currentUser, setCurrentUser }}>
            <header>
              <h1>Borrowable Items</h1>
              <nav>
                <Link to="/">Dashboard</Link>
              </nav>
              <UserSwitcher />
            </header>
            <Routes>
              <Route path="/" element={<Dashboard />} />
              <Route path="/view/:id" element={<BorrowableDetails />} />
              <Route path="/create/borrowable" element={<CreateBorrowable />} />
              <Route path="/create/user" element={<CreateUser />} />
            </Routes>
          </UserContext.Provider>
        </BorrowedContext.Provider>
      </BorrowablesContext.Provider>
    </>
  );
}

export { BorrowablesContext, BorrowedContext, UserContext, App as default };
