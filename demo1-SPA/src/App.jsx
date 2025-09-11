// App.jsx
import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Polls, { NewPoll } from "./components/PollComponent";
import User from "./components/UserComponent";
import { useState } from "react";

import "./app.css";

const URL = "http://localhost:8080";

function App() {
  const [users, _setUsers] = useState([]);
  const [currentUsername, setCurrentUsername] = useState("");

  //---------------------------------

  const setUsers = (array, db = true) => {
    if (db) {
      // get user that was added
      const oldArray = users.map(u => u.username);
      const newArray = array.map(u => u.username);
      const user = newArray.filter(item => !oldArray.includes(item)).at(0);
      
      if (user == null || user == undefined) return;

      try {
        fetch(`${URL}/user/`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(array.find(u => u.username == user)),
        });
      } catch (error) {
        console.error("Error adding user:", error);
      }
    }
    _setUsers(array);
  }

  const fetchUsers = async () => {
    try {
      const response = await fetch(`${URL}/user/`);
      const data = await response.json();
      setUsers(data, false);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  //---------------------------------

  const sendOptions = async (options) => {
    if (!currentUser) return;

    try {
      for (let i = 0; i < options.length; i++) {
        let res = await fetch(`${URL}/opt/`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(options[i]),
        });

        if (!res.ok) throw new Error(data.message || "Failed to add options");
        const data = await res.json();
        if (data == null || data == undefined || data == -1) throw new Error("Failed to add options");

        options[i].id = data;
      }
    } catch (error) {
      console.error("Error adding options:", error);
    }
  };

  //---------------------------------

  const setPolls = async (array, db = true) => {
    if (!currentUser) return;

    if (db) {
      // get poll that was added
      const oldArray = currentUser.created.map(p => p.id);
      const newArray = array.map(p => p.id);
      const poll = newArray.filter(item => !oldArray.includes(item)).at(0);
      
      if (poll == null || poll == undefined) return;

      const __poll = array.find(p => p.id == poll);

      await sendOptions(__poll.voteOpts);

      try {
        let res = await fetch(`${URL}/poll/${currentUser.username}`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(__poll),
        });

        if (!res.ok) throw new Error(res.message || "Failed to add poll");
        const data = await res.json();
        if (data == null || data == undefined || data == -1) throw new Error("Failed to add poll");

        __poll.id = data; // set the id returned from the server
      } catch (error) {
        console.error("Error adding poll:", error);
      }
    }

    // update user's created polls

    _setUsers((prev) =>
      prev.map((user) =>
        (user.username == currentUser.username ? { ...user, created: array } : user)
      )
    );
  };

  const fetchPolls = async () => {
    if (!currentUser) return;

    try {
      const response = await fetch(`${URL}/poll/${currentUser.username}`);
      const data = await response.json();
      setPolls(data, false);
    } catch (error) {
      console.error("Error fetching polls:", error);
    }
  };

  //---------------------------------

  const setVotes = (array, db = true) => {
    if (!currentUser) return;

    if (db) {
      // get vote that was added or removed
      const oldArray = currentUser.voted.map(v => v.option.id);
      const newArray = array.map(v => v.option.id);
      const voteAdded = newArray.filter(item => !oldArray.includes(item)).at(0);
      const voteRemoved = oldArray.filter(item => !newArray.includes(item)).at(0);

      try {
        if (voteAdded != null && voteAdded != undefined) {
          fetch(`${URL}/vote/${currentUser.username}`, {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(array.find(v => v.option.id == voteAdded)),
          });
        } else if (voteRemoved != null && voteRemoved != undefined) {
          fetch(`${URL}/vote/${currentUser.username}/${voteRemoved}`, {
            method: "DELETE",
          });
        }
      } catch (error) {
        console.error("Error updating votes:", error);
      }
    }

    // update user's voted polls

    _setUsers((prev) =>
      prev.map((user) =>
        (user.username == currentUser?.username ? { ...user, voted: array } : user)
      )
    );
  };

  const fetchVotes = async () => {
    if (!currentUser) return;

    try {
      const response = await fetch(`${URL}/vote/${currentUser.username}`);
      const data = await response.json();
      setVotes(data, false);
    } catch (error) {
      console.error("Error fetching votes:", error);
    }
  };

  //---------------------------------

  const fetchVoting = async () => {
    await fetchPolls();
    await fetchVotes();
  };

  // Fetch users when the component mounts
  useState(() => {
    fetchUsers();
  }, []);

  const currentUser = users.find((u) => u.username == currentUsername);

  return (
    <BrowserRouter>
      <nav className="p-4 bg-gray-200 flex gap-4 main-nav">
        <Link to="/User" onClick={fetchUsers}>Select User</Link>
        <Link to="/NewPoll">Create Poll</Link>
        <Link to="/Polls" onClick={fetchVoting}>View Polls</Link>
      </nav>

      <div className="p-6 content">
        <Routes>
          <Route path="/User" element={<User users={users} setUsers={setUsers} username={currentUsername} setUsername={setCurrentUsername} />} />
          <Route path="/NewPoll" element={<NewPoll polls={currentUser?.created} setPolls={setPolls} />} />
          <Route path="/Polls" element={<Polls polls={currentUser?.created} votes={currentUser?.voted} setVotes={setVotes} />} />
        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App
