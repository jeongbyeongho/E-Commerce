import { apiClient } from './client';
import { ApiResponse, Product, ProductSearchRequest } from '../types/api';

export const productsApi = {
  // 상품 목록 조회
  getProducts: async (params?: ProductSearchRequest): Promise<ApiResponse<Product[]>> => {
    const response = await apiClient.get('/products', { params });
    return response.data;
  },

  // 상품 상세 조회
  getProduct: async (id: number): Promise<ApiResponse<Product>> => {
    const response = await apiClient.get(`/products/${id}`);
    return response.data;
  },

  // 상품 검색
  searchProducts: async (params: ProductSearchRequest): Promise<ApiResponse<Product[]>> => {
    const response = await apiClient.get('/products/search', { params });
    return response.data;
  },

  // 인기 상품 조회
  getPopularProducts: async (): Promise<ApiResponse<Product[]>> => {
    const response = await apiClient.get('/products/popular');
    return response.data;
  },

  // 신상품 조회
  getNewProducts: async (): Promise<ApiResponse<Product[]>> => {
    const response = await apiClient.get('/products/new');
    return response.data;
  },
};