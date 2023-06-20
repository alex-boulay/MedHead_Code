<script>
var token = null;

import axios from 'axios';
export default {
    methods: {
      showToken(){
        const auth = useAuthStore();
        const token = auth.getToken();
        return token;
      },
      prettyTime(sec) {
        let value = "";
        if (sec > 3600) {
          value += Math.floor(sec / 3600) + " H ";
          sec %= 3600;
        }
        if (sec > 60) {
          value += Math.floor(sec / 60) + " M ";
        }
        value += Math.floor(sec % 60) + " s";

        return value;
      },
    },
  };

</script>

<script setup>

  onUnmounted(() => {
    console.log('Component unmounted')
  })

  const state = reactive({
    groupspecs: [],
    selectedGroupId: null,
    specialties: [],
    selectedSpecialtyId: null,
    address : "",
    hospitals :[],
    selectedHospital: null,
  })

  onBeforeMount(async () => {
    const auth = useAuthStore();
    token = auth.getToken();
    if (token == null) {
      this.$router.push('/');
    }
    else{
      fetch('https://localhost:29001/SpecGroup', {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer '+token,
      },
      rejectUnauthorized: false,
     })
      .then(response => response.json()) // Parsing the response data as JSON
      .then(data => {
        state.groupspecs = data; // Assigning the data to your state
      })
      .catch((error) => console.error('Error:', error));
    }
  });

  watch(() => state.selectedGroupId, async (newValue) => {
    if (newValue) {
    try {
      const response = await fetch(`https://localhost:29001/Specialities?spec_group_id=${newValue}`, {
        method: 'GET', server : false,
        headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token,
        },
      });
      
      if (!response.ok) {
        throw new Error('Request failed');
      }

      const data = await response.json();
      state.specialties = data;
    } catch (error) {
      console.error('Error:', error);
    }
  } else {
    state.specialties = [];
  }
  });

    // gestion de la validation de l'API
    const handleValidate = async () => {
      // fonction de débug, à retirer
      //console.log("Address:", state.address)
      //console.log("Selected Specialty ID:", state.selectedSpecialtyId)

      if (state.address && state.selectedSpecialtyId) {
        try {
          const endpoint = `https://localhost:29001/hospitals?address=${state.address}&spec_id=${state.selectedSpecialtyId}`
          const { data } = await useFetch(endpoint, {
            method: 'GET',
            headers: {
              'Authorization': 'Bearer '+token,
            },
            rejectUnauthorized: false,
          })
          state.hospitals = data
          // fonction de débug, à retirer
          //console.log("API Response:", state.hospitals)
        } catch (error) {
          console.error("API Error:", error)
        }
      }
    };

  const { groupspecs, selectedGroupId, specialties, selectedSpecialtyId } = toRefs(state);
</script>

<template>
  <div v-if="state.selectedHospital"  class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white rounded-lg p-6">
      <p class="text-lg mb-4"> You have successfully reserved a bed at 
        {{ state.selectedHospital.hospital.name }} located at 
        {{ state.selectedHospital.hospital.address }}.
      </p>

      <p class="text-lg mb-4">If you have any questions or need further assistance, please don't hesitate to reach out to our staff.</p>
      <p class="text-lg mb-4">If you want to cancel your reservation please contact the hospital.</p>
      <!--
      <h3 class="text-2xl font-bold mb-4">You reserved at {{ state.selectedHospital.hospital.name }}</h3>
      <p class="text-lg mb-4">Distance: {{ prettyTime(state.selectedHospital.distance) }}</p>
      <p class="text-lg mb-6">Address: {{state.selectedHospital.hospital.address }}</p> -->
      <button @click="state.selectedHospital = null" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded-lg">Close</button>
    </div>
  </div>

  <div class="flex justify">
    <div class ="flex w-1/2 mb-2">
      <select v-model="selectedGroupId" class="w-1/2 py-2 px-2 mr-2" required>
      <option class="text-red-900 bg-red-50" value="" :selected="selectedGroupId === ''">Select a group spec</option>
      <option v-for="gs in groupspecs" :value="gs.id" :key="gs.id">
        {{ gs.name }}
      </option>
      </select>
      <select v-if="specialties && specialties.length > 0" v-model="selectedSpecialtyId" 
        class="w-1/2 py-2 px-2 mr-2" required>
        <option class="text-red-900 bg-red-50" value="">Select a specialty</option>
        <option v-for="specialty in specialties" :value="specialty.id" :key="specialty.id">
          {{ specialty.name }}
        </option>
      </select>
    </div>
    
    <div class ="flex w-1/2 mb-2">
        <input class=" flex-grow px-1 mr-2" type="text" v-model="state.address" placeholder="Enter your Address">
      
      <div  v-if="state.selectedSpecialtyId > 0 && state.address.length>5">
        <button @click="handleValidate" class="bg-green-500 hover:bg-green-400 text-white font-bold py-1 px-2 border-b-4 border-green-700 hover:border-green-500 rounded mr-2">Validate</button>
      </div>
    </div >
  </div>
  <div v-if ="state.hospitals != [] && state.hospitals.length > 0" class="flex flex-col items-center justify-center w-full text-left text-sm font-light overflow-hidden">
    <table class="w-2/3 text-left text-sm font-light ">
      <thead class="border-b bg-white font-medium dark:border-neutral-500 ">
        <tr>
        <td scope="col" class="px-6 py-4 w-1/10 text-center">Index</td>
        <td scope="col" class="px-6 py-4 w-1/4">Hospital Name</td>
        <td scope="col" class="px-6 py-4 w-1/10 text-center">Available Beds</td>
        <td scope="col" class="px-6 py-4 w-1/10 text-center">Time to go</td>
        <td scope="col" class="px-6 py-4 w-1/10"></td>
      </tr>
      </thead>
      <tbody>
        <tr v-for="(hdata,index) in state.hospitals" :key="index"
        class="border-b bg-neutral-100 dark:border-neutral-500 ">
          <td class="px-6 py-4 w-1/20 text-center">{{ index + 1 }}</td> 
          <td class="px-6 py-4 w-1/3">{{ hdata.hospital.name }}</td>
          <td class="px-6 py-4 w-1/10 text-center">{{ hdata.availableBed }}</td>
          <td class="px-6 py-4 w-1/10 text-center">{{ prettyTime(hdata.distance)}}</td>
          <td class="px-6 py-4 w-1/20"><button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded-lg" 
            @click="state.selectedHospital = hdata" >Select</button></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>

h2{
    margin-bottom: 20px;
    font-size: 36 px;
  }
p{
    margin: 20px 0;
  }
</style>