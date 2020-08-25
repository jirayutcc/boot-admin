import React, { Component } from "react";

export default class Edit extends Component {
  render() {
    let userId = this.props.match.params.id;

    return (
      <div>
        <h4>Edit customer {userId} </h4>
        <hr />
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="username">Username</label>
            <input type="text" class="form-control" placeholder="Username" />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="password">Password</label>
            <input type="email" class="form-control" placeholder="Password" />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="email">Email</label>
            <input
              type="email"
              class="form-control"
              placeholder="you@example.com"
            />
          </div>
        </div>

        <div class="row">
          <div class="col-md-6 mb-3">
            <button class="btn btn-primary btn-block" type="submit">
              Save
            </button>
          </div>
        </div>
      </div>
    );
  }
}
