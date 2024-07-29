import React from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useAuth } from "../hooks/AuthContext"; // Adjust the import path as needed

const ProtectedRoute = () => {
  const { authState } = useAuth();

  return authState.isAuthenticated ? <Outlet /> : <Navigate to="/login" />;
};

export default ProtectedRoute;
