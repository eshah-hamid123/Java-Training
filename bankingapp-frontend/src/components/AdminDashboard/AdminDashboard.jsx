import React, { useState, useEffect } from "react";
import { Container, Typography, Paper, Grid, Box } from "@mui/material";
import Layout from "../Layout/Layout";
import axios from "axios";
import "./AdminDashboard.css";

const AdminDashboard = () => {
  const [totalUsers, setTotalUsers] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const token = localStorage.getItem("token");
        const usersResponse = await axios.get("http://localhost:8080/accounts/all-accounts", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setTotalUsers(usersResponse.data.length - 1);

      } catch (error) {
        console.error("Error fetching data", error);
      }
    };

    fetchData();
  }, []);
  
  return (
    <Layout>
      <Container component="main" maxWidth="md">
        <Paper elevation={5} className="dashboard-paper">
          <Typography variant="h4" gutterBottom className="dashboard-title">
            Welcome, Admin
          </Typography>
          <Typography variant="h6" gutterBottom>
            Here’s an overview of the system:
          </Typography>
          <Grid container spacing={4}>
            <Grid item xs={12} sm={6} md={4}>
              <Box className="dashboard-card">
                <Typography variant="h6" className="card-title">Total Users</Typography>
                <Typography variant="h4" className="card-value">{totalUsers}</Typography>
              </Box>
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <Box className="dashboard-card">
                <Typography variant="h6" className="card-title">Pending Transactions</Typography>
                <Typography variant="h4" className="card-value">456</Typography>
              </Box>
            </Grid>
            <Grid item xs={12} sm={6} md={4}>
              <Box className="dashboard-card">
                <Typography variant="h6" className="card-title">System Status</Typography>
                <Typography variant="h4" className="card-value">Operational</Typography>
              </Box>
            </Grid>
          </Grid>
        </Paper>
      </Container>
    </Layout>
  );
};

export default AdminDashboard;