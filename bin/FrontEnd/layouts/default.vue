<template>
    <div>
        <header class="shadow-sm bg-white">
            <nav class="container mx-auto p-4 flex justify-between">
                <NuxtLink to="/" class="font-bold">MedHead</NuxtLink>
            </nav>
        </header>
        <!-- Output the page content -->
        <div class="container mx-auto p-4"> 
            <slot />
        </div>
        <footer class="container mx-auto p-4 flex justify-between border-t-2">
            <ul class="flex gap-4">
                <li class="vertical-list"><NuxtLink to ="/">Home</NuxtLink></li>
                <li class="vertical-list"><NuxtLink to ="/about">About</NuxtLink></li>
                <li class="vertical-list"><NuxtLink to ="/contact">Contact</NuxtLink></li>
            </ul>
        </footer>
    </div>
</template>

<style scoped>
    .router-link-exact-active{
        color : #00499d;
    }
    .vertical-list
    {
        clear: left;
        display: inline;
        padding-right: 10px;
    }
</style>