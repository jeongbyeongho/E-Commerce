import React, { useState } from 'react';
import { useAuthStore } from '../store/authStore';

export const ProfilePage: React.FC = () => {
  const { user, updateUser } = useAuthStore();
  const [activeTab, setActiveTab] = useState<'profile' | 'orders' | 'addresses'>('profile');
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({
    name: user?.name || '',
    email: user?.email || '',
    phone: user?.phone || '',
  });

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSave = async () => {
    try {
      // 실제 API 호출 (현재는 시뮬레이션)
      // const response = await userApi.updateProfile(formData);
      
      // 시뮬레이션: 로컬 상태 업데이트
      if (user) {
        updateUser({
          ...user,
          ...formData
        });
      }
      
      setEditing(false);
      alert('프로필이 업데이트되었습니다.');
    } catch (error) {
      console.error('프로필 업데이트 에러:', error);
      alert('프로필 업데이트 중 오류가 발생했습니다.');
    }
  };

  const handleCancel = () => {
    setFormData({
      name: user?.name || '',
      email: user?.email || '',
      phone: user?.phone || '',
    });
    setEditing(false);
  };

  if (!user) {
    return (
      <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        <div className="text-center py-12">
          <p className="text-gray-600">사용자 정보를 불러올 수 없습니다.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="text-3xl font-bold text-gray-900 mb-8">마이페이지</h1>

      {/* 탭 네비게이션 */}
      <div className="border-b border-gray-200 mb-8">
        <nav className="-mb-px flex space-x-8">
          <button
            onClick={() => setActiveTab('profile')}
            className={`py-2 px-1 border-b-2 font-medium text-sm ${
              activeTab === 'profile'
                ? 'border-primary-500 text-primary-600'
                : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
            }`}
          >
            프로필 정보
          </button>
          <button
            onClick={() => setActiveTab('orders')}
            className={`py-2 px-1 border-b-2 font-medium text-sm ${
              activeTab === 'orders'
                ? 'border-primary-500 text-primary-600'
                : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
            }`}
          >
            주문 내역
          </button>
          <button
            onClick={() => setActiveTab('addresses')}
            className={`py-2 px-1 border-b-2 font-medium text-sm ${
              activeTab === 'addresses'
                ? 'border-primary-500 text-primary-600'
                : 'border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300'
            }`}
          >
            배송지 관리
          </button>
        </nav>
      </div>

      {/* 프로필 정보 탭 */}
      {activeTab === 'profile' && (
        <div className="bg-white rounded-lg shadow-sm p-6">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-semibold text-gray-900">프로필 정보</h2>
            {!editing ? (
              <button
                onClick={() => setEditing(true)}
                className="text-primary-600 hover:text-primary-700 font-medium"
              >
                수정
              </button>
            ) : (
              <div className="space-x-2">
                <button
                  onClick={handleSave}
                  className="text-primary-600 hover:text-primary-700 font-medium"
                >
                  저장
                </button>
                <button
                  onClick={handleCancel}
                  className="text-gray-600 hover:text-gray-700 font-medium"
                >
                  취소
                </button>
              </div>
            )}
          </div>

          <div className="space-y-6">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                사용자명
              </label>
              <div className="text-gray-900 bg-gray-50 px-3 py-2 rounded-md">
                {user.username}
              </div>
              <p className="text-xs text-gray-500 mt-1">사용자명은 변경할 수 없습니다.</p>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                이름
              </label>
              {editing ? (
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleInputChange}
                  className="input-field"
                />
              ) : (
                <div className="text-gray-900 px-3 py-2">
                  {user.name}
                </div>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                이메일
              </label>
              {editing ? (
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleInputChange}
                  className="input-field"
                />
              ) : (
                <div className="text-gray-900 px-3 py-2">
                  {user.email}
                </div>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                전화번호
              </label>
              {editing ? (
                <input
                  type="tel"
                  name="phone"
                  value={formData.phone}
                  onChange={handleInputChange}
                  className="input-field"
                />
              ) : (
                <div className="text-gray-900 px-3 py-2">
                  {user.phone}
                </div>
              )}
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                가입일
              </label>
              <div className="text-gray-900 px-3 py-2">
                {new Date(user.createdAt).toLocaleDateString('ko-KR')}
              </div>
            </div>
          </div>
        </div>
      )}

      {/* 주문 내역 탭 */}
      {activeTab === 'orders' && (
        <div className="bg-white rounded-lg shadow-sm p-6">
          <h2 className="text-xl font-semibold text-gray-900 mb-6">주문 내역</h2>
          
          <div className="text-center py-12">
            <svg className="mx-auto h-12 w-12 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5H7a2 2 0 00-2 2v10a2 2 0 002 2h8a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2" />
            </svg>
            <h3 className="text-lg font-medium text-gray-900 mb-2">주문 내역이 없습니다</h3>
            <p className="text-gray-600 mb-6">첫 주문을 시작해보세요.</p>
            <button
              onClick={() => window.location.href = '/products'}
              className="btn-primary"
            >
              쇼핑하러 가기
            </button>
          </div>
        </div>
      )}

      {/* 배송지 관리 탭 */}
      {activeTab === 'addresses' && (
        <div className="bg-white rounded-lg shadow-sm p-6">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-semibold text-gray-900">배송지 관리</h2>
            <button className="btn-primary">
              새 배송지 추가
            </button>
          </div>
          
          <div className="text-center py-12">
            <svg className="mx-auto h-12 w-12 text-gray-400 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
            </svg>
            <h3 className="text-lg font-medium text-gray-900 mb-2">등록된 배송지가 없습니다</h3>
            <p className="text-gray-600 mb-6">자주 사용하는 배송지를 등록해보세요.</p>
            <button className="btn-primary">
              배송지 추가하기
            </button>
          </div>
        </div>
      )}
    </div>
  );
};