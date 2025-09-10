// App.jsx
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Polls, { NewPoll } from "./components/PollComponent";
import User from "./components/UserComponent";
import { useState } from "react";

function App() {
  const [users, setUsers] = useState([]);
  const [currentUsername, setCurrentUsername] = useState("");

  const setPolls = (array) => {
    if (!currentUser) return;

    setUsers((prev) =>
      prev.map((user) =>
        (user.username == currentUser.username ? { ...user, created: array } : user)
      )
    );
  };

  const setVotes = (array) => {
    setUsers((prev) =>
      prev.map((user) =>
        user.username == currentUser?.username ? { ...user, voted: array } : user
      )
    );
  };

  const currentUser = users.find((u) => u.username == currentUsername);

  return (
    <BrowserRouter>
      <nav className="p-4 bg-gray-200 flex gap-4">
        <Link to="/User">Create User</Link>
        <Link to="/NewPoll">Create Poll</Link>
        <Link to="/Polls">View Polls</Link>
      </nav>

      <div className="p-6">
        <Routes>
          <Route path="/User" element={<User users={users} setUsers={setUsers} username={currentUsername} setUsername={setCurrentUsername} />} />
          <Route path="/NewPoll" element={<NewPoll polls={currentUser?.created} setPolls={setPolls} />} />
          <Route path="/Polls" element={<Polls polls={currentUser?.created} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App
