import React, { useEffect, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
import { Product, ProductSearchRequest } from '../types/api';
import { productsApi } from '../api/products';

export const ProductsPage: React.FC = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [products, setProducts] = useState<Product[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  // 검색 필터 상태
  const [filters, setFilters] = useState<ProductSearchRequest>({
    keyword: searchParams.get('keyword') || '',
    categoryId: searchParams.get('categoryId') ? Number(searchParams.get('categoryId')) : undefined,
    minPrice: searchParams.get('minPrice') ? Number(searchParams.get('minPrice')) : undefined,
    maxPrice: searchParams.get('maxPrice') ? Number(searchParams.get('maxPrice')) : undefined,
    sortBy: (searchParams.get('sortBy') as any) || 'createdAt',
    sortDirection: (searchParams.get('sortDirection') as any) || 'desc',
    page: 0,
    size: 20,
  });

  useEffect(() => {
    fetchProducts();
  }, [filters]);

  const fetchProducts = async () => {
    try {
      setLoading(true);
      setError('');
      
      const response = await productsApi.searchProducts(filters);
      
      if (response.success) {
        setProducts(response.data);
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

  const handleFilterChange = (newFilters: Partial<ProductSearchRequest>) => {
    const updatedFilters = { ...filters, ...newFilters, page: 0 };
    setFilters(updatedFilters);
    
    // URL 파라미터 업데이트
    const params = new URLSearchParams();
    Object.entries(updatedFilters).forEach(([key, value]) => {
      if (value !== undefined && value !== '' && value !== null) {
        params.set(key, String(value));
      }
    });
    setSearchParams(params);
  };

  const handleSearch = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const formData = new FormData(e.currentTarget);
    const keyword = formData.get('keyword') as string;
    handleFilterChange({ keyword });
  };

  const ProductCard: React.FC<{ product: Product }> = ({ product }) => (
    <Link to={`/products/${product.id}`} className="group">
      <div className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow">
        <div className="aspect-w-1 aspect-h-1 w-full overflow-hidden bg-gray-200">
          <img
            src={product.imageUrl || '/placeholder-product.jpg'}
            alt={product.name}
            className="h-48 w-full object-cover object-center group-hover:scale-105 transition-transform duration-200"
          />
        </div>
        <div className="p-4">
          <h3 className="text-sm font-medium text-gray-900 truncate">{product.name}</h3>
          <p className="text-sm text-gray-500 mt-1 truncate">{product.brand}</p>
          <div className="flex items-center justify-between mt-2">
            <p className="text-lg font-semibold text-gray-900">
              {product.price.toLocaleString()}원
            </p>
            <div className="flex items-center">
              <span className="text-yellow-400">★</span>
              <span className="text-sm text-gray-600 ml-1">
                {product.averageRating.toFixed(1)} ({product.reviewCount})
              </span>
            </div>
          </div>
          <div className="mt-2">
            <span className={`text-xs px-2 py-1 rounded-full ${
              product.stock > 0 
                ? 'bg-green-100 text-green-800' 
                : 'bg-red-100 text-red-800'
            }`}>
              {product.stock > 0 ? `재고 ${product.stock}개` : '품절'}
            </span>
          </div>
        </div>
      </div>
    </Link>
  );

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* 페이지 헤더 */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900 mb-4">상품 목록</h1>
        
        {/* 검색 폼 */}
        <form onSubmit={handleSearch} className="flex gap-4 mb-6">
          <div className="flex-1">
            <input
              name="keyword"
              type="text"
              placeholder="상품명, 브랜드를 검색하세요..."
              defaultValue={filters.keyword}
              className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent"
            />
          </div>
          <button
            type="submit"
            className="px-6 py-2 bg-primary-600 text-white rounded-lg hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-primary-500"
          >
            검색
          </button>
        </form>

        {/* 필터 및 정렬 */}
        <div className="flex flex-wrap gap-4 items-center">
          <div className="flex items-center gap-2">
            <label className="text-sm font-medium text-gray-700">정렬:</label>
            <select
              value={`${filters.sortBy}-${filters.sortDirection}`}
              onChange={(e) => {
                const [sortBy, sortDirection] = e.target.value.split('-');
                handleFilterChange({ sortBy: sortBy as any, sortDirection: sortDirection as any });
              }}
              className="px-3 py-1 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-primary-500"
            >
              <option value="createdAt-desc">최신순</option>
              <option value="price-asc">가격 낮은순</option>
              <option value="price-desc">가격 높은순</option>
              <option value="rating-desc">평점 높은순</option>
              <option value="sales-desc">판매량순</option>
              <option value="name-asc">이름순</option>
            </select>
          </div>

          <div className="flex items-center gap-2">
            <label className="text-sm font-medium text-gray-700">가격:</label>
            <input
              type="number"
              placeholder="최소"
              value={filters.minPrice || ''}
              onChange={(e) => handleFilterChange({ minPrice: e.target.value ? Number(e.target.value) : undefined })}
              className="w-20 px-2 py-1 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-primary-500"
            />
            <span className="text-gray-500">~</span>
            <input
              type="number"
              placeholder="최대"
              value={filters.maxPrice || ''}
              onChange={(e) => handleFilterChange({ maxPrice: e.target.value ? Number(e.target.value) : undefined })}
              className="w-20 px-2 py-1 border border-gray-300 rounded-md text-sm focus:outline-none focus:ring-2 focus:ring-primary-500"
            />
          </div>
        </div>
      </div>

      {/* 상품 목록 */}
      {loading ? (
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
          {[...Array(12)].map((_, i) => (
            <div key={i} className="bg-gray-200 rounded-lg h-80 animate-pulse"></div>
          ))}
        </div>
      ) : error ? (
        <div className="text-center py-12">
          <div className="text-red-600 mb-4">{error}</div>
          <button
            onClick={fetchProducts}
            className="btn-primary"
          >
            다시 시도
          </button>
        </div>
      ) : products.length === 0 ? (
        <div className="text-center py-12">
          <div className="text-gray-500 mb-4">검색 결과가 없습니다.</div>
          <button
            onClick={() => handleFilterChange({ keyword: '', minPrice: undefined, maxPrice: undefined })}
            className="btn-primary"
          >
            전체 상품 보기
          </button>
        </div>
      ) : (
        <>
          <div className="mb-4 text-sm text-gray-600">
            총 {products.length}개의 상품
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {products.map((product) => (
              <ProductCard key={product.id} product={product} />
            ))}
          </div>
        </>
      )}
    </div>
  );
};