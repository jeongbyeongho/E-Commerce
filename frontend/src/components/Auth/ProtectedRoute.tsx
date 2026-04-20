import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { useAuthStore } from '../../store/authStore';

interface ProtectedRouteProps {
  children: React.ReactNode;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({ children }) => {
  const { isAuthenticated } = useAuthStore();
  const location = useLocation();

  if (!isAuthenticated) {
    // 로그인 후 원래 페이지로 돌아가기 위해 현재 위치를 state로 전달
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  return <>{children}</>;
};