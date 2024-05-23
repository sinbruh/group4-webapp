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

export async function fetchFavorites(email) {
    try {
        const response = await asyncApiRequest('GET', '/api/users/' + email, null);
        console.log("fetched favorites");
        console.log(response);
        return response.favorites;
    } catch (error) {
        console.error('Error fetching favorites:', error);
        return [];
    }
}

// export function isProviderFavorite(providerID) {
//     return useFavoriteStore.getState().favorites.includes(providerID);
// }
