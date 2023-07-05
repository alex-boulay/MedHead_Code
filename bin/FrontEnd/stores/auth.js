import { defineStore } from 'pinia';

//const pinia = createPinia()
//app.use(pinia)

export const useAuthStore = defineStore('auth', {
  state: () => ({
    token: null,
  }),
  actions: {
    setToken(token) {
      this.token = token;
    },
    getToken(){
      return this.token;
    }
  },
});