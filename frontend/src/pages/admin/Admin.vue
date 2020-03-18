<template>
    <div id="app">
        <AdminHeader></AdminHeader>
    <!-- <keep-alive> -->
        <router-view></router-view>
    <!-- </keep-alive> -->

    </div>
</template>

<script>
import AdminHeader from '@/components/AdminHeader.vue'
export default {
	name: 'admin',
	components: {
		AdminHeader
	}
}
</script>