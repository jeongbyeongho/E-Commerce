// API 응답 공통 타입
export interface ApiResponse<T> {
  success: boolean;
  message: string;
  data: T;
  timestamp: string;
}

// 사용자 관련 타입
export interface User {
  id: number;
  username: string;
  email: string;
  name: string;
  phone: string;
  role: string;
  enabled: boolean;
  createdAt: string;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
  name: string;
  phone: string;
}

export interface AuthResponse {
  accessToken: string;
  refreshToken: string;
  user: User;
}

// 상품 관련 타입
export interface Product {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  imageUrl: string;
  brand: string;
  isActive: boolean;
  viewCount: number;
  salesCount: number;
  averageRating: number;
  reviewCount: number;
  categoryId: number;
  categoryName: string;
  createdAt: string;
  updatedAt: string;
}

export interface ProductSearchRequest {
  keyword?: string;
  categoryId?: number;
  minPrice?: number;
  maxPrice?: number;
  sortBy?: 'price' | 'name' | 'rating' | 'sales' | 'createdAt';
  sortDirection?: 'asc' | 'desc';
  page?: number;
  size?: number;
}

// 장바구니 관련 타입
export interface CartItem {
  id: number;
  productId: number;
  productName: string;
  price: number;
  quantity: number;
  totalPrice: number;
  imageUrl: string;
  stock: number;
}

export interface Cart {
  id: number;
  userId: number;
  items: CartItem[];
  totalPrice: number;
  totalQuantity: number;
}

export interface AddToCartRequest {
  productId: number;
  quantity: number;
}

// 주문 관련 타입
export interface OrderItem {
  id: number;
  productId: number;
  productName: string;
  price: number;
  quantity: number;
  totalPrice: number;
}

export interface Order {
  id: number;
  orderNumber: string;
  userId: number;
  username: string;
  status: string;
  totalAmount: number;
  discountAmount: number;
  shippingFee: number;
  finalAmount: number;
  paymentMethod: string;
  paymentStatus: string;
  shippingAddress: string;
  recipientName: string;
  recipientPhone: string;
  zipCode: string;
  orderMemo: string;
  trackingNumber: string;
  paidAt: string;
  shippedAt: string;
  deliveredAt: string;
  createdAt: string;
  orderItems: OrderItem[];
}

// 배송지 관련 타입
export interface Address {
  id: number;
  recipientName: string;
  phone: string;
  zipCode: string;
  address: string;
  detailAddress: string;
  addressType: string;
  isDefault: boolean;
}