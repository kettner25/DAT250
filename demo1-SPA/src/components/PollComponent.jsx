import React from "react";
import "./poll.css";

import { useState } from "react";
import Vote from "./VoteComponent";

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

let polls = [];

export default function Polls() {
    return (
        <div className="polls">
            {polls.map(p => <Poll key={p.id} id={p.id} />)}
        </div>
    );
}

export function NewPoll() {
    const [question, setQuestion] = useState("");
    const [valid, setDate] = useState("");
    const [opts, setOpts] = useState([]);

    const handleSubmit = (e) => {
        e.preventDefault();
        
        if (question.trim() === "" || opts == null || opts.length < 1) return;
        
        if (isNaN(Date.parse(valid)) || new Date(valid) <= new Date(Date.now())) return;

        console.log("Creating poll:", question, valid, opts);

        // Create new poll object
        const newPoll = {
            id: polls.length + 1,
            question: question,
            publishedAt: new Date(Date.now()).toISOString(),
            validUntil: valid,
            voteOpts: opts,
        };
        polls.push(newPoll);

        // Reset form
        setQuestion("");
        setDate("");
        setOpts([]);
    };

    return (
        <dialog className="new-poll">
            <button className="close">×</button>
            <h2>Create New Poll</h2>
            <form method="dialog" onSubmit={handleSubmit}>
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
                    <button type="reset">Cancel</button>
                </menu>
            </form>
        </dialog>
    );
}

export function Poll(id) {
    let poll = getPollById(id);   
    
    if (poll == null || poll == undefined) return;

    return (
      <div className="poll">
        <h2>{poll.question}</h2>
        <div>
            <Opts key={poll.voteOpts} data={poll.voteOpts} />
        </div>
      </div>  
    );
}

function getPollById(id) {
    return polls.find(p => p.id == id.id);
}

export function Opts({data}) {
    let index = 0;

    return (
        <ul className="opts">
            {data.map(o => 
            <li key={index++}>
                {o.caption}
                <Vote optId={o.id} />
            </li>)}
        </ul>
    );
}

export function NewOpt({opts, setOpts}) {
    const [caption, setOpt] = useState(""); 

    const addOpt = (e) => {
        e.preventDefault();
        if (caption.trim() === "") return;
        setOpts([...opts, {
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