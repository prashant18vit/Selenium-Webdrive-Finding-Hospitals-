import React from "react";

function Tweet(props) {
    return (
        <div>
            Tweet
            <h1>Your Tweets</h1>
            <h2>{props.name}</h2>
            <h3>{props.tweet}</h3>
        </div>
    );
}

export default Tweet;
