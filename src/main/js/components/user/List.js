import React, { Component } from 'react'; 
export default class List extends Component {
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
            <tr>
              <th scope="row">1</th>
              <td>John1</td>
              <td>1234</td>
              <td>john1@example.com</td>
              <td>
                <a href="#" class="btn btn-light">Edit</a>
                <a href="#" class="btn btn-danger">Delete</a>
              </td>
            </tr>
            <tr>
              <th scope="row">2</th>
              <td>John2</td>
              <td>1234</td>
              <td>john2@example.com</td>
              <td>
                <a href="#" class="btn btn-light">Edit</a>
                <a href="#" class="btn btn-danger">Delete</a>
              </td>
            </tr>

          </tbody>
        </table>
      </section>
    )
  }
}