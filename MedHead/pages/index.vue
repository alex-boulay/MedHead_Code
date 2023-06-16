<template>
    <div>
      <h2 v-if="isLoginMode">Login</h2>
      <h2 v-else>Register</h2>
  
      <form @submit.prevent="isLoginMode ? login() : register()">
        <input v-model="username" type="text" placeholder="Username" required />
        <input v-model="password" type="password" placeholder="Password" required />
        <button type="submit">{{ isLoginMode ? 'Login' : 'Register' }}</button>
      </form>
  
      <button @click="toggleMode">
        {{ isLoginMode ? 'Switch to Register' : 'Switch to Login' }}
      </button>
    </div>
  </template>
  
  <script>

  export default {
    data() {
      return {
        isLoginMode: true,
        username: "",
        password: "",
      };
    },
    methods: {
      toggleMode() {
        this.isLoginMode = !this.isLoginMode;
      },
      async login() {
        const credentials = {
            username: this.username,
            password: this.password,
        };

        try {
            const response = await fetch('https://localhost:29001/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
            });
            if (response.status === 200) {
            const data = await response.json();
            console.log(data);
            const token = data.accessToken;
            // Ajout du token dans le store
            const auth = useAuthStore();
            auth.setToken(token);
            this.$router.push('/main');
            } else {
            // Gestion des erreurs
            console.log('Login failed');
            }
        } catch (error) {
            // Gesting des Erreurs de Network
            console.error('Network error:', error);
        }
    },
    async register() {
        // Object JSON avec le nom et mot de passe
        const credentials = {
            username: this.username,
            password: this.password,
        };

        try {
            // La requéte post pour register
            const response = await fetch('https://localhost:29001/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(credentials),
            });
            if (response.status === 200) {
            // Attempt to log in the user
            await this.login();
            } else {
            // Si le statut n'est pas 200
            console.log('Registration failed');
            }
        } catch (error) {
            // ICi les Erreurs de network
            console.error('Network error:', error);
        }
        }
    },
  };
  </script>