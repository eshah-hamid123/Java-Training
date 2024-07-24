import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login/Login'; // Adjust the import paths as needed
import AdminDashboard from './components/AdminDashboard/AdminDashboard';
import AccountHolderDashboard from './components/AccountHolderDashboard/AccountHolderDashboard';
import ManageUsers from './components/ManageUsers/ManageUsers';
import ViewTransactions from './components/ViewTransactions/ViewTransactions';
import CreateAccount from './components/CreateAccount/CreateAccount';
import EditAccount from './components/EditAccount/EditAccount';
import TransactionHistory from './components/TransactionHistory/TransactionHistory';
import TransactionSuccess from './components/TransactionSuccessful/TransactionSuccessful';
import SendMoney from './components/SendMoney/SendMoney';
import { useAuth } from './hooks/useAuth';
import LandingPage from './components/LandingPage/LandingPage';

function App() {
  const { isAuthenticated, userRole } = useAuth();
  return (
    <Router>
      <Routes>
      <Route path="/" element={isAuthenticated ? (userRole === 'admin' ? <Navigate to="/admin-dashboard" /> : <Navigate to="/account-holder-dashboard" />) : <LandingPage />} />
        <Route path="/admin-dashboard" element={<AdminDashboard />} />
        <Route path="/login" element={<Login />} />
        <Route path="/account-holder-dashboard" element={<AccountHolderDashboard />} />
        <Route path="/manage-users" element={<ManageUsers />} />
        <Route path="/view-transactions" element={<ViewTransactions />} />
        <Route path="/create-account" element={<CreateAccount />} />
        <Route path="/edit-account/:accountId" element={<EditAccount />} />
        <Route path="/send-money" element={<SendMoney />} />
        <Route path="/transaction-history" element={<TransactionHistory />} />
        <Route path="/transaction-success" element={<TransactionSuccess />} />
      </Routes>
    </Router>
  );
}

export default App;
