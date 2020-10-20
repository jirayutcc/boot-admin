import React, { Component } from "react";
import ReactDOM from "react-dom";

import loginService from "../services/LoginService";

import Avatar from "@material-ui/core/Avatar";
import Button from "@material-ui/core/Button";
import Box from "@material-ui/core/Box";
import CssBaseline from "@material-ui/core/CssBaseline";
import Container from "@material-ui/core/Container";
import CardMedia from "@material-ui/core/CardMedia";
import TextField from "@material-ui/core/TextField";
import Typography from "@material-ui/core/Typography";
import {
  makeStyles,
  createMuiTheme,
  ThemeProvider,
} from "@material-ui/core/styles";
import { blueGrey, green, lightBlue } from "@material-ui/core/colors";

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: "flex",
    flexDirection: "column",
    alignItems: "center",
  },
  form: {
    width: "100%", // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
}));

const theme = createMuiTheme({
  palette: {
    primary: green,
  },
});

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {"Copyright © Jirayutcc "}
      {new Date().getFullYear()}
      {"."}
    </Typography>
  );
}

export default class Login extends Component {
  
  constructor() {
    // const classes = useStyles();
    super();
    this.state = {
      username: "",
      password: "",
      errorField: [],
    };
  }

  render() {
    return (
      <React.Fragment>
        <CssBaseline />
        <Container component="main" maxWidth="xs">
          <div >
            <img
              src="https://i.ibb.co/9wMQrTB/bootadmin.png"
              height="190"
              width="460"
            />
            <form noValidate>
              <TextField
                variant="outlined"
                margin="normal"
                fullWidth
                id="username"
                label="Username"
                name="username"
                autoComplete="username"
                autoFocus
                value={this.state.username}
                onChange={(event) =>
                  this.setState({ username: event.target.value })
                }
              />
              <TextField
                variant="outlined"
                margin="normal"
                fullWidth
                name="password"
                label="Password"
                type="password"
                id="password"
                autoComplete="current-password"
                value={this.state.password}
                onChange={(event) =>
                  this.setState({ password: event.target.value })
                }
              />
              <ThemeProvider theme={theme}>
                <Button
                  type="submit"
                  fullWidth
                  variant="contained"
                  color="primary"
                  //className={classes.submit}
                  //onClick={() => this.onLogin()}
                >
                  Log In
                </Button>
              </ThemeProvider>
            </form>
            <Box mt={3}>
              <Copyright />
            </Box>
          </div>
        </Container>
      </React.Fragment>
    );

    
  }
  async onLogin() {
    const res = await loginService.login(this.state);
    if (res.success) {
      alert(res.message);
      window.location.replace("/user");
    } else if (res.status == 400) {
      console.log(res.status);
      const dataError = [];
      const error = res.data.errors;

      if (error) {
        error.map((itemerror) => {
          console.log(itemerror.defaultMessage);
          dataError.push(itemerror.defaultMessage);
        });
        this.setState({ errorField: dataError });
      } else {
        dataError.push(res.data.message);
        this.setState({ errorField: dataError });
      }
    } else {
      // alert("error : " + res.message.message)
      const dataError = [];
      dataError.push(res.message);
      this.setState({ errorField: dataError });
    }
  }
}

ReactDOM.render(<Login />, document.getElementById("component-login"));
