import React, { Component } from 'react'

export default class UserInfo extends Component {
  render() {
    const { userData } = this.props
    return (
      <div>
        <div>{userData.map((users) => {
          return (
            <div style={{ backgroundColor: "#F08187", padding: "20px", margin: "20px" }}>
              <img src={users.avatar} />
              <h5>{users.first_name} {users.last_name}</h5>

              <h5>{users.email}</h5>
            </div>)
        })}</div>
      </div >

    )
  }
}
