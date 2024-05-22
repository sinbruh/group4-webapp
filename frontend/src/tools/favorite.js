import { asyncApiRequest } from '@/tools/request';
import { create } from 'zustand';

export async function sendFavoriteRequest(providerID) {
    const response = await asyncApiRequest('PUT', '/api/users/favorite/' + providerID, null, true);
    console.log(response);
}

export const useFavoriteStore = create((set) => ({
    favorites: [],
    addFavorite: (id) => set((state) => {
    if (!state.favorites.includes(id)) {
      return { favorites: [...state.favorites, id] };
    }
    return state;
  }),
  removeFavorite: (id) => set((state) => ({
    favorites: state.favorites.filter(favorite => favorite !== id)
  })),
}));

// export function isProviderFavorite(providerID) {
//     return useFavoriteStore.getState().favorites.includes(providerID);
// }
