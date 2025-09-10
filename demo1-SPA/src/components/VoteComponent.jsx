
import { useState } from "react";

export default function Vote({ optId }) {
    const [voted, setVoted] = useState(false);  
    const handleVote = () => {
        if (!voted) {
        }
        setVoted(true);
    };

    return (
        <button onClick={handleVote} disabled={voted}>
            {voted ? "Voted" : "Vote"}
        </button>
    );
}