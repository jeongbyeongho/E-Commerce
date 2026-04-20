import { apiClient } from './client';
import { ApiResponse, LoginRequest, RegisterRequest, AuthResponse } from '../types/api';

export const authApi = {
  // 로그인
  login: async (data: LoginRequest): Promise<ApiResponse<AuthResponse>> => {
    const response = await apiClient.post('/auth/login', data);
    return response.data;
  },

  // 회원가입
  register: async (data: RegisterRequest): Promise<ApiResponse<AuthResponse>> => {
    const response = await apiClient.post('/auth/register', data);
    return response.data;
  },

  // 토큰 갱신
  refreshToken: async (refreshToken: string): Promise<ApiResponse<{ accessToken: string }>> => {
    const response = await apiClient.post('/auth/refresh', { refreshToken });
    return response.data;
  },

  // 인증 테스트
  test: async (): Promise<ApiResponse<string>> => {
    const response = await apiClient.get('/auth/test');
    return response.data;
  },
};