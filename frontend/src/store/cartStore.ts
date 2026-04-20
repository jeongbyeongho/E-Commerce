import { create } from 'zustand';
import { Cart } from '../types/api';

interface CartState {
  cart: Cart | null;
  isLoading: boolean;
  
  // Actions
  setCart: (cart: Cart) => void;
  setLoading: (loading: boolean) => void;
  clearCart: () => void;
  updateCartItemQuantity: (cartItemId: number, quantity: number) => void;
  removeCartItem: (cartItemId: number) => void;
}

export const useCartStore = create<CartState>((set, get) => ({
  cart: null,
  isLoading: false,

  setCart: (cart) => {
    set({ cart });
  },

  setLoading: (loading) => {
    set({ isLoading: loading });
  },

  clearCart: () => {
    set({ cart: null });
  },

  updateCartItemQuantity: (cartItemId, quantity) => {
    const { cart } = get();
    if (!cart) return;

    const updatedItems = cart.items.map(item => 
      item.id === cartItemId 
        ? { ...item, quantity, totalPrice: item.price * quantity }
        : item
    );

    const totalQuantity = updatedItems.reduce((sum, item) => sum + item.quantity, 0);
    const totalPrice = updatedItems.reduce((sum, item) => sum + item.totalPrice, 0);

    set({
      cart: {
        ...cart,
        items: updatedItems,
        totalQuantity,
        totalPrice,
      }
    });
  },

  removeCartItem: (cartItemId) => {
    const { cart } = get();
    if (!cart) return;

    const updatedItems = cart.items.filter(item => item.id !== cartItemId);
    const totalQuantity = updatedItems.reduce((sum, item) => sum + item.quantity, 0);
    const totalPrice = updatedItems.reduce((sum, item) => sum + item.totalPrice, 0);

    set({
      cart: {
        ...cart,
        items: updatedItems,
        totalQuantity,
        totalPrice,
      }
    });
  },
}));