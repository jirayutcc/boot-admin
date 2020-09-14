import React, { Component } from "react";

import userServices from "../services/User";

export default class Edit extends Component {
  constructor() {
    super();
    this.state = {
      id: 0,
      username: "",
      password: "",
      email: "",
    };
  }

  async componentDidMount() {
    console.log("Mounted Edit");
    const id = this.props.match.params.id;
    const res = await userServices.get(id);
    console.log(res);
    if (res.success) {
      console.log(res.data);
      this.setState({
        id: res.data.id,
        username: res.data.username,
        password: res.data.password,
        email: res.data.email,
      });
    } else {
      alert("Error: " + res.message);
    }
  }

  render() {
    return (
      <div>
        <h4>Edit User {this.state.username} </h4>
        <hr />
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="username">Username</label>
            <input
              type="text"
              class="form-control"
              value={this.state.username}
              onChange={(value) =>
                this.setState({ username: value.target.value })
              }
            />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="password">Password</label>
            <input
              type="text"
              class="form-control"
              value={this.state.password}
              onChange={(value) =>
                this.setState({ password: value.target.value })
              }
            />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="email">Email</label>
            <input
              type="text"
              class="form-control"
              value={this.state.email}
              onChange={(value) => this.setState({ email: value.target.value })}
            />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <button
              class="btn btn-primary btn-block"
              onClick={() => this.onUpdate()}
              type="submit"
            >
              Save
            </button>
          </div>
        </div>
      </div>
    );
  }
}
