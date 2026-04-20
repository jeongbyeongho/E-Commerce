import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCartStore } from '../store/cartStore';
import { useAuthStore } from '../store/authStore';
import { cartApi } from '../api/cart';

export const CheckoutPage: React.FC = () => {
  const navigate = useNavigate();
  const { user } = useAuthStore();
  const { cart, setCart } = useCartStore();
  
  const [loading, setLoading] = useState(true);
  const [submitting, setSubmitting] = useState(false);
  const [orderForm, setOrderForm] = useState({
    recipientName: user?.name || '',
    recipientPhone: user?.phone || '',
    zipCode: '',
    address: '',
    detailAddress: '',
    orderMemo: '',
    paymentMethod: 'CARD',
  });

  useEffect(() => {
    fetchCart();
  }, []);

  const fetchCart = async () => {
    try {
      setLoading(true);
      const response = await cartApi.getCart();
      
      if (response.success) {
        setCart(response.data);
        if (!response.data || response.data.items.length === 0) {
          alert('장바구니가 비어있습니다.');
          navigate('/cart');
        }
      } else {
        alert('장바구니 정보를 불러올 수 없습니다.');
        navigate('/cart');
      }
    } catch (error) {
      console.error('장바구니 로딩 에러:', error);
      alert('장바구니 정보를 불러오는 중 오류가 발생했습니다.');
      navigate('/cart');
    } finally {
      setLoading(false);
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setOrderForm(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!cart || cart.items.length === 0) {
      alert('주문할 상품이 없습니다.');
      return;
    }

    // 필수 필드 검증
    if (!orderForm.recipientName || !orderForm.recipientPhone || !orderForm.address) {
      alert('배송 정보를 모두 입력해주세요.');
      return;
    }

    try {
      setSubmitting(true);
      
      // 실제 주문 API 호출 (현재는 시뮬레이션)
      // const response = await orderApi.createOrderFromCart(orderForm);
      
      // 시뮬레이션: 2초 후 성공
      await new Promise(resolve => setTimeout(resolve, 2000));
      
      alert('주문이 완료되었습니다!');
      
      // 장바구니 비우기
      await cartApi.clearCart();
      setCart(null);
      
      // 주문 완료 페이지로 이동 (현재는 홈으로)
      navigate('/');
      
    } catch (error: any) {
      console.error('주문 에러:', error);
      alert('주문 처리 중 오류가 발생했습니다.');
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="animate-pulse space-y-6">
          <div className="h-8 bg-gray-200 rounded w-1/4"></div>
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <div className="space-y-4">
              {[...Array(6)].map((_, i) => (
                <div key={i} className="h-12 bg-gray-200 rounded"></div>
              ))}
            </div>
            <div className="space-y-4">
              <div className="h-32 bg-gray-200 rounded"></div>
              <div className="h-16 bg-gray-200 rounded"></div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  if (!cart || cart.items.length === 0) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center py-12">
          <h3 className="text-lg font-medium text-gray-900 mb-2">주문할 상품이 없습니다</h3>
          <p className="text-gray-600 mb-6">장바구니에 상품을 담아주세요.</p>
          <button onClick={() => navigate('/products')} className="btn-primary">
            쇼핑하러 가기
          </button>
        </div>
      </div>
    );
  }

  const shippingFee = cart.totalPrice >= 50000 ? 0 : 3000;
  const finalAmount = cart.totalPrice + shippingFee;

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">주문/결제</h1>

      <form onSubmit={handleSubmit}>
        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* 주문 정보 입력 */}
          <div className="space-y-6">
            {/* 배송 정보 */}
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">배송 정보</h2>
              
              <div className="space-y-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    받는 분 이름 *
                  </label>
                  <input
                    type="text"
                    name="recipientName"
                    value={orderForm.recipientName}
                    onChange={handleInputChange}
                    required
                    className="input-field"
                    placeholder="받는 분 이름을 입력하세요"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    연락처 *
                  </label>
                  <input
                    type="tel"
                    name="recipientPhone"
                    value={orderForm.recipientPhone}
                    onChange={handleInputChange}
                    required
                    className="input-field"
                    placeholder="연락처를 입력하세요"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    우편번호
                  </label>
                  <div className="flex space-x-2">
                    <input
                      type="text"
                      name="zipCode"
                      value={orderForm.zipCode}
                      onChange={handleInputChange}
                      className="input-field flex-1"
                      placeholder="우편번호"
                    />
                    <button
                      type="button"
                      className="px-4 py-2 bg-gray-200 text-gray-700 rounded-md hover:bg-gray-300"
                    >
                      검색
                    </button>
                  </div>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    주소 *
                  </label>
                  <input
                    type="text"
                    name="address"
                    value={orderForm.address}
                    onChange={handleInputChange}
                    required
                    className="input-field"
                    placeholder="주소를 입력하세요"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    상세 주소
                  </label>
                  <input
                    type="text"
                    name="detailAddress"
                    value={orderForm.detailAddress}
                    onChange={handleInputChange}
                    className="input-field"
                    placeholder="상세 주소를 입력하세요"
                  />
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">
                    배송 메모
                  </label>
                  <textarea
                    name="orderMemo"
                    value={orderForm.orderMemo}
                    onChange={handleInputChange}
                    rows={3}
                    className="input-field"
                    placeholder="배송 시 요청사항을 입력하세요"
                  />
                </div>
              </div>
            </div>

            {/* 결제 방법 */}
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">결제 방법</h2>
              
              <div className="space-y-3">
                <label className="flex items-center">
                  <input
                    type="radio"
                    name="paymentMethod"
                    value="CARD"
                    checked={orderForm.paymentMethod === 'CARD'}
                    onChange={handleInputChange}
                    className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300"
                  />
                  <span className="ml-2 text-sm text-gray-700">신용카드</span>
                </label>
                
                <label className="flex items-center">
                  <input
                    type="radio"
                    name="paymentMethod"
                    value="BANK_TRANSFER"
                    checked={orderForm.paymentMethod === 'BANK_TRANSFER'}
                    onChange={handleInputChange}
                    className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300"
                  />
                  <span className="ml-2 text-sm text-gray-700">계좌이체</span>
                </label>
                
                <label className="flex items-center">
                  <input
                    type="radio"
                    name="paymentMethod"
                    value="VIRTUAL_ACCOUNT"
                    checked={orderForm.paymentMethod === 'VIRTUAL_ACCOUNT'}
                    onChange={handleInputChange}
                    className="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300"
                  />
                  <span className="ml-2 text-sm text-gray-700">가상계좌</span>
                </label>
              </div>
            </div>
          </div>

          {/* 주문 요약 */}
          <div className="space-y-6">
            {/* 주문 상품 */}
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">주문 상품</h2>
              
              <div className="space-y-4">
                {cart.items.map((item) => (
                  <div key={item.id} className="flex items-center space-x-4">
                    <div className="flex-shrink-0 w-16 h-16 bg-gray-100 rounded-lg overflow-hidden">
                      <img
                        src={item.imageUrl || '/placeholder-product.jpg'}
                        alt={item.productName}
                        className="w-full h-full object-cover object-center"
                      />
                    </div>
                    <div className="flex-1 min-w-0">
                      <h3 className="text-sm font-medium text-gray-900 truncate">
                        {item.productName}
                      </h3>
                      <p className="text-sm text-gray-600">
                        {item.price.toLocaleString()}원 × {item.quantity}개
                      </p>
                    </div>
                    <div className="text-sm font-medium text-gray-900">
                      {item.totalPrice.toLocaleString()}원
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* 결제 금액 */}
            <div className="bg-white p-6 rounded-lg shadow-sm">
              <h2 className="text-xl font-semibold text-gray-900 mb-4">결제 금액</h2>
              
              <div className="space-y-3">
                <div className="flex justify-between text-sm">
                  <span className="text-gray-600">상품 금액</span>
                  <span className="text-gray-900">{cart.totalPrice.toLocaleString()}원</span>
                </div>
                
                <div className="flex justify-between text-sm">
                  <span className="text-gray-600">배송비</span>
                  <span className="text-gray-900">
                    {shippingFee === 0 ? '무료' : `${shippingFee.toLocaleString()}원`}
                  </span>
                </div>
                
                {cart.totalPrice < 50000 && (
                  <p className="text-xs text-gray-500">
                    50,000원 이상 구매 시 배송비 무료
                  </p>
                )}
                
                <div className="border-t pt-3">
                  <div className="flex justify-between text-lg font-semibold">
                    <span className="text-gray-900">총 결제 금액</span>
                    <span className="text-primary-600">{finalAmount.toLocaleString()}원</span>
                  </div>
                </div>
              </div>
            </div>

            {/* 주문 버튼 */}
            <button
              type="submit"
              disabled={submitting}
              className="w-full bg-primary-600 text-white px-6 py-4 rounded-lg font-semibold text-lg hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-primary-500 disabled:opacity-50 disabled:cursor-not-allowed"
            >
              {submitting ? (
                <div className="flex items-center justify-center">
                  <svg className="animate-spin -ml-1 mr-3 h-5 w-5 text-white" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24">
                    <circle className="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="4"></circle>
                    <path className="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>
                  </svg>
                  주문 처리 중...
                </div>
              ) : (
                `${finalAmount.toLocaleString()}원 결제하기`
              )}
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};