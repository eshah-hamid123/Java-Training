import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import {
  Container,
  Paper,
  Typography,
  TextField,
  Button,
  CircularProgress,
  Alert
} from '@mui/material';
import Layout from '../Layout/Layout';
import './EditAccount.css';

const EditAccount = () => {
  const { accountId } = useParams();
  const [account, setAccount] = useState({
    username: '',
    password: '',
    email: '',
    address: '',
    balance: '',
    accountNumber: ''
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get(`http://localhost:8080/v1/accounts/get-account/${accountId}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        setAccount(response.data);
        setLoading(false);
      })
      .catch(err => {
        setError('Failed to fetch account details');
        setLoading(false);
      });
  }, [accountId]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setAccount({
      ...account,
      [name]: value
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const token = localStorage.getItem('token');
    axios.put(`http://localhost:8080/v1/accounts/edit-account/${accountId}`, account, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(response => {
        navigate('/manage-users');
      })
      .catch(err => {
        setError('Failed to update account' + err);
      });
  };

  if (loading) return (
    <Layout>
      <Container component="main" maxWidth="sm" className="edit-account-container">
        <CircularProgress />
      </Container>
    </Layout>
  );

  if (error) return (
    <Layout>
      <Container component="main" maxWidth="sm" className="edit-account-container">
        <Alert severity="error">{error}</Alert>
      </Container>
    </Layout>
  );

  return (
    <Layout>
      <Container component="main" maxWidth="sm" className="edit-account-container">
        <Paper elevation={5} className="edit-account-paper">
          <Typography variant="h4" gutterBottom className="edit-account-title">
            Edit Account
          </Typography>
          <form onSubmit={handleSubmit}>
            <TextField
              label="Username"
              name="username"
              value={account.username}
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
              value={account.password}
              onChange={handleChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Email"
              name="email"
              type="email"
              value={account.email}
              onChange={handleChange}
              fullWidth
              required
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Address"
              name="address"
              value={account.address}
              onChange={handleChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <TextField
              label="Balance"
              name="balance"
              type="number"
              value={account.balance}
              onChange={handleChange}
              fullWidth
              margin="normal"
              variant="outlined"
            />
            <Button
              type="submit"
              variant="contained"
              color="success"
              fullWidth
              className="edit-account-button"
            >
              Update
            </Button>
          </form>
        </Paper>
      </Container>
    </Layout>
  );
};

export default EditAccount;
