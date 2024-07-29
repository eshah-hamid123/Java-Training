import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  Alert,
} from "@mui/material";
import Layout from "../Layout/Layout";
import "./CreateAccount.css";

const CreateAccount = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    address: "",
    balance: "",
    accountNumber: "",
  });
  const [error, setError] = useState("");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const token = localStorage.getItem("token");
      await axios.post(
        "http://localhost:8080/v1/accounts/create-account",
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
    
      setFormData({
        username: "",
        password: "",
        email: "",
        address: "",
        balance: "",
        accountNumber: "",
      });
      navigate("/manage-users");
    } catch (error) {
      if (error.response && error.response.status === 409) {
        setError(error.response.data);
      } else {
        console.error("There was an error creating the account!", error);
        setError("Error creating account");
      }
    }
  };

  return (
    <Layout>
      <Container
        component="main"
        maxWidth="sm"
        className="create-account-container"
      >
        <Paper elevation={5} className="create-account-paper">
          <Typography
            variant="h4"
            gutterBottom
            className="create-account-title"
          >
            Create Account
          </Typography>
          {error && <Alert severity="error">{error}</Alert>}
          <form onSubmit={handleSubmit}>
            <TextField
              label="Username"
              name="username"
              value={formData.username}
              onChange={handleChange}
              fullWidth
              required
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              fullWidth
              required
              margin="normal"
              variant="outlined"
              inputProps={{ minLength: 6 }}
            />
            <TextField
              label="Email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              fullWidth
              required
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Address"
              name="address"
              value={formData.address}
              onChange={handleChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Balance"
              name="balance"
              type="number"
              required
              value={formData.balance}
              onChange={handleChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Account Number"
              name="accountNumber"
              value={formData.accountNumber}
              onChange={handleChange}
              fullWidth
              required
              margin="normal"
              variant="outlined"
              inputProps={{ minLength: 8 }}
            />
            <Button
              type="submit"
              variant="contained"
              color="success"
              fullWidth
              className="create-account-button"
            >
              Create Account
            </Button>
          </form>
        </Paper>
      </Container>
    </Layout>
  );
};

export default CreateAccount;
