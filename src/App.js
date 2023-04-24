import React, { Component } from "react";
import UserInfo from "./components/UserInfo";
import SearchBar from "./components/SearchBar";

export default class App extends Component {
    constructor() {
        super();
        this.state = {
            userDetails: [],
            searchText: "",
        };
    }
    /**
     *
     * component did mount
     * as soon as my component is
     * loaded or mounted on my dom this method is called
     */
    componentDidMount() {
        fetch("https://reqres.in/api/users").then((res) => {
            const users = res.json();
            this.setState(() => {
                return { userDetails: users.data };
            });
        });
    }
    onUserSearch = (event) => {
        const searhKey = event.target.value.toLocaleLowerCase();
        console.log(searhKey);

        this.setState(() => {
            return { searchText: searhKey };
        });
    };
    render() {
        console.log(
            "userDetails this.state.userdetails: = ",
            this.state.userDetails
        );
        const filteredUserData = this.state.userDetails.filter((user) => {
            console.log("Users:", user.first_name);
            return (
                user.first_name
                    .toLocaleLowerCase()
                    .includes(this.state.searchText) ||
                user.last_name
                    .toLocaleLowerCase()
                    .includes(this.state.searchText)
            );
        });

        // console.log("MAP", userData);
        return (
            <div>
                App
                <SearchBar
                    onSearchHandler={this.onUserSearch}
                    className="SearchBar"
                    placeHolder="Find"
                />
                <UserInfo userData={filteredUserData} />
            </div>
        );
    }
}
