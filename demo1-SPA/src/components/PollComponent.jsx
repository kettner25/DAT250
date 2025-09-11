import React from "react";
import "./poll.css";

import { useState } from "react";
import Vote from "./VoteComponent";

import { Link, useNavigate } from "react-router-dom";

/*Poll consisted of
Poll {
    question: String,
    publishedAt: Date,
    validUntil: Date
    voteOpts: [Object]
}
/**/

/*VoteOpt consisted of
VoteOpt {
    caption: String,
    presentationOrder: Number
    votes: [Object]
}
/**/

/**
 * Polls component is responsible for rendering all polls for user and rendering VoteComponents. So you can be able to vote.
 * @param {Array} polls - List of polls
 * @param {Array} votes - List of votes
 * @param {Function<Array>} setVotes - Function to set votes
 * @returns 
 */
export default function Polls({polls, votes, setVotes}) {
    if (polls == null || polls == undefined) 
        return <div className="error">
            <div>User not selected</div>
            <Link to="/User">Select User</Link>
        </div>;

    if (polls == null || polls.length < 1) return <div>No polls available.</div>;

    return (
        <div className="polls">
            {polls.map(p => <Poll key={p.id} id={p.id} polls={polls} votes={votes} setVotes={setVotes} />)}
        </div>
    );
}

/**
 * NewPoll component does create new Poll by selected user and also do add new Options for the Pol
 * @param {Array} polls - List of polls
 * @param {Function<Array>} setPolls - Function to set polls
 * @returns 
 */
export function NewPoll({polls, setPolls}) {
    if (polls == null || polls == undefined) 
        return <div className="error">
            <div>User not selected</div>
            <Link to="/User">Select User</Link>
        </div>;

    const [question, setQuestion] = useState("");
    const [valid, setDate] = useState("");
    const [opts, setOpts] = useState([]);

    const handleSubmit = (e) => {
        e.preventDefault();
        
        if (question.trim() === "" || opts == null || opts.length < 2) return;
        
        if (isNaN(Date.parse(valid)) || new Date(valid) <= new Date(Date.now())) return;

        console.log("Creating poll:", question, valid, opts);

        // Create new poll object
        const newPoll = {
            id: polls.length + 1,
            question: question,
            publishedAt: new Date(Date.now()).toISOString(),
            validUntil: new Date(valid).toISOString(),
            voteOpts: opts,
        };
        setPolls([...polls, newPoll]); 
        //polls.push(newPoll);

        // Reset form
        handleReset();
    };

    const handleReset = () => {
        setQuestion("");
        setDate("");
        setOpts([]);
    };

    let navigate = useNavigate();
    
    const closeDialog = () => {
        handleReset();
        navigate("/Polls");
    };

    return (
        <dialog className="new-poll">
            <button className="close" onClick={closeDialog}>×</button>
            <h2>Create New Poll</h2>
            <form method="dialog" onSubmit={handleSubmit} onReset={handleReset}>
                <label>
                    Question:
                    <input type="text" name="question" value={question} onChange={(e) => setQuestion(e.target.value)} required />
                </label>
                <label>
                    Valid Until:
                    <input type="date" name="validUntil" value={valid} onChange={(e) => setDate(e.target.value)} required />
                </label>
                <NewOpt opts={opts} setOpts={setOpts} />
                
                {/* Display current options */}
                <ul>
                    {opts.map((o, index) => <li key={index}>{o.presentationOrder}: {o.caption}</li>)}
                </ul>

                <menu>
                    <button type="submit">Create Poll</button>
                    <button type="reset">Clear</button>
                </menu>
            </form>
        </dialog>
    );
}

/**
 * Poll component render one poll in Polls
 * @param {Number} id - Poll id
 * @param {Array} polls - List of polls
 * @param {Array} votes - List of votes
 * @param {Function<Array>} setVotes - Function to set votes 
 * @returns 
 */
export function Poll({id, polls, votes, setVotes}) {
    let poll = polls?.find(p => p.id == id);   
    
    if (poll == null || poll == undefined) return;

    return (
      <div className="poll">
        <h2>{poll.question}</h2>
        <div>
            <Opts key={poll.voteOpts} data={poll.voteOpts} votes={votes} setVotes={setVotes} />
        </div>
      </div>  
    );
}


/**
 * Opts render options in Polls
 * @param {Array} data - List of options
 * @param {Array} votes - List of votes
 * @param {Function<Array>} setVotes - Function to set votes
 * @returns 
 */
export function Opts({data, votes, setVotes}) {
    return (
        <ul className="opts">
            {data.map((o, index) => 
            <li key={index}>
                {o.caption}
                <Vote opt={o} votes={votes} setVotes={setVotes} />
            </li>)}
        </ul>
    );
}

/**
 * NewOpts add new option when creating poll
 * @param {Array} opts - List of options
 * @param {Function<Array>} setOpts - Function to set options 
 * @returns 
 */
export function NewOpt({opts, setOpts}) {
    const [caption, setOpt] = useState(""); 

    const addOpt = (e) => {
        e.preventDefault();
        if (caption.trim() === "") return;
        setOpts([...opts, {
            id: opts.length + 1,
            caption: caption,
            presentationOrder: opts.length + 1
        }]);
        setOpt("");
    };

    return (
        <div className="new-opt">
            <h3>Options</h3>
            <div>
                <input type="text" value={caption} onChange={(e) => setOpt(e.target.value)} />
                <button onClick={addOpt}>Add Option</button>
            </div>
        </div>
    );
}