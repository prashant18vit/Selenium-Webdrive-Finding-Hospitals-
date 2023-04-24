import React from "react";

function AddTweet() {
    return (
        <div>
            <form action="submit">
                <textarea name="tweetText" id="" cols="30" rows="10"></textarea>
                <button>Add</button>
                <button>Delete </button>
            </form>
        </div>
    );
}

export default AddTweet;
