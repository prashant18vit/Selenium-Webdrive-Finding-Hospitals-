import React, { Component } from "react";

export default class SearchBar extends Component {
    render() {
        return (
            <div>
                <h1>Search Bar Component</h1>
                <input
                    className={this.props.className}
                    type="search"
                    placeholder={this.props.placeHolder}
                    onChange={this.props.onSearchHandler}
                />
                ;
            </div>
        );
    }
}
