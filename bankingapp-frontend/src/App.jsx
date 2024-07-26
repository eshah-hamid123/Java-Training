import React, { useEffect, useState } from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Login from "./components/Login/Login"; // Adjust the import paths as needed
import AdminDashboard from "./components/AdminDashboard/AdminDashboard";
import AccountHolderDashboard from "./components/AccountHolderDashboard/AccountHolderDashboard";
import ManageUsers from "./components/ManageUsers/ManageUsers";
import ViewTransactions from "./components/ViewTransactions/ViewTransactions";
import CreateAccount from "./components/CreateAccount/CreateAccount";
import EditAccount from "./components/EditAccount/EditAccount";
import TransactionHistory from "./components/TransactionHistory/TransactionHistory";
import TransactionSuccess from "./components/TransactionSuccessful/TransactionSuccessful";
import SendMoney from "./components/SendMoney/SendMoney";
import { useAuth } from "./hooks/useAuth";
import LandingPage from "./components/LandingPage/LandingPage";
import Unauthorized from "./components/Unauthorized/Unauthorized";

function App() {
  const { isAuthenticated, userRole } = useAuth();
  const [authState, setAuthState] = useState({
    isAuthenticated: false,
    userRole: "",
  });

  useEffect(() => {
    setAuthState({
      isAuthenticated,
      userRole,
    });
  }, [isAuthenticated, userRole]);

  return (
    <Router>
      <Routes>
        <Route
          path="/"
          element={
            authState.isAuthenticated ? (
              authState.userRole === "admin" ? (
                <Navigate to="/admin-dashboard" />
              ) : (
                <Navigate to="/account-holder-dashboard" />
              )
            ) : (
              <LandingPage />
            )
          }
        />

        <Route path="/login" element={<Login />} />

        {authState.userRole === "admin" ? (
          <>
            <Route path="/admin-dashboard" element={<AdminDashboard />} />
            <Route path="/manage-users" element={<ManageUsers />} />
            <Route path="/view-transactions" element={<ViewTransactions />} />
            <Route path="/create-account" element={<CreateAccount />} />
            <Route path="/edit-account/:accountId" element={<EditAccount />} />
            <Route
              path="/account-holder-dashboard"
              element={<Unauthorized />}
            />
            <Route path="/send-money" element={<Unauthorized />} />
            <Route path="/transaction-history" element={<Unauthorized />} />
            <Route path="/transaction-success" element={<Unauthorized />} />
          </>
        ) : (
          <>
            <Route
              path="/account-holder-dashboard"
              element={<AccountHolderDashboard />}
            />
            <Route path="/send-money" element={<SendMoney />} />
            <Route
              path="/transaction-history"
              element={<TransactionHistory />}
            />
            <Route
              path="/transaction-success"
              element={<TransactionSuccess />}
            />
            <Route path="/admin-dashboard" element={<Unauthorized />} />
            <Route path="/manage-users" element={<Unauthorized />} />
            <Route path="/view-transactions" element={<Unauthorized />} />
            <Route path="/create-account" element={<Unauthorized />} />
            <Route path="/edit-account/:accountId" element={<Unauthorized />} />
          </>
        )}
      </Routes>
    </Router>
  );
}

export default App;
