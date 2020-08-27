import React, { Component } from "react";
import ReactDOM from "react-dom";

import Nav from "./Nav";
import Form from "./Form";
import List from "./List";
import Edit from "./Edit";

import { BrowserRouter as Router, Switch, Route } from "react-router-dom";

export default class Main extends Component {
  render() {
    return (
      <Router>
        <main>
          <Nav />
          <Switch>
            <Route path="/index" exact component={List} />
            <Route path="/form" component={Form} />
            <Route path="/edit/:id" component={Edit} />
          </Switch>
        </main>
      </Router>
    );
  }
}

ReactDOM.render(<Main />, document.getElementById("component-user"));
