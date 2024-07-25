import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import {
  Button,
  TextField,
  Typography,
  Paper,
  Container,
  Box,
  Grid,
} from "@mui/material";
import Layout from "../Layout/Layout";
import "./Login.css";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post("http://localhost:8080/v1/auth/login", {
        username,
        password,
      });

      if (response.status === 200) {
        const { jwtToken, account } = response.data;
        const role = account.role;
        localStorage.setItem("token", jwtToken);
        localStorage.setItem("role", role);
        localStorage.setItem("accountId", account.id);

        if (role === "admin") {
          navigate("/admin-dashboard");
        } else if (role === "account-holder") {
          navigate("/account-holder-dashboard");
        }
      } else {
        setErrorMessage("Login failed");
      }
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setErrorMessage(error.response.data.message);
      } else {
        setErrorMessage("An error occurred. Please try again.");
      }
    }
  };

  return (
    <Layout>
      <Container component="main" maxWidth="lg">
        <Paper elevation={3} className="login-paper">
          <Grid container spacing={2}>
            <Grid item xs={12} md={6}>
              <Box className="login-form-container">
                <Typography component="h1" variant="h5" className="login-title">
                  Login
                </Typography>
                {errorMessage && (
                  <Typography color="error">{errorMessage}</Typography>
                )}
                <Box
                  component="form"
                  onSubmit={handleLogin}
                  className="login-form"
                >
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="username"
                    label="Username"
                    name="username"
                    autoComplete="username"
                    autoFocus
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="password"
                    label="Password"
                    type="password"
                    id="password"
                    autoComplete="current-password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                  />
                  <Button
                    type="submit"
                    fullWidth
                    variant="contained"
                    className="login-button"
                  >
                    Login
                  </Button>
                </Box>
              </Box>
            </Grid>
            <Grid item xs={12} md={6} className="login-image-container">
              <img
                src="src/assets/images/hero_1.jpg"
                alt="Login"
                className="login-image"
              />
            </Grid>
          </Grid>
        </Paper>
      </Container>
    </Layout>
  );
};

export default Login;
