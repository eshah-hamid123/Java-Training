import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Login from './components/Login/Login'; // Adjust the import paths as needed
import AdminDashboard from './components/AdminDashboard/AdminDashboard';
import AccountHolderDashboard from './components/AccountHolderDashboard/AccountHolderDashboard';
import ManageUsers from './components/ManageUsers/ManageUser';
import ViewTransactions from './components/ViewTransactions/ViewTransactions';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        <Route path="/account-holder-dashboard" element={<AccountHolderDashboard />} />
        <Route path="/manage-users" element={<ManageUsers />} />
        <Route path="/view-transactions" element={<ViewTransactions />} />
      </Routes>
    </Router>
  );
}

export default App;