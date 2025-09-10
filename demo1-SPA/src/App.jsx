// App.jsx
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Polls, { NewPoll } from "./components/PollComponent";
import User from "./components/UserComponent";
import { useState } from "react";

function App() {
  const [polls, setPolls] = useState([]);
  const [users, setUsers] = useState([]);
  const [currentUser, setCurrentUser] = useState(null);

  return (
    <BrowserRouter>
      <nav className="p-4 bg-gray-200 flex gap-4">
        <Link to="/User">Create User</Link>
        <Link to="/NewPoll">Create Poll</Link>
        <Link to="/Polls">View Polls</Link>
      </nav>

      <div className="p-6">
        <Routes>
          <Route path="/User" element={<User users={users} setUsers={setUsers} user={currentUser} setUser={setCurrentUser} />} />
          <Route path="/NewPoll" element={<NewPoll polls={polls} setPolls={setPolls} />} />
          <Route path="/Polls" element={<Polls polls={polls} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App
