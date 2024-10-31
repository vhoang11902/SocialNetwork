import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import MyContext from '../config/MyContext';

const ProtectedRoute = ({ children }) => {
  const [user] = useContext(MyContext);
  if (!user) {
    // If user is not logged in, redirect to login page
    return <Navigate to="/login" />;
  }

  // If user is logged in, render the children components
  return children;
};

export default ProtectedRoute;
