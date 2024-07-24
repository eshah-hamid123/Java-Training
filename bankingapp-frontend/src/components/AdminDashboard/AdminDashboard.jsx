import React from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./AdminDashboard.css";

const AdminDashboard = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    navigate("/");
  };

  const handleViewTransactions = async () => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.get("http://localhost:8080/transactions/all-transactions", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
        params: {
          page: 0,
          size: 1000,
        },
      });
      navigate("/view-transactions", { state: { transactions: response.data } });
    } catch (error) {
      console.error("Error fetching transactions", error);
    }
  };

  return (
    <div className="admin-dashboard">
      <h1>Admin Dashboard</h1>
      <div className="dashboard-options">
        <Link to="/manage-users" className="dashboard-option">
          Manage Users
        </Link>
        <button onClick={handleViewTransactions} className="dashboard-option">
          View Transactions
        </button>
        <button onClick={handleLogout} className="dashboard-option">
          Logout
        </button>
      </div>
    </div>
  );
};

export default AdminDashboard;
