var __name = null;
var __id = -1;

async function newUser() {
    document.getElementById("CreateUserDialog").style.display = "block";
}

async function SendUserC() {
    let name = document.getElementById("CreateUserDialog").getElementsByClassName("UserName")[0].value;
    let email = document.getElementById("CreateUserDialog").getElementsByClassName("UserEmail")[0].value;

    try {
        let res = await fetch("/user", {
            method: "POST",
            body: JSON.stringify({
                username: name,
                email: email,
            })
        })

        let data = await res.json();

        if (data == true) {}
        else alert("Error occurred!");
    }
    catch (error) { console.log(error); }

    location.reload();
}

async function SendUserU() {
    let name = document.getElementById("UpdateUserDialog").getElementsByClassName("UserName")[0].value;
    let email = document.getElementById("UpdateUserDialog").getElementsByClassName("UserEmail")[0].value;

    try {
        let res = await fetch("/user", {
            method: "PUT",
            body: JSON.stringify({
                username: name,
                email: email,
            })
        })

        let data = await res.json();

        if (data == true) {}
        else alert("Error occurred!");
    }
    catch (error) { console.log(error); }

    location.reload();
}

async function UpdateUser(name) {
    __name = name;
    document.getElementById("UpdateUserDialog").style.display = "block";
}

async function DeteleUser(name) {
    try {
        let res = await fetch("/user/"+name, {
            method: "DELETE"
        });

        let data = await res.json();

        if (data == true) {}
        else alert("Error occurred!");
    }
    catch (error) { console.log(error); }

    location.reload();
}

//------------------------------------------------------
async function newVoteOpt() {
    document.getElementById("CreateVoteOptDialog").style.display = "block";
}

async function UpdateVoteOpt(id) {
    __id = id;
    document.getElementById("UpdateVoteOptDialog").style.display = "block";
}

async function DeteleVoteOpt(id) {
    try {
        let res = await fetch("opt/"+id, {
            method: "DELETE"
        });

        let data = await res.json();

        if (data == true) {}
        else alert("Error occurred!");
    }
    catch (error) { console.log(error); }

    location.reload();
}
//------------------------------------------------------
async function newPoll() {
    document.getElementById("CreatePollDialog").style.display = "block";
}

async function UpdatePoll(id) {
    __id = id;
    document.getElementById("UpdatePollDialog").style.display = "block";
}

async function DetelePoll(id) {
    try {
        let res = await fetch("poll/"+id, {
            method: "DELETE"
        });

        let data = await res.json();

        if (data == true) {}
        else alert("Error occurred!");
    }
    catch (error) { console.log(error); }

    location.reload();
}

//------------------------------------------------------
async function newVote() {
    document.getElementById("CreateVoteDialog").style.display = "block";
}

async function UpdateVote(name, id) {
    __name = name;
    __id = id;
    document.getElementById("UpdateVoteDialog").style.display = "block";
}

async function DeteleVote(name, id) {
    try {
        let res = await fetch("vote/"+name+"/"+id, {
            method: "DELETE"
        });

        let data = await res.json();

        if (data == true) {}
        else alert("Error occurred!");
    }
    catch (error) { console.log(error); }

    location.reload();
}