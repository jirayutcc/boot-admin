import React, { Component } from "react";

import userServices from "../services/User"

export default class Form extends Component {
  constructor() {
    super();
    this.state = {
      username: "",
      password: "",
      email: "",
    };
  }

  render() {
    return (
      <div>
        <h4>Create User {this.state.user}</h4>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="username">Username</label>
            <input
              type="text"
              class="form-control"
              placeholder="Username"
              value={this.state.username}
              onChange={(event) =>
                this.setState({ username: event.target.value })
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
              placeholder="Password"
              value={this.state.password}
              onChange={(event) =>
                this.setState({ password: event.target.value })
              }
            />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="email">Email</label>
            <input
              type="email"
              class="form-control"
              placeholder="you@example.com"
              value={this.state.email}
              onChange={(event) => this.setState({ email: event.target.value })}
            />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <button
              onClick={() => this.onSave()}
              class="btn btn-primary btn-block"
              type="submit"
            >
              Save
            </button>
          </div>
        </div>
      </div>
    );
  }

  async onSave() {
    const res = await userServices.create(this.state)
    if (res.success) {
      alert(res.message)
      window.location.replace("/user")
    } else {
      alert("error : " + res.message.message)
    }
  }
}
