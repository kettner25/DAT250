
import { useState } from "react";

export default function Vote({ optId }) {
    const [voted, setVoted] = useState(false);  
    const handleVote = () => {
        console.log("Voted for option ID:", optId);
        
        setVoted(!voted);
    };

    return (
        <button onClick={handleVote} disabled={voted}>
            {voted ? "Voted" : "Vote"}
        </button>
    );
}