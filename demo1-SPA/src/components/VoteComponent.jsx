
import { useState } from "react";

/*Vote consisted of
Vote {
    publishedAt: Date,
    option: VoteOption
}
/**/

export default function Vote({ opt, votes, setVotes }) {
    const voted = votes?.some(v => v.option.id == opt.id);
    
    const handleVote = () => {
        console.log("Voted for option ID:", opt.id, " Voted:", !voted);

        if (voted) setVotes(votes.filter((vote) => vote.option.id != opt.id));
        else setVotes([...votes, { publishedAt: new Date(Date.now()).toISOString(), option: opt }]);
    };

    return (
        <button onClick={handleVote}>
            {voted ? "Unvote" : "Vote"}
        </button>
    );
}