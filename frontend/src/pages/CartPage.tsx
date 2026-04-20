import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useCartStore } from '../store/cartStore';
import { cartApi } from '../api/cart';
import { CartItem } from '../types/api';

export const CartPage: React.FC = () => {
  const navigate = useNavigate();
  const { cart, setCart, setLoading, updateCartItemQuantity, removeCartItem } = useCartStore();
  const [loading, setLocalLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      setLocalLoading(true);
      setLoading(true);
      setError('');
      
      const response = await cartApi.getCart();
      
      if (response.success) {
        setCart(response.data);
      } else {
        setError(response.message || '장바구니를 불러오는데 실패했습니다.');
      }
    } catch (error: any) {
      console.error('장바구니 로딩 에러:', error);
      setError('장바구니를 불러오는 중 오류가 발생했습니다.');
    } finally {
      setLocalLoading(false);
      setLoading(false);
    }
  };

  const handleQuantityChange = async (cartItemId: number, newQuantity: number) => {
    if (newQuantity < 1) return;

    try {
      const response = await cartApi.updateCartItem(cartItemId, newQuantity);
      
      if (response.success) {
        setCart(response.data);
      } else {
        alert(response.message || '수량 변경에 실패했습니다.');
      }
    } catch (error: any) {
      console.error('수량 변경 에러:', error);
      alert('수량 변경 중 오류가 발생했습니다.');
    }
  };

  const handleRemoveItem = async (cartItemId: number) => {
    if (!confirm('이 상품을 장바구니에서 제거하시겠습니까?')) return;

    try {
      const response = await cartApi.removeFromCart(cartItemId);
      
      if (response.success) {
        setCart(response.data);
      } else {
        alert(response.message || '상품 제거에 실패했습니다.');
      }
    } catch (error: any) {
      console.error('상품 제거 에러:', error);
      alert('상품 제거 중 오류가 발생했습니다.');
    }
  };

  const handleClearCart = async () => {
    if (!confirm('장바구니를 비우시겠습니까?')) return;

    try {
      const response = await cartApi.clearCart();
      
      if (response.success) {
        setCart(null);
      } else {
        alert(response.message || '장바구니 비우기에 실패했습니다.');
      }
    } catch (error: any) {
      console.error('장바구니 비우기 에러:', error);
      alert('장바구니 비우기 중 오류가 발생했습니다.');
    }
  };

  const CartItemComponent: React.FC<{ item: CartItem }> = ({ item }) => (
    <div className="flex items-center py-6 border-b border-gray-200">
      {/* 상품 이미지 */}
      <div className="flex-shrink-0 w-24 h-24 bg-gray-100 rounded-lg overflow-hidden">
        <img
          src={item.imageUrl || '/placeholder-product.jpg'}
          alt={item.productName}
          className="w-full h-full object-cover object-center"
        />
      </div>

      {/* 상품 정보 */}
      <div className="ml-6 flex-1">
        <div className="flex justify-between">
          <div>
            <h3 className="text-lg font-medium text-gray-900">
              <Link to={`/products/${item.productId}`} className="hover:text-primary-600">
                {item.productName}
              </Link>
            </h3>
            <p className="text-gray-600 mt-1">{item.price.toLocaleString()}원</p>
          </div>
          <button
            onClick={() => handleRemoveItem(item.id)}
            className="text-gray-400 hover:text-red-500"
          >
            <svg className="h-5 w-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        <div className="flex items-center justify-between mt-4">
          {/* 수량 조절 */}
          <div className="flex items-center border border-gray-300 rounded-md">
            <button
              type="button"
              onClick={() => handleQuantityChange(item.id, item.quantity - 1)}
              disabled={item.quantity <= 1}
              className="px-3 py-1 text-gray-600 hover:text-gray-800 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              -
            </button>
            <span className="px-4 py-1 text-center min-w-[3rem]">{item.quantity}</span>
            <button
              type="button"
              onClick={() => handleQuantityChange(item.id, item.quantity + 1)}
              disabled={item.quantity >= item.stock}
              className="px-3 py-1 text-gray-600 hover:text-gray-800 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              +
            </button>
          </div>

          {/* 총 가격 */}
          <div className="text-lg font-semibold text-gray-900">
            {item.totalPrice.toLocaleString()}원
          </div>
        </div>

        {/* 재고 경고 */}
        {item.quantity >= item.stock && (
          <p className="text-sm text-red-600 mt-2">
            재고가 {item.stock}개 남았습니다.
          </p>
        )}
      </div>
    </div>
  );

  if (loading) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          {[...Array(3)].map((_, i) => (
            <div key={i} className="flex items-center py-6 border-b border-gray-200">
              <div className="w-24 h-24 bg-gray-200 rounded-lg"></div>
              <div className="ml-6 flex-1 space-y-2">
                <div className="h-6 bg-gray-200 rounded w-3/4"></div>
                <div className="h-4 bg-gray-200 rounded w-1/2"></div>
                <div className="h-8 bg-gray-200 rounded w-1/4"></div>
              </div>
            </div>
          ))}
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center py-12">
          <div className="text-red-600 mb-4">{error}</div>
          <button onClick={fetchCart} className="btn-primary">
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  if (!cart || cart.items.length === 0) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-8">장바구니</h1>
        <div className="text-center py-12">
          <svg className="mx-auto h-12 w-12 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 3h2l.4 2M7 13h10l4-8H5.4m0 0L7 13m0 0l-1.5 6M7 13l-1.5 6m0 0h9m-9 0V19a2 2 0 002 2h7a2 2 0 002-2v-0" />
          </svg>
          <h3 className="text-lg font-medium text-gray-900 mb-2">장바구니가 비어있습니다</h3>
          <p className="text-gray-600 mb-6">원하는 상품을 장바구니에 담아보세요.</p>
          <Link to="/products" className="btn-primary">
            쇼핑 계속하기
          </Link>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold text-gray-900">장바구니</h1>
        <button
          onClick={handleClearCart}
          className="text-sm text-gray-600 hover:text-red-600"
        >
          전체 삭제
        </button>
      </div>

      <div className="bg-white rounded-lg shadow-sm">
        <div className="px-6">
          {cart.items.map((item) => (
            <CartItemComponent key={item.id} item={item} />
          ))}
        </div>

        {/* 주문 요약 */}
        <div className="bg-gray-50 px-6 py-6 rounded-b-lg">
          <div className="flex justify-between items-center mb-4">
            <span className="text-lg font-medium text-gray-900">총 상품 수량</span>
            <span className="text-lg text-gray-900">{cart.totalQuantity}개</span>
          </div>
          <div className="flex justify-between items-center mb-6">
            <span className="text-xl font-bold text-gray-900">총 결제 금액</span>
            <span className="text-2xl font-bold text-primary-600">
              {cart.totalPrice.toLocaleString()}원
            </span>
          </div>

          <div className="flex space-x-4">
            <Link
              to="/products"
              className="flex-1 bg-gray-200 text-gray-800 px-6 py-3 rounded-lg font-medium text-center hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500"
            >
              쇼핑 계속하기
            </Link>
            <button
              onClick={() => navigate('/checkout')}
              className="flex-1 bg-primary-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-primary-500"
            >
              주문하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};