// App.jsx
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Polls, { NewPoll } from "./components/PollComponent";
import User from "./components/UserComponent";

function App() {
  return (
    <BrowserRouter>
      <nav className="p-4 bg-gray-200 flex gap-4">
        <Link to="/User">Create User</Link>
        <Link to="/NewPoll">Create Poll</Link>
        <Link to="/Polls">View Polls</Link>
      </nav>

      <div className="p-6">
        <Routes>
          <Route path="/User" element={<User />} />
          <Route path="/NewPoll" element={<NewPoll />} />
          <Route path="/Polls" element={<Polls />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App
