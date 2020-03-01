<template>
    <div id="app">
        <AdminHeader></AdminHeader>

        <router-view></router-view>

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