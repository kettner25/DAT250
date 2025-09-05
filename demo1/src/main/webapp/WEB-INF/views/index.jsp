<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Voting site</title>
    <script src="${pageContext.request.contextPath}/js/script.js"></script>
</head>
<body>
<h1>Voting site</h1>

<div>
    <button onclick="newUser()">Create User</button>
    <button onclick="newVoteOpt()">Create Vote Option</button>
    <button onclick="newPoll()">Create Poll</button>
    <button onclick="newVote()">Create Vote</button>
</div>

<h2>Users</h2>
<div id="users">
    <c:forEach var="i" items="${data.getData().getUsers()}">
        <div class="user" data-id="${i.getUsername()}">
            <span>Username:</span>
            <span>${i.getUsername()}</span>
            <span>Email:</span>
            <span>${i.getEmail()}</span>

            <div class="polls">
                <c:forEach var="poll" items="${i.getCreated()}">
                    <div class="poll">
                        <span>Question:</span>
                        <span>${poll.getQuestion()}</span>
                        <span>Date</span>
                        <span>${poll.getPublishedAt()}-${poll.getValidUntil()}</span>
                        <div class="polls-opts">
                            <c:forEach var="opt" items="${poll.getVoteOpts()}">
                                <div class="vote-opt">${opt.getPresentationOrder()}</div>
                            </c:forEach>
                        </div>
                        <button onclick="UpdatePoll(${i.getId()})">Update</button>
                        <button onclick="DetelePoll(${i.getId()})">Delete</button>
                    </div>
                </c:forEach>
            </div>

            <div class="votes">
                <c:forEach var="vote" items="${i.getVoted()}">
                    <div class="vote">
                        <span>Vote option</span>
                        <span>${vote.getVoteOpt().getPresentationOrder()}</span>
                        <span>Date</span>
                        <span>${vote.getPublishedAt()}</span>

                        <button onclick="UpdateVote(${i.getUsername()}, ${vote.getVoteOpt().getId()})">Update</button>
                        <button onclick="DeteleVote(${i.getUsername()}, ${vote.getVoteOpt().getId()})">Delete</button>
                    </div>
                </c:forEach>
            </div>
            <button onclick="UpdateUser(${i.getUsername()})">Update</button>
            <button onclick="DeteleUser(${i.getUsername()})">Delete</button>
        </div>
        <hr>
    </c:forEach>
</div>

<h2>Vote Options</h2>
<ul>
    <c:forEach var="i" items="${data.getData().getVoteOpts()}">
        <li data-id="${i.getId()}">
            <span>${i.getPresentationOrder()}</span>
            <span>${i.getCaption()}</span>

            <button onclick="UpdateVoteOpt(${i.getId()})">Update</button>
            <button onclick="DeteleVoteOpt(${i.getId()})">Delete</button>
        </li>
    </c:forEach>
</ul>

<dialog id="CreateVoteOptDialog">
    <input class="VoteOptCaption" type="text" placeholder="Caption">
    <input class="VoteOptOrder" type="number" placeholder="Order">

    <button onclick="SendVoteOptC()">Create</button>
</dialog>

<dialog id="UpdateVoteOptDialog">
    <input class="VoteOptCaption" type="text" placeholder="Caption">
    <input class="VoteOptOrder" type="number" placeholder="Order">

    <button onclick="SendVoteOptU()">Update</button>
</dialog>

<dialog id="CreateVoteDialog">
    <select class="VoteUser">
        <c:forEach var="i" items="${data.getData().getUsers()}">
            <option value="${i.getUsername()}">${i.getUsername()}</option>
        </c:forEach>
    </select>
    <select class="VoteVoteOpt">
        <c:forEach var="i" items="${data.getData().getVoteOpts()}">
            <option value="${i.getId()}">${i.getPresentationOrder()}</option>
        </c:forEach>
    </select>

    <button onclick="SendVoteC()">Create</button>
</dialog>

<dialog id="UpdateVoteDialog">
    <select class="VoteVoteOpt">
        <c:forEach var="i" items="${data.getData().getVoteOpts()}">
            <option value="${i.getId()}">${i.getPresentationOrder()}</option>
        </c:forEach>
    </select>

    <button onclick="SendVoteU()">Update</button>
</dialog>

<dialog id="CreatePollDialog">
    <input class="PollQuestion" type="text" placeholder="Question">
    <input class="PollDate" type="date">

    <select class="VoteVoteOpt" multiple="multiple">
        <c:forEach var="i" items="${data.getData().getVoteOpts()}">
            <option value="${i.getId()}">${i.getPresentationOrder()}</option>
        </c:forEach>
    </select>

    <button onclick="SendPollC()">Create</button>
</dialog>

<dialog id="UpdatePollDialog">
    <input class="PollQuestion" type="text" placeholder="Question">
    <input class="PollDate" type="date">

    <select class="VoteVoteOpt" multiple="multiple">
        <c:forEach var="i" items="${data.getData().getVoteOpts()}">
            <option value="${i.getId()}">${i.getPresentationOrder()}</option>
        </c:forEach>
    </select>

    <button onclick="SendPollU()">Update</button>
</dialog>

<dialog id="CreateUserDialog">
    <input class="UserName" type="text" placeholder="Username">
    <input class="UserEmail" type="text" placeholder="Email">

    <button onclick="SendUserC()">Create</button>
</dialog>

<dialog id="UpdateUserDialog">
    <input class="UserName" type="text" placeholder="Username">
    <input class="UserEmail" type="text" placeholder="Email">

    <button onclick="SendUserU()">Update</button>
</dialog>

</body>
</html>