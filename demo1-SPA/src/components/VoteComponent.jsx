
import { useState } from "react";

/*Vote consisted of
Vote {
    publishedAt: Date,
    option: VoteOption
}
/**/


/**
 * Vote component react on user voting for spec. vote
 * @param {Object} opt - Vote option
 * @param {Array} votes - List of votes
 * @param {Function<Array>} setVotes - Function to set votes
 * @returns 
 */
export default function Vote({ opt, votes, setVotes }) {
    const voted = votes?.some(v => v.option.id == opt.id);
    
    const handleVote = () => {
        console.log("Voted for option ID:", opt.id, " Voted:", !voted);

        if (voted) setVotes(votes.filter((vote) => vote.option.id != opt.id));
        else setVotes([...votes, { publishedAt: new Date(Date.now()).toISOString(), option: opt }]);
    };

    return (
        <button onClick={handleVote} className={voted ? "voted" : ""}>
            {voted ? "Unvote" : "Vote"}
        </button>
    );
}