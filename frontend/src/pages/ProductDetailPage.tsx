import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { Product } from '../types/api';
import { productsApi } from '../api/products';
import { cartApi } from '../api/cart';
import { useAuthStore } from '../store/authStore';
import { useCartStore } from '../store/cartStore';

export const ProductDetailPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const { isAuthenticated } = useAuthStore();
  const { setCart } = useCartStore();

  const [product, setProduct] = useState<Product | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [addingToCart, setAddingToCart] = useState(false);

  useEffect(() => {
    if (id) {
      fetchProduct(Number(id));
    }
  }, [id]);

  const fetchProduct = async (productId: number) => {
    try {
      setLoading(true);
      setError('');
      
      const response = await productsApi.getProduct(productId);
      
      if (response.success) {
        setProduct(response.data);
      } else {
        setError(response.message || '상품을 불러오는데 실패했습니다.');
      }
    } catch (error: any) {
      console.error('상품 로딩 에러:', error);
      setError('상품을 불러오는 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = async () => {
    if (!isAuthenticated) {
      navigate('/login', { state: { from: location } });
      return;
    }

    if (!product) return;

    try {
      setAddingToCart(true);
      
      const response = await cartApi.addToCart({
        productId: product.id,
        quantity,
      });
      
      if (response.success) {
        setCart(response.data);
        alert('장바구니에 추가되었습니다.');
      } else {
        alert(response.message || '장바구니 추가에 실패했습니다.');
      }
    } catch (error: any) {
      console.error('장바구니 추가 에러:', error);
      alert('장바구니 추가 중 오류가 발생했습니다.');
    } finally {
      setAddingToCart(false);
    }
  };

  const handleBuyNow = () => {
    if (!isAuthenticated) {
      navigate('/login', { state: { from: location } });
      return;
    }
    
    // 바로 구매 로직 (장바구니에 추가 후 결제 페이지로 이동)
    handleAddToCart().then(() => {
      navigate('/checkout');
    });
  };

  if (loading) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="animate-pulse">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
            <div className="bg-gray-200 aspect-square rounded-lg"></div>
            <div className="space-y-4">
              <div className="h-8 bg-gray-200 rounded"></div>
              <div className="h-6 bg-gray-200 rounded w-1/2"></div>
              <div className="h-10 bg-gray-200 rounded w-1/3"></div>
              <div className="h-20 bg-gray-200 rounded"></div>
            </div>
          </div>
        </div>
      </div>
    );
  }

  if (error || !product) {
    return (
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center py-12">
          <div className="text-red-600 mb-4">{error || '상품을 찾을 수 없습니다.'}</div>
          <button
            onClick={() => navigate('/products')}
            className="btn-primary"
          >
            상품 목록으로 돌아가기
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* 브레드크럼 */}
      <nav className="flex mb-8" aria-label="Breadcrumb">
        <ol className="flex items-center space-x-4">
          <li>
            <button onClick={() => navigate('/')} className="text-gray-400 hover:text-gray-500">
              홈
            </button>
          </li>
          <li>
            <div className="flex items-center">
              <svg className="flex-shrink-0 h-5 w-5 text-gray-300" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
              </svg>
              <button onClick={() => navigate('/products')} className="ml-4 text-gray-400 hover:text-gray-500">
                상품
              </button>
            </div>
          </li>
          <li>
            <div className="flex items-center">
              <svg className="flex-shrink-0 h-5 w-5 text-gray-300" fill="currentColor" viewBox="0 0 20 20">
                <path fillRule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clipRule="evenodd" />
              </svg>
              <span className="ml-4 text-gray-500">{product.name}</span>
            </div>
          </li>
        </ol>
      </nav>

      {/* 상품 상세 정보 */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
        {/* 상품 이미지 */}
        <div className="aspect-square overflow-hidden rounded-lg bg-gray-100">
          <img
            src={product.imageUrl || '/placeholder-product.jpg'}
            alt={product.name}
            className="h-full w-full object-cover object-center"
          />
        </div>

        {/* 상품 정보 */}
        <div className="space-y-6">
          <div>
            <h1 className="text-3xl font-bold text-gray-900">{product.name}</h1>
            <p className="text-lg text-gray-600 mt-2">{product.brand}</p>
          </div>

          {/* 평점 */}
          <div className="flex items-center space-x-2">
            <div className="flex items-center">
              {[...Array(5)].map((_, i) => (
                <svg
                  key={i}
                  className={`h-5 w-5 ${
                    i < Math.floor(product.averageRating) ? 'text-yellow-400' : 'text-gray-300'
                  }`}
                  fill="currentColor"
                  viewBox="0 0 20 20"
                >
                  <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                </svg>
              ))}
            </div>
            <span className="text-sm text-gray-600">
              {product.averageRating.toFixed(1)} ({product.reviewCount}개 리뷰)
            </span>
          </div>

          {/* 가격 */}
          <div className="text-3xl font-bold text-gray-900">
            {product.price.toLocaleString()}원
          </div>

          {/* 재고 상태 */}
          <div>
            <span className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium ${
              product.stock > 0 
                ? 'bg-green-100 text-green-800' 
                : 'bg-red-100 text-red-800'
            }`}>
              {product.stock > 0 ? `재고 ${product.stock}개` : '품절'}
            </span>
          </div>

          {/* 상품 설명 */}
          <div>
            <h3 className="text-lg font-medium text-gray-900 mb-2">상품 설명</h3>
            <p className="text-gray-600 whitespace-pre-line">{product.description}</p>
          </div>

          {/* 수량 선택 및 구매 버튼 */}
          {product.stock > 0 && (
            <div className="space-y-4">
              <div className="flex items-center space-x-4">
                <label className="text-sm font-medium text-gray-700">수량:</label>
                <div className="flex items-center border border-gray-300 rounded-md">
                  <button
                    type="button"
                    onClick={() => setQuantity(Math.max(1, quantity - 1))}
                    className="px-3 py-1 text-gray-600 hover:text-gray-800"
                  >
                    -
                  </button>
                  <input
                    type="number"
                    min="1"
                    max={product.stock}
                    value={quantity}
                    onChange={(e) => setQuantity(Math.max(1, Math.min(product.stock, Number(e.target.value))))}
                    className="w-16 px-2 py-1 text-center border-0 focus:ring-0"
                  />
                  <button
                    type="button"
                    onClick={() => setQuantity(Math.min(product.stock, quantity + 1))}
                    className="px-3 py-1 text-gray-600 hover:text-gray-800"
                  >
                    +
                  </button>
                </div>
              </div>

              <div className="flex space-x-4">
                <button
                  onClick={handleAddToCart}
                  disabled={addingToCart}
                  className="flex-1 bg-gray-200 text-gray-800 px-6 py-3 rounded-lg font-medium hover:bg-gray-300 focus:outline-none focus:ring-2 focus:ring-gray-500 disabled:opacity-50 disabled:cursor-not-allowed"
                >
                  {addingToCart ? '추가 중...' : '장바구니 담기'}
                </button>
                <button
                  onClick={handleBuyNow}
                  className="flex-1 bg-primary-600 text-white px-6 py-3 rounded-lg font-medium hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-primary-500"
                >
                  바로 구매
                </button>
              </div>
            </div>
          )}

          {/* 상품 정보 */}
          <div className="border-t pt-6">
            <dl className="grid grid-cols-1 gap-4 sm:grid-cols-2">
              <div>
                <dt className="text-sm font-medium text-gray-500">브랜드</dt>
                <dd className="mt-1 text-sm text-gray-900">{product.brand}</dd>
              </div>
              <div>
                <dt className="text-sm font-medium text-gray-500">카테고리</dt>
                <dd className="mt-1 text-sm text-gray-900">{product.categoryName}</dd>
              </div>
              <div>
                <dt className="text-sm font-medium text-gray-500">조회수</dt>
                <dd className="mt-1 text-sm text-gray-900">{product.viewCount.toLocaleString()}</dd>
              </div>
              <div>
                <dt className="text-sm font-medium text-gray-500">판매량</dt>
                <dd className="mt-1 text-sm text-gray-900">{product.salesCount.toLocaleString()}</dd>
              </div>
            </dl>
          </div>
        </div>
      </div>
    </div>
  );
};