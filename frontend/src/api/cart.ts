import { apiClient } from './client';
import { ApiResponse, Cart, AddToCartRequest } from '../types/api';

export const cartApi = {
  // 장바구니 조회
  getCart: async (): Promise<ApiResponse<Cart>> => {
    const response = await apiClient.get('/cart');
    return response.data;
  },

  // 장바구니에 상품 추가
  addToCart: async (data: AddToCartRequest): Promise<ApiResponse<Cart>> => {
    const response = await apiClient.post('/cart/add', data);
    return response.data;
  },

  // 장바구니 아이템 수량 변경
  updateCartItem: async (cartItemId: number, quantity: number): Promise<ApiResponse<Cart>> => {
    const response = await apiClient.put(`/cart/items/${cartItemId}`, { quantity });
    return response.data;
  },

  // 장바구니에서 상품 제거
  removeFromCart: async (cartItemId: number): Promise<ApiResponse<Cart>> => {
    const response = await apiClient.delete(`/cart/items/${cartItemId}`);
    return response.data;
  },

  // 장바구니 비우기
  clearCart: async (): Promise<ApiResponse<void>> => {
    const response = await apiClient.delete('/cart/clear');
    return response.data;
  },
};