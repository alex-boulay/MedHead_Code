<template>
<div class="flex flex-col items-center justify-center py-8">
  <h2 class="text-2xl font-bold mb-4" v-if="isLoginMode">Login</h2>
  <h2 class="text-2xl font-bold mb-4" v-else>Register</h2>

  <form class="flex flex-col items-center" @submit.prevent="isLoginMode ? login() : register()">
    <input
      v-model="username"
      type="text"
      placeholder="Username"
      required
      class="w-64 p-2 mb-4 border border-gray-300 rounded-lg"
    />
    <input
      v-model="password"
      type="password"
      placeholder="Password"
      required
      class="w-64 p-2 mb-4 border border-gray-300 rounded-lg"
    />
    <button
      type="submit"
      class="w-64 p-2 bg-blue-500 hover:bg-blue-700 text-white font-bold rounded-lg"
    >
      {{ isLoginMode ? 'Login' : 'Register' }}
    </button>
  </form>

  <button
    @click="toggleMode"
    class="mt-4 text-blue-500 hover:text-blue-700 font-bold underline"
  >
    {{ isLoginMode ? 'Switch to Register' : 'Switch to Login' }}
  </button>

  <!-- Login failure popup -->
  <div v-if="loginFailed" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white rounded-lg p-6">
      <h3 class="text-xl font-bold mb-4">Login Failed</h3>
      <p class="text-lg mb-4">Invalid username or password. Please try again.</p>
      <button @click="loginFailed = false" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-lg">Close</button>
    </div>
  </div>

  <!-- Registration failure popup -->
  <div v-if="registerFailed" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white rounded-lg p-6">
      <h3 class="text-xl font-bold mb-4">Registration Failed</h3>
      <p class="text-lg mb-4">Something went wrong with the registration. Please try again later.</p>
      <button @click="registerFailed = false" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-lg">Close</button>
    </div>
  </div>

</div>

  </template>
  
  <script>

  export default {
    data() {
      return {
        isLoginMode: true,
        username: "",
        password: "",
        loginFailed: false,
        registerFailed: false
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
            this.loginFailed = true;
            console.log('Login failed');
            }
        } catch (error) {
            // Gesting des Erreurs de Network

            //this.registerFailed = true;
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
            this.registerFailed = true;
            console.log('Registration failed');
            }
        } catch (error) {
            // ICi les Erreurs de network
            //this.registerFailed = true;
            console.error('Network error:', error);
        }
        }
    },
  };
  </script>