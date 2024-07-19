import React from 'react';
import { Link } from 'react-router-dom';
import './AdminDashboard.css'

const AdminDashboard = () => {
  return (
    <div className="admin-dashboard">
      <h1>Admin Dashboard</h1>
      <div className="dashboard-options">
        <Link to="/manage-users" className="dashboard-option">
          Manage Users
        </Link>
        <Link to="/view-transactions" className="dashboard-option">
          View Transactions
        </Link>
      </div>
    </div>
  );
};

export default AdminDashboard;
