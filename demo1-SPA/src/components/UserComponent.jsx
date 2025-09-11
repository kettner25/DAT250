import { use, useState } from "react";
import "./user.css"

/*User consisted of
User {
    username: String,
    email: String

    created: [Poll]
    voted: [Vote]
}
/**/

/**
 * User on the other hand render all users and handle selection process. 
 * @param {Array} users - List of users
 * @param {Function<Array>} setUsers - Function to set users
 * @param {Array} username - Currently selected username
 * @param {Function<Array>} setUsername - Function to set username 
 * @returns 
 */
export default function User({users, setUsers, username, setUsername}) {
    const handleClick = (user) => {
        if (user == null || user == undefined) return;

        console.log("User selected:", user.username);

        setUsername(user.username);
    };
    
    return (
        <div className="users">
            {users.map(u =>
                <div className={u.username == username ? "user selected" : "user"} onClick={() => handleClick(u)} key={u.username} data-key={u.username}>
                    <div> Name: {u.username} </div> 
                    <div> Email: {u.email} </div>
                </div>
                )
            }
            <NewUser users={users} setUsers={setUsers} />
        </div>
    );
};


/**
 * NewUser is component that create new users.
 * @param {Array} users - List of users
 * @param {Function<Array>} setUsers - Function to set users
 * @returns 
 */
export function NewUser({users, setUsers}) {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        if (username.trim() === "" || email.trim() === "") return;
        console.log("Creating user:", username, email);
        
        let newUser = {
            username: username,
            email: email,
            created: [],
            voted: []
        };
        setUsers([...users, newUser]);
        
        setUsername("");
        setEmail("");
    }

    return (
        <div className="new-user">
            <h2>Create New User</h2>
            <form onSubmit={handleSubmit}>
                <label>
                    Username:
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </label>
                <label>
                    Email:
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </label>
                <button type="submit">Create User</button>
            </form>
        </div>
    );
}