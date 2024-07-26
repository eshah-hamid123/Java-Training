import React from 'react';
import { useAuth } from '../hooks/useAuth';
import { Navigate, Outlet } from "react-router-dom";

const ProtectedRoute = () => {
  const { isAuthenticated, userRole } = useAuth();

  return isAuthenticated === true?<Outlet/>:<Navigate to="login"/>;
};

export default ProtectedRoute;
