import React from 'react';

export const Footer: React.FC = () => {
  return (
    <footer className="bg-gray-800 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* 회사 정보 */}
          <div>
            <h3 className="text-lg font-semibold mb-4">E-Commerce</h3>
            <p className="text-gray-300 text-sm">
              최고의 상품을 합리적인 가격에 제공하는 온라인 쇼핑몰입니다.
            </p>
          </div>

          {/* 고객 서비스 */}
          <div>
            <h3 className="text-lg font-semibold mb-4">고객 서비스</h3>
            <ul className="space-y-2 text-sm text-gray-300">
              <li><a href="#" className="hover:text-white">고객센터</a></li>
              <li><a href="#" className="hover:text-white">배송 안내</a></li>
              <li><a href="#" className="hover:text-white">반품/교환</a></li>
              <li><a href="#" className="hover:text-white">FAQ</a></li>
            </ul>
          </div>

          {/* 쇼핑 정보 */}
          <div>
            <h3 className="text-lg font-semibold mb-4">쇼핑 정보</h3>
            <ul className="space-y-2 text-sm text-gray-300">
              <li><a href="#" className="hover:text-white">이용약관</a></li>
              <li><a href="#" className="hover:text-white">개인정보처리방침</a></li>
              <li><a href="#" className="hover:text-white">결제 안내</a></li>
              <li><a href="#" className="hover:text-white">적립금 안내</a></li>
            </ul>
          </div>

          {/* 연락처 */}
          <div>
            <h3 className="text-lg font-semibold mb-4">연락처</h3>
            <div className="space-y-2 text-sm text-gray-300">
              <p>고객센터: 1588-0000</p>
              <p>평일 09:00 - 18:00</p>
              <p>주말/공휴일 휴무</p>
              <p>이메일: support@ecommerce.com</p>
            </div>
          </div>
        </div>

        <div className="border-t border-gray-700 mt-8 pt-8 text-center text-sm text-gray-400">
          <p>&copy; 2026 E-Commerce. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};