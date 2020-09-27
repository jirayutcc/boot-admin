import React, { Component } from "react";

import userServices from "../services/User";

import { Link } from "react-router-dom";

export default class List extends Component {
  constructor() {
    super();
    this.state = {
      listUser: [],
    };
  }

  async componentDidMount() {
    console.log("Mounted list");
    const res = await userServices.list();
    console.log(res);
    if (res.success) {
      this.setState({ listUser: res.list });
    } else {
      alert("Error: " + res.message);
    }
  }

  render() {
    return (
      <section>
        <table class="table">
          <thead class="thead-dark">
            <tr>
              <th scope="col">#</th>
              <th scope="col">Username</th>
              <th scope="col">Password</th>
              <th scope="col">Email</th>
              <th scope="col">Action</th>
            </tr>
          </thead>
          <tbody>
            {this.state.listUser.map((data, i) => {
              return (
                <tr>
                  <th scope="row">{data.id}</th>
                  <td>{data.username}</td>
                  <td>{data.password}</td>
                  <td>{data.email}</td>
                  <td>
                    <Link
                      class="btn btn-outline-info"
                      to={"/user/edit/" + data.id}
                    >
                      Edit
                    </Link>
                    <a
                      onClick={() => this.onDelete(i, data.id)}
                      href="#"
                      class="btn btn-danger"
                    >
                      {" "}
                      Delete{" "}
                    </a>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </section>
    );
  }

  async onDelete(i, id) {
    var yes = confirm("are you sure to delete this item?");
    if (yes === true) {
      const res = await userServices.delete(id);

      if (res.success) {
        alert(res.message);
        const list = this.state.listUser;
        list.splice(i, 1);
        this.setState({ listUser: list });
      } else {
        console.log(res);
        alert(JSON.stringify(res));
      }
    }
  }
}
